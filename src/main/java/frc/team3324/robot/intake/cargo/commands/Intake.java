package frc.team3324.robot.intake.cargo.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.team3324.robot.Robot;
import frc.team3324.robot.util.OI;

public class Intake extends Command {

    public Intake() { requires(Robot.cargoIntake); }

    @Override
    protected void execute() {
        Robot.cargoIntake.intakeMotor.set(1);
    }

    @Override
    protected boolean isFinished() {
        return OI.primaryController.getStartButton() || Robot.cargoIntake.intakeLimitSwitch.get();
    }
}
