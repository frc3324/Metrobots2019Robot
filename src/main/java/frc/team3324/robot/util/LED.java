package frc.team3324.robot.util;

import edu.wpi.first.wpilibj.Solenoid;

public class LED {
    Solenoid redLED = new Solenoid(Constants.PCM.LEDS_MODULE_NUMBER, Constants.PCM.RED_LED_PORT);
    Solenoid greenLED = new Solenoid(Constants.PCM.LEDS_MODULE_NUMBER, Constants.PCM.GREEN_LED_PORT);
    Solenoid blueLED = new Solenoid(Constants.PCM.LEDS_MODULE_NUMBER, Constants.PCM.BLUE_LED_PORT);

    public LED() { }

    public void setIntakeState() {
        redLED.set(true);
    }

    public void setOuttakeState() {
        greenLED.set(true);
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
        redLED.setPulseDuration(0.5);
        redLED.startPulse();
    }

    public void setNeutralState() {
        redLED.set(true);
        greenLED.set(true);
        blueLED.set(true);
    }
}
