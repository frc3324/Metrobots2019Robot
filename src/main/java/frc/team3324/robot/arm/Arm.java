package frc.team3324.robot.arm;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import frc.team3324.robot.Robot;
import frc.team3324.robot.arm.commands.ControlArm;
import frc.team3324.robot.util.Constants;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team3324.robot.util.OI;
import frc.team3324.robot.wrappers.Logger;

/**
 * Subsystem class to control the arm. This class will set the arm motor velocities.
 */
public class Arm extends Subsystem {

    private ShuffleboardTab armTab = Shuffleboard.getTab("Arm");

    private NetworkTableEntry armEncoder = armTab.add("Arm Encoder Distance", 0).withPosition(0, 0).getEntry();
    private NetworkTableEntry armPDP = armTab.add("Arm Max PDP", 0).withPosition(1, 0).getEntry();
    private NetworkTableEntry armSpeed = armTab.add("Arm Speed", 0).withPosition(2, 0).getEntry();
    private NetworkTableEntry frontLimitSwitch = armTab.add("Front Switch", false).withPosition(3, 0).getEntry();
    private NetworkTableEntry backLimitSwitch = armTab.add("Back Switch", false).withPosition(4, 0).getEntry();

    private DigitalInput frontSwitch = new DigitalInput(9);
    private DigitalInput backSwitch = new DigitalInput(8);

    private static Encoder encoder =
            new Encoder(Constants.Arm.ENCODER_PORT_A, Constants.Arm.ENCODER_PORT_B, true, Encoder.EncodingType.k4X);
    private WPI_TalonSRX armMotorOne = new WPI_TalonSRX(Constants.Arm.MOTOR_PORT_ARM_ONE);
    private WPI_TalonSRX armMotorTwo = new WPI_TalonSRX(Constants.Arm.MOTOR_PORT_ARM_TWO);
    private WPI_VictorSPX armMotorThree = new WPI_VictorSPX(Constants.Arm.MOTOR_PORT_ARM_THREE);

    /**
     * Creates an instance of the Arm class.
     */

    public Arm() {
        initializeBadlog();
        armMotorOne.configContinuousCurrentLimit(8, 0);
        armMotorOne.enableCurrentLimit(true);

        armMotorTwo.follow(armMotorOne);
        armMotorThree.follow(armMotorOne);

        armMotorOne.setInverted(true);
        armMotorTwo.setInverted(false);

        setBrakeMode();
    }


    public void setBrakeMode() {
        armMotorOne.setNeutralMode(com.ctre.phoenix.motorcontrol.NeutralMode.Brake);
        armMotorTwo.setNeutralMode(com.ctre.phoenix.motorcontrol.NeutralMode.Brake);
        armMotorThree.setNeutralMode(com.ctre.phoenix.motorcontrol.NeutralMode.Brake);
    }

    private void initializeBadlog() {
        Logger.createTopic("arm/Arm Current One", "amps", () -> Robot.pdp.getCurrent(Constants.Arm.MOTOR_PORT_PDP_ONE));
        Logger.createTopic("arm/Arm Current Two", "amps", () -> Robot.pdp.getCurrent(Constants.Arm.MOTOR_PORT_PDP_TWO));
        Logger.createTopic("arm/Arm Current Three", "amps", () -> Robot.pdp.getCurrent(Constants.Arm.MOTOR_PORT_PDP_THREE));
        Logger.createTopic("arm/Arm Current Max", "amps", () -> getPDPMax());
        Logger.createTopic("arm/Arm Radians", "rad", () -> getArmRadians());
    }

    public void updateShuffleBoard() {
        armEncoder.setNumber(Math.toDegrees(getArmRadians()));
        armPDP.setNumber(getPDPMax());
        frontLimitSwitch.setBoolean(frontSwitch.get());
        backLimitSwitch.setBoolean(backSwitch.get());
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
        if (stopArmIfOnSwitchOrAtHardstop(speed)) {
            OI.secondaryController.setRumble(GenericHID.RumbleType.kLeftRumble, 0.2);
            speed = 0;
        } else {
            OI.secondaryController.setRumble(GenericHID.RumbleType.kLeftRumble, 0);
        }

        double feedforward = 0.06 * Math.cos(getArmRadians());
        armMotorOne.set(speed + feedforward);

        armSpeed.setDouble(speed + feedforward);
    }

    private boolean stopArmIfOnSwitchOrAtHardstop(double speed) {
        return (encoder.get() <= 0 && speed < 0)|| (encoder.get() >= (Constants.Arm.ENCODER_TICKS_PER_REV) / 2 && speed > 0) || ((frontSwitch.get() && speed < 0) || (backSwitch.get() && speed > 0));
    }

    public void setRawArmSpeed(double speed) {
        if (frontSwitch.get()) {
            armMotorOne.set(0);
            encoder.reset();
        }
        armMotorOne.set(-speed);
    }
    public boolean getFrontSwitch() {
        return frontSwitch.get();
    }

    @Override
    public void initDefaultCommand() {
        setDefaultCommand(new ControlArm()); }
}
