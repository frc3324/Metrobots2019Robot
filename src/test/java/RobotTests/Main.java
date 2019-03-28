package RobotTests;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Main")
class Main {
    PIDCommandTest zeroTest = new PIDCommandTest(0,0,0,10, new TestSubsystem());
    PIDCommandTest oneTest = new PIDCommandTest(1,1,1,10, new TestSubsystem());

    @Test
    public void initialTest() {
        assertEquals(5,5);
    }

    @Test
    public void testPIDCommandZero() {
        zeroTest.test();
    }

    @Test
    public void testPIDCommandOne() {
        oneTest.test();
    }
}
