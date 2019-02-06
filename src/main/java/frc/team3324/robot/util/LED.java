package frc.team3324.robot.util;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Timer;

public class LED {
    private Solenoid redLED = new Solenoid(Constants.PCM.LEDS_MODULE_NUMBER, Constants.PCM.RED_LED_PORT);
    private Solenoid greenLED = new Solenoid(Constants.PCM.LEDS_MODULE_NUMBER, Constants.PCM.GREEN_LED_PORT);
    private Solenoid blueLED = new Solenoid(Constants.PCM.LEDS_MODULE_NUMBER, Constants.PCM.BLUE_LED_PORT);
    private Timer timer = new Timer();

    public LED() {
        redLED.setPulseDuration(0.5);
        timer.start();
    }

    public void setIntakeState() {
        if (timer.hasPeriodPassed(0.5)) {
            redLED.startPulse();
            Timer.delay(0.5);
            timer.reset();
        }
    }

    public void setOuttakeState() {
        redLED.set(true);
        greenLED.set(false);
        blueLED.set(false);
    }

    /*
    Pressing limit switch
     */
    public void setIntookState() {
        greenLED.set(true);
        redLED.set(false);
        blueLED.set(false);
    }

    public void setStageOneBrownout() {
        redLED.setPulseDuration(1.5);
        redLED.startPulse();
    }

    public void setStageTwoBrownout() {
        redLED.setPulseDuration(1.0);
        redLED.startPulse();
    }

    public void setStageThreeBrownout() {
        if (timer.hasPeriodPassed(0.3)) {
            redLED.setPulseDuration(0.3);
            blueLED.setPulseDuration(0.3);
            greenLED.setPulseDuration(0.3);
            redLED.startPulse();
            blueLED.startPulse();
            greenLED.startPulse();

            Timer.delay(0.3);
            timer.reset();
        }
    }

    public void setNeutralState() {
        redLED.set(true);
        blueLED.set(true);
        greenLED.set(true);
    }

}
