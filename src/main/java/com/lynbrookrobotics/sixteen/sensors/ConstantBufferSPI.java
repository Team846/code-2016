package com.lynbrookrobotics.sixteen.sensors;

import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.hal.SPIJNI;

import java.nio.ByteBuffer;

public class ConstantBufferSPI extends SPI {
    private byte port;

    private ByteBuffer sendBuffer;
    private ByteBuffer receiveBuffer;

    /**
     * Constructor
     *
     * @param port the physical SPI port
     */
    public ConstantBufferSPI(Port port, int size) {
        super(port);

        this.port = (byte) port.getValue();
        this.sendBuffer = ByteBuffer.allocateDirect(size);
        this.receiveBuffer = ByteBuffer.allocateDirect(size);
    }

    @Override
    public int transaction(byte[] dataToSend, byte[] dataReceived, int size) {
        sendBuffer.clear();
        receiveBuffer.clear();

        sendBuffer.put(dataToSend); // put the send data into dataToSend
        int resultCode = SPIJNI.spiTransaction(port, sendBuffer, receiveBuffer, (byte) size);
        receiveBuffer.get(dataReceived); // pull the result data into dataRecieved

        return resultCode;
    }
}
