package frc.team3324.robot.intake.hatch.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.team3324.robot.Robot;
import frc.team3324.robot.util.OI;

/**
 * Command class to set hatch intake/outtake system double solenoid to forward status.
 */
public class Intake extends Command {

    /**
     * Creates an instance of the Intake class.
     */
    public Intake() { requires(Robot.hatchIntake); }

    @Override
    protected void execute() {
        Robot.hatchIntake.setHatchIntake();
    }

    @Override
    protected boolean isFinished() {
        return OI.primaryController.getBackButton();
    }
}
