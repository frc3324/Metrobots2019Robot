package frc.team3324.robot.drivetrain.commands.Auto;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import frc.team3324.robot.util.Constants;
import frc.team3324.robot.drivetrain.DriveTrain;
import frc.team3324.robot.Robot;

import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.followers.EncoderFollower;
import jaci.pathfinder.modifiers.TankModifier;

import java.io.File;

public class JaciPathfinding extends Command {
    public enum path {
        TOP_HATCH
    }

    private ShuffleboardTab pathfinderTab = Shuffleboard.getTab("Pathfinder");
    private NetworkTableEntry leftOutput = pathfinderTab.add("Left Motor Output", 0).withPosition(0,0).getEntry();
    private NetworkTableEntry rightOutput = pathfinderTab.add("Right Motor Output", 0).withPosition(1,0).getEntry();
    private NetworkTableEntry finished = pathfinderTab.add("Finished", false).withPosition(2,0).getEntry();

    private NetworkTableEntry desiredHeading = pathfinderTab.add("Desired Heading", 0).withPosition(0,1).getEntry();
    private NetworkTableEntry heading = pathfinderTab.add("Current Heading",0).withPosition(1,1).getEntry();
    private NetworkTableEntry angleError = pathfinderTab.add("Angle Difference",0).withPosition(2,1).getEntry();
    private NetworkTableEntry headingCorrectSpeed = pathfinderTab.add("Heading Correct Speed",0).withPosition(3,1).getEntry();


    private double angleDifference, turn;

    private EncoderFollower left;
    private EncoderFollower right;

    public JaciPathfinding(path path) {
        Waypoint[] defaultPoints = new Waypoint[] {
            new Waypoint(0, 0,
                         0), // Waypoint @ x=-0, y=-0, exit angle= 0 degrees
            new Waypoint(3.048, 0, 0),
        };
        Waypoint[] topFirstHatch = new Waypoint[] {
                new Waypoint(feetToMeters(1.322), feetToMeters(17.946), 0),
                new Waypoint(feetToMeters(21.639), feetToMeters(17.215), Pathfinder.d2r(270))
        };

        Trajectory.Config config = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_FAST, 0.01,
                                                         Constants.DriveTrain.LOW_GEAR_MAX_VELOCITY * 0.7, 4.5, 9);
        Trajectory trajectory;
        switch (path) {
        case TOP_HATCH:
            trajectory = Pathfinder.generate(topFirstHatch, config);
            File myFile = new File("/home/lvuser/topHatch.traj");
            Pathfinder.writeToFile(myFile, trajectory);
            SmartDashboard.putBoolean("Running Top Hatch", true);
            break;
        default:
            trajectory = Pathfinder.generate(defaultPoints, config);
            break;
        }
        TankModifier modifier = new TankModifier(trajectory).modify(Constants.DriveTrain.DISTANCE_BETWEEN_WHEELS);
        left                  = new EncoderFollower(modifier.getLeftTrajectory());
        right                 = new EncoderFollower(modifier.getRightTrajectory());
        left.configureEncoder(DriveTrain.lEncoder.getRaw(), Constants.DriveTrain.PULSES,
                              Constants.DriveTrain.WHEEL_DIAMETER_METERS);
        right.configureEncoder(DriveTrain.rEncoder.getRaw(), Constants.DriveTrain.PULSES,
                               Constants.DriveTrain.WHEEL_DIAMETER_METERS);
        left.configurePIDVA(0.3, 0.0, 0, 1 / Constants.DriveTrain.LOW_GEAR_MAX_VELOCITY, 0);
        right.configurePIDVA(0.3, 0.0, 0, 1 / Constants.DriveTrain.LOW_GEAR_MAX_VELOCITY, 0);
        Robot.driveTrain.clearGyro();
        Robot.driveTrain.setBrakeMode();
    }

    private Notifier notifier = new Notifier(() -> {
        SmartDashboard.putBoolean("Running", true);
        double lOutput         = left.calculate(DriveTrain.lEncoder.getRaw());
        double rOutput         = right.calculate(DriveTrain.rEncoder.getRaw());
        double gyroHeading     = -Robot.driveTrain.getYaw();       // Assuming the gyro is giving a value in degrees
        double desiredHeading = Pathfinder.r2d(left.getHeading()); // Should also be in degrees
        angleDifference        = Pathfinder.boundHalfDegrees(desiredHeading - gyroHeading);
        turn                   = 1.2 * (-1.0 / 80.0) * angleDifference;
        Robot.driveTrain.mDrive.tankDrive((lOutput + turn), (rOutput - turn), false);

        leftOutput.setDouble(lOutput);
        rightOutput.setDouble(rOutput);
        finished.setBoolean(left.isFinished() && right.isFinished() && angleDifference < 3);
        this.desiredHeading.setDouble(desiredHeading);
        heading.setDouble(gyroHeading);
        angleError.setDouble(angleDifference);
        headingCorrectSpeed.setDouble(turn);
    });

    // Called just before this Command runs the first time
    protected void initialize() { notifier.startPeriodic(0.01); }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
            return left.isFinished() && right.isFinished() && angleDifference < 3;
    }

    // Called once after isFinished returns true
    protected void end() {
        notifier.stop();
        SmartDashboard.putBoolean("JaciFinished", true);
        Robot.driveTrain.mDrive.tankDrive(0, 0, false);
        Robot.driveTrain.setCoastMode();
    }

    private double feetToMeters(double feet) {
       return feet * 0.3048;
    }
}
