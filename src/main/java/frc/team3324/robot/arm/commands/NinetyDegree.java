package frc.team3324.robot.arm.commands;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.command.PIDCommand;
import frc.team3324.robot.Robot;
import frc.team3324.robot.util.OI;

public class NinetyDegree extends PIDCommand {

    private double goal = Math.toRadians(90);

    public NinetyDegree() {
        super(0.7, 0.0001, 1, 0.02);
        requires(Robot.arm);
    }

    @Override
    protected void initialize() {
        super.setSetpoint(goal);
    }

    @Override
    protected boolean isFinished() {
        return (goal == getPosition() || (OI.secondaryController.getY(GenericHID.Hand.kLeft) > 0) || (OI.secondaryController.getY(GenericHID.Hand.kRight) > 0) || (OI.secondaryController.getBButton()));
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
