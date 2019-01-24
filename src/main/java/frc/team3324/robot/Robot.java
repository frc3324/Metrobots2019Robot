package frc.team3324.robot;

import badlog.lib.BadLog;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import frc.team3324.robot.arm.Arm;
import frc.team3324.robot.drivetrain.commands.auto.*;
import frc.team3324.robot.drivetrain.DriveTrain;

import edu.wpi.first.wpilibj.TimedRobot;
import frc.team3324.robot.util.FrontCamera;
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
    private static BadLog logger;
    public static Characterizer characterizer;
    public static OI oi;
    public static FrontCamera frontCamera = new FrontCamera();

    public void robotInit() {
        logger = BadLog.init("/home/lvuser/log.bag" + System.currentTimeMillis(), true);
        {

            driveTrain = new DriveTrain();
            arm = new Arm();
            characterizer = new Characterizer();
            OI oi = new OI();
            BadLog.createTopic("System/Battery Voltage", "V", () -> RobotController.getBatteryVoltage());
            BadLog.createTopic("Match Time", "s", () -> DriverStation.getInstance().getMatchTime());
        }
        driveTrain.clearGyro();
        logger.finishInitialization();
        Shuffleboard.startRecording();
    }

    public void robotPeriodic() {
        Robot.driveTrain.printEncoderDistance();
        logger.updateTopics();
        logger.log();
        frontCamera.start();
    }

    public void disabledInit() {
       }

    @Override
    public void autonomousInit() {
//        Scheduler.getInstance().add(new levelOneTest());
    }
}
