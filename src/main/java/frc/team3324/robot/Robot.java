package frc.team3324.robot;

import badlog.lib.BadLog;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import frc.team3324.robot.arm.Arm;
import frc.team3324.robot.climber.Climber;
import frc.team3324.robot.intake.cargo.CargoIntake;
import frc.team3324.robot.drivetrain.DriveTrain;
import frc.team3324.robot.intake.HatchIntake;
import frc.team3324.robot.util.LED;
import frc.team3324.robot.util.OI;
import frc.team3324.robot.wrappers.Logger;

import edu.wpi.first.wpilibj.TimedRobot;

/**
 * Main robot class. Declares and instantiates subsystems and peripheral systems (OI, LEDs, etc.).
 */
public class Robot extends TimedRobot {

    public Robot() { super(0.02); }
    public static Compressor compressor = new Compressor(0);
    /*
     * Instantiate subsystems
     */
    public static PowerDistributionPanel pdp = new PowerDistributionPanel();

    public static Arm arm;
    public static CargoIntake cargoIntake;
    public static Climber climber;
    public static DriveTrain driveTrain;
    public static HatchIntake hatchIntake;
    public static OI oi;
    public static LED led;
    public static Logger genericLogger;

    public void robotInit() {
        {
            arm = new Arm();
            cargoIntake = new CargoIntake();
            climber = new Climber();
            driveTrain = new DriveTrain();
            hatchIntake = new HatchIntake();
            led = new LED();
            genericLogger = new Logger("/home/lvuser/log.bag" + System.currentTimeMillis(), true);
            oi = new OI();

            BadLog.createTopic("System/Battery Voltage", "V", () -> RobotController.getBatteryVoltage());
            BadLog.createTopic("Match Time", "s", () -> DriverStation.getInstance().getMatchTime());
        }
        compressor.start();

        driveTrain.clearGyro();
        genericLogger.finishInitialization();
        Shuffleboard.startRecording();
        CameraServer.getInstance().startAutomaticCapture(0);
        CameraServer.getInstance().startAutomaticCapture(1);
        CameraServer.getInstance().putVideo("Camera output", 1280, 720);
    }

    public void robotPeriodic() {
        Scheduler.getInstance().run();
        Robot.driveTrain.printEncoderDistance();
        genericLogger.updateTopics();
        genericLogger.log();

        CameraServer.getInstance().getVideo();
    }

    public void disabledInit() {
    }

    @Override
    public void disabledPeriodic() {
    }

    @Override
    public void autonomousInit() {
        //        Scheduler.getInstance().add(new levelOneTest());
    }

    @Override
    public void teleopPeriodic() {
    }
}
