package frc.team3324.robot.util;

/**
 * Class with unchanging variables. <br>
 * Examples: motor controller ports, sensor ports, gamepad ports,
 * etc.<br>
 * <br>
 */
public class Constants {

    public class DriveTrain {
        public final static int DRIVETRAIN_PCM_MODULE = 0;

        // Motor ports
        public final static int FL_MOTOR_PORT = 7; // Talon
        public final static int BL_MOTOR_PORT = 6; // Victor
        public final static int FR_MOTOR_PORT = 0; // Victor
        public final static int BR_MOTOR_PORT = 1; // Talon

        public final static int FL_PDP_MOTOR_PORT = 12;
        public final static int BL_PDP_MOTOR_PORT = 13;
        public final static int FR_PDP_MOTOR_PORT = 1;
        public final static int BR_PDP_MOTOR_PORT = 0;

        public final static int LEFT_ENCODER_PORT_A = 0;
        public final static int LEFT_ENCODER_PORT_B = 1;
        public final static int RIGHT_ENCODER_PORT_A = 2;
        public final static int RIGHT_ENCODER_PORT_B = 3;

        // Encoder and Auto constants
        public final static double WHEEL_DIAMETER_METERS = 0.1555575;
        private final static double CIRCUMFERENCE = Math.PI * WHEEL_DIAMETER_METERS; // (Meters)
        public final static int PULSES = 1870; // 256 (pulses) * 4 (quadrature, 4 ticks/pulse) * 3 * 2.5 (gear ratios)
        public final static int TICKS = PULSES * 4;
        public final static double DISTANCE_PER_PULSE = CIRCUMFERENCE / PULSES;
        public final static double DISTANCE_BETWEEN_WHEELS = 0.61;
        public final static double HIGH_GEAR_MAX_VELOCITY = 3.4072;
        public final static double HIGH_GEAR_MAX_ACCELERATION = 4.38;
        public final static double LOW_GEAR_MAX_VELOCITY = 1.8;
        public final static double LOW_GEAR_MAX_ACCELERATION = 6.51;

        // Drivetrain ports
        public final static int DRIVETRAIN_PORT_FORWARD = 0;
        public final static int DRIVETRAIN_PORT_REVERSE = 1;
    }

    public class Arm {
        public final static int MOTOR_PORT_ARM_ONE = 2; // Talon
        public final static int MOTOR_PORT_ARM_TWO = 4; // Talon
        public final static int MOTOR_PORT_ARM_THREE = 5; // Victor

        public final static int MOTOR_PORT_PDP_ONE = 15;
        public final static int MOTOR_PORT_PDP_TWO = 14;
        public final static int MOTOR_PORT_PDP_THREE = 2;

        public final static int ENCODER_PORT_A = 5;
        public final static int ENCODER_PORT_B = 6;

        public final static int ENCODER_TICKS_PER_REV = 256;

        public final static int FRONT_LIMIT_SWITCH = 9;
        public final static int BACK_LIMIT_SWITCH = 8;
    }

    public class HatchIntake {
        public final static int HATCH_INTAKE_PORT_FORWARD = 2;
        public final static int HATCH_INTAKE_PORT_BACKWARD = 3;
    }

    public class CargoIntake {
        public final static int CARGO_INTAKE_MOTOR_PORT = 3;

        public final static int CARGO_INTAKE_PDP_PORT = 3;

        public final static int LIMIT_SWITCH_PORT = 4;
    }

    public class Climber {
        public final static int CLIMBER_BACK_FORWARD = 4;
        public final static int CLIMBER_BACK_BACKWARD = 5;

        public final static int CLIMBER_FRONT_FORWARD = 6;
        public final static int CLIMBER_FRONT_BACKWARD = 7;
    }

    public class LED {
        public final static int LED_PCM_MODULE = 1;

        public final static int RED_LED_PORT = 5;
        public final static int GREEN_LED_PORT = 6;
        public final static int BLUE_LED_PORT = 7;
    }
}