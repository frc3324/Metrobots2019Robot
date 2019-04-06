package frc.team3324.robot.arm;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team3324.robot.Robot;
import frc.team3324.robot.arm.commands.ControlArm;
import frc.team3324.robot.util.Constants;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team3324.robot.util.MotorConstants.MiniCim;
import frc.team3324.robot.util.OI;
import frc.team3324.robot.util.PredictiveCurrentLimiting;
import frc.team3324.robot.wrappers.Logger;

/**
 * Subsystem class to control the arm. This class will set the arm motor velocities.
 */
public class Arm extends Subsystem {

    private double lastEncoderValue;
    private double armRPM;
    private double gearRatio = 147;
    private ShuffleboardTab armTab = Shuffleboard.getTab("Arm");

    private NetworkTableEntry armEncoder = armTab.add("Arm Encoder Distance", 0).withPosition(0, 0).getEntry();
    private NetworkTableEntry armPDP = armTab.add("Arm Max PDP", 0).withPosition(1, 0).getEntry();
    private NetworkTableEntry armSpeed = armTab.add("Arm Speed", 0).withPosition(2, 0).getEntry();
    private NetworkTableEntry frontLimitSwitch = armTab.add("Front Switch", false).withPosition(3, 0).getEntry();
    private NetworkTableEntry backLimitSwitch = armTab.add("Back Switch", false).withPosition(4, 0).getEntry();

    private DigitalInput frontSwitch = new DigitalInput(Constants.Arm.FRONT_LIMIT_SWITCH);
    private DigitalInput backSwitch = new DigitalInput(Constants.Arm.BACK_LIMIT_SWITCH);

    private static Encoder encoder =
            new Encoder(Constants.Arm.ENCODER_PORT_A, Constants.Arm.ENCODER_PORT_B, true, Encoder.EncodingType.k4X);
    private WPI_TalonSRX armMotorOne = new WPI_TalonSRX(Constants.Arm.MOTOR_PORT_ARM_ONE);
    private WPI_VictorSPX armMotorTwo = new WPI_VictorSPX(Constants.Arm.MOTOR_PORT_ARM_TWO);
    private WPI_TalonSRX armMotorThree = new WPI_TalonSRX(Constants.Arm.MOTOR_PORT_ARM_THREE);

   MiniCim armMotor = new MiniCim(3);
   PredictiveCurrentLimiting predictiveCurrentLimiting = new PredictiveCurrentLimiting(8, -8, gearRatio, armMotor);


   /**
     * Creates an instance of the Arm class.
     */

    public Arm() {
        initializeLogger();
        initializeCurrentLimiting();
        setBrakeMode();
        encoder.setDistancePerPulse(1.0/256.0);
    }


    private void initializeCurrentLimiting() {
        armMotorOne.configContinuousCurrentLimit(8, 0);
        armMotorOne.enableCurrentLimit(true);
        armMotorTwo.follow(armMotorOne);
        armMotorThree.follow(armMotorOne);
    }


    public void setBrakeMode() {
        armMotorOne.setNeutralMode(com.ctre.phoenix.motorcontrol.NeutralMode.Brake);
        armMotorTwo.setNeutralMode(com.ctre.phoenix.motorcontrol.NeutralMode.Brake);
        armMotorThree.setNeutralMode(com.ctre.phoenix.motorcontrol.NeutralMode.Brake);
    }

    private void initializeLogger() {
        Logger.createTopic("arm/Arm Current One", "amps", () -> armMotorOne.getOutputCurrent());
        Logger.createTopic("arm/Arm Current Max", "amps", () -> armMotorOne.getOutputCurrent() * 3);
        Logger.createTopic("arm/Arm Radians", "rad", () -> getArmRadians());
    }

    public void updateShuffleBoard() {
        armEncoder.setNumber(Math.toDegrees(getArmRadians()));
        armPDP.setNumber(getPDPMax());
        frontLimitSwitch.setBoolean(frontSwitch.get());
        backLimitSwitch.setBoolean(backSwitch.get());
    }

    public void updateRPM() {
        double velocity = (encoder.get() - lastEncoderValue) / 0.02;
        double RPM = velocity / Constants.Arm.ENCODER_TICKS_PER_REV * 60;
        lastEncoderValue = encoder.get();
        armRPM = RPM;
        SmartDashboard.putNumber("RPM", armRPM * 147);
    }

    public double getRPM() {
        return armRPM;
    }

    public double getArmRadians() {
        return encoder.get() * ((Math.PI * 2)/Constants.Arm.ENCODER_TICKS_PER_REV);
    }

    public double getPDPMax() {
        return Robot.pdp.getCurrent(Constants.Arm.MOTOR_PORT_PDP_ONE) + Robot.pdp.getCurrent(Constants.Arm.MOTOR_PORT_PDP_TWO) + Robot.pdp.getCurrent(Constants.Arm.MOTOR_PORT_PDP_THREE);
    }

    /**
     * Moves the arm at the specified speed.
     *
     * @param speed, -1.0 to 1.0
     */
    public void setArmSpeed(double speed) {
        if (frontSwitch.get()) {
            encoder.reset();
        }
        if (armIsOnLimitSwitchOrHardstop(speed)) {
            OI.secondaryController.setRumble(GenericHID.RumbleType.kLeftRumble, 0.2);
            speed = 0;
        } else {
            OI.secondaryController.setRumble(GenericHID.RumbleType.kLeftRumble, 0);
        }
        double feedforward = 0.06 * Math.cos(getArmRadians());
        speed = speed + feedforward;
//        speed = predictiveCurrentLimiting.getVoltage(speed * 12, getRPM()) / 12;
        armMotorOne.set(speed);

        armSpeed.setDouble(speed);
    }

    private boolean armIsOnLimitSwitchOrHardstop(double speed) {
        return (encoder.get() <= 0 && speed < 0)|| (encoder.get() >= (Constants.Arm.ENCODER_TICKS_PER_REV) / 2 && speed > 0) || ((frontSwitch.get() && speed < 0) || (backSwitch.get() && speed > 0));
    }

    public void resetArmSpeed(double speed) {
        if (frontSwitch.get()) {
            armMotorOne.set(0);
            encoder.reset();
        }
        armMotorOne.set(speed);
    }

    public void setRawArm(double speed) {
            armMotorOne.set(speed);
    }

    public boolean getFrontSwitch() {
        return frontSwitch.get();
    }

    @Override
    public void initDefaultCommand() {
        setDefaultCommand(new ControlArm()); }
}
