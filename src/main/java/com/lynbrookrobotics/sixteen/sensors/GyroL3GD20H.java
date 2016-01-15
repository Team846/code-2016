package com.lynbrookrobotics.sixteen.sensors;

import edu.wpi.first.wpilibj.SPI;

import java.util.Arrays;


//* Because the class LRTGyro should be able to use this class regardless of what gyro we use, All future digital gyro communication classes must have the following methods:

//        * Make sure that all state and mode variables that are ued in both LRTGyro and LRTGyroCommunication are EXACTLY THE SAME

/**
 * Communications for the L3GD20H gyro by Adafruit
 * @see <a href="http://www.adafruit.com/datasheets/L3GD20H.pdf">http://www.adafruit.com/datasheets/L3GD20H.pdf</a>
 */
public class GyroL3GD20H {
    private SPI gyro;

    private final byte L3GD20_REGISTER_OUT_X_L = 0x28; //  * This is a digital gyro. These are registers to read and write data
    private final byte L3GD20_REGISTER_CTRL_REG1 = 0x20;
    private final byte L3GD20_REGISTER_CTRL_REG4 = 0x23;
    private final byte L3GD20_REGISTER_CTRL_REG5 = 0x24;
    private final byte L3GD20_REGISTER_FIFO_CTRL = 0x2E;
    private final byte L3GD20_REGISTER_FIFO_SRC = 0x2F;

    private final int BYPASSMODE = 0;
    private final int STREAMMODE = 1;
    private final int mode = STREAMMODE;//If the gyro does not have an imbedded queue or stack, use BYPASSMODE

    private final double conversionFactor = 0.00875F;

    public static final boolean CALIBRATE = true;
    public static final boolean DONT_CALIBRATE = false;


    private  byte[] inputFromSlave = new byte[7];
    private  byte[] outputToSlave = new byte[7];

    private double xFIFO = 0;
    private double xVel = 0;

    private double yVel = 0;
    private double yFIFO = 0;

    private double zVel = 0;
    private double zFIFO = 0;

    private double xFIFOValues[] = new double[32];//gyro queue only stores 32 values
    private double yFIFOValues[] = new double[32];
    private double zFIFOValues[] = new double[32];

    private static GyroL3GD20H instance = null;

    private GyroL3GD20H() //initialization code, ensures there is only one gyro communication object
    {
        setupGyroCommunciation(mode);
    }

    public static GyroL3GD20H getInstance() {
        if (instance == null)//ensures that there is only one gyro object
        {
            instance = new GyroL3GD20H();
        }

        return instance;
    }

    private void setupGyroCommunciation(int mode) {
        gyro = new SPI(SPI.Port.kOnboardCS3);
        gyro.setClockRate(2000000);
//      gyro.setSampleDataOnRising();
        gyro.setSampleDataOnFalling(); // Reversed

        gyro.setMSBFirst(); //set most significant bit first (see pg. 29)


        gyro.setChipSelectActiveLow();
        gyro.setClockActiveLow();

        byte[] out = new byte[2];
        out[0] = (byte) (L3GD20_REGISTER_CTRL_REG1);
        out[1] = (byte) (0b11001111);//byte to enable axis and misc. setting
        byte[] in = new byte[2];
        gyro.transaction(out, in, 2);

        out = new byte[2];
        out[0] = (byte) (L3GD20_REGISTER_CTRL_REG4);
        out[1] = (byte) (0b00110000);//set sensitivity
        in = new byte[2];
        gyro.transaction(out, in, 2);

        if (mode == STREAMMODE) {
            out[0] = (byte) (L3GD20_REGISTER_CTRL_REG5);
            out[1] = (byte) 0b01000000;//byte to enable FIFO
            gyro.transaction(out, in, 2);

            out[0] = (byte) (L3GD20_REGISTER_FIFO_CTRL);
            out[1] = (byte) (0b01000000);//byte that sets to stream mode
            gyro.transaction(out, in, 2);
        }

    }

