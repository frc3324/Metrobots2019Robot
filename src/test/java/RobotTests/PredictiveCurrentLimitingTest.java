package RobotTests;

import frc.team3324.robot.util.MotorConstants.*;
import frc.team3324.robot.util.PredictiveCurrentLimiting;
import frc.team3324.robot.wrappers.Motor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.function.BooleanSupplier;

@DisplayName("Predictive Current Limiting Test")
public class PredictiveCurrentLimitingTest {
    private Cim cim = new Cim(1);
    private Cim gearReductionCim = new Cim(1);
    enum condition { EQUAL, GREATER, LESS }
    private PredictiveCurrentLimiting predictiveCurrentLimiting = new PredictiveCurrentLimiting(-30, 30, 1, cim);
    private PredictiveCurrentLimiting predictiveCurrentLimitingGearRatio = new PredictiveCurrentLimiting(-30, 30, 2, gearReductionCim);


    @Test
    public void getVoltageTestSame() {
        getVoltageTest(predictiveCurrentLimiting, cim, 12, 12, 0, 0, condition.EQUAL);
    }

    @Test
    public void getVoltageTestDifferent() {
        getVoltageTest(predictiveCurrentLimiting, cim, 12, 24, 0, 0, condition.EQUAL);
    }

    @Test
    public void getVoltageHighRPMTest() {
        getVoltageTest(predictiveCurrentLimiting, cim, 12, 24, 3000, 3000, condition.EQUAL);
    }

    @Test
    public void getVoltageDifferentGearRatioTest() {
        getVoltageTest(predictiveCurrentLimitingGearRatio, cim, 12, 12, 1500, 3000, condition.GREATER);
    }
    @Test
    public void getVoltageGearRatioLowRPMTest() {
        getVoltageTest(predictiveCurrentLimitingGearRatio, cim, 12, 12, 2000, 1000, condition.EQUAL);
    }

    @Test
    public void getVoltageMultipleMotorsTest() {
        Cim twoCims = new Cim(2);
        PredictiveCurrentLimiting twoCimTest = new PredictiveCurrentLimiting(-30, 30,1, twoCims);
        getVoltageTest(twoCimTest, cim, 12, 12, 0, 0, condition.EQUAL);
    }

    @Test
    public void getVoltageMultipleMotorsHighRPMTest() {
        Cim twoCims = new Cim(2);
        PredictiveCurrentLimiting twoCimTest = new PredictiveCurrentLimiting(-30, 30,1, twoCims);
        getVoltageTest(twoCimTest, cim, 12, 12, 5330, 5330, condition.EQUAL);
    }

    @Test
    public void getVoltageNonLimitedTest() {
        double voltage = 1;
        MiniCim miniCim = new MiniCim(3);
        PredictiveCurrentLimiting predictiveCurrentLimiting = new PredictiveCurrentLimiting(-24, 24, 147, miniCim);
        double predictedVoltage = predictiveCurrentLimiting.getVoltage(voltage, 0);
        System.out.println("Predicted Voltage " + predictedVoltage + " Test Voltage: " + voltage);
        assert(voltage == predictedVoltage);
    }

    @Test
    private void getVoltageTest(PredictiveCurrentLimiting testLimiting, Motor testMotor, double predictedVoltage, double testVoltage, double testRPM, double predictedRPM, condition condition) {
        predictedVoltage = testLimiting.getVoltage(predictedVoltage, predictedRPM);
        double voltage_max = 30 * testMotor.getR() + testMotor.getKW() * testRPM;
        double voltage_min = -30 * testMotor.getR() + testMotor.getKW() * testRPM;
        testVoltage = Math.max(testVoltage, voltage_min);
        testVoltage = Math.min(testVoltage, voltage_max);
        System.out.println("Predicted Voltage: " + predictedVoltage + " Test Voltage: " + testVoltage);
        switch (condition) {
            case EQUAL:
                assert(predictedVoltage == testVoltage);
                break;
            case LESS:
                assert(predictedVoltage < testVoltage);
                break;
            case GREATER:
                assert (predictedVoltage > testVoltage);
                break;
        }
    }
}
