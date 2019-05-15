package frc.team3324.robot.util;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;

/**
 * Class to control the LEDs.
 */
public class LED {
    private Solenoid redLED = new Solenoid(Constants.LED.LED_PCM_MODULE, Constants.LED.RED_LED_PORT);
    private Solenoid greenLED = new Solenoid(Constants.LED.LED_PCM_MODULE, Constants.LED.GREEN_LED_PORT);
    private Solenoid blueLED = new Solenoid(Constants.LED.LED_PCM_MODULE, Constants.LED.BLUE_LED_PORT);
    private Timer timer = new Timer();

    double pulseTime = 0.5;

    /**
     * Creates an instance of the LED class.
     */
    public LED() {
        redLED.setPulseDuration(pulseTime);
        timer.start();
    }

    /**
     * Activates set time pulses red LEDs and turns off all other colors.
     */
    public void setCargoIntakeState() {
        blueLED.set(false);
        greenLED.set(false);
        if (!redLED.get() && timer.get() >= pulseTime) {
            redLED.startPulse();
            timer.stop();
            timer.reset();
        }
        else if (redLED.get() && timer.get() > pulseTime) {
            redLED.set(false);
        }
        else {
            timer.start();
        }
    }

    /**
     * Turn on green LEDs and turn off all other colors.
     */
    public void setCargoOuttakeState() {
        greenLED.set(true);
        redLED.set(false);
        blueLED.set(false);
    }

    /**
     * Turn on red LEDs and turn off all other colors.
     */
    public void setCargoIntookState() {
        redLED.set(true);
        greenLED.set(false);
        blueLED.set(false);
    }

    /**
     * Turn on all LED colors.
     */
    public void setGreenLED(boolean value) {
        greenLED.set(value);
    }

    public void setBlueLED(boolean value) {
        blueLED.set(value);
    }

    public void setRedLED(boolean value) {
        redLED.set(value);
    }
}
