package frc.team3324.robot.util;

import edu.wpi.first.wpilibj.command.Command;
import frc.team3324.robot.Robot;

public class Compress extends Command {

    @Override
    protected void execute() {
        Robot.compressor.start();
    }

    @Override
    protected boolean isFinished() {
        return true;
    }

}
