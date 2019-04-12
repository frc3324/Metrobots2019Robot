package frc.team3324.robot.drivetrain;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import com.kauailabs.navx.frc.AHRS;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import frc.team3324.robot.Robot;
import frc.team3324.robot.drivetrain.commands.teleop.Drive;
import frc.team3324.robot.util.Constants;
import frc.team3324.robot.wrappers.Logger;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.SPI;

import java.util.function.Function;

import static frc.team3324.robot.Robot.pdp;


/**
 * Subsystem class to control the drivetrain and peripheral drivetrain systems.
 */
public class DriveTrain extends Subsystem { // Identify Drivetrain as a subsystem (class)

    private Function<Double, Double> curve = Function.identity();

    private ShuffleboardTab sensorTab = Shuffleboard.getTab("Encoder Values");

    private NetworkTableEntry leftDistance = sensorTab.add("Left Encoder Distance", 0).withPosition(0, 0).getEntry();
    private NetworkTableEntry rightDistance = sensorTab.add("Right Encoder Distance", 0).withPosition(1, 0).getEntry();
    private NetworkTableEntry rightRaw = sensorTab.add("Right Encoder Raw", 0).withPosition(2, 0).getEntry();
    private NetworkTableEntry leftRaw = sensorTab.add("Left Encoder Raw", 0).withPosition(3, 0).getEntry();
    private NetworkTableEntry rightRate = sensorTab.add("Right Encoder Rate", 0).withPosition(4, 0).getEntry();
    private NetworkTableEntry leftRate = sensorTab.add("Left Encoder Rate", 0).withPosition(5, 0).getEntry();

    private NetworkTableEntry leftDistanceGraph = sensorTab.add("Left Encoder Distance Graph", 0).withPosition(0, 1).withWidget(BuiltInWidgets.kGraph).getEntry();
    private NetworkTableEntry rightDistanceGraph = sensorTab.add("Right Encoder Distance Graph", 0).withPosition(1, 1).withWidget(BuiltInWidgets.kGraph).getEntry();
    private NetworkTableEntry rightRawGraph = sensorTab.add("Right Encoder Raw Graph", 0).withPosition(2, 1).withWidget(BuiltInWidgets.kGraph).getEntry();
    private NetworkTableEntry leftRawGraph = sensorTab.add("Left Encoder Raw Graph", 0).withPosition(3, 1).withWidget(BuiltInWidgets.kGraph).getEntry();
    private NetworkTableEntry rightRateGraph = sensorTab.add("Right Encoder Rate Graph", 0).withPosition(4, 1).withWidget(BuiltInWidgets.kGraph).getEntry();
    private NetworkTableEntry leftRateGraph = sensorTab.add("Left Encoder Rate Graph", 0).withPosition(5, 1).withWidget(BuiltInWidgets.kGraph).getEntry();

    private NetworkTableEntry gyroYaw = sensorTab.add("Gyro Yaw", 0).withPosition(0, 2).getEntry();

    private DoubleSolenoid gearShifter = new DoubleSolenoid(Constants.DriveTrain.DRIVETRAIN_PCM_MODULE, Constants.DriveTrain.DRIVETRAIN_PORT_FORWARD, Constants.DriveTrain.DRIVETRAIN_PORT_REVERSE);

    public Encoder lEncoder =
            new Encoder(Constants.DriveTrain.LEFT_ENCODER_PORT_A, Constants.DriveTrain.LEFT_ENCODER_PORT_B, false, Encoder.EncodingType.k4X);
    public Encoder rEncoder =
            new Encoder(Constants.DriveTrain.RIGHT_ENCODER_PORT_A, Constants.DriveTrain.RIGHT_ENCODER_PORT_B, true, Encoder.EncodingType.k4X);

    private static AHRS gyro = new AHRS(SPI.Port.kMXP);

    private WPI_VictorSPX flMotor = new WPI_VictorSPX(Constants.DriveTrain.FL_MOTOR_PORT);
    private WPI_TalonSRX blMotor = new WPI_TalonSRX(Constants.DriveTrain.BL_MOTOR_PORT);

