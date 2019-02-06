package frc.team3324.robot;

import badlog.lib.BadLog;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import frc.team3324.robot.arm.Arm;
import frc.team3324.robot.intake.cargo.CargoIntake;
import frc.team3324.robot.drivetrain.commands.auto.*;
import frc.team3324.robot.drivetrain.DriveTrain;
import frc.team3324.robot.util.LED;
import frc.team3324.robot.util.FrontCamera;
import frc.team3324.robot.intake.hatch.HatchIntake;

import edu.wpi.first.wpilibj.TimedRobot;
import frc.team3324.robot.util.OI;

public class Robot extends TimedRobot {

    public Robot() { super(0.02); }
    /*
     * Instantiate subsystems
     */
    public static PowerDistributionPanel pdp = new PowerDistributionPanel();

    public static Arm arm;
    public static CargoIntake cargoIntake;
    public static Characterizer characterizer;
    public static DriveTrain driveTrain;
    public static FrontCamera frontCamera = new FrontCamera();
    public static HatchIntake hatchIntake;
    public static LED led;
    public static OI oi;

    private static BadLog logger;

    public void robotInit() {
        logger = BadLog.init("/home/lvuser/log.bag" + System.currentTimeMillis(), true);
        {
            arm = new Arm();
            cargoIntake = new CargoIntake();
            characterizer = new Characterizer();
            driveTrain = new DriveTrain();
            hatchIntake = new HatchIntake();
            led = new LED();
            oi = new OI();
            BadLog.createTopic("System/Battery Voltage", "V", () -> RobotController.getBatteryVoltage());
            BadLog.createTopic("Match Time", "s", () -> DriverStation.getInstance().getMatchTime());
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

        // FIX
        led.setNeutralState();

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
