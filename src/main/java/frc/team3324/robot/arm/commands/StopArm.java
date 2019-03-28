package frc.team3324.robot.arm.commands;

import edu.wpi.first.wpilibj.command.InstantCommand;
import frc.team3324.robot.Robot;

public class StopArm extends InstantCommand {

    @Override
    protected void initialize() {
        Robot.oi.oneEightyDegree.stopNotifier();
        Robot.oi.zeroDegree.stopNotifier();
    }
}
