package frc.team3324.robot.drivetrain.commands.teleop;

import frc.team3324.robot.util.OI;
import frc.team3324.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.GenericHID;

/**
 * Command class to drive the robot using driver input through a controller.
 */
public class Drive extends Command {

    /**
     * Creates an instance of the Drive class.
     */
    public Drive() {
        requires(Robot.driveTrain);
        Robot.driveTrain.setBrakeMode();
    }

    protected void execute() {
        double leftY = OI.primaryController.getY(GenericHID.Hand.kLeft); // Get the Y (Up/Down) value of the LEFT Joystick
        double rightX = OI.primaryController.getX(GenericHID.Hand.kRight); // Get the X (Left/Right) value of the LEFT Joystick
        Robot.driveTrain.curvatureDrive(-leftY, -rightX * 0.7, false);
    }

    @Override
    protected boolean isFinished() {
        return OI.primaryController.getBackButton();
    }
}