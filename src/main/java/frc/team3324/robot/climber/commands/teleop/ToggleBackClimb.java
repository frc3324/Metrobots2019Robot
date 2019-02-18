package frc.team3324.robot.climber.commands.teleop;

import frc.team3324.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Command class to push up the climber.
 */
public class ToggleBackClimb extends Command {

    public ToggleBackClimb() { super("ToggleBackClimb"); }

    protected void execute() { Robot.climber.switchBackClimb(); }

    @Override
    protected boolean isFinished() {
        return true;
    }
}
