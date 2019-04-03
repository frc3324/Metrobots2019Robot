package frc.team3324.robot.util;

import frc.team3324.robot.wrappers.Motor;

public class PredictiveCurrentLimiting {
    private double maxCurrent, minCurrent;
    private Motor motor;

    public PredictiveCurrentLimiting(double min, double max, double gearRatio, Motor motor) {
        motor.reduce(gearRatio);
        setCurrentLimits(min, max);
        this.motor = motor;
    }

    public void setCurrentLimits(double min, double max) {
       this.minCurrent = min;
       this.maxCurrent = max;
    }

    public double getVoltage(double voltage, double angularVelocity) {
        double voltage_min = minCurrent * motor.getR() + motor.getKW() * angularVelocity;
        double voltage_max = maxCurrent * motor.getR() + motor.getKW() * angularVelocity;
        voltage = Math.max(voltage, voltage_min);
        voltage = Math.min(voltage, voltage_max);
        return voltage;
    }
}
