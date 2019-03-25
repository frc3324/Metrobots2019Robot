package frc.team3324.robot.wrappers;

public interface Motor {

    double getR();
    double getKW();
    double getStallTorque();
    double getStallCurrent();
    double getFreeSpeed();
    double getFreeCurrent();
    double getKt();
    void reduce(double reduction);
}
