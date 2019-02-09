package frc.team3324.robot.arm.commands;

import edu.wpi.first.wpilibj.command.PIDCommand;
import frc.team3324.robot.Robot;

public class ZeroDegree extends PIDCommand{

    private double goal = 0;

    public ZeroDegree() {
        super(0, 0, 0);
    }

    @Override
    protected void initialize() {
        requires(Robot.arm);
        super.setSetpoint(goal);
    }

    @Override
    protected boolean isFinished() {
        return (goal == getPosition());
    }

    @Override
    protected double returnPIDInput() {
        return Robot.arm.getArmRadians();
    }


    @Override
    protected void usePIDOutput(double output) {
        Robot.arm.setArmSpeed(output);
    }
}


