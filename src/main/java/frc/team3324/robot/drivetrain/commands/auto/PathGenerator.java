package frc.team3324.robot.drivetrain.commands.auto;

import badlog.lib.BadLog;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team3324.robot.util.Constants;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;

import java.io.File;

public class PathGenerator {
    public enum path {
        LEFT_CLOSE_HUMAN_STATION, LEFT_CLOSE_ROCKET, LEFT_CLOSE_CARGO, LEFT_MIDDLE_CARGO, LEFT_FAR_CARGO, LEFT_MIDDLE_ROCKET,
        LEFT_FAR_ROCKET, RIGHT_CLOSE_ROCKET, RIGHT_MIDDLE_ROCKET, RIGHT_FAR_ROCKET, RIGHT_CLOSE_CARGO, RIGHT_MIDDLE_CARGO,
        RIGHT_FAR_CARGO, DEFAULT
    }

    public static Trajectory generateTrajectory(path path, boolean readFromFile) {
        System.out.println("Generating Trajectory");
        Trajectory trajectory;
        File file;

        String defaultPath = "/home/lvuser/default.traj";
        String leftCloseHumanStationPath = "/home/lvuser/leftCloseHumanStation.traj";
        String leftCloseRocketPath = "/home/lvuser/leftCloseRocket.traj";
        String leftMiddleRocketPath = "/home/lvuser/leftMiddleRocket.traj";
        String leftFarRocketPath = "/home/lvuser/leftFarRocket.traj";
        String leftCloseCargoPath = "/home/lvuser/leftCloseCargo.traj";
        String leftMiddleCargoPath = "/home/lvuser/leftMiddleCargo.traj";
        String leftFarCargoPath = "/home/lvuser/leftFarCargo.traj";

        String rightCloseRocketPath = "/home/lvuser/rightCloseRocket.traj";
        String rightMiddleRocketPath = "/home/lvuser/rightMiddleRocket.traj";
        String rightFarRocketPath = "/home/lvuser/rightFarRocket.traj";
        String rightCloseCargoPath = "/home/lvuser/rightCloseCargo.traj";
        String rightMiddleCargoPath = "/home/lvuser/rightMiddleCargo.traj";
        String rightFarCargoPath = "/home/lvuser/rightMiddleCargo.traj";

        Waypoint[] defaultPoints = new Waypoint[]{
                new Waypoint(0, 0,
                        0), // Waypoint @ x=-0, y=-0, exit angle= 0 degrees
                new Waypoint(8, 0, 0),
        };

        Waypoint[] leftCloseHumanStation = new Waypoint[]{
                new Waypoint(feetToMeters(16.409), feetToMeters(24.693), Pathfinder.d2r(30)),
                new Waypoint(feetToMeters(1.442), feetToMeters(24.842), 0),
        };

        Waypoint[] leftCloseRocket = new Waypoint[]{
                new Waypoint(feetToMeters(1.442), feetToMeters(24.842), 0),
                new Waypoint(feetToMeters(16.409), feetToMeters(24.693), Pathfinder.d2r(30))
        };
        Waypoint[] leftMiddleRocket = new Waypoint[]{
                new Waypoint(feetToMeters(1.5), feetToMeters(23.0), 0),
                new Waypoint(feetToMeters(11.521), feetToMeters(22.821), 0),
                new Waypoint(feetToMeters(19.042), feetToMeters(23.229), Pathfinder.d2r(90))
        };
        Waypoint[] leftFarRocket = new Waypoint[]{
                new Waypoint(feetToMeters(1.5), feetToMeters(23.0), 0),
                new Waypoint(feetToMeters(16.93), feetToMeters(21.811), 0),
                new Waypoint(feetToMeters(21.725), feetToMeters(24.515), Pathfinder.d2r(140.0))
        };
        Waypoint[] leftCloseCargo = new Waypoint[]{
                new Waypoint(feetToMeters(1.5), feetToMeters(23.0), 0),
                new Waypoint(feetToMeters(14.07), feetToMeters(21.765), 0),
                new Waypoint(feetToMeters(21.637), feetToMeters(17.26), Pathfinder.d2r(-90)),
        };
        Waypoint[] leftMiddleCargo = new Waypoint[]{
                new Waypoint(feetToMeters(1.5), feetToMeters(23.0), 0),
                new Waypoint(feetToMeters(14.07), feetToMeters(21.765), 0),
                new Waypoint(feetToMeters(23.44), feetToMeters(17.26), Pathfinder.d2r(-90)),
        };
        Waypoint[] leftFarCargo = new Waypoint[]{
                new Waypoint(feetToMeters(1.5), feetToMeters(23.0), 0),
                new Waypoint(feetToMeters(14.07), feetToMeters(21.765), 0),
                new Waypoint(feetToMeters(25.155), feetToMeters(17.26), Pathfinder.d2r(-90)),
        };


        Waypoint[] rightCloseRocket = new Waypoint[]{
                new Waypoint(feetToMeters(1.5), feetToMeters(23.0), 0),
                new Waypoint(feetToMeters(14.116), feetToMeters(9.918), Pathfinder.d2r(-59.0)),
                new Waypoint(feetToMeters(16.272), feetToMeters(2.566), Pathfinder.d2r(-35.0)),
        };
        Waypoint[] rightMiddleRocket = new Waypoint[]{
                new Waypoint(feetToMeters(1.5), feetToMeters(23.0), 0),
                new Waypoint(feetToMeters(13.192), feetToMeters(13.684), Pathfinder.d2r(-59.0)),
                new Waypoint(feetToMeters(18.954), feetToMeters(3.898), Pathfinder.d2r(-90.0)),
        };
        Waypoint[] rightFarRocket = new Waypoint[]{
                new Waypoint(feetToMeters(1.5), feetToMeters(23.0), 0),
                new Waypoint(feetToMeters(16.798), feetToMeters(11.434), Pathfinder.d2r(-59.0)),
                new Waypoint(feetToMeters(21.75), feetToMeters(2.428), Pathfinder.d2r(-148.0)),
        };

        Waypoint[] rightCloseCargo = new Waypoint[]{
                new Waypoint(feetToMeters(1.5), feetToMeters(23.0), 0),
                new Waypoint(feetToMeters(14.116), feetToMeters(9.918), Pathfinder.d2r(-59.0)),
                new Waypoint(feetToMeters(21.725), feetToMeters(9.775), Pathfinder.d2r(90.0)),
        };
        Waypoint[] rightMiddleCargo = new Waypoint[]{
                new Waypoint(feetToMeters(1.5), feetToMeters(23.0), 0),
                new Waypoint(feetToMeters(14.116), feetToMeters(9.918), Pathfinder.d2r(-59.0)),
                new Waypoint(feetToMeters(23.44), feetToMeters(9.683), Pathfinder.d2r(90.0)),
        };
        Waypoint[] rightFarCargo = new Waypoint[]{
                new Waypoint(feetToMeters(1.5), feetToMeters(23.0), 0),
                new Waypoint(feetToMeters(17.897), feetToMeters(9.092), Pathfinder.d2r(-25.0)),
                new Waypoint(feetToMeters(25.243), feetToMeters(9.775), Pathfinder.d2r(90.0)),
        };



        Trajectory.Config config = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, 0.01,
                Constants.DriveTrain.HIGH_GEAR_MAX_VELOCITY, Constants.DriveTrain.HIGH_GEAR_MAX_ACCELERATION, 9);

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
                    case LEFT_MIDDLE_ROCKET:
                        file = new File(leftMiddleRocketPath);
                        trajectory = Pathfinder.readFromFile(file);
                        SmartDashboard.putBoolean("Running Left Middle Rocket", true);
                        break;
                    case LEFT_FAR_ROCKET:
                        file = new File(leftFarRocketPath);
                        trajectory = Pathfinder.readFromFile(file);
                        SmartDashboard.putBoolean("Running Left Far Rocket", true);
                        break;
                    case LEFT_CLOSE_CARGO:
                        file = new File(leftCloseCargoPath);
                        trajectory = Pathfinder.readFromFile(file);
                        SmartDashboard.putBoolean("Running Left Close Cargo", true);
                        break;
                    case LEFT_MIDDLE_CARGO:
                        file = new File(leftMiddleCargoPath);
                        trajectory = Pathfinder.readFromFile(file);
                        SmartDashboard.putBoolean("Running Left Middle Cargo", true);
                        break;
                    case LEFT_FAR_CARGO:
                        file = new File(leftFarCargoPath);
                        trajectory = Pathfinder.readFromFile(file);
                        SmartDashboard.putBoolean("Running Left Far Cargo", true);
                        break;
                    case RIGHT_CLOSE_ROCKET:
                        file = new File(rightCloseRocketPath);
                        trajectory = Pathfinder.readFromFile(file);
                        SmartDashboard.putBoolean("Running Right Close Rocket", true);
                        break;
                    case RIGHT_MIDDLE_ROCKET:
                        file = new File(rightMiddleRocketPath);
                        trajectory = Pathfinder.readFromFile(file);
                        SmartDashboard.putBoolean("Running Right Middle Rocket", true);
                        break;
                    case RIGHT_FAR_ROCKET:
                        file = new File(rightFarRocketPath);
                        trajectory = Pathfinder.readFromFile(file);
                        SmartDashboard.putBoolean("Running Right Far Rocket", true);
                        break;
                    case RIGHT_CLOSE_CARGO:
                        file = new File(rightCloseCargoPath);
                        trajectory = Pathfinder.readFromFile(file);
                        SmartDashboard.putBoolean("Running Right Close Cargo", true);
                        break;
                    case RIGHT_MIDDLE_CARGO:
                        file = new File(rightMiddleCargoPath);
                        trajectory = Pathfinder.readFromFile(file);
                        SmartDashboard.putBoolean("Running Right Middle Cargo", true);
                        break;
                    case RIGHT_FAR_CARGO:
                        file = new File(rightFarCargoPath);
                        trajectory = Pathfinder.readFromFile(file);
                        SmartDashboard.putBoolean("Running Right Far Cargo", true);
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
                case LEFT_MIDDLE_ROCKET:
                    trajectory = Pathfinder.generate(leftMiddleRocket, config);
                    file = new File(leftMiddleRocketPath);
                    Pathfinder.writeToFile(file, trajectory);
                    SmartDashboard.putBoolean("Running Left Middle Rocket", true);
                    break;
                case LEFT_FAR_ROCKET:
                    trajectory = Pathfinder.generate(leftFarRocket, config);
                    file = new File(leftFarRocketPath);
                    Pathfinder.writeToFile(file, trajectory);
                    SmartDashboard.putBoolean("Running Left Far Rocket", true);
                    break;
                case LEFT_CLOSE_CARGO:
                    trajectory = Pathfinder.generate(leftCloseCargo, config);
                    file = new File(leftCloseCargoPath);
                    Pathfinder.writeToFile(file, trajectory);
                    SmartDashboard.putBoolean("Running Left Close Cargo", true);
                    break;
                case LEFT_MIDDLE_CARGO:
                    trajectory = Pathfinder.generate(leftMiddleCargo, config);
                    file = new File(leftMiddleCargoPath);
                    Pathfinder.writeToFile(file, trajectory);
                    SmartDashboard.putBoolean("Running Left Middle Cargo", true);
                    break;
                case LEFT_FAR_CARGO:
                    trajectory = Pathfinder.generate(leftFarCargo, config);
                    file = new File(leftFarCargoPath);
                    Pathfinder.writeToFile(file, trajectory);
                    SmartDashboard.putBoolean("Running Left Far Cargo", true);
                    break;
                case RIGHT_CLOSE_ROCKET:
                    trajectory = Pathfinder.generate(rightCloseRocket, config);
                    file = new File(rightCloseRocketPath);
                    Pathfinder.writeToFile(file, trajectory);
                    SmartDashboard.putBoolean("Running Right Close Rocket", true);
                    break;
                case RIGHT_MIDDLE_ROCKET:
                    trajectory = Pathfinder.generate(rightMiddleRocket, config);
                    file = new File(rightMiddleRocketPath);
                    Pathfinder.writeToFile(file, trajectory);
                    SmartDashboard.putBoolean("Running Right Middle Rocket", true);
                    break;
                case RIGHT_FAR_ROCKET:
                    trajectory = Pathfinder.generate(rightFarRocket, config);
                    file = new File(rightFarRocketPath);
                    Pathfinder.writeToFile(file, trajectory);
                    SmartDashboard.putBoolean("Running Right Far Rocket", true);
                    break;
                case RIGHT_CLOSE_CARGO:
                    trajectory = Pathfinder.generate(rightCloseCargo, config);
                    file = new File(rightCloseCargoPath);
                    Pathfinder.writeToFile(file, trajectory);
                    SmartDashboard.putBoolean("Running Right Close Cargo", true);
                    break;
                case RIGHT_MIDDLE_CARGO:
                    trajectory = Pathfinder.generate(rightMiddleCargo, config);
                    file = new File(rightMiddleCargoPath);
                    Pathfinder.writeToFile(file, trajectory);
                    SmartDashboard.putBoolean("Running Right Middle Cargo", true);
                    break;
                case RIGHT_FAR_CARGO:
                    trajectory = Pathfinder.generate(rightFarCargo, config);
                    file = new File(rightFarCargoPath);
                    Pathfinder.writeToFile(file, trajectory);
                    SmartDashboard.putBoolean("Running Right Far Cargo", true);
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