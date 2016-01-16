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

        boolean justEnabled = true;

        private GyroL3GD20H gyroCom = null;//gyroCom is short for gyro Communications

        public LRTGyro(){
            gyroCom = new GyroL3GD20H();
        }

        /**
         * Updates the gyro when the robot is disabled
         */
        public void updateDisabledPeriodic(){
            if (!calibrated) {
                calibrateUpdate();

            } else{ //if already calibrated
                angleUpdate();
            }
        }

        /**
        * Updates the gyro when the robot is enabled
        */
        public void updateEnabledPeriodic(){
            if (justEnabled) {
                justEnabled = false;
                calibrated = true;
            }
            resetTimer();

            angleUpdate();
        }

        /**
        *Updates values for the drift on the axis
        */
        public void calibrateUpdate(){
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
        }

        /**
         * Updates values for the angle on the gyro
         */
        public void angleUpdate(){
            ///NOT_CALIBRATING is here just in case we lose connection on the field and enter the disabled state
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

        /** Gets the drift by taking the average of values that are read when the gyro is not moving
        * @param calibValues Values that are read when the gyro not moving.
        * @return The drift calculated from the values read when the gyro is not moving
        */
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

        /**
        * Returns the angle about the x axis in between -180 and 180
        * @return angle about the x axis in between -180 and 180
        */
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

         /*** Returns the angle about the y axis in between -180 and 180
         * @return angle about the y axis in between -180 and 180
         */
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

         /**
         * Returns the angle about the z axis in between -180 and 180
         * @return angle about the z axis in between -180 and 180
         */
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

        /**Takes the previous velocity and current velocity, and takes the trapezoidal integral to find the angle traveled
        * It works by taking the average of the 2 velocities, and multiplies it by the time in between updates (20 milliseconds)
        * @param previousAngle The previous angle
        * @param velocity   The current velocity read from the gyro
        * @param previousVelocity   the previous velocity read from the gyro
        * @return The angle gotten by taking the integral of the angular velocity
        */
        private double trapaziodalIntegration(double previousAngle, double velocity, double previousVelocity) {
            return (previousAngle + /*timePassed*/ 20 * ((velocity + previousVelocity) / 2) / 1000);
        }
}
