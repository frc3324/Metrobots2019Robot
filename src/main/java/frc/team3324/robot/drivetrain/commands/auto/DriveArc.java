package frc.team3324.robot.drivetrain.commands.auto;

import frc.team3324.robot.util.Constants;
import frc.team3324.robot.drivetrain.DriveTrain;
import frc.team3324.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *	This class can be edited to incorporate rotate at some point, if the radius is zero.
 */
public class DriveArc extends Command {

    private double circleAngle, circleRadius;
    private double leftSideSpeed, rightSideSpeed;
    private double innerSpeed;
    private double outerDistance;

    private boolean isFinished;

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
        outerDistance = (circleAngle / 360) * (2 * Math.PI) *
            ((circleRadius + (Constants.DriveTrain.DISTANCE_BETWEEN_WHEELS / 2)));
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
        // Turning left
        if (circleAngle < 0) {

            /*
    		 * When turning left, the right wheels have to travel more than the left wheels.
    		 *
    		 * Use the formula for circumference, 2PIr to find the circumference of the circle according to the
    		 * said radius.
    		 *
    		 * Input an angle and divide by the total degrees in a circle to find the amount of the circle the robot
    		 * will travel around.
    		 *
    		 *  Find the distance per each side of the wheels, as the right side will be traveling more than the left side.
    		 *  Two circles will thus be derived from the center of the robot, one for each side of the wheels.
    		 *
    		 *  leftDistance and rightDistance are in inches, and are not yet converted to encoder pulses.
    		*/

            double rightDistance = outerDistance;

            /*
    		 * Encoder pulse count is used to check that the robot has traveled the correct distance.
    		 *
    		 * It does not matter which side is checked, but for consistency, the outside side has been chosen.
    		 *
    		 * The outside side is then converted into encoder pulses. This encoder distance pulse is then compared to how far
    		 * the robot has already traveled. If the distance to travel is over 0.5 encoder pulses, the outside side,
    		 * the right side, will be set to full speed at 1.0. The left side will follow the proportion from the leftDistance
    		 * and the rightDistance in order to accommodate to the outside wheels. This ensures that the turn is at the maximum
    		 * speed within the circle constraints.
    		 *
    		 * The deadband for the encoder distance checking can be edited.
    		 *
    		 * If the encoder distance to travel is past 0.5, the command will be finished, and will stop running. Otherwise,
    		 * the command will be keep running until complete.
    		 */

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
            /*
    		 * When turning right, the left wheels have to travel more than the right wheels.
    		 * The math is the same as the left turn, except the wheels are switched.
    		 */
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

        // If the robot has an angle of zero, the robot will not move. This will be changed to incorporate rotate at some point.
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
