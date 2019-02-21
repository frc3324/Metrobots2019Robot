package frc.team3324.robot.climber;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team3324.robot.util.Constants;

/**
 * Subsystem class to control the frontClimber. This class will get and set the frontClimber status.
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

    private DoubleSolenoid frontClimber = new DoubleSolenoid(Constants.Climber.CLIMBER_BACK_FORWARD, Constants.Climber.CLIMBER_BACK_BACKWARD);
    private DoubleSolenoid backClimber = new DoubleSolenoid(Constants.Climber.CLIMBER_FRONT_FORWARD, Constants.Climber.CLIMBER_FRONT_BACKWARD);
    /**
     * Sets the frontClimber double solenoid status to forward.
     */

    public void switchFrontClimb() {
        if (frontClimber.get() == DoubleSolenoid.Value.kForward) {
            frontClimber.set(DoubleSolenoid.Value.kReverse);
        } else {
            frontClimber.set(DoubleSolenoid.Value.kForward);
        }
    }

    public void switchBackClimb() {
        if (backClimber.get() == DoubleSolenoid.Value.kForward) {
            backClimber.set(DoubleSolenoid.Value.kReverse);
        } else {
            backClimber.set(DoubleSolenoid.Value.kForward);
        }
    }
    /**
     * Sets the frontClimber double solenoid status to reverse.
     */

    /**
     * Gets status of frontClimber double solenoid.
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
