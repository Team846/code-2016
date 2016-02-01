package com.lynbrookrobotics.sixteen.sensors.imu;

import com.lynbrookrobotics.sixteen.sensors.ConstantBufferSPI;
import com.lynbrookrobotics.sixteen.sensors.Value3D;
import edu.wpi.first.wpilibj.SPI;

import java.nio.ByteBuffer;

/**
 * Implements the SPI protocol for the ADIS16448 IMU
 */
class ADIS16448Protocol {
    private static class Registers {
        static final IMURegister SMPL_PRD = new IMURegister(0x36);
        static final IMURegister SENS_AVG = new IMURegister(0x38);
        static final IMURegister MSC_CTRL = new IMURegister(0x34);
        static final IMURegister PROD_ID = new IMURegister(0x56);
    }

    private static final byte X_GYRO_REG = 0x04;
    private static final byte Y_GYRO_REG = 0x06;
    private static final byte Z_GYRO_REG = 0x08;

    private static class Constants {
        // TODO: test with 0.01 as per http://www.analog.com/media/en/technical-documentation/data-sheets/ADIS16448.pdf#page=23
        static final double DegreePerSecondPerLSB = 1.0 / 25.0;
        static final double GPerLSB = 1.0 / 1200.0;
        static final double MilligaussPerLSB = 1.0 / 7.0;
    }

    // TODO: what is the global command doing?
    private static final byte[] globalCommand = {0x08, 0};
    private ConstantBufferSPI spi;

    public ADIS16448Protocol() {
        spi = new ConstantBufferSPI(SPI.Port.kMXP, 2);
        spi.setClockRate(2000000); // TODO: check if this is a random number
        spi.setMSBFirst();
        spi.setSampleDataOnFalling();
        spi.setClockActiveLow();
        spi.setChipSelectActiveLow();

        Registers.PROD_ID.read(spi); // TODO: check if dummy read is needed
        if (Registers.PROD_ID.read(spi) != 16448) {
            throw new IllegalStateException("The device in the MXP port is not an ADIS16448 IMU");
        }

        Registers.SMPL_PRD.write(1/*0b1000000001*/, spi); // TODO: Magic Number
        Registers.MSC_CTRL.write(4, spi); // TODO: Magic Number
        Registers.SENS_AVG.write(1030, spi); // TODO: Magic Number
    }

    private static final byte[] X_GYRO_OUT = new byte[]{ X_GYRO_REG, 0 };
    private static final byte[] Y_GYRO_OUT = new byte[]{ Y_GYRO_REG, 0 };
    private static final byte[] Z_GYRO_OUT = new byte[]{ Z_GYRO_REG, 0 };

    private short readGyroRegister(byte[] outData) {
        byte[] gyroData = new byte[2];
        spi.write(outData, 2);
//        spi.transaction(outData, gyroData, 2);
//        spi.transaction(outData, gyroData, 2);
        spi.read(false, gyroData, 2);
        ByteBuffer gyroBuffer = ByteBuffer.wrap(gyroData);

        return gyroBuffer.getShort();
    }

    /**
     * @return the current gyro, accel, and magneto data from the IMU
     */
    public IMUValue currentData() {
        Value3D gyro = new Value3D(
            readGyroRegister(X_GYRO_OUT),
            readGyroRegister(Y_GYRO_OUT),
            readGyroRegister(Z_GYRO_OUT)
        ).times(Constants.DegreePerSecondPerLSB);

        // TODO: kalman calculation?
        return new IMUValue(gyro, null, null);
    }
}
