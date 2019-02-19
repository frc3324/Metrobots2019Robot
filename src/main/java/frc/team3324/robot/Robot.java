package frc.team3324.robot;

import badlog.lib.BadLog;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import frc.team3324.robot.arm.Arm;
import frc.team3324.robot.arm.commands.ResetArm;
import frc.team3324.robot.climber.Climber;
import frc.team3324.robot.intake.cargo.CargoIntake;
import frc.team3324.robot.drivetrain.DriveTrain;
import frc.team3324.robot.intake.hatch.HatchIntake;

import edu.wpi.first.wpilibj.TimedRobot;
import frc.team3324.robot.util.OI;

/**
 * Main robot class. Declares and instantiates subsystems and peripheral systems (OI, LEDs, etc.).
 */
public class Robot extends TimedRobot {

    public Robot() { super(0.02); }
    public static Compressor compressor = new Compressor(0);
    private ShuffleboardTab compressorTab = Shuffleboard.getTab("Compressor");
    private NetworkTableEntry compressorPressure = compressorTab.add("Compressor Pressure", false).withPosition(0, 0).getEntry();


    /*
     * Instantiate subsystems
     */
    public static PowerDistributionPanel pdp = new PowerDistributionPanel();

    public static Arm arm;
    public static DriveTrain driveTrain;
    public static CargoIntake cargoIntake;
    public static HatchIntake hatchIntake;
    public static Climber climber;
    public static OI oi;

    //public static LED led;

    private static BadLog logger;

    public void robotInit() {
        logger = BadLog.init("/home/lvuser/log.bag" + System.currentTimeMillis(), true);
        {
            arm = new Arm();
            cargoIntake = new CargoIntake();
            driveTrain = new DriveTrain();
            hatchIntake = new HatchIntake();
           // led = new LED();
            oi = new OI();
            climber = new Climber();

            BadLog.createTopic("System/Battery Voltage", "V", () -> RobotController.getBatteryVoltage());
            BadLog.createTopic("Match Time", "s", () -> DriverStation.getInstance().getMatchTime());
        }

        compressor.start();

        driveTrain.clearGyro();
        logger.finishInitialization();
        Shuffleboard.startRecording();
        CameraServer.getInstance().startAutomaticCapture(1);
        CameraServer.getInstance().startAutomaticCapture(0);

        CameraServer.getInstance().putVideo("Camera output", 1280, 720);
    }

    public void robotPeriodic() {
        hatchIntake.updateRumble();
        Scheduler.getInstance().run();

        compressorPressure.setBoolean(compressor.getPressureSwitchValue());
        Robot.driveTrain.printEncoderDistance();
        logger.updateTopics();
        logger.log();

        CameraServer.getInstance().getVideo();
    }

    public void disabledInit() {
    }

    @Override
    public void disabledPeriodic() {
    }

    @Override
    public void autonomousInit() {
        Scheduler.getInstance().add(new ResetArm());
    }

    @Override
    public void teleopPeriodic() {
    }
}
