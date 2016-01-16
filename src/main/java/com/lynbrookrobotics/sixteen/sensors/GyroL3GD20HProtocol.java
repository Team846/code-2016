package com.lynbrookrobotics.sixteen.sensors;

import edu.wpi.first.wpilibj.SPI;

import java.util.Arrays;

/**
 * Communications for the L3GD20H gyro by Adafruit
 * @see <a href="http://www.adafruit.com/datasheets/L3GD20H.pdf">http://www.adafruit.com/datasheets/L3GD20H.pdf</a>
 */
class GyroL3GD20HProtocol {
    private SPI gyro;

    private final byte L3GD20_REGISTER_OUT_X_L = 0x28; //This is a digital gyro. These are registers to read and write data
    private final byte L3GD20_REGISTER_CTRL_REG1 = 0x20;
    private final byte L3GD20_REGISTER_CTRL_REG4 = 0x23;
    private final byte L3GD20_REGISTER_CTRL_REG5 = 0x24;
    private final byte L3GD20_REGISTER_FIFO_CTRL = 0x2E;

    private final int SIZE_GYRO_QUEU = 32;

    private final int SETTING_BYTES_SENT_RECEIVED = 2;
    private final int READING_BYTES_SENT_RECEIVED = 7; //the # of bytes sent and received while reading data for the 3 axis

    private final int BYPASS_MODE = 0;
    private final int STREAM_MODE = 1;
    private final int mode = STREAM_MODE;//If the gyro does not have an imbedded queue or stack, use BYPASSMODE

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

    private double xFIFOValues[] = new double[SIZE_GYRO_QUEU];//gyro queue only stores 32 values
    private double yFIFOValues[] = new double[SIZE_GYRO_QUEU];
    private double zFIFOValues[] = new double[SIZE_GYRO_QUEU];

    private static GyroL3GD20HProtocol instance = null;

    public GyroL3GD20HProtocol(){ //initialization code, ensures there is only one gyro communication object
        setupGyroCommunciation(mode);
    }

    /**
     * This method sets up the settings and SPI connection.
     * It controls the following setting: sensitivity,
     * @param mode This can be BYPASS_MODE or STREAM_MODE. BYPASS_MODE doesn't use  the gyro's FIFO, STREAM_MODE does use the gyro's FIFO
     */
    private void setupGyroCommunciation(int mode) {
        gyro = new SPI(SPI.Port.kOnboardCS3);
        gyro.setClockRate(2000000);

        gyro.setSampleDataOnFalling(); // Reversed due to wpi bug. Should be gyro.setSampleDataOnRising();

        gyro.setMSBFirst(); //set most significant bit first (see pg. 29)

        gyro.setChipSelectActiveLow();
        gyro.setClockActiveLow();

        byte[] out = new byte[SETTING_BYTES_SENT_RECEIVED];
        out[0] = (byte) (L3GD20_REGISTER_CTRL_REG1);
        out[1] = (byte) (0b11001111);//byte to enable axis and misc. setting
        byte[] in = new byte[SETTING_BYTES_SENT_RECEIVED];
        gyro.transaction(out, in, SETTING_BYTES_SENT_RECEIVED);

        out = new byte[SETTING_BYTES_SENT_RECEIVED];
        out[0] = (byte) (L3GD20_REGISTER_CTRL_REG4);
        out[1] = (byte) (0b00110000);//set sensitivity
        in = new byte[SETTING_BYTES_SENT_RECEIVED];
        gyro.transaction(out, in, SETTING_BYTES_SENT_RECEIVED);

        if (mode == STREAM_MODE) {
            out[0] = (byte) (L3GD20_REGISTER_CTRL_REG5);
            out[1] = (byte) 0b01000000;//byte to enable FIFO
            gyro.transaction(out, in, SETTING_BYTES_SENT_RECEIVED);

            out[0] = (byte) (L3GD20_REGISTER_FIFO_CTRL);
            out[1] = (byte) (0b01000000);//byte that sets to stream mode
            gyro.transaction(out, in, SETTING_BYTES_SENT_RECEIVED);
        }

    }

