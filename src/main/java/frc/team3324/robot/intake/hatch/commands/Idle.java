package frc.team3324.robot.intake.hatch.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.team3324.robot.Robot;

/**
 * Command class to turn off hatch intake/outtake system pneumatics.
 */
public class Idle extends Command {

    /**
     * Creates an instance of the Idle class.
     */
    public Idle() { requires(Robot.hatchIntake); }

    @Override
    protected void execute() {
        Robot.hatchIntake.initDefaultCommand();
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}
