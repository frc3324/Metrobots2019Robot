package frc.team3324.robot.climber;

import badlog.lib.BadLog;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team3324.robot.Robot;
import frc.team3324.robot.util.Constants;

public class Climber extends Subsystem {

    enum Status {

        Up,
        Down;
    }

    public Status status = Status.Up;

    public Climber() { }

    private DoubleSolenoid climber = new DoubleSolenoid(Constants.Climber.CLIMBER_PORT_FORWARD, Constants.Climber.CLIMBER_PORT_BACKWARD);

    public void pushDown() {
        climber.set(DoubleSolenoid.Value.kForward);
        status = Status.Down;
    }

    public void pushUp() {
        climber.set(DoubleSolenoid.Value.kReverse);
        status = Status.Up;
    }

    public double getStatus() {
        if (status == Status.Up) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    protected void initDefaultCommand() {}
}
