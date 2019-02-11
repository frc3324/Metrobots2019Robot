package frc.team3324.robot.util;

/**
 * Class with unchanging variables. <br>
 * Examples: motor controller ports, sensor ports, gamepad ports,
 * etc.<br>
 * <br>
 */
public class Constants {

    /**
     * Class with drivetrain constants.
     */
    public class DriveTrain {
        public final static int DRIVETRAIN_PCM_MODULE = 0;

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

    /**
     * Class with arm constants.
     */
    public class Arm {
        public final static int MOTOR_PORT_ARM_ONE = 9;
        public final static int MOTOR_PORT_ARM_TWO = 8;
        public final static int MOTOR_PORT_ARM_THREE = 9;
    }

    /**
     * Class with hatch intake/outtake system constants.
     */
    public class HatchIntake {
        public final static int HATCH_INTAKE_PORT_FORWARD = 2;
        public final static int HATCH_INTAKE_PORT_BACKWARD = 3;
    }

    /**
     * Class with cargo intake/outtake system constants.
     */
    public class CargoIntake {
        public final static int CARGO_INTAKE_MOTOR_PORT = 2;

        public final static int CARGO_INTAKE_PDP_PORT = 1;

        public final static int LIMIT_SWITCH_PORT = 4;
    }

    /**
     * Class with climber constants.
     */
    public class Climber {
        public final static int CLIMBER_PORT_FORWARD = 4;
        public final static int CLIMBER_PORT_BACKWARD = 5;
    }

    /**
     * Class with LED constants.
     */
    public class LED {
        public final static int LED_PCM_MODULE = 1;

        public final static int RED_LED_PORT = 6;
        public final static int GREEN_LED_PORT = 7;
        public final static int BLUE_LED_PORT = 8;
    }
}
