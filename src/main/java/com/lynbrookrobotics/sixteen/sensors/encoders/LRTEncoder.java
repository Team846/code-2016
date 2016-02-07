package com.lynbrookrobotics.sixteen.sensors.encoders;
import edu.wpi.first.wpilibj.CANTalon;

public class LRTEncoder  {
    CANTalon talon;
    boolean reversed;
    int pulsePerRevolution = 256;
    //int pulsePerRevolution = 256;

    /**
     *
     * @param talon
     * @param reversed
     */
    public LRTEncoder(CANTalon talon, boolean reversed) {
        if (talon == null)
            throw new IllegalArgumentException("[ERROR] Talon must be already constructed");
        this.reversed = reversed;
        this.talon = talon;

        this.talon.configEncoderCodesPerRev(pulsePerRevolution);
        //talon.setFeedbackDevice(FeedbackDevice.QuadEncoder);
        //talon.setStatusFrameRateMs(StatusFrameRate.QuadEncoder, updatePeriod);
        /*//encoderInterface = new Encoder(1, 1, false);
        this.encoderInterface = encoderInterface;

        //There are 256 ticks per revolution. Then we multiply 360 degrees per revolution
        this.encoderInterface.setDistancePerPulse((1 / (double) pulsePerRevolution) * 360D);*/
    }

    public double getSpeed(){
        if(reversed){
            return - talon.getEncVelocity();
        }

        return talon.getEncVelocity();
        /*// If The encoderInterface is stopped, or in the case we don't get a value from
        // the encoderInterface and rate is not a number, we return 0
        if ( encoderInterface.getStopped() || Double.isNaN(rate) ){
            return 0D;
        } else {
            return rate;
        }*/
    }

    public double getPosition(){
        if(reversed){
            return - talon.getEncPosition();
        }

        return talon.getEncPosition();
            }
}
