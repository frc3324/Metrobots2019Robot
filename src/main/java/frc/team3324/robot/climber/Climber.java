package frc.team3324.robot.climber;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team3324.robot.util.Constants;

/**
 * Subsystem class to control the climber. This class will get and set the climber status.
 */
public class Climber extends Subsystem {

    enum Status {

        Up,
        Down;
    }

    public Status status = Status.Up;

    /**
     * Creates an instance of the Climber class.
     */
    public Climber() { }

    private DoubleSolenoid climber = new DoubleSolenoid(Constants.Climber.CLIMBER_PORT_FORWARD, Constants.Climber.CLIMBER_PORT_BACKWARD);

    /**
     * Sets the climber double solenoid status to forward.
     */
    public void pushDown() {
        climber.set(DoubleSolenoid.Value.kForward);
        status = Status.Down;
    }

    /**
     * Sets the climber double solenoid status to reverse.
     */
    public void pushUp() {
        climber.set(DoubleSolenoid.Value.kReverse);
        status = Status.Up;
    }

    /**
     * Gets status of climber double solenoid.
     *
     * @return boolean, 1 or 0.
     */
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
