package frc.team3324.robot.intake.hatch;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team3324.robot.intake.hatch.commands.Idle;
import frc.team3324.robot.util.Constants;

public class HatchIntake extends Subsystem {
    public DoubleSolenoid intake = new DoubleSolenoid(Constants.HatchIntake.HATCH_INTAKE_PORT_FORWARD, Constants.HatchIntake.HATCH_INTAKE_PORT_BACKWARD);

    public void initDefaultCommand() { setDefaultCommand(new Idle());}
}
