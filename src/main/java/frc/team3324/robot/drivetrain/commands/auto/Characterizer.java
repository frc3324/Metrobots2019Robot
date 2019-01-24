package frc.team3324.robot.drivetrain.commands.auto;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team3324.robot.Robot;

public class Characterizer extends Command {

    private double rampingRate = 0.05;
    private double leftVoltage = 12;
    private double rightVoltage = 12;
    private double leftVelocity = 0;
    private double rightVelocity = 0;

    public double getRightVelocity() {
        return rightVelocity;
    }

    public double getLeftVelocity() {
        return leftVelocity;
    }

    public void execute() {
        double leftAppliedVoltage = leftVoltage/12;
        double rightAppliedVoltage = rightVoltage/12;
        Robot.driveTrain.mDrive.tankDrive(-leftAppliedVoltage, -rightAppliedVoltage);
        leftVoltage = leftVoltage + rampingRate;
        rightVoltage = rightVoltage + rampingRate;
        leftVelocity = Robot.driveTrain.lEncoder.getRate();
        rightVelocity = Robot.driveTrain.rEncoder.getRate();
        SmartDashboard.putNumber("Voltage Left", leftAppliedVoltage);
        SmartDashboard.putNumber("Voltage Right", rightAppliedVoltage);

    }

    public boolean isFinished() {
        return Robot.oi.primaryController.getBackButton();
    }
}
