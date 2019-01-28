package frc.team3324.robot.util;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.command.Command;
import frc.team3324.robot.Robot;

public class BackCamera extends Command {
    @Override
    protected void initialize() {
        CameraServer.getInstance().startAutomaticCapture("Back", 1);
        CameraServer.getInstance().putVideo("Camera output", 1280, 720);
    }

    @Override
    protected void execute() {
        CameraServer.getInstance().getVideo("Back");
    }

    @Override
    protected boolean isFinished() {
        return OI.primaryController.getStartButton();
    }

    @Override
    protected void end() {
        Command frontCamera = new FrontCamera();
        frontCamera.start();
    }
}
