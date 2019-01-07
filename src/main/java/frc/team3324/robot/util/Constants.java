package frc.team3324.robot.util;

/**
 * Class with unchanging variables. <br>
 * Examples: motor controller ports, sensor ports, gamepad ports,
 * etc.<br>
 * <br>
 */
public class Constants {
    public class DriveTrain {
        // Motor ports
        public final static int FL_MOTOR_PORT = 6;
        public final static int BL_MOTOR_PORT = 4;
        public final static int FR_MOTOR_PORT = 0;
        public final static int BR_MOTOR_PORT = 2;

        public final static int LEFT_ENCODER_PORT_A  = 0;
        public final static int LEFT_ENCODER_PORT_B  = 1;
        public final static int RIGHT_ENCODER_PORT_A = 2;
        public final static int RIGHT_ENCODER_PORT_B = 3;

        public final static double WHEEL_DIAMETER_METERS   = 0.15240359;
        private final static double CIRCUMFERENCE          = 2 * Math.PI * WHEEL_DIAMETER_METERS; // (Meters)
        public final static int PULSES = 7680; // 256 (pulses) * 4 (quadature, 4 ticks/pulse) * 3 * 2.5 (gear ratios)
        public final static double DISTANCE_PER_PULSE = CIRCUMFERENCE / PULSES;
        public final static double DISTANCE_BETWEEN_WHEELS = 0.5715;
        public final static double LOW_GEAR_MAX_VELOCITY = 2.4384;
    }
}
