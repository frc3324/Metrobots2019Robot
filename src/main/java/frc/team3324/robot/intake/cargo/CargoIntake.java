package frc.team3324.robot.intake.cargo;

import badlog.lib.BadLog;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team3324.robot.intake.cargo.commands.Intake;
import frc.team3324.robot.intake.cargo.commands.Outtake;
import frc.team3324.robot.util.Constants;

import static frc.team3324.robot.Robot.pdp;

public class CargoIntake extends Subsystem {
    public WPI_TalonSRX intakeMotor =
        new WPI_TalonSRX(Constants.CargoIntake.CARGO_INTAKE_MOTOR_PORT);

    public DigitalInput intakeLimitSwitch =
        new DigitalInput(Constants.CargoIntake.LIMIT_SWITCH_PORT);

    public CargoIntake() {
        BadLog.createTopic("cargointake/isIntook", "Boolean", () -> getLimitSwitchAsDouble());
        BadLog.createTopic("cargointake/Current Draw", "amps",
                           () -> pdp.getCurrent(Constants.CargoIntake.CARGO_INTAKE_PDP_PORT));
    }

    public double getLimitSwitchAsDouble() {
        if (intakeLimitSwitch.get()) {
            return 1;
        } else {
            return 0;
        }
    }

    public boolean isSwitchPressed() {
        return !intakeLimitSwitch.get();
    }

    public void initDefaultCommand() { setDefaultCommand(new Intake()); }
}
