package frc.team3324.robot.drivetrain.commands.teleop;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team3324.robot.util.OI;
import frc.team3324.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.GenericHID;

public class Drive extends Command {

    public Drive() {
        SmartDashboard.putString("Here2", "her2");
        requires(Robot.driveTrain);
        Robot.driveTrain.setBrakeMode();
    }

    protected void execute() {
        SmartDashboard.putString("Here3", "here3");
        double leftY = OI.primaryController.getY(GenericHID.Hand.kLeft); // Get the Y (Up/Down) value of the LEFT Joystick
        double rightX = OI.primaryController.getX(GenericHID.Hand.kRight); // Get the X (Left/Right) value of the LEFT Joystick

        if (OI.PRIMARY_RIGHT_BUMPER.get()) {
            Robot.driveTrain.mDrive.curvatureDrive(leftY, rightX, true);
        } else if (leftY < 0.1) {
            Robot.driveTrain.mDrive.curvatureDrive(leftY, rightX, true);
        } else {
            Robot.driveTrain.mDrive.curvatureDrive(leftY, rightX * 0.7, false);
        }
    }

    @Override
    protected boolean isFinished() {
        return OI.primaryController.getBackButton();
    }
}