    private WPI_TalonSRX frMotor = new WPI_TalonSRX(Constants.DriveTrain.FR_MOTOR_PORT);
    private WPI_VictorSPX brMotor = new WPI_VictorSPX(Constants.DriveTrain.BR_MOTOR_PORT);

    public DifferentialDrive mDrive = new DifferentialDrive(frMotor, blMotor);


    /**
     * Creates an instance of the DriveTrain class.
     * <p>Configures motor controller current limits, durations.</p>
     * <p>Slaves motor controllers to TalonSRXs.</p>
     * <p>Initializes logger.</p>
     * <p>Enables drivetrain motor controller safety mode.</p>
     * <p>Set drivetrain distance per pulse.</p>
     */
    public DriveTrain() {
        frMotor.configPeakCurrentLimit(200);
        frMotor.configPeakCurrentDuration(200);
        frMotor.configContinuousCurrentLimit(40);

        blMotor.configPeakCurrentLimit(200);
        blMotor.configPeakCurrentDuration(200);
        blMotor.configContinuousCurrentLimit(40);

        frMotor.enableCurrentLimit(true);
        blMotor.enableCurrentLimit(true);

        brMotor.setInverted(false);
        frMotor.setInverted(false);

        brMotor.follow(frMotor);
        flMotor.follow(blMotor);

        initializeLogger();
        mDrive.setSafetyEnabled(true);

        lEncoder.setDistancePerPulse(Constants.DriveTrain.DISTANCE_PER_PULSE);
        rEncoder.setDistancePerPulse(Constants.DriveTrain.DISTANCE_PER_PULSE);
    }

    /**
     * Populates the logger with basic robot information (current, encoder values, distance, velocity, voltage).
     *
     * @see Logger
     */
    private void initializeLogger() {
        Logger.createTopic("drivetrain/Total Current", "Amps", this::getTotalCurrent);
        Logger.createTopic("drivetrain/FL Current", "Amps", () -> pdp.getCurrent(Constants.DriveTrain.FL_PDP_MOTOR_PORT));
        Logger.createTopic("drivetrain/BL Current", "Amps", () -> pdp.getCurrent(Constants.DriveTrain.BL_PDP_MOTOR_PORT));
        Logger.createTopic("drivetrain/FR Current", "Amps", () -> pdp.getCurrent(Constants.DriveTrain.FR_PDP_MOTOR_PORT));
        Logger.createTopic("drivetrain/BR Current", "Amps", () -> pdp.getCurrent(Constants.DriveTrain.BR_PDP_MOTOR_PORT));

        Logger.createTopic("drivetrain/Left Raw", "Ticks", () -> (double) lEncoder.getRaw());
        Logger.createTopic("drivetrain/Right Raw", "Ticks", () -> (double) rEncoder.getRaw());
        Logger.createTopic("drivetrain/Left Distance", "m", () -> lEncoder.getDistance());
        Logger.createTopic("drivetrain/Right Distance", "m", () -> rEncoder.getDistance());
        Logger.createTopic("drivetrain/Left Rate", "m/s", () -> lEncoder.getRate());
        Logger.createTopic("drivetrain/Right Rate", "m/s", () -> rEncoder.getRate());
        Logger.createTopic("drivetrain/Left Voltage", "V",
                () -> (Robot.driveTrain.blMotor.getMotorOutputVoltage() + Robot.driveTrain.flMotor.getMotorOutputVoltage()));
        Logger.createTopic("drivetrain/Right Voltage", "V",
                () -> (Robot.driveTrain.brMotor.getMotorOutputVoltage() + Robot.driveTrain.frMotor.getMotorOutputVoltage()));

    }

    /**
     * Resets both drivetrain encoders.
     *
     * @see Encoder
     */
    public void clearEncoder() {
        lEncoder.reset();
        rEncoder.reset();
    }

