package frc.team3324.robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import frc.team3324.robot.DriveTrain.Commands.Auto.JaciPathfinding;
import frc.team3324.robot.DriveTrain.DriveTrain;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.TimedRobot;

/**
 * Main robot code<br>
 * <br>
 */
public class Robot extends TimedRobot {
    /*
     * Instantiate subsystems
     */
    public static final DriveTrain mDriveTrain = new DriveTrain();

    public void robotInit() {
        Shuffleboard.startRecording();
    }
    public void robotPeriodic() {
        CameraServer.getInstance().getVideo();
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
        mDriveTrain.printEncoderDistance();
    }
}
