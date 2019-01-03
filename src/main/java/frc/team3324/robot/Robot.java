package frc.team3324.robot;

import frc.team3324.robot.DriveTrain.DriveTrain;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Main robot code<br>
 * <br>
 */
public class Robot extends TimedRobot {

    /*
     * Instantiate subsystems
     */
    public static final DriveTrain mDriveTrain = new DriveTrain();
    Command selectedCommand;
    SendableChooser<Integer> autoSelector = new SendableChooser<Integer>();

    private int defaultSet = 0;
    private int left       = 1;
    private int middle     = 2;
    private int right      = 3;

    private String gameData;
    private String infoString;
    private String positionString;

    private char firstLetter;

    /**
     * When the robot first boots up, initialize all of the gamepads, motor
     * controllers, sensors, and subsystems
     */
    public void robotInit() {
        /* Initialize AUTO selecter UI */
        autoSelector.addDefault("Default", defaultSet);
        autoSelector.addObject("Left position", left);
        autoSelector.addObject("Middle position", middle);
        autoSelector.addObject("Right position", right);

        SmartDashboard.putData("CHOOSE ONE", autoSelector);
    }
    public void robotPeriodic() { gameData = DriverStation.getInstance().getGameSpecificMessage(); }
    /**
     * Initialize whatever you need to when the robot becomes disables
     */
    public void disabledInit() {
        CameraServer.getInstance().startAutomaticCapture();
        CameraServer.getInstance().putVideo("Camera output", 1280, 720);

        DriveTrain.clearEncoder();
    }

    /**
     * Runs constantly when the robot is disabled. Good for displaying stuff to
     * the driver station
     */
    public void disabledPeriodic() {

        if (autoSelector.getSelected() == defaultSet) {
            positionString = "Default position";
        } else if (autoSelector.getSelected() == left) {
            positionString = "Left position";
        } else if (autoSelector.getSelected() == middle) {
            positionString = "Middle position";
        } else if (autoSelector.getSelected() == right) {
            positionString = "Right position";
        }

        SmartDashboard.putString("You are in: ", positionString);

        if (gameData != null && gameData.length() > 0) {

            firstLetter = gameData.charAt(0);
            if (positionString.equals("Default position")) {
                infoString = "Drive forward (default)";
            } else if (firstLetter == 'L' && positionString.equals("Left position")) {
                infoString = "LLeft";
            }
            if (firstLetter == 'L' && positionString.equals("Middle position")) {
                infoString = "LMiddle";
            } else if (firstLetter == 'L' && positionString.equals("Right position")) {
                infoString = "LRight";
            }
            if (firstLetter == 'L' && positionString.equals("Right position")) {
                infoString = "RLeft";
            } else if (firstLetter == 'R' && positionString.equals("Middle position")) {
                infoString = "RMiddle";
            } else if (firstLetter == 'R') {
                infoString = "RRight";
            } else {
                DriverStation.reportError("No game data received.", false);
                infoString = "No game data received.";
            }

            CameraServer.getInstance().getVideo();
        }
    }

    /**
     * Initialize whatever you need to when the robot starts autonomous
     */
    public void autonomousInit() {
        Scheduler.getInstance().add(selectedCommand);
        SmartDashboard.putString("COMMENCING: ", infoString);
    }

    /**
     * Runs constantly when autonomous is enabled
     */
    public void autonomousPeriodic() { Scheduler.getInstance().run(); }

    /**
     * Initialize whatever you need to when the robot starts teleop
     */
    public void teleopInit() {}

    /**
     * Runs constantly when teleop is enabled
     */
    public void teleopPeriodic() {
        CameraServer.getInstance().getVideo();
        Scheduler.getInstance().run();
        mDriveTrain.printEncoder();
    }

    /**
     * Runs constantly when test mode is enabled
     */
    public void testPeriodic() {}
}
