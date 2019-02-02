package frc.team3324.robot.intake;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team3324.robot.util.Constants;

public class HatchIntake extends Subsystem {
    private DoubleSolenoid hatchIntake = new DoubleSolenoid(
            Constants.PCM.PNEUMATICS_MODULE_NUMBER,
            Constants.PCM.HATCH_PORT_FORWARD,
            Constants.PCM.HATCH_PORT_REVERSE);

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
