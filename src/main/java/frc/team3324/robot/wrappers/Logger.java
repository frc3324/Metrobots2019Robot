package frc.team3324.robot.wrappers;

import badlog.lib.BadLog;

import java.util.function.DoubleSupplier;

/**
 * Class to encapsulate (wrap) a logger class.
 */
public class Logger {

    /**
     * Creates an instance of the Logger class.
     *
     * @param path, path name
     * @param compress, whether to compress
     */
    public Logger(String path, boolean compress) { BadLog.init(path, compress); }
}
