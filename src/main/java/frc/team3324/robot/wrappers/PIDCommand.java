package frc.team3324.robot.wrappers;

import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;

public abstract class PIDCommand extends Command {

    protected double kP;
    protected double kI;
    protected double kD;
    protected double goal;
    protected double dt;

    protected double lastPosition = returnPIDInput();
    protected double integral = 0;

    Notifier notifier = new Notifier(() -> {
        executePID();
    });

    public PIDCommand(double kP, double kI, double kD, double goal, Subsystem subsystem, double dt) {
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
        this.goal = goal;

        requires(subsystem);
    }

    @Override
    protected void initialize() {
        integral = 0;
        notifier.startPeriodic(dt);
    }

    protected void executePID() {
        double position = returnPIDInput();
        double error = goal - position;
        double deriv = position - lastPosition;
        integral += error;
        lastPosition = position;

        double calculatedValue = (error * kP) + (integral * kI) - (deriv * kD);
        usePIDOutput(calculatedValue);
    }

    @Override
    protected void end() {
        notifier.stop();
        notifier.stop();
    }

    @Override
    protected void interrupted() {
        notifier.stop();
        end();
    }

    protected abstract void usePIDOutput(double output);

    protected abstract double returnPIDInput();

    protected abstract boolean isFinished();
}
