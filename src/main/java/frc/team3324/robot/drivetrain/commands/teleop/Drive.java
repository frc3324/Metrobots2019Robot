package frc.team3324.robot.drivetrain.commands.teleop;

import frc.team3324.robot.util.OI;
import frc.team3324.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.GenericHID;

public class Drive extends Command {

    public Drive() { requires(Robot.driveTrain); }

    protected void execute() {
        double leftY  = OI.primaryController.getY(GenericHID.Hand.kLeft);  // Get the Y (Up/Down) value of the LEFT Joystick
        double rightX = OI.primaryController.getX(GenericHID.Hand.kRight); // Get the X (Left/Right) value of the LEFT Joystick
        if (OI.PRIMARY_RIGHT_BUMPER.get()) {
            Robot.driveTrain.mDrive.curvatureDrive(leftY, rightX, true);
        } else if (leftY < 0.1) {
            Robot.driveTrain.mDrive.curvatureDrive(leftY, rightX, true);
        } else {
            Robot.driveTrain.mDrive.curvatureDrive(leftY, rightX, false);
        }
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

}