package frc.team3324.robot;

/**
 * Class with unchanging variables. <br>
 * Examples: motor controller ports, sensor ports, gamepad ports,
 * etc.<br>
 * <br>
 */
public class Constants {
    // TODO Research const
    public class DriveTrain {
        // Motor ports
        public final static int FL_MOTOR_PORT = 6;
        public final static int BL_MOTOR_PORT = 4;
        public final static int FR_MOTOR_PORT = 0;
        public final static int BR_MOTOR_PORT = 2;
        // Sensor ports (encoder)
        public final static int LEFT_ENCODER_PORT_A  = 0;
        public final static int LEFT_ENCODER_PORT_B  = 1;
        public final static int RIGHT_ENCODER_PORT_A = 2;
        public final static int RIGHT_ENCODER_PORT_B = 3;
        // DriveTrain variables
        public final static double CIRCUMFERENCE           = 18.85; // (Inches) Need to measure in inches
        public final static double WHEEL_DIAMETER_METERS   = 0.15240359;
        public final static int ACTUAL_PULSES              = 7680; // 256 (pulses) * 4 (quadature, 4 ticks/pulse) * 3 * 2.5 (gear ratios)
        public final static double ENCODER_CONVERSION_RATE = 0.68667;
        public final static double DISTANCE_BETWEEN_WHEELS = 22.5;
        public final static double DISTANCE_BETWEEN_WHEELS_METERS = 0.5715;
        public final static double LOW_GEAR_METERS_PER_SECOND     = 2.4384; // lowgearSpeedMeters
    }
}
