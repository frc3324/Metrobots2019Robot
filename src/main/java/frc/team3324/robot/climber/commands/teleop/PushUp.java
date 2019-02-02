package frc.team3324.robot.climber.commands.teleop;

import frc.team3324.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class PushUp extends Command {

    private boolean gearClimberStatus = false;

    public PushUp() { super("PushUp"); }

    protected void initialize() {}

    protected void executed() {
        Robot.climber.pushUp();

    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}
