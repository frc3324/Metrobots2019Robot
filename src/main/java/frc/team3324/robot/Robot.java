package frc.team3324.robot;

import badlog.lib.BadLog;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import frc.team3324.robot.arm.Arm;
import frc.team3324.robot.drivetrain.commands.Auto.JaciPathfinding;
import frc.team3324.robot.drivetrain.DriveTrain;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.TimedRobot;

public class Robot extends TimedRobot {
    /*
     * Instantiate subsystems
     */
    public static PowerDistributionPanel pdp = new PowerDistributionPanel();
    public static DriveTrain driveTrain;
    public static Arm arm;
    public static BadLog logger;


    public void robotInit() {
        logger = BadLog.init("/home/lvuser/log1.bag", true);
        {
            driveTrain = new DriveTrain();
            arm = new Arm();

            BadLog.createTopic("System/Battery Voltage", "V", () -> RobotController.getBatteryVoltage());
            BadLog.createTopic("Match Time", "s", () -> DriverStation.getInstance().getMatchTime());
        }
        logger.finishInitialization();
        Shuffleboard.startRecording();
    }
    public void robotPeriodic() {
        CameraServer.getInstance().getVideo();
        logger.updateTopics();
        logger.log();
    }

    public void disabledInit() {
        CameraServer.getInstance().startAutomaticCapture();
        CameraServer.getInstance().putVideo("Camera output", 1280, 720);
    }

    @Override
    public void autonomousInit() {
        Scheduler.getInstance().add(new JaciPathfinding(JaciPathfinding.path.TOP_HATCH));
    }

    public void autonomousPeriodic() { Scheduler.getInstance().run(); }

    public void teleopPeriodic() {
        Scheduler.getInstance().run();
        driveTrain.printEncoderDistance();
    }
}
