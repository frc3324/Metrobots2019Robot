package frc.team3324.robot.arm.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.team3324.robot.Robot;

public class ResetArm extends Command {

    public ResetArm() {
        requires(Robot.arm);
    }
    @Override
    protected void execute() {
        Robot.arm.resetArmSpeed(0.5);
    }

    @Override
    protected boolean isFinished() {
        return Robot.arm.getFrontSwitch();
    }

    @Override
    protected void end() {
    }
}
