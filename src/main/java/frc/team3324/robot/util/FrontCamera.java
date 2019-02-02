package frc.team3324.robot.util;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.command.Command;

public class FrontCamera extends Command {
    @Override
    protected void initialize() {
        CameraServer.getInstance().startAutomaticCapture("Front", 0);
        CameraServer.getInstance().putVideo("Camera output", 1280, 720);
    }

    @Override
    protected void execute() {
        CameraServer.getInstance().getVideo("Front");
        System.out.println("Help");
    }

    @Override
    protected boolean isFinished() {
        return OI.primaryController.getStartButton();
    }

    @Override
    protected void end() { }
}
