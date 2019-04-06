package frc.team3324.robot.drivetrain.commands.auto;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class LeftLevelTwo extends CommandGroup {
    public LeftLevelTwo() {
        this.addSequential(new JaciPathfinding(PathGenerator.path.LEFT_LEVEL_2, true, false));
        this.addSequential(new JaciPathfinding(PathGenerator.path.LEFT_TWO_INTAKE_STATION, true, true));
    }
}
