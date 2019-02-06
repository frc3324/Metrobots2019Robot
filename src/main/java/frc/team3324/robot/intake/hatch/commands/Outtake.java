package frc.team3324.robot.intake.hatch.commands;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Command;
import frc.team3324.robot.Robot;
import frc.team3324.robot.util.OI;

public class Outtake extends Command {

    public Outtake() { requires(Robot.hatchIntake); }

    @Override
    protected void execute() {
        Robot.hatchIntake.intake.set(DoubleSolenoid.Value.kReverse);
    }

    @Override
    protected boolean isFinished() {
        return OI.primaryController.getBackButton();
    }
}
