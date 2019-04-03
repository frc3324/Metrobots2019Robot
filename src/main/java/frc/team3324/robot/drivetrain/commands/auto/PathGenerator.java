package frc.team3324.robot.drivetrain.commands.auto;

import badlog.lib.BadLog;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team3324.robot.util.Constants;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;

import java.io.File;
import java.nio.file.Path;

/**
 * Class to generate paths or read paths from file.
 */
public class PathGenerator {
    public enum path {
        LEFT_LEVEL_1,
        LEFT_LEVEL_2,
        RIGHT_LEVEL_1,
        RIGHT_LEVEL_2
    }

    /**
     * Generates path from specified file.
     *
     * @param   path, (enum) path to take
     * @param   readFromCSV, whether to read path from file
     * @return  trajectory, from CSV file
     * @see     Trajectory
     * @see     Waypoint
     */
    public static Trajectory generateTrajectory(path path, boolean readFromCSV) {
        System.out.println("Generating Trajectory");
        Trajectory trajectory;
        File file;

        String leftLevelOne = "/home/lvuser/deploy/paths/leftLevelOne.pf1.csv";
        String leftLevelTwo = "/home/lvuser/deploy/paths/leftLevelTwo.pf1.csv";

        String rightLevelOne = "/home/lvuser/deploy/paths/rightLevelOne.pf1.csv";
        String rightLevelTwo = "/home/lvuser/deploy/paths/rightLevelTwo.pf1.csv";

        Waypoint[] defaultPoints = new Waypoint[] {
            new Waypoint(0, 0,
                         0), // Waypoint @ x=-0, y=-0, exit angle= 0 degrees
            new Waypoint(2, 0, 0),
        };

        Trajectory.Config config =
            new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_FAST, 0.01,
                                  Constants.DriveTrain.HIGH_GEAR_MAX_VELOCITY, Constants.DriveTrain.HIGH_GEAR_MAX_ACCELERATION, 9);

        try {
            switch (path) {
                case LEFT_LEVEL_1:
                file = new File(leftLevelOne);
                trajectory = Pathfinder.readFromCSV(file);
                break;
                case LEFT_LEVEL_2:
                file = new File(leftLevelTwo);
                trajectory = Pathfinder.readFromCSV(file);
                break;
                case RIGHT_LEVEL_1:
                file = new File(rightLevelOne);
                trajectory = Pathfinder.readFromCSV(file);
                break;
                case RIGHT_LEVEL_2:
                file = new File(rightLevelTwo);
                trajectory = Pathfinder.readFromCSV(file);
                break;
                default:
                trajectory = Pathfinder.generate(defaultPoints, config);
                break;

            }
        } catch (Exception CouldNotRunAuto) {
            BadLog.createValue("Auto Read Failed", "true");
            System.err.println("Pathfinder Read Failed");
            trajectory = Pathfinder.generate(defaultPoints, config);
        }
        return trajectory;
    }

    public static double feetToMeters(double feet) { return feet * 0.3048; }
}