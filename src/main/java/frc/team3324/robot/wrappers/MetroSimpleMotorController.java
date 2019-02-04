package frc.team3324.robot.wrappers;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.IMotorController;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

public class MetroSimpleMotorController extends WPI_VictorSPX {
    // Wrapper for a simple motor controller like a Spark or a VictorSPX.
    public MetroSimpleMotorController(int port) {
        super(port);
    }

    @Override
    public void follow(IMotorController masterToFollow) {
        super.follow(masterToFollow);
    }

    @Override
    public void set(double speed) {
        super.set(speed);
    }

    @Override
    public void set(ControlMode mode, double value) {
        super.set(mode, value);
    }

    @Override
    public void set(ControlMode mode, double demand0, DemandType demand1Type, double demand1) {
        super.set(mode, demand0, demand1Type, demand1);
    }

    @Override
    public void setInverted(boolean inverted) {
        super.setInverted(inverted);
    }
}
