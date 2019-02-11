package frc.team3324.robot.drivetrain.commands.auto;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

/**
 * Class to populate Shuffleboard with odometry information.
 */
public class PathfinderShuffleboard {
    public static ShuffleboardTab pathfinderTab = Shuffleboard.getTab("Pathfinder");
    public static NetworkTableEntry leftOutput = pathfinderTab.add("Left Motor Output", 0).withPosition(0, 0).getEntry();
    public static NetworkTableEntry rightOutput = pathfinderTab.add("Right Motor Output", 0).withPosition(1, 0).getEntry();
    public static NetworkTableEntry finished = pathfinderTab.add("Finished", false).withPosition(2, 0).getEntry();

    public static NetworkTableEntry desiredHeading = pathfinderTab.add("Desired Heading", 0).withPosition(0, 1).getEntry();
    public static NetworkTableEntry heading = pathfinderTab.add("Current Heading", 0).withPosition(1, 1).getEntry();
    public static NetworkTableEntry angleError = pathfinderTab.add("Angle Difference", 0).withPosition(2, 1).getEntry();
    public static NetworkTableEntry headingCorrectSpeed = pathfinderTab.add("Heading Correct Speed", 0).withPosition(3, 1).getEntry();
}
