package frc.team3324.robot.drivetrain.commands.teleop;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team3324.robot.Robot;
import frc.team3324.robot.util.OI;

public class LineUp extends Command {
    private double kP = 0.5;
    private double kI = 0.001;
    private double kD;
    private double integral = 0;

    public LineUp() {
        requires(Robot.driveTrain);
    }

    @Override
    protected void execute() {
        double error = Math.IEEEremainder(Robot.driveTrain.getYaw(), 45);
        integral = integral + error;
        double speed = (error * kP) + (integral * kI);
        Robot.driveTrain.mDrive.curvatureDrive(0, speed, true);
    }

    @Override
    protected boolean isFinished() {
        return OI.primaryController.getX(GenericHID.Hand.kRight) > 0 || OI.primaryController.getY(GenericHID.Hand.kLeft) > 0;
    }
}
