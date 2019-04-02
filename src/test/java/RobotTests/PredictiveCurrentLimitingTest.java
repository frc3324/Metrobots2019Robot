package RobotTests;

import frc.team3324.robot.util.MotorConstants.*;
import frc.team3324.robot.util.PredictiveCurrentLimiting;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Predictive Current Limiting Test")
public class PredictiveCurrentLimitingTest {
  Cim cim = new Cim(1);
  Cim gearReductionCim = new Cim(1);
  private PredictiveCurrentLimiting predictiveCurrentLimiting = new PredictiveCurrentLimiting(30, -30, 1, cim);
  private PredictiveCurrentLimiting predictiveCurrentLimitingGearRatio = new PredictiveCurrentLimiting(30, -30, 2, gearReductionCim);


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
    double voltage_max = 30 * cim.getR() + cim.getKW() * 2000;
    double voltage_min = -30 * cim.getR() + cim.getKW() * 2000;
    voltage = Math.max(voltage, voltage_min);
    voltage = Math.min(voltage, voltage_max);
    System.out.println("Predicted Voltage: " + predictedVoltage + "Test Voltage" + voltage);
    assert(voltage == predictedVoltage);
  }

  @Test
  public void getVoltageMultipleMotorsTest() {
    Cim twoCims = new Cim(2);
    PredictiveCurrentLimiting twoCimTest = new PredictiveCurrentLimiting(30, 30,1, twoCims);
    double voltage = 12;
    double predictedVoltage = twoCimTest.getVoltage(12, 0);
    double voltage_max = 30 * cim.getR() + cim.getKW() * 0;
    double voltage_min = -30 * cim.getR() + cim.getKW() * 0;
    voltage = Math.max(voltage, voltage_min);
    voltage = Math.min(voltage, voltage_max);
    System.out.println("Predicted Voltage: " + predictedVoltage + "Test Voltage" + voltage);
    assert(voltage < predictedVoltage);
  }

}
