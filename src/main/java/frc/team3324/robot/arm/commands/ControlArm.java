package frc.team3324.robot.arm.commands;

import frc.team3324.robot.util.OI;
import frc.team3324.robot.Robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.command.Command;

/**
 * Moves the set arm from forward to backward with the left joystick.
 */
public class ControlArm extends Command {

    /**
     * Move the arm to its opposite position when called. <p>
     */
    public ControlArm() { requires(Robot.arm); }

    /*
     * Arm should be set to starting position.
     */
    protected void initialize() {}

    // Called repeatedly when this Command is scheduled to run
    /*
     * Voltage to motor speed
     */
    protected void execute() {
        double leftY = OI.secondaryController.getY(GenericHID.Hand.kLeft);

        Robot.arm.setArmSpeed(leftY);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() { return false; }

    // Called once after isFinished returns true
    protected void end() {}

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {}
}