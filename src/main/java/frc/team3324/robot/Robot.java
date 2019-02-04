package frc.team3324.robot;

import badlog.lib.BadLog;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import frc.team3324.robot.arm.Arm;
import frc.team3324.robot.intake.cargo.CargoIntake;
import frc.team3324.robot.drivetrain.commands.auto.*;
import frc.team3324.robot.drivetrain.DriveTrain;
import frc.team3324.robot.intake.hatch.HatchIntake;
import frc.team3324.robot.util.LED;

import edu.wpi.first.wpilibj.TimedRobot;
import frc.team3324.robot.util.OI;

public class Robot extends TimedRobot {
    public Robot() {
        super(0.02);
    }
    /*
     * Instantiate subsystems
     */
    public static PowerDistributionPanel pdp = new PowerDistributionPanel();

    public static DriveTrain driveTrain;
    public static Arm arm;
    public static CargoIntake cargoIntake;
    public static HatchIntake hatchIntake;
    public static OI oi;

    public static Characterizer characterizer;
    public static LED led;

    private static BadLog logger;

    public void robotInit() {
        logger = BadLog.init("/home/lvuser/log.bag" + System.currentTimeMillis(), true);
        {

            driveTrain = new DriveTrain();
            arm = new Arm();
            hatchIntake = new HatchIntake();
            cargoIntake = new CargoIntake();

            characterizer = new Characterizer();
            OI oi = new OI();

            BadLog.createTopic("System/Battery Voltage", "V", () -> RobotController.getBatteryVoltage());
            BadLog.createTopic("System/Match Time", "s", () -> DriverStation.getInstance().getMatchTime());
        }
        driveTrain.clearGyro();
        logger.finishInitialization();

        Shuffleboard.startRecording();
        CameraServer.getInstance().startAutomaticCapture(0);
        CameraServer.getInstance().startAutomaticCapture(1);
    }

    public void robotPeriodic() {
        Robot.driveTrain.printEncoderDistance();
        logger.updateTopics();
        logger.log();

        CameraServer.getInstance().getVideo();
    }

    public void disabledInit() {
        CameraServer.getInstance().startAutomaticCapture(0);
        CameraServer.getInstance().startAutomaticCapture(1);
        CameraServer.getInstance().putVideo("Camera output", 1280, 720);
    }

    @Override
    public void disabledPeriodic() {
        CameraServer.getInstance().getVideo();

    }

    @Override
    public void autonomousInit() {
//        Scheduler.getInstance().add(new levelOneTest());
    }
}
