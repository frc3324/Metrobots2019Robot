package frc.team3324.robot.arm.commands;

import frc.team3324.robot.util.OI;
import frc.team3324.robot.Robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.command.Command;

public class ControlArm extends Command {

    public ControlArm() { requires(Robot.arm); }

    protected void initialize() {
    }

    /*
     * Voltage to motor speed
     */
    protected void execute() {
        System.out.println("here");
        double leftY = OI.secondaryController.getY(GenericHID.Hand.kLeft);

        Robot.arm.setArmSpeed(-leftY * 0.3);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() { return false; }

    protected void end() {}

    protected void interrupted() {}
}