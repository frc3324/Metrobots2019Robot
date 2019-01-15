package frc.team3324.robot.drivetrain.commands.auto;

import badlog.lib.BadLog;
import edu.wpi.first.wpilibj.command.Command;
import frc.team3324.robot.Robot;

public class Characterizer extends Command {

    private double rampingRate = 0.25;
    private double startingVelocity = 0;
    private double leftVoltage = 0;
    private double rightVoltage = 0;
    private double leftVelocity = 0;
    private double rightVelocity = 0;

    public void initialize() {

    }

    private double getRightVoltage() {
        return rightVoltage;
    }

    private double getLeftVoltage() {
        return leftVoltage;
    }

    private double getRightVelocity() {
        return rightVelocity;
    }

    private double getLeftVelocity() {
        return leftVelocity;
    }

    public void execute() {
        Robot.driveTrain.mDrive.tankDrive(leftVoltage * (1/12), rightVoltage * (1/12));
        leftVoltage = leftVoltage + rampingRate;
        rightVoltage = rightVoltage + rampingRate;
        leftVelocity = Robot.driveTrain.lEncoder.getRate();
        rightVelocity = Robot.driveTrain.rEncoder.getRate();
        BadLog.createTopic("characterizer/Right Voltage", "V", () -> getRightVoltage());
        BadLog.createTopic("characterizer/Left Voltage", "V", () -> getLeftVoltage());
        BadLog.createTopic("characterizer/Right Velocity", "m/s", () -> getRightVelocity());
        BadLog.createTopic("characterizer/Left Velocity", "m/s", () -> getLeftVelocity());

    }

    public boolean isFinished() {
        return Robot.oi.primaryController.getBackButton();
    }
}
