package frc.team3324.robot.wrappers;

import badlog.lib.BadLog;

import java.util.function.DoubleSupplier;

public class Logger {

    public Logger(String path, boolean compress) { BadLog.init(path, compress); }
}
