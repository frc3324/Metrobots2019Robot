package frc.team3324.robot.util;

import edu.wpi.first.wpilibj.command.Command;
import frc.team3324.robot.Robot;

public class Compress extends Command {

    public Compress() {
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
