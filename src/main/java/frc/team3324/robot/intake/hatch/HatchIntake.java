package frc.team3324.robot.intake.hatch;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team3324.robot.util.Constants;
import frc.team3324.robot.util.OI;

/**
 * Subsystem class to control hatch intake/outtake system.
 */
public class HatchIntake extends Subsystem {
    private DoubleSolenoid hatchIntake = new DoubleSolenoid(
            Constants.HatchIntake.HATCH_INTAKE_PORT_FORWARD, Constants.HatchIntake.HATCH_INTAKE_PORT_BACKWARD);

    /**
     * Creates an instance of the HatchIntake class.
     */
    public HatchIntake() {
        SmartDashboard.putBoolean("Intake", false);
    }

    /**
     * Switches intake state to forward if reverse/off and reverse if forward.
     *
     * @see DoubleSolenoid
     */
    public void switchIntake() {
        if (hatchIntake.get() == DoubleSolenoid.Value.kForward) {
            hatchIntake.set(DoubleSolenoid.Value.kReverse);
        } else {
            hatchIntake.set(DoubleSolenoid.Value.kForward);
        }
    }

    /**
     * Sets the hatch intake double solenoid to off.
     *
     * @see DoubleSolenoid
     */

    public void initDefaultCommand() { }
}
