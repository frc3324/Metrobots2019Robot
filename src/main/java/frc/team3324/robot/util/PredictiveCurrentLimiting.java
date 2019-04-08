package frc.team3324.robot.util;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team3324.robot.wrappers.Motor;

public class PredictiveCurrentLimiting {
    private double maxCurrent, minCurrent;
    private Motor motor;

    public PredictiveCurrentLimiting(double min, double max, double gearRatio, Motor motor) {
        setCurrentLimits(min, max);
        this.motor = motor;
        this.motor.reduce(gearRatio);
    }

    public void setCurrentLimits(double min, double max) {
       this.minCurrent = min;
       this.maxCurrent = max;
    }

    public double getVoltage(double voltage, double angularVelocity) {
        double voltage_min = minCurrent * motor.getR() + motor.getKW() * angularVelocity;
        double voltage_max = maxCurrent * motor.getR() + motor.getKW() * angularVelocity;
        SmartDashboard.putNumber("voltage_min", voltage_min);
        SmartDashboard.putNumber("voltage_max", voltage_max);
        SmartDashboard.putNumber("voltage", voltage);
        voltage = Math.max(voltage, voltage_min);
        voltage = Math.min(voltage, voltage_max);
        return voltage;
    }
}
