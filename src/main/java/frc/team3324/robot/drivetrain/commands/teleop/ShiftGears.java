package frc.team3324.robot.drivetrain.commands.teleop;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team3324.robot.util.OI;
import frc.team3324.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Command class to shift drivetrain gears (high/low).
 */
public class ShiftGears extends Command {

    private boolean gearShifterStatus = false;

    /**
     * Creates an instance of the ShiftGears class.
     */
    public ShiftGears() { super("ShiftGears"); }

    protected void initialize() {}

    protected void execute() {
        SmartDashboard.putBoolean("SHIFTING", true);
        if (OI.primaryController.getAButton()) {
            if (gearShifterStatus) {
                Robot.driveTrain.shiftGears();
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