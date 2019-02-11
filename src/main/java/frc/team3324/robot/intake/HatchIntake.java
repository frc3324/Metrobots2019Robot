package frc.team3324.robot.intake;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import frc.team3324.robot.util.Constants;

/**
 * Subsystem class to control hatch intake/outtake system.
 */
public class HatchIntake extends Subsystem {
    private DoubleSolenoid hatchIntake = new DoubleSolenoid(
            Constants.HatchIntake.HATCH_INTAKE_PORT_FORWARD, Constants.HatchIntake.HATCH_INTAKE_PORT_BACKWARD);

    /**
     * Creates an instance of the HatchIntake class.
     */
    public HatchIntake() {}

    /**
     * Sets the hatch intake double solenoid to forward.
     *
     * @see DoubleSolenoid
     */
    public void setHatchIntake() {
        hatchIntake.set(DoubleSolenoid.Value.kForward);
    }

    /**
     * Sets the hatch intake double solenoid to backward.
     *
     * @see DoubleSolenoid
     */
    public void setHatchOuttake() {
        hatchIntake.set(DoubleSolenoid.Value.kReverse);
    }

    /**
     * Sets the hatch intake double solenoid to off.
     *
     * @see DoubleSolenoid
     */
    public void setHatchSystemOff() {
        hatchIntake.set(DoubleSolenoid.Value.kOff);
    }

    public void initDefaultCommand() {
        hatchIntake.set(DoubleSolenoid.Value.kOff);
    }
}
