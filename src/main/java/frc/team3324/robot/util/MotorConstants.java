package frc.team3324.robot.util;

import frc.team3324.robot.wrappers.Motor;

public class MotorConstants {
    public static class Cim implements Motor {
        public double STALL_TORQUE = 2.41;
        public double FREE_SPEED = 5330;
        public double FREE_CURRENT = 2.7;
        public double STALL_CURRENT = 131;
        public double R = 12 / STALL_CURRENT;
        public double kW = (12 - FREE_CURRENT * R) / FREE_SPEED;
        public double kT = STALL_TORQUE / STALL_CURRENT;

        @Override
        public double getR() {
            return R;
        }

        @Override
        public double getFreeCurrent() {
            return FREE_CURRENT;
        }

        @Override
        public double getFreeSpeed() {
            return FREE_SPEED;
        }

        @Override
        public double getKt() {
            return kT;
        }

        @Override
        public double getKW() {
            return kW;
        }

        @Override
        public double getStallCurrent() {
            return STALL_CURRENT;
        }

        @Override
        public double getStallTorque() {
            return STALL_TORQUE;
        }

        public void reduce(double reduction) {
            FREE_SPEED =  FREE_SPEED / reduction;
            STALL_TORQUE = STALL_TORQUE * reduction;
            kW = (12 - FREE_CURRENT * R) / FREE_SPEED;
            kT = STALL_TORQUE / STALL_CURRENT;
        }
    }

    public static class MiniCim implements Motor {
        public double STALL_TORQUE = 1.41;
        public double FREE_SPEED = 5840;
        public double FREE_CURRENT = 3;
        public double STALL_CURRENT = 89;
        public double R = 12 / STALL_CURRENT;
        public double kW = (12 - FREE_CURRENT * R) / FREE_SPEED;
        public double kT = STALL_TORQUE / STALL_CURRENT;

        @Override
        public double getR() {
            return R;
        }

        @Override
        public double getFreeCurrent() {
            return FREE_CURRENT;
        }

        @Override
        public double getFreeSpeed() {
            return FREE_SPEED;
        }

        @Override
        public double getKt() {
            return kT;
        }

        @Override
        public double getKW() {
            return kW;
        }

        @Override
        public double getStallCurrent() {
            return STALL_CURRENT;
        }

        @Override
        public double getStallTorque() {
            return STALL_TORQUE;
        }

        @Override
        public void reduce(double reduction) {
            FREE_SPEED =  FREE_SPEED / reduction;
            STALL_TORQUE = STALL_TORQUE * reduction;
            kW = (12 - FREE_CURRENT * R) / FREE_SPEED;
            kT = STALL_TORQUE / STALL_CURRENT;
        }
    }

    public static class Bag implements Motor {
        public double STALL_TORQUE = 0.4;
        public double FREE_SPEED = 13180;
        public double FREE_CURRENT = 1.8;
        public double STALL_CURRENT = 53;
        public double R = 12 / STALL_CURRENT;
        public double kW = (12 - FREE_CURRENT * R) / FREE_SPEED;
        public double kT = STALL_TORQUE / STALL_CURRENT;

        @Override
        public double getR() {
            return R;
        }

        @Override
        public double getFreeCurrent() {
            return FREE_CURRENT;
        }

        @Override
        public double getFreeSpeed() {
            return FREE_SPEED;
        }

        @Override
        public double getKt() {
            return kT;
        }

        @Override
        public double getKW() {
            return kW;
        }

        @Override
        public double getStallCurrent() {
            return STALL_CURRENT;
        }

        @Override
        public double getStallTorque() {
            return STALL_TORQUE;
        }

        @Override
        public void reduce(double reduction) {
            FREE_SPEED =  FREE_SPEED / reduction;
            STALL_TORQUE = STALL_TORQUE * reduction;
            kW = (12 - FREE_CURRENT * R) / FREE_SPEED;
            kT = STALL_TORQUE / STALL_CURRENT;
        }
    }

}
