package frc.team3324.robot.wrappers;

import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.command.Command;
import frc.team3324.robot.Robot;

public class Log extends Command {
    Notifier notifier = new Notifier(() -> { log();});

    @Override
    protected void initialize() {
        notifier.startPeriodic(0.04);
    }

    private void log() {
        Robot.genericLogger.log();
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}
