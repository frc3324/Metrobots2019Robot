package frc.team3324.robot.DriveTrain.Commands.Auto;

import frc.team3324.robot.Constants;
import frc.team3324.robot.DriveTrain.DriveTrain;
import frc.team3324.robot.Robot;

import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.followers.EncoderFollower;
import jaci.pathfinder.modifiers.TankModifier;

/**
 *
 */
public class JaciPathfinding extends Command {

    private double angleDifference, turn;

    private EncoderFollower left;
    private EncoderFollower right;

    public JaciPathfinding(String path) {
        Waypoint[] Defaultpoints = new Waypoint[] {
            new Waypoint(0, 0,
                         0), // Waypoint @ x=-0, y=-0, exit angle= 0 degrees
            new Waypoint(3.048, 0, 0),
        };
        Waypoint[] LMiddlepoints = new Waypoint[] {
            new Waypoint(0, 0, 0), new Waypoint(3.556, 1.2192, 0),
        };
        Waypoint[] RMiddlepoints = new Waypoint[] {
            new Waypoint(0, 0, 0), new Waypoint(3.556, -1.9812, 0),
        };
        Waypoint[] LLLeftpoints = new Waypoint[] {
            new Waypoint(0, 0,
                         0), // Waypoint @ x=-0, y=-0, exit angle= 0 degrees
            new Waypoint(4.2672, 0.4064, Pathfinder.d2r(90)),
        };
        Waypoint[] RRRightpoints = new Waypoint[] {
            new Waypoint(0, 0, 0), new Waypoint(4.2672, -1.8288, 90),
        };

        Trajectory.Config config = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_LOW, 0.01,
                                                         Constants.DriveTrain.LOW_GEAR_MAX_VELOCITY * 0.7, 4.5, 9);
        Trajectory trajectory;

        switch (path) {
        case "LLLeft":
            trajectory = Pathfinder.generate(LLLeftpoints, config);
            break;
        case "RRRight":
            trajectory = Pathfinder.generate(RRRightpoints, config);
            break;
        case "RMiddle":
            trajectory = Pathfinder.generate(RMiddlepoints, config);
            break;
        case "LMiddle":
            trajectory = Pathfinder.generate(LMiddlepoints, config);
            break;
        default:
            trajectory = Pathfinder.generate(Defaultpoints, config);
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
        Robot.mDriveTrain.clearGyro();
        Robot.mDriveTrain.setBrakeMode();
    }

    private Notifier notifier = new Notifier(() -> {
        double lOutput         = left.calculate(DriveTrain.lEncoder.getRaw());
        double rOutput         = right.calculate(DriveTrain.rEncoder.getRaw());
        double gyroHeading     = -Robot.mDriveTrain.getYaw();       // Assuming the gyro is giving a value in degrees
        double desired_heading = Pathfinder.r2d(left.getHeading()); // Should also be in degrees
        angleDifference        = Pathfinder.boundHalfDegrees(desired_heading - gyroHeading);
        turn                   = 1.2 * (-1.0 / 80.0) * angleDifference;
        SmartDashboard.putNumber("lOutput", lOutput);
        SmartDashboard.putNumber("rOutput", rOutput);
        SmartDashboard.putBoolean("JaciFinished", left.isFinished() && right.isFinished());
        Robot.mDriveTrain.mDrive.tankDrive(-(lOutput + turn), -(rOutput - turn), false);
    });

    // Called just before this Command runs the first time
    protected void initialize() { notifier.startPeriodic(0.01); }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
            return left.isFinished();
    }

    // Called once after isFinished returns true
    protected void end() {
        notifier.stop();
        SmartDashboard.putBoolean("JaciFinished", true);
        Robot.mDriveTrain.mDrive.tankDrive(0, 0, false);
        Robot.mDriveTrain.setCoastMode();
    }
}
