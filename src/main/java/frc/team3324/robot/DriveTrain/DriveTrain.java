package frc.team3324.robot.DriveTrain;

import frc.team3324.robot.Constants;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.SPI;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import com.kauailabs.navx.frc.AHRS;
import frc.team3324.robot.DriveTrain.Commands.Teleop.Drive;

// Identify Drivetrain as a subsystem (class)
public class DriveTrain extends Subsystem {

    private DoubleSolenoid gearShifter = new DoubleSolenoid(0, 1);

    private static Encoder lEncoder =
        new Encoder(Constants.DriveTrain.LEFT_ENCODER_PORT_A, Constants.DriveTrain.LEFT_ENCODER_PORT_B, false, Encoder.EncodingType.k4X);
    private static Encoder rEncoder =
        new Encoder(Constants.DriveTrain.RIGHT_ENCODER_PORT_A, Constants.DriveTrain.RIGHT_ENCODER_PORT_B, false, Encoder.EncodingType.k4X);
    private double distancePerPulse = Constants.DriveTrain.CIRCUMFERENCE / Constants.DriveTrain.ACTUAL_PULSES;

    private static AHRS gyro = new AHRS(SPI.Port.kMXP);

    WPI_VictorSPX flMotor        = new WPI_VictorSPX(Constants.DriveTrain.FL_MOTOR_PORT);
    WPI_VictorSPX blMotor        = new WPI_VictorSPX(Constants.DriveTrain.BL_MOTOR_PORT);
    SpeedControllerGroup lMotors = new SpeedControllerGroup(flMotor, blMotor);

    WPI_VictorSPX frMotor        = new WPI_VictorSPX(Constants.DriveTrain.FR_MOTOR_PORT);
    WPI_VictorSPX brMotor        = new WPI_VictorSPX(Constants.DriveTrain.BR_MOTOR_PORT);
    SpeedControllerGroup rMotors = new SpeedControllerGroup(frMotor, brMotor);

    public DifferentialDrive mDrive = new DifferentialDrive(lMotors, rMotors);

    public DriveTrain() {
        mDrive.setSafetyEnabled(true);
        lEncoder.setDistancePerPulse(distancePerPulse);
        rEncoder.setDistancePerPulse(distancePerPulse);
    }

    public static double getLeftDistance() { return lEncoder.getDistance(); }

    /**
     * Get distance of the right encoder in inches
     * @return
     */
    public static double getRightDistance() { return rEncoder.getDistance(); }

    public static int getLeftDistanceRaw() {
        final int rawLDistance = lEncoder.getRaw();
        return rawLDistance;
    }

    public static int getRightDistanceRaw() {
        final int rawLDistance = rEncoder.getRaw();
        return rawLDistance;
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
    public void printEncoder() {
        SmartDashboard.putNumber("L Encoder Distance", getLeftDistance());
        SmartDashboard.putNumber("R Encoder Distance", getRightDistance());
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
}