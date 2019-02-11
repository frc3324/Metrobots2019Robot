package frc.team3324.robot.util;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

public class VisionComms {

    NetworkTable table;
    double visionX;
    double visionY;
    double visionAngle;

    public VisionComms() {
        table = NetworkTable.getTable("SmartDashboard");
    }

    public double getVisionX() {
        visionX = table.getNumber("visionX", 0.0);
        return visionX;
    }

    public double getVisionY() {
        visionY = table.getNumber("visionY", 0.0);
        return visionY;
    }

    public double getVisionAngle() {
        visionAngle = table.getNumber("visionAngle", 0.0);
        return visionAngle;
    }

}
