package frc.team3324.robot.intake.cargo.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.team3324.robot.Robot;

public class CargoFeedforward extends Command {
    public CargoFeedforward() {
        requires(Robot.cargoIntake);
    }


    @Override
    protected void execute() {
        Robot.cargoIntake.intakeMotor.set(0.2);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}

