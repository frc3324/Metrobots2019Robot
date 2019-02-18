package frc.team3324.robot.climber.commands.teleop;

import frc.team3324.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Command class to push down the climber.
 */
public class ToggleFrontClimb extends Command {

    public ToggleFrontClimb() { super("ToggleFrontClimb"); }

    protected void execute() { Robot.climber.switchFrontClimb(); }

    @Override
    protected boolean isFinished() {
        return true;
    }
}
