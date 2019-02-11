package frc.team3324.robot.intake.cargo.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.team3324.robot.Robot;
import frc.team3324.robot.util.OI;

/**
 * Command class to activate outtake of cargo intake/outtake system through controller.
 */
public class Outtake extends Command {

    public Outtake() {
        requires(Robot.cargoIntake);
        setTimeout(1);
    }

    @Override
    protected void execute() {
        Robot.cargoIntake.intakeMotor.set(-1);
    }

    @Override
    protected boolean isFinished() {
        return OI.primaryController.getStartButton();
    }
}