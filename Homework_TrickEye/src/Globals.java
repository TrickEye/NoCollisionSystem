import java.awt.*;

public class Globals {
    static String Title = "No-Collision System Illustrator";
    static String[] description = {
            "Author: TrickEye",
            "This is a java program aimed at generating a no-collision-system from ",
            "  a randomly-generated status of distribution of the balls.",
            "This is still being developed. Any content of this software does not",
            "  represent final work."
    };
    static String[] guide = {
            "Input an integer n as the number of the balls. (1 < n <= 50)",
            "Input an integer as the upperbound of diameters.",
            "Input lowerBound. (10 < lowerBound < upperBound <= 500)"
    };
    static String[] defaultInput = {
            "10",
            "300",
            "100"
    };

    static int HistoryMemorySize = 100;
    static int NotCollidedMemorySize = 40000;
    static double Gravity_Ratio = 10.0;
    static Color[] colorArray = {
            new Color(255, 0, 0),
            new Color(239, 16, 0),
            new Color(223, 32, 0),
            new Color(207, 48, 0),
            new Color(191, 64, 0),

            new Color(175, 80, 0),
            new Color(159, 96, 0),
            new Color(143, 112, 0),
            new Color(127, 128, 0),
            new Color(111, 144, 0),

            new Color(95, 160, 0),
            new Color(79, 176, 0),
            new Color(63, 192, 0),
            new Color(47, 208, 0),
            new Color(31, 224, 0),

            new Color(15, 240, 0),
            new Color(0, 255, 0),
    };
    static int colorArrayLen = 17;

    static int SpeedFactor = 1;
}
