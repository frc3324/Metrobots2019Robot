package frc.team3324.robot.drivetrain.commands.Teleop;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team3324.robot.util.OI;
import frc.team3324.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class ShiftGears extends Command {

    private boolean gearShifterStatus = false;

    public ShiftGears() {
        super("ShiftGears");
        requires(Robot.driveTrain);
    }

    protected void initialize() {}

    protected void execute() {
        SmartDashboard.putBoolean("SHIFTING", true);
        if (OI.primaryController.getAButton()) {
            if (gearShifterStatus) {
                Robot.driveTrain.setHighGear();
                gearShifterStatus = !gearShifterStatus;
            } else {
                Robot.driveTrain.setLowGear();
                gearShifterStatus = !gearShifterStatus;
            }
        }
    }

    protected boolean isFinished() { return true; }

    protected void end() {}

    protected void interrupted() {}
}