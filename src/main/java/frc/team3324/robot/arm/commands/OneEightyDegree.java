package frc.team3324.robot.arm.commands;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.command.Command;
import frc.team3324.robot.Robot;
import frc.team3324.robot.util.OI;

public class OneEightyDegree extends Command {

    private double goal = Math.toRadians(180);
    private double kP = 0.55;
    private double kI = 0.001;
    private double kD = 0;
    private double integral = 0;
    private double error;
    private Notifier notifier = new Notifier(() ->{ executePID(); });

    public OneEightyDegree() {
        requires(Robot.arm);
    }

    @Override
    protected void initialize() {
        Robot.oi.zeroDegree.stopNotifier();
        integral = 0;
        notifier.startPeriodic(0.01);
    }

    private void executePID() {
        double position = Robot.arm.getArmRadians();
        error = goal - position;
        double proportional = error * kP;
        integral = integral + error;
        Robot.arm.updateShuffleBoard();
        Robot.arm.setArmSpeed(proportional + (integral * kI));
    }
    @Override
    protected boolean isFinished() {
        return (OI.secondaryController.getY(GenericHID.Hand.kLeft) > 0) || (OI.secondaryController.getBButton());
    }


    public void stopNotifier() {
        notifier.stop();
        notifier.stop();
    }

    @Override
    protected void end() {
        stopNotifier();
    }

    @Override
    protected void interrupted() {
        notifier.stop();
        end();
    }
    protected void usePIDOutput(double output) {
        Robot.arm.updateShuffleBoard();
        Robot.arm.setArmSpeed(output);
    }
}

