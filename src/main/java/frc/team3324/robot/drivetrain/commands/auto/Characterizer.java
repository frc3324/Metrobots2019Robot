package frc.team3324.robot.drivetrain.commands.auto;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team3324.robot.Robot;
import frc.team3324.robot.util.OI;

import java.util.function.Supplier;

/**
 * Command class to drive robot at a set velocity.
 */
public class Characterizer extends Command {


    private Supplier<Double> leftEncoderPosition;
    private Supplier<Double> leftEncoderRate;
    private Supplier<Double> rightEncoderPosition;
    private Supplier<Double> rightEncoderRate;

    private NetworkTableEntry autoSpeedEntry = NetworkTableInstance.getDefault().getEntry("/robot/autospeed");
    private NetworkTableEntry telemetryEntry = NetworkTableInstance.getDefault().getEntry("/robot/telemetry");

    private double priorAutospeed = 0;
    private Number[] numberArray = new Number[9];


    @Override
    protected void initialize() {
        leftEncoderPosition = Robot.driveTrain.lEncoder::getDistance;
        leftEncoderRate = Robot.driveTrain.lEncoder::getRate;
        rightEncoderPosition = Robot.driveTrain.rEncoder::getDistance;
        rightEncoderRate = Robot.driveTrain.rEncoder::getRate;
        NetworkTableInstance.getDefault().setUpdateRate(0.010);
    }

    public void execute() {
        // Retrieve values to send back before telling the motors to do something
        double now = Timer.getFPGATimestamp();

        double leftPosition = leftEncoderPosition.get();
        double leftRate = leftEncoderRate.get();

        double rightPosition = rightEncoderPosition.get();
        double rightRate = rightEncoderRate.get();

        double battery = RobotController.getBatteryVoltage();
        double motorVolts = battery * Math.abs(priorAutospeed);

        double leftMotorVolts = motorVolts;
        double rightMotorVolts = motorVolts;

        // Retrieve the commanded speed from NetworkTables
        double autospeed = autoSpeedEntry.getDouble(0);
        priorAutospeed = autospeed;

        // command motors to do things
        Robot.driveTrain.mDrive.tankDrive(autospeed, autospeed, false);

        // send telemetry data array back to NT
        numberArray[0] = now;
        numberArray[1] = battery;
        numberArray[2] = autospeed;
        numberArray[3] = leftMotorVolts;
        numberArray[4] = rightMotorVolts;
        numberArray[5] = leftPosition;
        numberArray[6] = rightPosition;
        numberArray[7] = leftRate;
        numberArray[8] = rightRate;

        telemetryEntry.setNumberArray(numberArray);

        SmartDashboard.putNumber("l_encoder_pos", leftEncoderPosition.get());
        SmartDashboard.putNumber("l_encoder_rate", leftEncoderRate.get());
        SmartDashboard.putNumber("r_encoder_pos", rightEncoderPosition.get());
        SmartDashboard.putNumber("r_encoder_rate", rightEncoderRate.get());
    }

    public boolean isFinished() { return OI.primaryController.getBButton(); }
}
