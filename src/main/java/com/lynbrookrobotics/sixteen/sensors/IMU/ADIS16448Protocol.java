package com.lynbrookrobotics.sixteen.sensors.IMU;

import com.lynbrookrobotics.sixteen.sensors.ConstantBufferSPI;
import com.lynbrookrobotics.sixteen.sensors.Value3D;
import edu.wpi.first.wpilibj.SPI;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Implements the SPI protocol for the ADIS16448 IMU
 */
class ADIS16448Protocol {
    // TODO: rename to something more understandable
    // TODO: registers should be objects, with write/read on them
    private static class Registers {
        static final int RegSMPL_PRD = 0x36;
        static final int RegSENS_AVG = 0x38;
        static final int RegMSC_CTRL = 0x34;
        static final int RegPROD_ID = 0x56;
    }

    // TODO: capitalization, rename
    private static class Constants {
        static final double DegreePerSecondPerLSB = 1.0 / 25.0;
        static final double GPerLSB = 1.0 / 1200.0;
        static final double MilligaussPerLSB = 1.0 / 7.0;
    }

    // TODO: what is glob command? where did the name come from?
    private static final byte[] globCommand = {0x3E, 0};
    private ConstantBufferSPI spi;

    public ADIS16448Protocol() {
        spi = new ConstantBufferSPI(SPI.Port.kMXP, 26);
        spi.setClockRate(100000); // TODO: check if this is a random number
        spi.setMSBFirst();
        spi.setSampleDataOnFalling();
        spi.setClockActiveLow();
        spi.setChipSelectActiveLow();

        registerBuffer = ByteBuffer.allocateDirect(2);

        readRegister(Registers.RegPROD_ID); // TODO: check if dummy read is needed
        if (readRegister(Registers.RegPROD_ID) != 16448) {
            throw new IllegalStateException("The device in the MXP port is not an ADIS16448 IMU");
        }

        writeRegister(Registers.RegSMPL_PRD, 769); // TODO: Magic Number
        writeRegister(Registers.RegMSC_CTRL, 4); // TODO: Magic Number
        writeRegister(Registers.RegSENS_AVG, 1030); // TODO: Magic Number
    }

    /**
     * @return the current gyro, accel, and magneto data from the IMU
     */
    public IMUValue currentData() {
        byte[] outData = new byte[26];
        spi.transaction(globCommand, outData, 26);
        ByteBuffer buffer = ByteBuffer.wrap(outData); // TODO: just use byte buffer from the beginning

        Value3D gyro = new Value3D(
            buffer.getShort(4) * Constants.DegreePerSecondPerLSB,
            buffer.getShort(6) * Constants.DegreePerSecondPerLSB,
            buffer.getShort(8) * Constants.DegreePerSecondPerLSB
        );

        Value3D accel = new Value3D(
            buffer.getShort(10) * Constants.GPerLSB,
            buffer.getShort(12) * Constants.GPerLSB,
            buffer.getShort(14) * Constants.GPerLSB
        );

        Value3D mag = new Value3D(
            buffer.getShort(16) * Constants.MilligaussPerLSB,
            buffer.getShort(18) * Constants.MilligaussPerLSB,
            buffer.getShort(20) * Constants.MilligaussPerLSB
        );

        // TODO: kalman calculation?
        return new IMUValue(gyro, accel, mag);
    }

    // Utility communication code
    private ByteBuffer registerBuffer;

    private int readRegister(int register) {
        registerBuffer.order(ByteOrder.BIG_ENDIAN);

        registerBuffer.clear();
        registerBuffer.put(0, (byte) (register & 0x7f));
        registerBuffer.put(1, (byte) 0);

        spi.write(registerBuffer, 2);
        spi.read(false, registerBuffer, 2);

        return ((int) registerBuffer.getShort(0)) & 0xffff;
    }

    private void writeRegister(int register, int value) {
        registerBuffer.clear();
        registerBuffer.put((byte) (0x80 | register));
        registerBuffer.put((byte) value);
        spi.write(registerBuffer, 2);

        registerBuffer.clear();
        registerBuffer.put((byte) (0x81 | register));
        registerBuffer.put((byte) (value >> 8));
        spi.write(registerBuffer, 2);
    }
}
