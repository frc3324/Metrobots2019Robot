package frc.team3324.robot;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import frc.team3324.robot.DriveTrain.Commands.Auto.JaciPathfinding;
import frc.team3324.robot.DriveTrain.Commands.Auto.PathGenerator;
import frc.team3324.robot.DriveTrain.DriveTrain;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.TimedRobot;
import frc.team3324.robot.util.OI;

public class Robot extends TimedRobot {
    public Robot() {
        super(0.01);
    }
    /*
     * Instantiate subsystems
     */
    public static final DriveTrain mDriveTrain = new DriveTrain();
    public static final OI mOI = new OI();

    public void robotInit() {
        Shuffleboard.startRecording();
    }
    public void robotPeriodic() {
        CameraServer.getInstance().getVideo();
    }

    public void disabledInit() {
        CameraServer.getInstance().startAutomaticCapture();
        CameraServer.getInstance().putVideo("Camera output", 240, 144);
    }

    @Override
    public void autonomousInit() {
        Scheduler.getInstance().add(new JaciPathfinding(PathGenerator.path.LEFT_CLOSE_ROCKET, false, false));
    }

    public void autonomousPeriodic() { Scheduler.getInstance().run(); }

    public void teleopPeriodic() {
        Scheduler.getInstance().run();
        mDriveTrain.printEncoderDistance();
    }
}
