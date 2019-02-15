package frc.team3324.robot.intake.cargo.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.team3324.robot.Robot;
import frc.team3324.robot.util.OI;

/**
 * Command class to activate intake of cargo intake/outtake system through controller or limit switch.
 */
public class Intake extends Command {

    /**
     * Creates an instance of the SwitchIntake class.
     */
    public Intake() { requires(Robot.cargoIntake); }

    @Override
    protected void execute() {
        Robot.cargoIntake.intakeMotor.set(1);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        Robot.cargoIntake.intakeMotor.set(0);
    }
}
