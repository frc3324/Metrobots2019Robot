package frc.team3324.robot.DriveTrain.Commands.Auto;

import badlog.lib.BadLog;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team3324.robot.util.Constants;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;

import java.io.File;

public class PathGenerator {
    public enum path {
        LEFT_CLOSE_HUMAN_STATION, LEFT_CLOSE_ROCKET, DEFAULT
    }

    public static Trajectory generateTrajectory(path path, boolean readFromFile) {
        System.out.println("Generating Trajectory");
        Trajectory trajectory;
        File file;

        String defaultPath = "/home/lvuser/default.traj";
        String leftCloseHumanStationPath = "/home/lvuser/leftCloseHumanStation.traj";
        String leftCloseRocketPath = "/home/lvuser/leftCloseRocket.traj";

        Waypoint[] defaultPoints = new Waypoint[]{
                new Waypoint(0, 0,
                        0), // Waypoint @ x=-0, y=-0, exit angle= 0 degrees
                new Waypoint(3, 0, 0),
        };
        Waypoint[] leftCloseRocket = new Waypoint[]{
                new Waypoint(feetToMeters(1.442), feetToMeters(24.842), 0),
                new Waypoint(feetToMeters(16.409), feetToMeters(24.693), Pathfinder.d2r(30))
        };

        Waypoint[] leftCloseHumanStation = new Waypoint[]{
                new Waypoint(feetToMeters(16.409), feetToMeters(24.693), Pathfinder.d2r(30)),
                new Waypoint(feetToMeters(1.442), feetToMeters(24.842), 0),
        };

        Trajectory.Config config = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, 0.01,
                Constants.DriveTrain.LOW_GEAR_MAX_VELOCITY * 0.7, 4.5, 9);

        if (readFromFile) {
            try {
                switch (path) {
                    case LEFT_CLOSE_HUMAN_STATION:
                        file = new File(leftCloseHumanStationPath);
                        trajectory = Pathfinder.readFromFile(file);
                        SmartDashboard.putBoolean("Running Left Close Human Station", true);
                        break;
                    case LEFT_CLOSE_ROCKET:
                        file = new File(leftCloseRocketPath);
                        trajectory = Pathfinder.readFromFile(file);
                        SmartDashboard.putBoolean("Running Left Close Rocket", true);
                        break;
                    default:
                        file = new File(defaultPath);
                        trajectory = Pathfinder.readFromFile(file);
                        break;
                }
            } catch (Exception CouldNotRunAuto) {
                BadLog.createValue("Auto Read Failed", "true");
                System.err.println("Pathfinder Read Failed");
                trajectory = Pathfinder.generate(defaultPoints, config);
            }
        } else {
            switch (path) {
                case LEFT_CLOSE_HUMAN_STATION:
                    trajectory = Pathfinder.generate(leftCloseHumanStation, config);
                    file = new File(leftCloseHumanStationPath);
                    Pathfinder.writeToFile(file, trajectory);
                    SmartDashboard.putBoolean("Running Left Close Human Station", true);
                    break;
                case LEFT_CLOSE_ROCKET:
                    trajectory = Pathfinder.generate(leftCloseRocket, config);
                    file = new File(leftCloseRocketPath);
                    Pathfinder.writeToFile(file, trajectory);
                    SmartDashboard.putBoolean("Running Left Close Rocket", true);
                    break;
                default:
                    trajectory = Pathfinder.generate(defaultPoints, config);
                    file = new File(defaultPath);
                    Pathfinder.writeToFile(file, trajectory);
                    break;
            }
        }
        return trajectory;
    }
    public static double feetToMeters(double feet) {
        return feet * 0.3048;
    }
}
