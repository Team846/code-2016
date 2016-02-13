package com.lynbrookrobotics.sixteen.sensors.imu;

import com.lynbrookrobotics.sixteen.CoreRobot;
import com.lynbrookrobotics.sixteen.config.RobotConstants;
import com.lynbrookrobotics.sixteen.sensors.ConstantBufferSPI;
import com.lynbrookrobotics.sixteen.sensors.Value3D;
import com.lynbrookrobotics.sixteen.sensors.imu.IMURegister;

import edu.wpi.first.wpilibj.SPI;

import java.nio.ByteBuffer;

/**
 * Implements the SPI protocol for the ADIS16448 IMU.
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

    Registers.SMPL_PRD.write(1, spi); // TODO: Magic Number
    Registers.MSC_CTRL.write(4, spi); // TODO: Magic Number
    Registers.SENS_AVG.write(0b10000000000, spi); // TODO: Magic Number
  }

  private static final ByteBuffer globBuffer = ByteBuffer.allocateDirect(26);
  static {
    globBuffer.put((byte) 0x3E);
    globBuffer.put((byte) 0);
  }

  private final ByteBuffer outBuffer = ByteBuffer.allocateDirect(26);

  /**
   * Gets the current gyro, accel, and magneto data from the IMU.
   */
  public IMUValue currentData() {
    Value3D gyro = RobotConstants.time(() -> {
      outBuffer.clear();
      spi.transaction(globBuffer, outBuffer, 26);

      return new Value3D(
          outBuffer.getShort(4) * Constants.DegreePerSecondPerLSB,
          outBuffer.getShort(6) * Constants.DegreePerSecondPerLSB,
          outBuffer.getShort(8) * Constants.DegreePerSecondPerLSB
      );
    }, "gyro-all-values");

    return new IMUValue(gyro, null, null);
  }
}
