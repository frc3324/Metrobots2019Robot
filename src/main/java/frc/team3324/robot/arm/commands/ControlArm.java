package frc.team3324.robot.arm.commands;

import frc.team3324.robot.util.OI;
import frc.team3324.robot.Robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.command.Command;

/**
 * Command class to control the arm using a controller.
 */
public class ControlArm extends Command {

    /**
     * Creates an instance of the ControlArm class.
     */
    public ControlArm() { requires(Robot.arm); }

    protected void initialize() {
    }

    protected void execute() {
        double leftY = OI.secondaryController.getY(GenericHID.Hand.kLeft);
        Robot.arm.updateShuffleBoard();
        Robot.arm.setArmSpeed(leftY * 0.5);
    }

    protected boolean isFinished() { return false; }

    protected void end() {}

    protected void interrupted() {}
}