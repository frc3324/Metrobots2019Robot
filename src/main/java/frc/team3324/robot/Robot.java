package frc.team3324.robot;

import badlog.lib.BadLog;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import frc.team3324.robot.arm.Arm;
import frc.team3324.robot.climber.Climber;
import frc.team3324.robot.intake.cargo.CargoIntake;
import frc.team3324.robot.intake.hatch.HatchIntake;
import frc.team3324.robot.drivetrain.DriveTrain;
import frc.team3324.robot.util.LED;
import frc.team3324.robot.util.OI;
import frc.team3324.robot.wrappers.Logger;

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
    public static CargoIntake cargoIntake;
    public static Climber climber;
    public static DriveTrain driveTrain;
    public static HatchIntake hatchIntake;
    public static OI oi;
    public static LED led;
    public static Logger genericLogger;

    public void robotInit() {
        genericLogger = new Logger("/home/lvuser/log.bag" + System.currentTimeMillis(), true);
        {
            arm = new Arm();
            cargoIntake = new CargoIntake();
            climber = new Climber();
            driveTrain = new DriveTrain();
            hatchIntake = new HatchIntake();

            led = new LED();

            oi = new OI();

            BadLog.createTopic("System/Battery Voltage", "V", () -> RobotController.getBatteryVoltage());
            BadLog.createTopic("Match Time", "s", () -> DriverStation.getInstance().getMatchTime());
        }

        driveTrain.clearGyro();
        genericLogger.finishInitialization();
        Shuffleboard.startRecording();

        compressor.setClosedLoopControl(true);

        driveTrain.clearGyro();

        CameraServer.getInstance().startAutomaticCapture(1);
        CameraServer.getInstance().startAutomaticCapture(0);

        CameraServer.getInstance().putVideo("Camera output", 240, 144);
    }

    public void robotPeriodic() {
        hatchIntake.updateRumble();
        Scheduler.getInstance().run();

        compressorPressure.setBoolean(compressor.getPressureSwitchValue());
        Robot.driveTrain.updateSensors();
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

    }

    @Override
    public void teleopPeriodic() {
    }
}
