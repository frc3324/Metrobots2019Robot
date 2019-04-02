package RobotTests;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team3324.robot.wrappers.PIDCommand;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("PIDCommandTest")
public class PIDCommandTest extends PIDCommand {
    private double integral = 0;

    public PIDCommandTest(double kP, double kI, double kD, double goal, Subsystem subsystem) {
        super(kP, kI, kD, goal, 0.01);
    }

    @Override
    protected double returnPIDInput() {
        return 0;
    }

    public void test() {
       executePID();
    }

    private double calculateTestPID() {
        double position = returnPIDInput();
        double error = goal - position;
        double deriv = position - lastPosition;
        integral += error;
        lastPosition = position;

        return (error * kP) + (integral * kI) - (deriv * kD);
    }

    @Override
    protected void usePIDOutput(double output) {
        assert(output == calculateTestPID());
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}