    /**
     * Prints drivetrain encoder values through NetworkTables.
     * <p>Left (L Encoder Distance) and right (R Encoder Distance).</p>
     *
     * @see NetworkTableEntry
     */
    public void updateSensors() {
        rightDistance.setDouble(rEncoder.getDistance());
        leftDistance.setDouble(lEncoder.getDistance());

        rightRaw.setDouble(rEncoder.getRaw());
        leftRaw.setDouble(lEncoder.getRaw());

        rightRate.setDouble(rEncoder.getRate());
        leftRate.setDouble(lEncoder.getRate());

        rightDistanceGraph.setDouble(rEncoder.getDistance());
        leftDistanceGraph.setDouble(lEncoder.getDistance());

        rightRawGraph.setDouble(rEncoder.getRaw());
        leftRawGraph.setDouble(lEncoder.getRaw());

        rightRateGraph.setDouble(rEncoder.getRate());
        leftRateGraph.setDouble(lEncoder.getRate());

        gyroYaw.setNumber(getYaw());
    }

    public void curvatureDrive(double forward, double rotation, boolean quickTurn) {
        double left;
        double right;

        if (forward < 0.05) {
            quickTurn = true;
        }


        if (quickTurn) {
            left = forward + rotation;
            right = forward - rotation;
        } else {
            left = forward + curve.apply(forward) * rotation;
            right = forward - curve.apply(forward) * rotation;
        }

        double[] normalSpeed = normalizeSpeeds(left, right);

        left = normalSpeed[0];
        right = normalSpeed[1];

        Robot.driveTrain.mDrive.tankDrive(left, right);
    }

    private double[] normalizeSpeeds(double left, double right) {
        double maxMagnitude = Math.max(Math.abs(left), Math.abs(right));
        if (maxMagnitude > 1) {
            left /= maxMagnitude;
            right /= maxMagnitude;
        }
        return new double[]{left, right};
    }

    /**
     * Resets the gyro to zero.
     * <p>Avoid usage at all costs.</p>
     *
     * @see AHRS
     */
    public void clearGyro() {
        gyro.reset();
    }

    /**
     * Gets current gyro yaw.
     *
     * @return Current yaw value in degrees, -180.0 to 180.0.
     * @see AHRS
     */
    public double getYaw() {
        return gyro.getYaw();
    }

    /**
     * Sets drivetrain motors to brake mode (apply force to brake).
     *
     * @see WPI_TalonSRX
     */
    public void setBrakeMode() {
        frMotor.setNeutralMode(com.ctre.phoenix.motorcontrol.NeutralMode.Brake);
        brMotor.setNeutralMode(com.ctre.phoenix.motorcontrol.NeutralMode.Brake);
        blMotor.setNeutralMode(com.ctre.phoenix.motorcontrol.NeutralMode.Brake);
        flMotor.setNeutralMode(com.ctre.phoenix.motorcontrol.NeutralMode.Brake);
    }

    /**
     * Sets drivetrain motors to coast mode (coasts to a stop).
     *
     * @see WPI_TalonSRX
     */
    public void setCoastMode() {
        frMotor.setNeutralMode(com.ctre.phoenix.motorcontrol.NeutralMode.Coast);
        brMotor.setNeutralMode(com.ctre.phoenix.motorcontrol.NeutralMode.Coast);
        blMotor.setNeutralMode(com.ctre.phoenix.motorcontrol.NeutralMode.Coast);
        flMotor.setNeutralMode(com.ctre.phoenix.motorcontrol.NeutralMode.Coast);
    }

    /**
     * Sets drivetrain to high gear using a double solenoid.
     *
     * @see DoubleSolenoid
     */
    public void shiftGears() {
        if (gearShifter.get() == DoubleSolenoid.Value.kForward) {
            gearShifter.set(DoubleSolenoid.Value.kReverse);
        } else {
            gearShifter.set(DoubleSolenoid.Value.kForward);
        }
    }

    protected void initDefaultCommand() {
        setDefaultCommand(new Drive());
    }

    /**
     * Gets total current of drivetrain through PDP.
     *
     * @return Total current of drivetrain.
     * @see PowerDistributionPanel
     */
    public double getTotalCurrent() {
        return pdp.getCurrent(Constants.DriveTrain.FL_PDP_MOTOR_PORT) + pdp.getCurrent(Constants.DriveTrain.BL_PDP_MOTOR_PORT) +
                pdp.getCurrent(Constants.DriveTrain.FR_PDP_MOTOR_PORT) + pdp.getCurrent(Constants.DriveTrain.BR_PDP_MOTOR_PORT);
    }
}