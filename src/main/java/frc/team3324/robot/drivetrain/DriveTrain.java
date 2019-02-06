package frc.team3324.robot.drivetrain;

import badlog.lib.BadLog;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import frc.team3324.robot.Robot;
import frc.team3324.robot.drivetrain.commands.teleop.Drive;
import frc.team3324.robot.util.Constants;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.SPI;

import com.kauailabs.navx.frc.AHRS;

import static frc.team3324.robot.Robot.pdp;

// Identify Drivetrain as a subsystem (class)
public class DriveTrain extends Subsystem {
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

    private DoubleSolenoid gearShifter = new DoubleSolenoid(Constants.DriveTrain.DRIVETRAIN_PCM_MODULE, Constants.DriveTrain.DRIVETRAIN_PORT_FORWARD, Constants.DriveTrain.DRIVETRAIN_PORT_REVERSE);

    public static Encoder lEncoder =
        new Encoder(Constants.DriveTrain.LEFT_ENCODER_PORT_A,
                    Constants.DriveTrain.LEFT_ENCODER_PORT_B, false, Encoder.EncodingType.k4X);
    public static Encoder rEncoder =
        new Encoder(Constants.DriveTrain.RIGHT_ENCODER_PORT_A,
                    Constants.DriveTrain.RIGHT_ENCODER_PORT_B, false, Encoder.EncodingType.k4X);

    public static AHRS gyro = new AHRS(SPI.Port.kMXP);

    public WPI_TalonSRX flMotor = new WPI_TalonSRX(Constants.DriveTrain.FL_MOTOR_PORT);
    public WPI_TalonSRX blMotor = new WPI_TalonSRX(Constants.DriveTrain.BL_MOTOR_PORT);
    private SpeedControllerGroup lMotors = new SpeedControllerGroup(flMotor, blMotor);

    public WPI_TalonSRX frMotor = new WPI_TalonSRX(Constants.DriveTrain.FR_MOTOR_PORT);
    public WPI_TalonSRX brMotor = new WPI_TalonSRX(Constants.DriveTrain.BR_MOTOR_PORT);

    private SpeedControllerGroup rMotors = new SpeedControllerGroup(frMotor, brMotor);

    public DifferentialDrive mDrive = new DifferentialDrive(lMotors, rMotors);

    public DriveTrain() {
        frMotor.configPeakCurrentLimit(400);
        frMotor.configPeakCurrentDuration(200);
        frMotor.configContinuousCurrentLimit(200);

        brMotor.follow(frMotor);
        flMotor.follow(frMotor);
        blMotor.follow(frMotor);

        initializeBadLog();

        rMotors.setInverted(true);
        lMotors.setInverted(true);

        mDrive.setSafetyEnabled(true);

        lEncoder.setDistancePerPulse(Constants.DriveTrain.DISTANCE_PER_PULSE);
        rEncoder.setDistancePerPulse(Constants.DriveTrain.DISTANCE_PER_PULSE);
    }

    private void initializeBadLog() {
         BadLog.createTopic("drivetrain/Total Current", "Amps", () -> getTotalCurrent());
        BadLog.createTopic("drivetrain/FL Current", "Amps",
                           () -> pdp.getCurrent(Constants.DriveTrain.FL_PDP_MOTOR_PORT));
        BadLog.createTopic("drivetrain/BL Current", "Amps",
                           () -> pdp.getCurrent(Constants.DriveTrain.BL_PDP_MOTOR_PORT));
        BadLog.createTopic("drivetrain/FR Current", "Amps",
                           () -> pdp.getCurrent(Constants.DriveTrain.FR_PDP_MOTOR_PORT));
        BadLog.createTopic("drivetrain/BR Current", "Amps",
                           () -> pdp.getCurrent(Constants.DriveTrain.BR_PDP_MOTOR_PORT));

        BadLog.createTopic("drivetrain/Left Raw", "Ticks", () -> (double)lEncoder.getRaw());
        BadLog.createTopic("drivetrain/Right Raw", "Ticks", () -> (double)rEncoder.getRaw());
        BadLog.createTopic("drivetrain/Left Distance", "m", () -> lEncoder.getDistance());
        BadLog.createTopic("drivetrain/Right Distance", "m", () -> rEncoder.getDistance());
        BadLog.createTopic("drivetrain/Left Rate", "m/s", () -> lEncoder.getRate());
        BadLog.createTopic("drivetrain/Right Rate", "m/s", () -> rEncoder.getRate());
        BadLog.createTopic("drivetrain/Left Voltage", "V",
                           ()
                               -> (Robot.driveTrain.blMotor.getMotorOutputVoltage() +
                                   Robot.driveTrain.flMotor.getMotorOutputVoltage()));
        BadLog.createTopic("drivetrain/Right Voltage", "V",
                           ()
                               -> (Robot.driveTrain.brMotor.getMotorOutputVoltage() +
                                   Robot.driveTrain.frMotor.getMotorOutputVoltage()));
        BadLog.createTopic("drivetrain/Right Velocity", "m/s",
                           () -> Robot.characterizer.getRightVelocity());
        BadLog.createTopic("drivetrain/Left Velocity", "m/s",
                           () -> Robot.characterizer.getLeftVelocity());

    }

    /**
     * Reset both of encoders
     */
    public static void clearEncoder() {
        lEncoder.reset();
        rEncoder.reset();
    }

    /**
     * Print the encoder values, left (L Encoder Distance) and right (R Encoder Distance)
     */
    public void printEncoderDistance() {
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
    }

    /**
     * Reset the gyro to zero
     * Avoid usage at all costs
     */
    public void clearGyro() { gyro.reset(); }

    public double getYaw() { return gyro.getYaw(); }

    public void setBrakeMode() {
        frMotor.setNeutralMode(com.ctre.phoenix.motorcontrol.NeutralMode.Brake);
        brMotor.setNeutralMode(com.ctre.phoenix.motorcontrol.NeutralMode.Brake);
        blMotor.setNeutralMode(com.ctre.phoenix.motorcontrol.NeutralMode.Brake);
        flMotor.setNeutralMode(com.ctre.phoenix.motorcontrol.NeutralMode.Brake);
    }

    public void setCoastMode() {
        frMotor.setNeutralMode(com.ctre.phoenix.motorcontrol.NeutralMode.Coast);
        brMotor.setNeutralMode(com.ctre.phoenix.motorcontrol.NeutralMode.Coast);
        blMotor.setNeutralMode(com.ctre.phoenix.motorcontrol.NeutralMode.Coast);
        flMotor.setNeutralMode(com.ctre.phoenix.motorcontrol.NeutralMode.Coast);
    }

    public void setHighGear() { gearShifter.set(DoubleSolenoid.Value.kForward); }

    public void setLowGear() { gearShifter.set(DoubleSolenoid.Value.kReverse); }

    protected void initDefaultCommand() { setDefaultCommand(new Drive()); }

    public double getTotalCurrent() {
        return pdp.getCurrent(Constants.DriveTrain.FL_PDP_MOTOR_PORT) +
            pdp.getCurrent(Constants.DriveTrain.BL_PDP_MOTOR_PORT) +
            pdp.getCurrent(Constants.DriveTrain.FR_PDP_MOTOR_PORT) +
            pdp.getCurrent(Constants.DriveTrain.BR_PDP_MOTOR_PORT);
    }
}