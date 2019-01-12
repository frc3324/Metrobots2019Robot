package frc.team3324.robot.drivetrain.commands.Auto;

import frc.team3324.robot.util.Constants;
import frc.team3324.robot.drivetrain.DriveTrain;
import frc.team3324.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import frc.team3324.robot.util.OI;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.followers.EncoderFollower;
import jaci.pathfinder.modifiers.TankModifier;

import static frc.team3324.robot.drivetrain.commands.Auto.PathfinderShuffleboard.*;
import static frc.team3324.robot.drivetrain.commands.Auto.PathGenerator.*;

public class JaciPathfinding extends Command {


    private double angleDifference, turn;
    private boolean reversed, readFromFile;
    private PathGenerator.path path;

    private EncoderFollower left;
    private EncoderFollower right;

    public JaciPathfinding(PathGenerator.path path, boolean readFromFile, boolean reversed) {
        this.reversed = reversed;
        this.path = path;
        this.readFromFile = readFromFile;
        requires(Robot.driveTrain);

    }

    // Called just before this Command runs the first time
    protected void initialize() {
        Trajectory trajectory = generateTrajectory(path, readFromFile);

        TankModifier modifier = new TankModifier(trajectory).modify(Constants.DriveTrain.DISTANCE_BETWEEN_WHEELS);
        left = new EncoderFollower(modifier.getLeftTrajectory());
        right = new EncoderFollower(modifier.getRightTrajectory());
        left.configureEncoder(DriveTrain.lEncoder.getRaw(), Constants.DriveTrain.PULSES,
                Constants.DriveTrain.WHEEL_DIAMETER_METERS);
        right.configureEncoder(DriveTrain.rEncoder.getRaw(), Constants.DriveTrain.PULSES,
                Constants.DriveTrain.WHEEL_DIAMETER_METERS);
        left.configurePIDVA(0.3, 0, 0, 1 / Constants.DriveTrain.LOW_GEAR_MAX_VELOCITY, 0);
        right.configurePIDVA(0.3, 0.0, 0, 1 / Constants.DriveTrain.LOW_GEAR_MAX_VELOCITY, 0);
        Robot.driveTrain.clearGyro();
        Robot.driveTrain.setBrakeMode();
        //notifier.startPeriodic(0.01);
    }
    protected void execute() {
        followPath();
    }
    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return left.isFinished() && right.isFinished() && angleDifference < 3 || OI.primaryController.getBackButton();
    }

    // Called once after isFinished returns true
    protected void end() {
       // notifier.stop();
        SmartDashboard.putBoolean("JaciFinished", true);
        Robot.driveTrain.mDrive.tankDrive(0, 0, false);
        Robot.driveTrain.setCoastMode();
    }

    private void followPath() {
        if (reversed) {
            followReversed();
        } else {
            follow();
        }
    }

    private void follow() {
        SmartDashboard.putBoolean("Running", true);
        double lOutput = left.calculate(DriveTrain.lEncoder.getRaw());
        double rOutput = right.calculate(DriveTrain.rEncoder.getRaw());
        double gyroHeading = -Robot.driveTrain.getYaw();       // Assuming the gyro is giving a value in degrees
        double desiredHeading = Pathfinder.r2d(left.getHeading()); // Should also be in degrees
        angleDifference = Pathfinder.boundHalfDegrees(desiredHeading - gyroHeading);
        turn = 1.2 * (-1.0 / 80.0) * angleDifference;
        Robot.driveTrain.mDrive.tankDrive((lOutput + turn), (rOutput - turn), false);

        try {
            updateShuffleBoard(lOutput, rOutput, gyroHeading, desiredHeading);
        } catch (Exception e) {
            System.err.println("Failed to put ShuffleBoard data");
        }
    }

    private void followReversed() {
        SmartDashboard.putBoolean("Running", true);
        double lOutput = left.calculate(-DriveTrain.lEncoder.getRaw());
        double rOutput = right.calculate(-DriveTrain.rEncoder.getRaw());
        double gyroHeading = -Robot.driveTrain.getYaw();       // Assuming the gyro is giving a value in degrees
        double desiredHeading = Pathfinder.r2d(left.getHeading()); // Should also be in degrees
        angleDifference = Pathfinder.boundHalfDegrees(desiredHeading - gyroHeading);
        turn = 1.2 * (-1.0 / 80.0) * angleDifference;
        Robot.driveTrain.mDrive.tankDrive(-(rOutput - turn), -(lOutput + turn), false);

        updateShuffleBoard(lOutput, rOutput, gyroHeading, desiredHeading);
    }

    private void updateShuffleBoard(double lOutput, double rOutput, double gyroHeading, double desiredHeading) {
        leftOutput.setDouble(lOutput);
        rightOutput.setDouble(rOutput);
        finished.setBoolean(left.isFinished() && right.isFinished() && angleDifference < 3);
        PathfinderShuffleboard.desiredHeading.setDouble(desiredHeading);
        heading.setDouble(gyroHeading);
        angleError.setDouble(angleDifference);
        headingCorrectSpeed.setDouble(turn);
    }
}
