package frc.team3324.robot.arm.commands;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team3324.robot.Robot;
import frc.team3324.robot.util.OI;

public class OneEightyDegree extends PIDCommand{

    private double goal = 2.96705973;

    public OneEightyDegree() {
        super(0.25, 0.005, 1);
        requires(Robot.arm);
    }

    @Override
    protected void initialize() {
        super.setSetpoint(goal);
    }

    @Override
    protected boolean isFinished() {
        return (goal == getPosition() || (OI.secondaryController.getY(GenericHID.Hand.kLeft) > 0));
    }

    @Override
    protected double returnPIDInput() {
        return Robot.arm.getArmRadians();
    }


    @Override
    protected void usePIDOutput(double output) {
        double feedforward = 0.2 * Math.cos(Robot.arm.getArmRadians());
        Robot.arm.updateShuffleBoard();
        Robot.arm.setArmSpeed(output + feedforward);
    }
}
