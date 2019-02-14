package frc.team3324.robot.arm.commands;

import edu.wpi.first.wpilibj.command.PIDCommand;
import frc.team3324.robot.Robot;

public class NinetyDegree extends PIDCommand {

    private double goal = Math.PI/2.0;

    public NinetyDegree() {
        super(0, 0, 0);
        requires(Robot.arm);
    }

    @Override
    protected void initialize() {
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
        Robot.arm.updateShuffleBoard();
        Robot.arm.setArmSpeed(output);
    }
}
