package com.lynbrookrobotics.sixteen.sensors;

import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.hal.SPIJNI;

import java.nio.ByteBuffer;

public class ConstantBufferSPI extends SPI {
    private byte port;
    private byte size;

    private ByteBuffer sendBuffer;
    private ByteBuffer receiveBuffer;

    /**
     * Constructor
     *
     * @param port the physical SPI port
     */
    public ConstantBufferSPI(Port port, int size) {
        super(port);

        this.size = (byte) size;
        this.port = (byte) port.getValue();
        this.sendBuffer = ByteBuffer.allocateDirect(size);
        this.receiveBuffer = ByteBuffer.allocateDirect(size);
    }

    @Override
    public int transaction(byte[] dataToSend, byte[] dataReceived, int size) {
        sendBuffer.put(dataToSend); // put the send data into dataToSend
        int resultCode = SPIJNI.spiTransaction(port, sendBuffer, receiveBuffer, this.size);
        receiveBuffer.get(dataReceived); // pull the result data into dataRecieved

        return resultCode;
    }
}
