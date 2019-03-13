package frc.team3324.robot.arm.commands;

import edu.wpi.first.wpilibj.GenericHID;
import frc.team3324.robot.Robot;
import frc.team3324.robot.util.OI;
import frc.team3324.robot.wrappers.PIDCommand;

public class ZeroDegree extends PIDCommand {

    public ZeroDegree() {
        super(0.5, 0.001, 0, 0.0175, Robot.arm, 0.01);
    }

    @Override
    protected void usePIDOutput(double output) {
        Robot.arm.updateShuffleBoard();
        Robot.arm.setArmSpeed(output);
    }

    @Override
    protected double returnPIDInput() {
        return Robot.arm.getArmRadians();
    }

    @Override
    protected boolean isFinished() {
        return (OI.secondaryController.getY(GenericHID.Hand.kLeft) > 0) || (OI.secondaryController.getBButton());
    }
}


