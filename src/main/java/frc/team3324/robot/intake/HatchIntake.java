package frc.team3324.robot.intake;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team3324.robot.util.Constants;

public class HatchIntake extends Subsystem {
    private DoubleSolenoid hatchIntake = new DoubleSolenoid(
            Constants.HatchIntake.HATCH_INTAKE_PORT_FORWARD, Constants.HatchIntake.HATCH_INTAKE_PORT_BACKWARD);

    public HatchIntake() { }

    public void setHatchIntake() {
        hatchIntake.set(DoubleSolenoid.Value.kForward);
    }
    public void setHatchOuttake() {
        hatchIntake.set(DoubleSolenoid.Value.kReverse);
    }
    public void initDefaultCommand() {
        hatchIntake.set(DoubleSolenoid.Value.kOff);
    }
}
