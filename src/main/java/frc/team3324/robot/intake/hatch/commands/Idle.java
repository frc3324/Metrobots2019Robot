package frc.team3324.robot.intake.hatch.commands;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Command;
import frc.team3324.robot.Robot;

public class Idle extends Command {

    public Idle() { requires(Robot.hatchIntake); }

    @Override
    protected void execute() {
        Robot.hatchIntake.intake.set(DoubleSolenoid.Value.kOff);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}
