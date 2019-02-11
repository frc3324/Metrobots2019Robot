package frc.team3324.robot.arm;

import badlog.lib.BadLog;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import frc.team3324.robot.Robot;
import frc.team3324.robot.arm.commands.ControlArm;
import frc.team3324.robot.util.Constants;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.opencv.core.Mat;

public class Arm extends Subsystem {

    private ShuffleboardTab armTab = Shuffleboard.getTab("Arm");

    private NetworkTableEntry armEncoder = armTab.add("Arm Encoder Distance", 0).withPosition(0, 0).getEntry();
    private NetworkTableEntry armPDP = armTab.add("Arm Max PDP", 0).withPosition(1, 0).getEntry();
    private NetworkTableEntry armSpeed = armTab.add("Arm Speed", 0).withPosition(2, 0).getEntry();

    // TODO Invert stuff
    public static Encoder encoder =
            new Encoder(Constants.Arm.ENCODER_PORT_A, Constants.Arm.ENCODER_PORT_B, false, Encoder.EncodingType.k4X);
    private WPI_TalonSRX armMotorOne = new WPI_TalonSRX(Constants.Arm.MOTOR_PORT_ARM_ONE);
    private WPI_VictorSPX armMotorTwo = new WPI_VictorSPX(Constants.Arm.MOTOR_PORT_ARM_TWO);
    private WPI_TalonSRX armMotorThree = new WPI_TalonSRX(Constants.Arm.MOTOR_PORT_ARM_THREE);

    private SpeedControllerGroup armMotors = new SpeedControllerGroup(armMotorOne, armMotorTwo, armMotorThree);

    public Arm() {
        armMotorOne.configContinuousCurrentLimit(84);
        armMotorOne.configPeakCurrentDuration(200);
        armMotorOne.configPeakCurrentLimit(150);
        armMotorTwo.follow(armMotorOne);
        armMotorThree.follow(armMotorOne);
    }

    public void initializeBadlog() {
        BadLog.createTopic("Arm Current One", "amps", () -> Robot.pdp.getCurrent(Constants.Arm.MOTOR_PORT_PDP_ONE));
        BadLog.createTopic("Arm Current Two", "amps", () -> Robot.pdp.getCurrent(Constants.Arm.MOTOR_PORT_PDP_TWO));
        BadLog.createTopic("Arm Current Three", "amps", () -> Robot.pdp.getCurrent(Constants.Arm.MOTOR_PORT_PDP_THREE));
        BadLog.createTopic("Arm Current Max", "amps", () -> getPDPMax());

    }

    public void updateShuffleBoard() {
        armEncoder.setNumber(encoder.get());
        armPDP.setNumber(getPDPMax());
    }

    public double getPDPMax() {
        return Robot.pdp.getCurrent(Constants.Arm.MOTOR_PORT_PDP_ONE) + Robot.pdp.getCurrent(Constants.Arm.MOTOR_PORT_PDP_TWO) + Robot.pdp.getCurrent(Constants.Arm.MOTOR_PORT_PDP_THREE);
    }
    /**
     * Move the arm at the specified speed.
     * @param speed
     */
    public void setArmSpeed(double speed) {
        if ((encoder.get() <= 0 && speed < 0)|| (encoder.get() >= (Constants.Arm.ENCODER_TICKS_PER_REV) / 2 && speed > 0)) {
            speed = 0;
        }
        armMotors.set(speed);
        armSpeed.setDouble(speed);
    }

    public double getArmRadians() {
        return 0;
    }

    @Override
    public void initDefaultCommand() {
        setDefaultCommand(new ControlArm()); }
}
