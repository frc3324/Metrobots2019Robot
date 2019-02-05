package frc.team3324.robot.climber.commands.teleop;

import frc.team3324.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class PushDown extends Command {

    private boolean gearClimberStatus = false;

    public PushDown() { super("PushDown"); }

    protected void initialize() {}

    protected void executed() {
        Robot.climber.pushDown();

    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}
