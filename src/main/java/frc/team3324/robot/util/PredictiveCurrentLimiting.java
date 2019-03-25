package frc.team3324.robot.util;

import frc.team3324.robot.wrappers.Motor;

import java.util.function.DoubleSupplier;

public class PredictiveCurrentLimiting {
    private double maxCurrent, minCurrent, gearRatio;
    private Motor motor;

    public PredictiveCurrentLimiting(double max, double min, double gearRatio, Motor motor) {
        motor.reduce(gearRatio);
        setCurrentLimits(max, min);
        this.gearRatio = gearRatio;
        this.motor = motor;
    }

    public void setCurrentLimits(double max, double min) {
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
