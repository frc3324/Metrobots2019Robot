package frc.team3324.robot.intake.hatch.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.team3324.robot.Robot;
import frc.team3324.robot.util.OI;

/**
 * Command class to set hatch intake/outtake system double solenoid to reverse status.
 */
public class Outtake extends Command {

    /**
     * Creates an instance of the Outtake class.
     */
    public Outtake() { requires(Robot.hatchIntake); }

    @Override
    protected void execute() {
        Robot.hatchIntake.setHatchOuttake();
    }

    @Override
    protected boolean isFinished() {
        return OI.primaryController.getBackButton();
    }
}