    public void updateGyro(boolean calibrate, int streamOrBypass, double driftX, double driftY, double driftZ) {
        switch (streamOrBypass) {
            case STREAMMODE:
                if (isFIFOFull() == true) {
                    int FIFOCount = 0;

                    for (int i = 0; i < xFIFOValues.length; i++) {
                        Arrays.fill(outputToSlave, (byte) 0x00);

                        outputToSlave[0] = setByte(outputToSlave[0], L3GD20_REGISTER_OUT_X_L, (byte) 0b10000000, (byte) 0b01000000);//do not change

                        gyro.transaction(outputToSlave, inputFromSlave, 7);

                        xFIFO = ((inputFromSlave[1] & 0xFF) | (inputFromSlave[2] << 8)) * conversionFactor;//Data for an axis is expressed with 2 bytes
                        yFIFO = ((inputFromSlave[3] & 0xFF) | (inputFromSlave[4] << 8)) * conversionFactor;//the conversion factor converts the raw gyro value into degrees/second
                        zFIFO = ((inputFromSlave[5] & 0xFF) | (inputFromSlave[6] << 8)) * conversionFactor;//Not index 0 because at this time register selection byte is being sent

                        if (!calibrate)//if not currently calibrating
                        {
                            xFIFOValues[FIFOCount] = xFIFO - driftX;
                            yFIFOValues[FIFOCount] = yFIFO - driftY;
                            zFIFOValues[FIFOCount] = zFIFO - driftZ;

                        } else //if currently calibrating
                        {
                            xFIFOValues[FIFOCount] = xFIFO;
                            yFIFOValues[FIFOCount] = yFIFO;
                            zFIFOValues[FIFOCount] = zFIFO;
                        }

                        FIFOCount++;

                    }

                    xVel = average(xFIFOValues);
                    yVel = average(yFIFOValues);
                    zVel = average(zFIFOValues);

                }

                break;
            case BYPASSMODE:
                Arrays.fill(outputToSlave, (byte) 0b00000000);

                outputToSlave[0] = L3GD20_REGISTER_OUT_X_L | (byte) 0x80 | (byte) 0x40;
                gyro.transaction(outputToSlave, inputFromSlave, 7);

                xVel = ((inputFromSlave[1] & 0xFF) | (inputFromSlave[2] << 8)) * conversionFactor;
                yVel = ((inputFromSlave[3] & 0xFF) | (inputFromSlave[4] << 8)) * conversionFactor;
                zVel = ((inputFromSlave[5] & 0xFF) | (inputFromSlave[6] << 8)) * conversionFactor;

                if (!calibrate){//if not currently calibrating
                    xVel = xVel - driftX;
                    yVel = yVel - driftY;
                    zVel = zVel - driftZ;
                }

                break;
        }

    }

    private boolean isFIFOFull()
    {
        byte[] outputToSlave = new byte[2];//from RoboRio to slave (gyro)
        outputToSlave[0] = setByte(L3GD20_REGISTER_FIFO_CTRL, (byte) 0b10000000); // set bit 0 (READ bit) to 1 (pg. 31)
        byte[] inputFromSlave = new byte[2];

        gyro.transaction(outputToSlave, inputFromSlave, 2);//read from FIFO control register


        if (((inputFromSlave[1] >> 6) & 0b1) == 0b1)//if second bit from the left is 1 then it is full
        {
            return true;
        }

        return false;
    }

    //All methods below are abstraction methods
    public double getXVel() {
        return xVel;
    }

    public double getYVel() {
        return yVel;
    }

    public double getZVel() {
        return zVel;
    }

    private byte setByte(byte toSet, byte... modifiers) {
        toSet = modifiers[0];

        for (int i = 0; i < modifiers.length; i++)//for each loop
        {
            toSet = (byte) (toSet | modifiers[i]);
        }

        return toSet;
    }


    private double average(double[] values) {
        double sum = 0;

        for (int i = 0; i < values.length; i++) {
            sum += values[i];
        }
        return sum / values.length;
    }

}
