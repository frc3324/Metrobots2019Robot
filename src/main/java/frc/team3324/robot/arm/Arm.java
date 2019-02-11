package frc.team3324.robot.arm;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import frc.team3324.robot.arm.commands.ControlArm;
import frc.team3324.robot.util.Constants;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Subsystem class to control the arm. This class will set the arm motor velocities.
 */
public class Arm extends Subsystem {
    // TODO Invert stuff
    private WPI_TalonSRX armMotorOne = new WPI_TalonSRX(Constants.Arm.MOTOR_PORT_ARM_ONE);
    private WPI_VictorSPX armMotorTwo = new WPI_VictorSPX(Constants.Arm.MOTOR_PORT_ARM_TWO);
    private WPI_VictorSPX armMotorThree = new WPI_VictorSPX(Constants.Arm.MOTOR_PORT_ARM_THREE);

    private SpeedControllerGroup armMotors = new SpeedControllerGroup(armMotorOne, armMotorTwo);

    /**
     * Creates an instance of the Arm class.
     */
    public Arm() {
        armMotorOne.configContinuousCurrentLimit(84);
        armMotorOne.configPeakCurrentDuration(200);
        armMotorOne.configPeakCurrentLimit(150);
        armMotorTwo.follow(armMotorOne);
        armMotorThree.follow(armMotorOne);
    }

    /**
     * Moves the arm at the specified speed.
     *
     * @param speed, -1.0 to 1.0
     */
    public void setArmSpeed(double speed) { armMotors.set(speed); }

    public void initDefaultCommand() { setDefaultCommand(new ControlArm()); }
}
