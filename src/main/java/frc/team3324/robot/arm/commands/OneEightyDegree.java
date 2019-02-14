package frc.team3324.robot.arm.commands;

import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team3324.robot.Robot;

public class OneEightyDegree extends PIDCommand{

    private double goal = (35 *Math.PI)/36;

    public OneEightyDegree() {
        super(0.45, 0.003, 0.01);
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
