package frc.team3324.robot.drivetrain.commands.teleop;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.PIDCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team3324.robot.Robot;
import frc.team3324.robot.util.Constants;
import frc.team3324.robot.util.OI;

public class NinetyLeft extends Command {

    private double goal = Math.toRadians(-90);
    private double kP = 0.7;
    private double kI = 0.001;
    private double kD = 0;
    private double integral = 0;
    private double error;
    private Notifier notifier = new Notifier(() ->{ executePID(); });


    public NinetyLeft() {
        requires(Robot.driveTrain);
    }

    @Override
    protected void initialize() {
        integral = 0;
        notifier.startPeriodic(0.01);
    }

    private void executePID() {
        double position = Math.toRadians(Robot.driveTrain.getYaw());
        error = goal - position;
        double proportional = error * kP;
        integral = integral + error;
        double calculatedValue = proportional + (integral * kI);
        Robot.driveTrain.mDrive.arcadeDrive(0, -calculatedValue, false);
    }
    @Override
    protected boolean isFinished() {
        return OI.primaryController.getYButton() || OI.primaryController.getY(GenericHID.Hand.kLeft) > 0.1 || OI.primaryController.getY(GenericHID.Hand.kRight) > 0.1;
    }

    @Override
    protected void end() {
        notifier.stop();
        notifier.stop();
    }

    @Override
    protected void interrupted() {
        notifier.stop();
        end();
    }
}