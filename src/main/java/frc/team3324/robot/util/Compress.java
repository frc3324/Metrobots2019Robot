package frc.team3324.robot.util;

import edu.wpi.first.wpilibj.command.Command;
import frc.team3324.robot.Robot;

public class Compress extends Command {

    public Compress() {
        setTimeout(10);
    }

    @Override
    protected void initialize() {
        Robot.compressor.start();
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        Robot.compressor.stop();
    }
}
