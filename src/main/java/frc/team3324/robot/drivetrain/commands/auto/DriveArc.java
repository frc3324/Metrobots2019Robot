package frc.team3324.robot.drivetrain.commands.auto;

import frc.team3324.robot.drivetrain.DriveTrain;
import frc.team3324.robot.Robot;
import frc.team3324.robot.util.Constants;

import edu.wpi.first.wpilibj.command.Command;

/**
 *	Command class to drive robot in an arc. This class can be edited to incorporate
 *	rotate at some point if the radius is zero.
 */
public class DriveArc extends Command {

    private double circleAngle, circleRadius;
    private double leftSideSpeed, rightSideSpeed;
    private double innerSpeed;
    private double outerDistance;

    private boolean isFinished;

    /**
     * Creates an instance of the DriveArc class.
     *
     * @param angle, in degrees from 0 to 360
     * @param radius, in meters (probably)
     */
    public DriveArc(double angle, double radius) {
        requires(Robot.driveTrain);
        circleAngle = angle;
        circleRadius = radius;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        Robot.driveTrain.mDrive.tankDrive(0.0, 0.0, false);

        DriveTrain.clearEncoder();

        innerSpeed = ((circleRadius * (2 / Constants.DriveTrain.DISTANCE_BETWEEN_WHEELS)) - 1) /
            ((circleRadius * (2 / Constants.DriveTrain.DISTANCE_BETWEEN_WHEELS)) + 1);
        outerDistance = (circleAngle / 360) * (2 * Math.PI) * ((circleRadius + (Constants.DriveTrain.DISTANCE_BETWEEN_WHEELS / 2)));
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        // Turning left
        if (circleAngle < 0) {
            double rightDistance = outerDistance;
            double encoderDifference = rightDistance - DriveTrain.rEncoder.getDistance();

            if (Math.abs(encoderDifference) < 0.5) {
                isFinished = true;
            } else {
                leftSideSpeed = innerSpeed;
                rightSideSpeed = 1.0;
                isFinished = false;
            }
        }

        // Turning right
        else if (circleAngle > 0) {
            double leftDistance = outerDistance;
            double encoderDifference = (leftDistance - DriveTrain.lEncoder.getDistance());

            if (Math.abs(encoderDifference) < 0.1 || encoderDifference < 0) {
                isFinished = true;
            } else {
                rightSideSpeed = innerSpeed;
                leftSideSpeed = 1.0;
                isFinished = false;
            }
        }

        // Turning no direction
        else {
            leftSideSpeed = 0;
            rightSideSpeed = 0;
        }
        Robot.driveTrain.mDrive.tankDrive(-leftSideSpeed, -rightSideSpeed, false);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() { return isFinished; }

    // Called once after isFinished returns true
    protected void end() { Robot.driveTrain.mDrive.tankDrive(0.0, 0.0, false); }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {}
}
