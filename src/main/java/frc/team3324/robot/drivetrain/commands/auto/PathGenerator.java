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
        LEFT_CLOSE_HUMAN_STATION,
        LEFT_CLOSE_ROCKET,
        LEFT_CLOSE_CARGO,
        LEFT_MIDDLE_CARGO,
        LEFT_FAR_CARGO,
        LEFT_MIDDLE_ROCKET,
        LEFT_FAR_ROCKET,
        RIGHT_CLOSE_ROCKET,
        RIGHT_MIDDLE_ROCKET,
        RIGHT_FAR_ROCKET,
        RIGHT_CLOSE_CARGO,
        RIGHT_MIDDLE_CARGO,
        RIGHT_FAR_CARGO,
        LEFT_HAB_LEFT_MID_CARGO,
        MID_HAB_LEFT_MID_CARGO,
        MID_LEFT_CARGO_LEFT_PS,
        HATCH_TO_FEEDER,
        DEFAULT
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

        String defaultPath = "/home/lvuser/default.traj";
        String leftCloseHumanStationPath = "";
        String leftCloseRocketPath = "/home/lvuser/deploy/paths/Left_Close_Left_Rocket.pf1.csv";
        String leftMiddleRocketPath = "Left_Mid_Left_Rocket.pf1.csv";
        String leftFarRocketPath = "Left_Far_Left_Rocket.pf1.csv";
        String leftCloseCargoPath = "Left_Close_Cargo.pf1.csv";
        String leftMiddleCargoPath = "Left_Mid_Cargo.pf1.csv";
        String leftFarCargoPath = "Left_Far_Cargo.pf1.csv";

        String rightCloseRocketPath = "Right_Close_Cargo.pf1.csv";
        String rightMiddleRocketPath = "Right_Mid_Cargo.pf1.csv";
        String rightFarRocketPath = "/home/lvuser/rightFarRocket.traj";
        String rightCloseCargoPath = "/home/lvuser/rightCloseCargo.traj";
        String rightMiddleCargoPath = "/home/lvuser/rightMiddleCargo.traj";
        String rightFarCargoPath = "/home/lvuser/rightMiddleCargo.traj";

        String leftHabLeftMidCargo = "/home/lvuser/deploy/paths/Left_Hab_Left_Mid_Cargo.pf1.csv";
        String midHabLeftMidCargo = "/home/lvuser/deploy/paths/Mid_Hab_Left_Mid_Cargo.pf1.csv";
        String midLeftCargoLeftPS = "/home/lvuser/deploy/paths/Mid_Left_Cargo_Left_PS.pf1.csv";
        String hatch_to_feeder = "/home/lvuser/deploy/paths/hatch_to_feeder.pf1.csv";

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
            case LEFT_CLOSE_HUMAN_STATION:
                file = new File(leftCloseHumanStationPath);
                trajectory = Pathfinder.readFromCSV(file);
                break;
            case LEFT_CLOSE_ROCKET:
                file = new File(leftCloseRocketPath);
                trajectory = Pathfinder.readFromCSV(file);
                break;
            case LEFT_MIDDLE_ROCKET:
                file = new File(leftMiddleRocketPath);
                trajectory = Pathfinder.readFromCSV(file);
                break;
            case LEFT_FAR_ROCKET:
                file = new File(leftFarRocketPath);
                trajectory = Pathfinder.readFromCSV(file);
                break;
            case LEFT_CLOSE_CARGO:
                file = new File(leftCloseCargoPath);
                trajectory = Pathfinder.readFromCSV(file);
                break;
            case LEFT_MIDDLE_CARGO:
                file = new File(leftMiddleCargoPath);
                trajectory = Pathfinder.readFromCSV(file);
                break;
            case LEFT_FAR_CARGO:
                file = new File(leftFarCargoPath);
                trajectory = Pathfinder.readFromCSV(file);
                break;
            case RIGHT_CLOSE_ROCKET:
                file = new File(rightCloseRocketPath);
                trajectory = Pathfinder.readFromCSV(file);
                break;
            case RIGHT_MIDDLE_ROCKET:
                file = new File(rightMiddleRocketPath);
                trajectory = Pathfinder.readFromCSV(file);
                break;
            case RIGHT_FAR_ROCKET:
                file = new File(rightFarRocketPath);
                trajectory = Pathfinder.readFromCSV(file);
                break;
            case RIGHT_CLOSE_CARGO:
                file = new File(rightCloseCargoPath);
                trajectory = Pathfinder.readFromCSV(file);
                break;
            case RIGHT_MIDDLE_CARGO:
                file = new File(rightMiddleCargoPath);
                trajectory = Pathfinder.readFromCSV(file);
                break;
            case RIGHT_FAR_CARGO:
                file = new File(rightFarCargoPath);
                trajectory = Pathfinder.readFromCSV(file);
                break;
            case LEFT_HAB_LEFT_MID_CARGO:
                file = new File(leftHabLeftMidCargo);
                trajectory = Pathfinder.readFromCSV(file);
                break;
            case MID_HAB_LEFT_MID_CARGO:
                file = new File(midHabLeftMidCargo);
                trajectory = Pathfinder.readFromCSV(file);
                break;
            case MID_LEFT_CARGO_LEFT_PS:
                file = new File(midLeftCargoLeftPS);
                trajectory = Pathfinder.readFromCSV(file);
                break;
            case HATCH_TO_FEEDER:
                file = new File(hatch_to_feeder);
                trajectory = Pathfinder.readFromCSV(file);
                break;
            default:
                file = new File(defaultPath);
                trajectory = Pathfinder.readFromCSV(file);
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