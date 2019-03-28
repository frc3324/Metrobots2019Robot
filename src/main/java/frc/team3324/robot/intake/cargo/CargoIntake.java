package frc.team3324.robot.intake.cargo;

import badlog.lib.BadLog;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team3324.robot.intake.cargo.commands.Intake;
import frc.team3324.robot.intake.cargo.commands.ManualCargo;
import frc.team3324.robot.util.Constants;

import static frc.team3324.robot.Robot.pdp;

/**
 * Subsystem class to control cargo intake/outtake system.
 */
public class CargoIntake extends Subsystem {
    public WPI_VictorSPX intakeMotor = new WPI_VictorSPX(Constants.CargoIntake.CARGO_INTAKE_MOTOR_PORT);

    public DigitalInput intakeLimitSwitch = new DigitalInput(Constants.CargoIntake.LIMIT_SWITCH_PORT);

    /**
     * Creates an instance of the CargoIntake class.
     * <p>Populate badlog with limit switch status and current (amps) value.</p>
     */
    public CargoIntake() {
        intakeMotor.setInverted(true);
        intakeMotor.setNeutralMode(com.ctre.phoenix.motorcontrol.NeutralMode.Brake);
        BadLog.createTopic("cargointake/Current Draw", "amps", () -> pdp.getCurrent(Constants.CargoIntake.CARGO_INTAKE_PDP_PORT));
    }

    /**
     * Gets cargo intake limit switch value as a double.
     *
     * @return double, 1 or 0.
     */
    public double getLimitSwitchAsDouble() {
        if (intakeLimitSwitch.get()) {
            return 1;
        } else {
            return 0;
        }
    }

    /**
     * Gets state of limit switch.
     *
     * @return boolean, true or false.
     */
    public boolean isSwitchPressed() {
        return !intakeLimitSwitch.get();
    }

    public void initDefaultCommand() {
        setDefaultCommand(new ManualCargo());
    }
}
