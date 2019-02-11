package frc.team3324.robot.wrappers;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.IMotorController;

/**
 * Class to encapsulate (wrap) the VictorSPX class.
 */
public class MetroSimpleMotorController extends WPI_VictorSPX {

    /**
     * Creates constructor for motor controller.
     *
     * @param port, channel on PDP
     * @see WPI_VictorSPX
     */
    public MetroSimpleMotorController(int port) { super(port); }

    /**
     * Slaves motor controller to master motor controller.
     *
     * @param masterToFollow, motor controller
     * @see WPI_VictorSPX
     * @see IMotorController
     */
    @Override
    public void follow(IMotorController masterToFollow) {
        super.follow(masterToFollow);
    }

    /**
     * Sets velocity of motor controller.
     *
     * @param speed, -1.0 to 1.0
     * @see WPI_VictorSPX
     */
    @Override
    public void set(double speed) {
        super.set(speed);
    }

    /**
     * Sets motor controller mode and appropriate value.
     *
     * @param mode, output mode from 0 to 15
     * @param value, appropriate value according to mode
     * @see WPI_VictorSPX
     * @see ControlMode
     */
    @Override
    public void set(ControlMode mode, double value) {
        super.set(mode, value);
    }

    /**
     * Sets motor controller mode, appropriate value, type of supplementary value, and supplementary value.
     *
     * @param mode, output mode from 0 to 15
     * @param demand0, appropriate value according to mode
     * @param demand1Type, demand type for demand1
     * @param demand1, supplementary output value
     * @see WPI_VictorSPX
     * @see ControlMode
     * @see DemandType
     */
    @Override
    public void set(ControlMode mode, double demand0, DemandType demand1Type, double demand1) {
        super.set(mode, demand0, demand1Type, demand1);
    }

    /**
     * Inverts direction of motor controller.
     *
     * @param inverted, to invert or not
     * @see WPI_VictorSPX
     */
    @Override
    public void setInverted(boolean inverted) {
        super.setInverted(inverted);
    }
}
