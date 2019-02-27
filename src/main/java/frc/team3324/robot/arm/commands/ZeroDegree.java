package frc.team3324.robot.arm.commands;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team3324.robot.Robot;
import frc.team3324.robot.util.Constants;
import frc.team3324.robot.util.OI;

public class ZeroDegree extends PIDCommand{

    private double goal = 0.0175;

    public ZeroDegree() {
        super(0.25, 0.002, 1);
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
        Robot.arm.updateShuffleBoard();
        Robot.arm.setArmSpeed(output);
    }
}


