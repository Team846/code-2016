package com.lynbrookrobotics.sixteen.sensors.gyro;

import com.lynbrookrobotics.sixteen.sensors.ConstantBufferSPI;
import com.lynbrookrobotics.sixteen.sensors.Value3D;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Communications for the L3GD20H gyro by Adafruit
 *
 * @see <a href="http://www.adafruit.com/datasheets/L3GD20H.pdf">http://www.adafruit.com/datasheets/L3GD20H.pdf</a>
 */
class GyroL3GD20HProtocol {
  public enum CollectionMode {
    STREAM,
    BYPASS
  }

  private ConstantBufferSPI gyro;
  private CollectionMode mode;

  // This is a digital gyro. These are registers to read and write data
  private final byte L3GD20_REGISTER_OUT_X_L = 0x28;
  private final byte L3GD20_REGISTER_CTRL_REG1 = 0x20;
  private final byte L3GD20_REGISTER_CTRL_REG4 = 0x23;
  private final byte L3GD20_REGISTER_CTRL_REG5 = 0x24;
  private final byte L3GD20_REGISTER_FIFO_CTRL = 0x2E;

  private static final int SIZE_GYRO_QUEUE = 16;

  private final int SETTING_BYTES_SENT_RECEIVED = 2;

  // the # of bytes sent and received while reading data for the 3 axis
  private final int READING_BYTES_SENT_RECEIVED = 7;

  private final double CONVERSION_FACTOR = 0.0175;

  private byte[] inputFromSlave = new byte[7];
  private byte[] outputToSlave = new byte[7];

  // initialization code, ensures there is only one gyro communication object
  public GyroL3GD20HProtocol(CollectionMode mode) {
    this.mode = mode;
    setupGyroCommunciation();
  }

  /**
   * This method sets up the settings and SPI connection.
   * It controls the following setting: sensitivity
   */
  private void setupGyroCommunciation() {
    gyro = new ConstantBufferSPI(ConstantBufferSPI.Port.kOnboardCS3, 7);
    gyro.setClockRate(2000000);

    // Reversed due to wpi bug. Should be gyro.setSampleDataOnRising();
    gyro.setSampleDataOnFalling();

    gyro.setMSBFirst(); //set most significant bit first (see pg. 29)

    gyro.setChipSelectActiveLow();
    gyro.setClockActiveLow();

    byte[] out = new byte[SETTING_BYTES_SENT_RECEIVED];
    out[0] = L3GD20_REGISTER_CTRL_REG1;
    out[1] = (byte) (0b11001111);//byte to enable axis and misc. setting, set ODR to 800
    byte[] in = new byte[SETTING_BYTES_SENT_RECEIVED];
    gyro.transaction(out, in, SETTING_BYTES_SENT_RECEIVED);

    out = new byte[SETTING_BYTES_SENT_RECEIVED];
    out[0] = L3GD20_REGISTER_CTRL_REG4;
    out[1] = (byte) (0b00010000);//set sensitivity
    in = new byte[SETTING_BYTES_SENT_RECEIVED];
    gyro.transaction(out, in, SETTING_BYTES_SENT_RECEIVED);

    if (mode == CollectionMode.STREAM) {
      out[0] = L3GD20_REGISTER_CTRL_REG5;
      out[1] = (byte) 0b01100000;//byte to enable FIFO, and enable the FIFO to stop at 16 bits
      gyro.transaction(out, in, SETTING_BYTES_SENT_RECEIVED);

      out[0] = L3GD20_REGISTER_FIFO_CTRL;
      out[1] = (byte) (0b01000100);//byte that sets to stream mode, and set queue to 4 units
      gyro.transaction(out, in, SETTING_BYTES_SENT_RECEIVED);
    }

  }

  /**
   * Gets the current velocity measured by the gyro.
   */
  public Value3D getGyroValue() {
    if (mode == CollectionMode.STREAM) {
      if (isFIFOFull()) {
        Queue<Value3D> gyroValues = new LinkedList<>();
        for (int i = 0; i < SIZE_GYRO_QUEUE; i++) {
          gyroValues.add(getOneValue());
        }

        Value3D sum = gyroValues.stream().reduce(
            new Value3D(0, 0, 0), // inital value of acc
            Value3D::plus // (acc, cur) -> acc.plus(cur)
        );

        return sum.times(1D / SIZE_GYRO_QUEUE);
      } else {
        return getOneValue();
      }
    } else {
      return getOneValue();
    }
  }

  public Value3D getOneValue() {
    Arrays.fill(outputToSlave, (byte) 0b00000000);

    outputToSlave[0] = combineBytes(L3GD20_REGISTER_OUT_X_L, (byte) 0x80, (byte) 0x40);
    gyro.transaction(outputToSlave, inputFromSlave, READING_BYTES_SENT_RECEIVED);

    return new Value3D(
        ((inputFromSlave[1] & 0xFF) | (inputFromSlave[2] << 8)) * CONVERSION_FACTOR,
        ((inputFromSlave[3] & 0xFF) | (inputFromSlave[4] << 8)) * CONVERSION_FACTOR,
        ((inputFromSlave[5] & 0xFF) | (inputFromSlave[6] << 8)) * CONVERSION_FACTOR
    );
  }

  /**
   * Checks the gyro register for the FIFO status.
   *
   * @return whether the FIFO on the gyro is full or not
   */
  private boolean isFIFOFull() {
    byte[] outputToSlave = new byte[SETTING_BYTES_SENT_RECEIVED];//from RoboRio to slave (gyro)

    // set bit 0 (READ bit) to 1 (pg. 31)
    outputToSlave[0] = combineBytes(L3GD20_REGISTER_FIFO_CTRL, (byte) 0b10000000);
    byte[] inputFromSlave = new byte[SETTING_BYTES_SENT_RECEIVED];

    // read from FIFO control register
    gyro.transaction(outputToSlave, inputFromSlave, READING_BYTES_SENT_RECEIVED);

    return ((inputFromSlave[1] >> 6) & 0b1) == 0b1;
  }

  /**
   * This method takes a byte and a series of modifier bytes.
   *
   * @param toSet     The initial byte to be modified
   * @param modifiers The bytes that modify the toSet byte
   * @return The modified byte is returned
   */
  private byte combineBytes(byte toSet, byte... modifiers) {
    for (byte modifier : modifiers) {
      toSet = (byte) (toSet | modifier);
    }

    return toSet;
  }
}
