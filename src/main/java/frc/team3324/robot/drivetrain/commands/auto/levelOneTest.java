package frc.team3324.robot.drivetrain.commands.auto;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.team3324.robot.drivetrain.commands.teleop.Drive;

/**
 * CommandGroup class...
 */
public class levelOneTest extends CommandGroup {
    public levelOneTest() {
        this.addSequential(new JaciPathfinding(PathGenerator.path.MID_HAB_LEFT_MID_CARGO, true, false));
        this.addSequential(new Drive());
        this.addSequential(new JaciPathfinding(PathGenerator.path.LEFT_CLOSE_ROCKET, true, false));
    }
}