    /**
     * @param calibrate Whether or not the gyro is currently calibrating
     * @param streamOrBypass    Whether or not to use the Queue (STREAM_MODE) or not (BYPASS_MODE)
     * @param driftX    The calculated drift of gyro x axis values. If not callibrating, input 0
     * @param driftY    The calculated drift of gyro y axis values. If not callibrating, input 0
     * @param driftZ    The calculated drift of gyro z axis values. If not callibrating, input 0
     */
    public void updateGyro(boolean calibrate, int streamOrBypass, double driftX, double driftY, double driftZ) {
        if(streamOrBypass == STREAM_MODE) {
            if (isFIFOFull() == true) {
                int FIFOCount = 0;

                for (int i = 0; i < xFIFOValues.length; i++) {
                    Arrays.fill(outputToSlave, (byte) 0x00);

                    outputToSlave[0] = setByte(outputToSlave[0], L3GD20_REGISTER_OUT_X_L, (byte) 0b10000000, (byte) 0b01000000);//do not change

                    gyro.transaction(outputToSlave, inputFromSlave, READING_BYTES_SENT_RECEIVED);

                    //Data for an axis is expressed with 2 bytes
                    //Index is not 0 because the first byte is before the byte for register selection was sent
                    //Conversion factor converts the raw gyro value into degrees/second
                    xFIFO = ((inputFromSlave[1] & 0xFF) | (inputFromSlave[2] << 8)) * conversionFactor;
                    yFIFO = ((inputFromSlave[3] & 0xFF) | (inputFromSlave[4] << 8)) * conversionFactor;
                    zFIFO = ((inputFromSlave[5] & 0xFF) | (inputFromSlave[6] << 8)) * conversionFactor;

                    if (!calibrate){//if not currently calibrating
                        xFIFOValues[FIFOCount] = xFIFO - driftX;
                        yFIFOValues[FIFOCount] = yFIFO - driftY;
                        zFIFOValues[FIFOCount] = zFIFO - driftZ;

                    } else {//if currently calibrating
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
        }

        if(mode == BYPASS_MODE) {
            Arrays.fill(outputToSlave, (byte) 0b00000000);

            outputToSlave[0] = L3GD20_REGISTER_OUT_X_L | (byte) 0x80 | (byte) 0x40;
            gyro.transaction(outputToSlave, inputFromSlave, READING_BYTES_SENT_RECEIVED);

            xVel = ((inputFromSlave[1] & 0xFF) | (inputFromSlave[2] << 8)) * conversionFactor;
            yVel = ((inputFromSlave[3] & 0xFF) | (inputFromSlave[4] << 8)) * conversionFactor;
            zVel = ((inputFromSlave[5] & 0xFF) | (inputFromSlave[6] << 8)) * conversionFactor;

            if (!calibrate) {//if not currently calibrating
                xVel = xVel - driftX;
                yVel = yVel - driftY;
                zVel = zVel - driftZ;
            }

        }
    }

    /**
     * This method checks the gyro register for the FIFO status, and returns whether the queue is full
     * @returns: returns whether the FIFO on the gyro is full or not
     */
    private boolean isFIFOFull() {
        byte[] outputToSlave = new byte[SETTING_BYTES_SENT_RECEIVED];//from RoboRio to slave (gyro)
        outputToSlave[0] = setByte(L3GD20_REGISTER_FIFO_CTRL, (byte) 0b10000000); // set bit 0 (READ bit) to 1 (pg. 31)
        byte[] inputFromSlave = new byte[SETTING_BYTES_SENT_RECEIVED];

        gyro.transaction(outputToSlave, inputFromSlave, READING_BYTES_SENT_RECEIVED);//read from FIFO control register


        if (((inputFromSlave[1] >> 6) & 0b1) == 0b1){//if second bit from the left is 1 then it is full
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

    /**
     * This method takes a byte and a series of modifier bytes. The modifier bytes are ored with the the toSet byte
     * @param toSet The initial byte to be modified
     * @param modifiers The bytes that modify the toSet byte
     * @return The modified byte is returned
     */
    private byte setByte(byte toSet, byte... modifiers) {
        toSet = modifiers[0];

        for (int i = 0; i < modifiers.length; i++){//for each loop
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
