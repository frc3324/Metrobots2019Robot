package frc.team3324.robot.util;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

import frc.team3324.robot.Robot;
import frc.team3324.robot.climber.commands.teleop.PushDown;
import frc.team3324.robot.climber.commands.teleop.PushUp;
import frc.team3324.robot.intake.Intake;
import frc.team3324.robot.drivetrain.commands.teleop.ShiftGears;
import frc.team3324.robot.drivetrain.commands.auto.JaciPathfinding;
import frc.team3324.robot.drivetrain.commands.auto.PathGenerator;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
    private static final int BUTTON_A             = 1;
    private static final int BUTTON_B             = 2;
    private static final int BUTTON_X             = 3;
    private static final int BUTTON_Y             = 4;
    private static final int LEFT_BUMPER          = 5;
    private static final int RIGHT_BUMPER         = 6;
    private static final int BUTTON_BACK          = 7;
    private static final int BUTTON_START         = 8;
    private static final int JOYSTICK_LEFT_CLICK  = 9;
    private static final int JOYSTICK_RIGHT_CLICK = 10;

    public static XboxController primaryController   = new XboxController(0);
    public static XboxController secondaryController = new XboxController(1);
    /**
     * Primary driver buttons assignments
     */
    private static final Button PRIMARY_A_BUTTON     = new JoystickButton(primaryController, BUTTON_A);
    private static final Button PRIMARY_X_BUTTON = new JoystickButton(primaryController, BUTTON_X);
    private static final Button PRIMARY_Y_BUTTON = new JoystickButton(primaryController, BUTTON_Y);
    public static final Button PRIMARY_RIGHT_BUMPER = new JoystickButton(primaryController, RIGHT_BUMPER);
    public static final Button PRIMARY_LEFT_BUMPER = new JoystickButton(primaryController, LEFT_BUMPER);
    public static final Button PRIMARY_START_BUTTON = new JoystickButton(primaryController, BUTTON_START);
    public static final Button PRIMARY_BACK_BUTTON = new JoystickButton(primaryController, BUTTON_BACK)


    /**
     * Secondary driver buttons assignments
     */
    public static final Button SECONDARY_A_BUTTON              = new JoystickButton(secondaryController, BUTTON_A);
    public static final Button SECONDARY_B_BUTTON              = new JoystickButton(secondaryController, BUTTON_B);
    public static final Button SECONDARY_X_BUTTON              = new JoystickButton(secondaryController, BUTTON_X);
    public static final Button SECONDARY_Y_BUTTON              = new JoystickButton(secondaryController, BUTTON_Y);
    public static final Button SECONDARY_BACK_BUTTON           = new JoystickButton(secondaryController, BUTTON_BACK);
    public static final Button SECONDARY_START_BUTTON          = new JoystickButton(secondaryController, BUTTON_START);
    public static final Button SECONDARY_LEFT_JOYSTICK_BUTTON  = new JoystickButton(secondaryController, JOYSTICK_LEFT_CLICK);
    public static final Button SECONDARY_RIGHT_JOYSTICK_BUTTON = new JoystickButton(secondaryController, JOYSTICK_RIGHT_CLICK);

    public static final Button SECONDARY_LEFT_BUMPER  = new JoystickButton(secondaryController, LEFT_BUMPER);
    public static final Button SECONDARY_RIGHT_BUMPER = new JoystickButton(secondaryController, RIGHT_BUMPER);

    /**
     * Controller buttons.
     * aButton -> Shift gears
     */
    public OI() {
        PRIMARY_RIGHT_BUMPER.whenPressed(new ShiftGears());
        PRIMARY_X_BUTTON.whenPressed(new JaciPathfinding(PathGenerator.path.DEFAULT, true, false));
        PRIMARY_A_BUTTON.whenPressed(new JaciPathfinding(PathGenerator.path.LEFT_CLOSE_ROCKET, true ,true));
        PRIMARY_Y_BUTTON.whenPressed(Robot.characterizer);
        PRIMARY_START_BUTTON.whenPressed(new PushDown());
        PRIMARY_BACK_BUTTON.whenPressed(new PushUp());
        PRIMARY_LEFT_BUMPER.whenPressed(new Intake());
        //Secondary button commands
    }
}

