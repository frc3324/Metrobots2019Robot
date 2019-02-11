package frc.team3324.robot.climber.commands.teleop;

import frc.team3324.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Command class to push up the climber.
 */
public class PushUp extends Command {

    public PushUp() { super("PushUp"); }

    protected void initialize() {}

    protected void execute() { Robot.climber.pushUp(); }

    @Override
    protected boolean isFinished() {
        return true;
    }
}
