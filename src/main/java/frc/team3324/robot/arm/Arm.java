package frc.team3324.robot.arm;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import frc.team3324.robot.arm.commands.ControlArm;
import frc.team3324.robot.util.Constants;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Arm extends Subsystem {
    // TODO Invert stuff
    private WPI_TalonSRX armMotorLeft     = new WPI_TalonSRX(Constants.Arm.MOTOR_PORT_ARM_LEFT);
    private WPI_VictorSPX armMotorRight    = new WPI_VictorSPX(Constants.Arm.MOTOR_PORT_ARM_RIGHT);
    private SpeedControllerGroup armMotors = new SpeedControllerGroup(armMotorLeft, armMotorRight);

    public Arm() {
        armMotorRight.follow(armMotorLeft);
        // CAUTION: direction already set, don't change it
        armMotorLeft.setInverted(true);
    }

    public void setArmSpeed(double speed) { armMotors.set(speed); }

    public void initDefaultCommand() { setDefaultCommand(new ControlArm()); }
}
