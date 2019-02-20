package frc.team3324.robot.wrappers;

import badlog.lib.BadLog;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

import java.util.function.DoubleSupplier;
import java.util.function.Supplier;

/**
 * Class to encapsulate (wrap) a logger class.
 */
public class Logger {

    private static BadLog logger;

    /**
     * Creates an instance of the Logger class. Initialize logger.
     *
     * @param path, path name
     * @param compress, whether to compress
     */
    public Logger(String path, Boolean compress) {
        logger.init(path, compress);
    }

    class InvalidArrayException extends Exception {
        InvalidArrayException(String s) {
            super(s);
        }
    }

    public void createShuffleboardTab(String... tabNames) {

    }

    /**
     * Check whether a two dimensional array is rectangular.
     *
     * @param array
     * @throws InvalidArrayException
     */
    private void checkArrayRectanglerness(Object[][] array) throws InvalidArrayException {
        try {
            // Go through array rows
            for (int i = 0; i < array.length - 1; i++) {
                // Check if the two rows being compares are the same length
                if (!(array[i].length == array[i + 1].length)) {
                    // Unchecked (not checked at compile time) exception as wrapper not expected to change much
                    throw new InvalidArrayException("Array must be rectangular. See row " + i + " and " + (i + 1) + ".");
                }
            }
        }
        catch (InvalidArrayException ex) {
            System.out.println("Invalid array.");
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Check array for same type in each array.
     *
     * @param array, array to be checked
     * @throws InvalidArrayException
     */
    private void checkArray(Object[][] array) throws InvalidArrayException {
        boolean sameType;

        // Check the array is rectangular
        checkArrayRectanglerness(array);

        // Go through array rows
        for (int i = 0; i < array.length - 1; i++) {
            // Go through positions of the two array rows being compared
            for (int j = 0; j < array[0].length; j++) {
                // Compare type of each array position
                sameType = array[i][j].getClass() == array[i + 1][j].getClass();
                // If they are not the same type, throw an exception
                if (!sameType) {
                    //throw new InvalidArrayException("Array types do not match at row: " + i + " and " + i + 1);
                }
                // If any of the values are null, throw an exception
                else if (array[i][j] == null) {
                    //throw new InvalidArrayException("Array type is null at row " + i + ", column " + j);
                } else if (array[i + 1][j] == null) {
                    //throw new InvalidArrayException("Array type is null at row " + (i + 1) + ", column " + j);
                }
            }
        }
    }

    public void createNetworkTableEntries(String tabName, Object[][] networkTablesInfo) throws InvalidArrayException {
        // Check that the array is rectangular and contains the same type for each column
        checkArray(networkTablesInfo);
        // Create a Shuffleboard tab with the specified tab name
        ShuffleboardTab tab = Shuffleboard.getTab(tabName);
        //
        for (int i = 0; i < networkTablesInfo.length; i++) {
            for (int j = 0; j < networkTablesInfo[i].length; j++) {
                tab.add(networkTablesInfo[i][j].toString(), networkTablesInfo[i][j + 1]).withPosition((int)networkTablesInfo[i][j + 2], (int)networkTablesInfo[i][i]);
            }
        }
    }

    public void log() {
        logger.log();
    }

    public void updateTopics() {
        logger.updateTopics();
    }

    public void finishInitialization() {
        logger.finishInitialization();
    }

    /**
     * Create logger topics using array of strings for names and units.
     *
     * @param loggerStrings [number of topics being created][names, units, function to be called]
     */
    public void createTopics(Object[][] loggerStrings, String... attributes) throws InvalidArrayException {
        checkArrayRectanglerness(loggerStrings);

        for (int i = 0; i < loggerStrings.length; i++) {
            if (loggerStrings[i][2] instanceof Boolean) {
                if ((boolean)loggerStrings[i][2]) {
                    loggerStrings[i][2] = 1.0;
                }
                else {
                    loggerStrings[i][2] = 0.0;
                }
            }
            // TODO: Type check loggerStrings
            logger.createTopic(loggerStrings[i][0].toString(), loggerStrings[i][1].toString(), (Supplier<Double>)loggerStrings[i][2], attributes);
        }
    }

    public void createTopic(String name, String unit, Supplier<Double> supplier, String... attributes) {
        logger.createTopic(name, unit, supplier, attributes);
    }
}
