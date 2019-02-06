package frc.team3324.robot.intake;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team3324.robot.util.Constants;

public class CargoIntake extends Subsystem {

    private WPI_VictorSPX intakeMotorLeft = new WPI_VictorSPX(Constants.CargoIntake.LEFT_INTAKE_MOTOR_PORT);
    private WPI_VictorSPX intakeMotorRight = new WPI_VictorSPX(Constants.CargoIntake.RIGHT_INTAKE_MOTOR_PORT);
    private SpeedControllerGroup intakeMotors = new SpeedControllerGroup(intakeMotorLeft, intakeMotorRight);

    private DigitalInput limitSwitch = new DigitalInput(Constants.LIMIT_SWITCH_PORT);

    public CargoIntake() { }

    public void setIntakeSpeed(double speed) {
        intakeMotors.set(speed);
    }

    public boolean isSwitchPressed() {
        return !limitSwitch.get();
    }

    public void initDefaultCommand() { }
}
