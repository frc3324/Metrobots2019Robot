package frc.team3324.robot.util;

import edu.wpi.first.wpilibj.command.Command;
import frc.team3324.robot.Robot;

public class StopCompress extends Command {

    public StopCompress() {
    }

    @Override
    protected void initialize() {
        Robot.compressor.stop();
    }

    @Override
    protected boolean isFinished() {
        return true;
    }

    @Override
    protected void end() {
        Robot.compressor.stop();
    }
}
