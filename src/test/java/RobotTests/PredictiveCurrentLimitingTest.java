package RobotTests;

import frc.team3324.robot.util.MotorConstants.*;
import frc.team3324.robot.util.PredictiveCurrentLimiting;
import frc.team3324.robot.wrappers.Motor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.function.BooleanSupplier;

@DisplayName("Predictive Current Limiting Test")
public class PredictiveCurrentLimitingTest {
    Cim cim = new Cim(1);
    Cim gearReductionCim = new Cim(1);
    enum condition { EQUAL, GREATER, LESS }
    private PredictiveCurrentLimiting predictiveCurrentLimiting = new PredictiveCurrentLimiting(-30, 30, 1, cim);
    private PredictiveCurrentLimiting predictiveCurrentLimitingGearRatio = new PredictiveCurrentLimiting(-30, 30, 2, gearReductionCim);


    @Test
    public void getVoltageTestSame() {
        double voltage = 12;
        double predictedVoltage = predictiveCurrentLimiting.getVoltage(12, 0);
        double voltage_max = 30 * cim.getR() + cim.getKW() * 0;
        double voltage_min = -30 * cim.getR() + cim.getKW() * 0;
        voltage = Math.max(voltage, voltage_min);
        voltage = Math.min(voltage, voltage_max);
        System.out.println("Predicted Voltage: " + predictedVoltage + "Test Voltage" + voltage);
        assert(voltage == predictedVoltage);
    }

    @Test
    public void getVoltageTestDifferent() {
        double voltage = 24;
        double predictedVoltage = predictiveCurrentLimiting.getVoltage(12, 0);
        double voltage_max = 30 * cim.getR() + cim.getKW() * 0;
        double voltage_min = -30 * cim.getR() + cim.getKW() * 0;
        voltage = Math.max(voltage, voltage_min);
        voltage = Math.min(voltage, voltage_max);
        System.out.println("Predicted Voltage: " + predictedVoltage + "Test Voltage" + voltage);
        assert(voltage == predictedVoltage);
    }

    @Test
    public void getVoltageHighRPMTest() {
        double voltage = 24;
        double predictedVoltage = predictiveCurrentLimiting.getVoltage(12, 3000);
        double voltage_max = 30 * cim.getR() + cim.getKW() * 3000;
        double voltage_min = -30 * cim.getR() + cim.getKW() * 3000;
        voltage = Math.max(voltage, voltage_min);
        voltage = Math.min(voltage, voltage_max);
        System.out.println("Predicted Voltage: " + predictedVoltage + "Test Voltage" + voltage);
        assert(voltage == predictedVoltage);
    }

    @Test
    public void getVoltageDifferentGearRatioTest() {
        double voltage = 12;
        double predictedVoltage = predictiveCurrentLimitingGearRatio.getVoltage(12, 3000);
        double voltage_max = 30 * cim.getR() + cim.getKW() * 1500;
        double voltage_min = -30 * cim.getR() + cim.getKW() * 1500;
        voltage = Math.max(voltage, voltage_min);
        voltage = Math.min(voltage, voltage_max);
        System.out.println("Predicted Voltage: " + predictedVoltage + "Test Voltage" + voltage);
        assert(voltage < predictedVoltage);
    }
    @Test
    public void getVoltageGearRatioLowRPMTest() {
        double voltage = 12;
        double predictedVoltage = predictiveCurrentLimitingGearRatio.getVoltage(12, 1000);
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
