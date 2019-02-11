package frc.team3324.robot.arm.commands;

import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team3324.robot.Robot;

public class OneEightyDegree extends PIDCommand{

    private double goal = Math.PI;

    public OneEightyDegree() {
        super(1/Math.PI, 0, 0);
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
        SmartDashboard.putNumber("Output", output);
        Robot.arm.setArmSpeed(output);
    }
}
