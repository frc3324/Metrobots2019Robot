package frc.team3324.robot.drivetrain.commands.auto;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.RobotBase;
import frc.team3324.robot.util.Constants;
import frc.team3324.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

import frc.team3324.robot.util.OI;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.followers.EncoderFollower;
import jaci.pathfinder.modifiers.TankModifier;

//import static frc.team3324.robot.drivetrain.commands.auto.PathfinderShuffleboard.*;
import static frc.team3324.robot.drivetrain.commands.auto.PathGenerator.*;

/**
 * Command class to follow a path.
 */
public class JaciPathfinding extends Command {

    private double angleDifference, turn;
    private boolean reversed, readFromFile;
    private PathGenerator.path path;
    Odometry odometry;

    private EncoderFollower left;
    private EncoderFollower right;
    private Notifier notifier = new Notifier(() -> { followPath(); });

    /**
     * Creates an instance of the JaciPathfinding class.
     *
     * @param path, path to follow
     * @param readFromFile, whether to read path from file
     * @param reversed, whether path reversed
     * @see PathGenerator.path
     */
    public JaciPathfinding(PathGenerator.path path, boolean readFromFile, boolean reversed) {
        this.reversed = reversed;
        this.path = path;
        this.readFromFile = readFromFile;
        requires(Robot.driveTrain);
        Trajectory trajectory = generateTrajectory(path, readFromFile);
        TankModifier modifier = new TankModifier(trajectory).modify(Constants.DriveTrain.DISTANCE_BETWEEN_WHEELS);
        left = new EncoderFollower(modifier.getLeftTrajectory());
        right = new EncoderFollower(modifier.getRightTrajectory());
        if (reversed) {
            left.configureEncoder(-Robot.driveTrain.lEncoder.getRaw(), Constants.DriveTrain.TICKS,
                    Constants.DriveTrain.WHEEL_DIAMETER_METERS);
            right.configureEncoder(-Robot.driveTrain.rEncoder.getRaw(), Constants.DriveTrain.TICKS,
                    Constants.DriveTrain.WHEEL_DIAMETER_METERS);
        } else {
            left.configureEncoder(Robot.driveTrain.lEncoder.getRaw(), Constants.DriveTrain.TICKS,
                    Constants.DriveTrain.WHEEL_DIAMETER_METERS);
            right.configureEncoder(Robot.driveTrain.rEncoder.getRaw(), Constants.DriveTrain.TICKS,
                    Constants.DriveTrain.WHEEL_DIAMETER_METERS);
        }
        left.configurePIDVA(0.3, 0, 0, 2.6516 / 12, 0.0070);
        right.configurePIDVA(0.3, 0.0, 0,2.6660 / 12, 0.3057);
        Robot.driveTrain.setBrakeMode();
    }

    // Called just before this Command runs the first time
    protected void initialize() {
        Robot.driveTrain.clearGyro();
        notifier.startPeriodic(0.02);
    }

    private void followPath() {
        if (reversed) {
            followReversed();
        } else if (!reversed) {
            follow();
        }
    }

    private void follow() {
        double lOutput = left.calculate(Robot.driveTrain.lEncoder.getRaw());
        double rOutput = right.calculate(Robot.driveTrain.rEncoder.getRaw());
        double gyroHeading = Robot.driveTrain.getYaw(); // Assuming the gyro is giving a value in degrees
        double desiredHeading = Pathfinder.r2d(left.getHeading()); // Should also be in degrees
        angleDifference = Pathfinder.boundHalfDegrees(desiredHeading - gyroHeading);
        turn = 0.8 * (-1.0 / 80.0) * angleDifference;
        double leftSpeed = (lOutput + turn);
        double rightSpeed = (rOutput - turn);
        double leftFeedforward = 1.5 * Math.signum(leftSpeed) / 12;
        double rightFeedforward = 1.8 * Math.signum(rightSpeed) / 12;
        Robot.driveTrain.mDrive.tankDrive(leftSpeed + leftFeedforward, rightSpeed + rightFeedforward, false);

        try {
            updateShuffleBoard(lOutput, rOutput, gyroHeading, desiredHeading);
        } catch (Exception e) { System.err.println("Failed to put ShuffleBoard data"); }
    }

    private void followReversed() {
        double lOutput = left.calculate(-Robot.driveTrain.lEncoder.getRaw());
        double rOutput = right.calculate(-Robot.driveTrain.rEncoder.getRaw());
        double gyroHeading = Robot.driveTrain.getYaw() + 180; // Assuming the gyro is giving a value in degrees
        double desiredHeading = Pathfinder.r2d(left.getHeading()); // Should also be in degrees
        angleDifference = Pathfinder.boundHalfDegrees(desiredHeading - gyroHeading);
        turn = 2 * (-1.0 / 80.0) * angleDifference;
        Robot.driveTrain.mDrive.tankDrive(-(rOutput - turn), -(lOutput + turn), false);

        try {
            updateShuffleBoard(lOutput, rOutput, gyroHeading, desiredHeading);
        } catch (Exception e) { System.err.println("Failed to put ShuffleBoard data"); }
    }

    private void updateShuffleBoard(double lOutput, double rOutput, double gyroHeading, double desiredHeading) {
      //  leftOutput.setDouble(lOutput);
       // rightOutput.setDouble(rOutput);
        //finished.setBoolean(left.isFinished() && right.isFinished() && angleDifference < 3);
        PathfinderShuffleboard.desiredHeading.setDouble(desiredHeading);
        if (reversed) {
           // heading.setDouble(gyroHeading);
        } else {
          //  heading.setDouble(gyroHeading);
        }
        //angleError.setDouble(angleDifference);
        //headingCorrectSpeed.setDouble(turn);
    }

    // Called once after isFinished returns true
    protected void end() {
        notifier.stop();
        notifier.stop();
        Robot.driveTrain.mDrive.tankDrive(0, 0, false);
    }
    protected boolean isFinished() {
        return (left.isFinished() && right.isFinished() && Math.abs(angleDifference) < 3) || (OI.primaryController.getX(GenericHID.Hand.kLeft) > 0.05) || OI.primaryController.getXButton();
    }

    @Override
    protected void interrupted() {
        notifier.stop();
        end();
    }
}
