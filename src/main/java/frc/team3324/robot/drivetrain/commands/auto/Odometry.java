package frc.team3324.robot.drivetrain.commands.auto;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.team3324.robot.Robot;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import frc.team3324.robot.util.Constants;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;

import java.nio.file.Path;

public class Odometry extends Command {
    private double x, y, theta, phi, middleEncoder, lEncoder, rEncoder;
    private int segment = 0;
    private Trajectory trajectory;
    NetworkTableInstance inst = NetworkTableInstance.getDefault();
    NetworkTable table = inst.getTable("Live_Dashboard");
    NetworkTableEntry robotX = table.getEntry("robotX");
    NetworkTableEntry robotY = table.getEntry("robotY");
    NetworkTableEntry robotHeading = table.getEntry("robotHeading");
    NetworkTableEntry pathX = table.getEntry("pathX");
    NetworkTableEntry pathY = table.getEntry("pathY");
    NetworkTableEntry pathHeading = table.getEntry("pathHeading");

    public Odometry(double x, double y, double theta) {
        this.x = x;
        this.y = y;
        this.theta = theta;
        this.trajectory = trajectory;
    }
    // Called just before this Command runs the first time
    protected void initialize() {
        inst.startDSClient(); // recommended if running on DS computer; this gets the robot IP from the DS
        Robot.driveTrain.clearEncoder();
        Robot.driveTrain.clearGyro();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {

        lEncoder = Robot.driveTrain.lEncoder.getDistance();
        rEncoder = Robot.driveTrain.rEncoder.getDistance();
        double lastPosition = middleEncoder;
        middleEncoder = (lEncoder + rEncoder) / 2.0;
        double positionDifference = middleEncoder - lastPosition;
        phi = (lEncoder - rEncoder) / Constants.DriveTrain.DISTANCE_BETWEEN_WHEELS;
        theta = Math.toRadians(-Robot.driveTrain.getYaw());
        x += positionDifference * Math.cos(theta);
        y += positionDifference * Math.sin(theta);

        robotHeading.setNumber(theta);
        robotX.setNumber(x * 3.281);
        robotY.setNumber(y * 3.281);

        SmartDashboard.putNumber("robotX", x * 3.281);
        SmartDashboard.putNumber("robotY", y * 3.281);
        SmartDashboard.putNumber("robotTheta", theta);
    }

    protected boolean isFinished() { return false; }

    // Called once after isFinished returns true
    protected void end() {}

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {}
}
