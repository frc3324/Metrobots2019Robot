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
        public final static int FL_MOTOR_PORT = 4;
        public final static int BL_MOTOR_PORT = 0;
        public final static int FR_MOTOR_PORT = 1;
        public final static int BR_MOTOR_PORT = 5;

        public final static int FL_PDP_MOTOR_PORT = 0;
        public final static int BL_PDP_MOTOR_PORT = 15;
        public final static int FR_PDP_MOTOR_PORT = 1;
        public final static int BR_PDP_MOTOR_PORT = 14;

        public final static int LEFT_ENCODER_PORT_A = 0;
        public final static int LEFT_ENCODER_PORT_B = 1;
        public final static int RIGHT_ENCODER_PORT_A = 2;
        public final static int RIGHT_ENCODER_PORT_B = 3;

        public final static double WHEEL_DIAMETER_METERS = 0.1555575;
        private final static double CIRCUMFERENCE = Math.PI * WHEEL_DIAMETER_METERS; // (Meters)
        public final static int PULSES = 1870; // 256 (pulses) * 4 (quadature, 4 ticks/pulse) * 3 * 2.5 (gear ratios)
        public final static int TICKS = PULSES * 4;
        public final static double DISTANCE_PER_PULSE = CIRCUMFERENCE / PULSES;
        public final static double DISTANCE_BETWEEN_WHEELS = 0.61;
        public final static double HIGH_GEAR_MAX_VELOCITY = 3.4072;
        public final static double HIGH_GEAR_MAX_ACCELERATION = 4.38;
        public final static double LOW_GEAR_MAX_VELOCITY = 1.8;
        public final static double LOW_GEAR_MAX_ACCELERATION = 6.51;
    }

    public class Arm {
        public final static int MOTOR_PORT_ARM_LEFT = 9;
        public final static int MOTOR_PORT_ARM_RIGHT = 8;
    }

    public class Climber {
        public final static int SOLENOID_PORT_ONE = 0;
        public final static int SOLENOID_PORT_TWO = 1;
    }

}
