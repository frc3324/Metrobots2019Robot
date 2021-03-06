package frc.team3324.robot;

import badlog.lib.BadLog;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import frc.team3324.robot.arm.Arm;
import frc.team3324.robot.arm.commands.ResetArm;
import frc.team3324.robot.climber.Climber;
import frc.team3324.robot.drivetrain.commands.auto.JaciPathfinding;
import frc.team3324.robot.drivetrain.commands.auto.Odometry;
import frc.team3324.robot.drivetrain.commands.auto.PathGenerator;
import frc.team3324.robot.intake.cargo.CargoIntake;
import frc.team3324.robot.intake.hatch.HatchIntake;
import frc.team3324.robot.drivetrain.DriveTrain;
import frc.team3324.robot.util.LED;
import frc.team3324.robot.util.OI;
import frc.team3324.robot.wrappers.Log;
import frc.team3324.robot.wrappers.Logger;

/**
 * Main robot class. Declares and instantiates subsystems and peripheral systems (OI, LEDs, etc.).
 */
public class Robot extends TimedRobot {

    public Robot() { super(0.02); }
    public static Compressor compressor = new Compressor(0);
    private ShuffleboardTab compressorTab = Shuffleboard.getTab("Compressor");


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
    public static Logger logger;

    public void robotInit() {
        LiveWindow.disableAllTelemetry();
        logger = new Logger("/home/lvuser/Log.bag" + System.currentTimeMillis(), true);
        {
            arm = new Arm();
            cargoIntake = new CargoIntake();
            climber = new Climber();
            driveTrain = new DriveTrain();
            hatchIntake = new HatchIntake();

            led = new LED();

            oi = new OI();

            BadLog.createTopic("System/Battery Voltage", "V",  RobotController::getBatteryVoltage);
            BadLog.createTopic("Match Time", "s", () -> DriverStation.getInstance().getMatchTime());
        }

        logger.finishInitialization();
        driveTrain.clearGyro();

        Scheduler.getInstance().add(new Log());

        compressor.setClosedLoopControl(true);

        CameraServer.getInstance().startAutomaticCapture(1);
        CameraServer.getInstance().startAutomaticCapture(0);
//
        CameraServer.getInstance().putVideo("Camera output", 240, 144);
    }

    public void robotPeriodic() {
        Scheduler.getInstance().run();
        CameraServer.getInstance().getVideo();
    }

    public void disabledInit() {
    }

    @Override
    public void autonomousInit() {
        Scheduler.getInstance().add(new Odometry(3.018/3.281, 17.628/3.281, 0));
        //Scheduler.getInstance().add(new Odometry(3.118/3.281, 9.903/3.281, 0));
        //Scheduler.getInstance().add(new Odometry(5.13/3.281, 17.659/3.281, 0));
       // Scheduler.getInstance().add(new JaciPathfinding(PathGenerator.path.RIGHT_LEVEL_1, true, false));
    }

    @Override
    public void teleopPeriodic() {
    }
}
