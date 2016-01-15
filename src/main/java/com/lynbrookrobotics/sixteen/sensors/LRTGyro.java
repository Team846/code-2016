import com.lynbrookrobotics.sixteen.sensors.GyroL3GD20H;

import java.lang.System;


public class LRTGyro{

    private final int BYPASSMODE = 0; //Make sure that this is the same as in gyroCommunications class
    private final int STREAMMODE = 1;//Make sure that this is the same as in gyroCommunications class
    private final int mode = STREAMMODE;

    public static final boolean CALIBRATING = true; //Make sure that this is the same as in gyroCommunications class
    public static final boolean NOT_CALIBRATING = false; //Make sure that this is the same as in gyroCommunications class

    private boolean calibrated = false;

    double xVel = 0;
    double yVel = 0;
    double zVel = 0;

    double previousXVel = 0;
    double previousYVel = 0;
    double previousZVel = 0;

    double xAngle = 0;
    double yAngle = 0;
    double zAngle = 0;

    double driftX = 0;
    double driftY = 0;
    double driftZ = 0;

    double xCalibValues[] = new double[100];
    double yCalibValues[] = new double[100];//calib only uses last 100 values to make sure that it doesn't use values when robot is moving
    double zCalibValues[] = new double[100];

    long previousTime = System.currentTimeMillis();
    long timePassed = previousTime;

    long calibCount = 0;

    boolean justDisabled = true;
    boolean justEnabled = true;

    private GyroL3GD20H gyroCom = null;//gyroCom is short for gyro Communications

    public LRTGyro(){
        gyroCom = new GyroL3GD20H();
    }

    public void update() {

        if (RobotState.Instance().GameMode() == GameState.DISABLED) //TODO: Change to whatever we're using to check game state
        {

            if (!calibrated) {
                gyroCom.updateGyro(CALIBRATING, mode, 0, 0, 0);//Are calibrating
                calibCount++;

                xVel = gyroCom.getXVel();
                yVel = gyroCom.getYVel();
                zVel = gyroCom.getZVel();

                xCalibValues[(int) (calibCount % xCalibValues.length)] = xVel;
                yCalibValues[(int) (calibCount % yCalibValues.length)] = yVel;
                zCalibValues[(int) (calibCount % zCalibValues.length)] = zVel;

                driftX = getDrift(xCalibValues);
                driftY = getDrift(yCalibValues);
                driftZ = getDrift(zCalibValues);

            } else{ //if already calibrated
                gyroCom.updateGyro(NOT_CALIBRATING, mode, driftX, driftY, driftZ);

                previousXVel = xVel;
                previousYVel = yVel;
                previousZVel = zVel;

                xVel = gyroCom.getXVel();
                yVel = gyroCom.getYVel();
                zVel = gyroCom.getZVel();

                timePassed = getTimePassed();

                xAngle = trapaziodalIntegration(xAngle, xVel, previousXVel);
                yAngle = trapaziodalIntegration(xAngle, yVel, previousYVel);
                zAngle = trapaziodalIntegration(zAngle, zVel, previousZVel);
            }
        }

        if (RobotState.Instance().GameMode() == GameState.TELEOPERATED || RobotState.Instance().GameMode() == GameState.AUTONOMOUS) {
            if (justEnabled) {
                resetTimer();//ensures that timePassed is accurate
                justEnabled = false;
                justDisabled = true;
                calibrated = true;
            }

            gyroCom.updateGyro(NOT_CALIBRATING, mode, driftX, driftY, driftZ);//all returned values are calibrated

            previousXVel = xVel;
            previousYVel = yVel;
            previousZVel = zVel;

            xVel = gyroCom.getXVel();
            yVel = gyroCom.getYVel();
            zVel = gyroCom.getZVel();

            timePassed = getTimePassed();

            xAngle = trapaziodalIntegration(xAngle, xVel, previousXVel);
            yAngle = trapaziodalIntegration(xAngle, yVel, previousYVel);
            zAngle = trapaziodalIntegration(zAngle, zVel, previousZVel);
        }

    }

    private double getDrift(double[] calibValues)//called after updateGyro()
    {
        double sum = 0;

        if (calibCount < calibValues.length)//if calibrating for less than 2 seconds
        {
            for (int i = 0; i < calibCount; i++) {
                sum += calibValues[i];
            }

            return sum / calibCount;
        }

        for (int i = 0; i < calibValues.length; i++)//If there are least 100 calibration values, AKA more than 2 seconds
        {
            sum += calibValues[i];
        }

        return sum / calibValues.length;
    }

    public double getXAngle() {
        double angleToReturn = 0;

        if (xAngle > 0) {
            angleToReturn = xAngle - (Math.floor(xAngle / 360.0) * 360.0);//brings the angle to within 0 and 360
        } else {
            angleToReturn = xAngle - (Math.ceil(xAngle / 360.0) * 360.0);//brings the angle to within 0 and -360
        }

        if (angleToReturn < -180) {
            return angleToReturn + 360.0;//brings the angle to within 0 and 180
        }
        if (angleToReturn > 180) {
            return angleToReturn - 360.0;//brings the angle to within 0 and 180
        }

        return angleToReturn;
    }

    public double getYAngle() {
        double angleToReturn = 0;

        if (yAngle > 0) {
            angleToReturn = yAngle - (Math.floor(yAngle / 360.0) * 360.0);
        } else {
            angleToReturn = yAngle - (Math.ceil(yAngle / 360.0) * 360.0);
        }

        if (angleToReturn < -180) {
            return angleToReturn + 360.0;
        }
        if (angleToReturn > 180) {
            return angleToReturn - 360.0;
        }

        return angleToReturn;
    }

    public double getZAngle() {
        double angleToReturn = 0;

        if (zAngle > 0) {
            angleToReturn = zAngle - (Math.floor(zAngle / 360.0) * 360.0);
        } else {
            angleToReturn = zAngle - (Math.ceil(zAngle / 360.0) * 360.0);
        }

        if (angleToReturn < -180) {
            return angleToReturn + 360.0;
        }
        if (angleToReturn > 180) {
            return angleToReturn - 360.0;
        }

        return angleToReturn;
    }

    //All below are abstraction methods

    public double getXVel() {
        return xVel;
    }

    public double getYVel() {
        return yVel;
    }

    public double getZVel() {
        return zVel;
    }

    private double average(double[] toAverage) {
        double sum = 0;

        for (int i = 0; i < toAverage.length; i++) {
            sum += toAverage[i];
        }
        return sum / toAverage.length;
    }

    private void resetTimer() {
        previousTime = System.currentTimeMillis();
    }

    private void resetCalibCount() {
        calibCount = 0;
    }

    public void resetXAngle() {
        xAngle = 0;
    }

    public void resetYAngle() {
        yAngle = 0;
    }

    public void resetZAngle() {
        zAngle = 0;
    }

    private long getTimePassed() {
        return (System.currentTimeMillis() - previousTime);//gets time in between gyro readings
    }

    private double trapaziodalIntegration(double previousAngle, double velocity, double previousVelocity) {
        return (previousAngle + /*timePassed*/ 20 * ((velocity + previousVelocity) / 2) / 1000);
    }
}
