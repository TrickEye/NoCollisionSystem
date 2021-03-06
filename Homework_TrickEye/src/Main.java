import java.awt.*;
import java.util.*;

public class Main {
    //static int total;
    //static int upperBound, lowerBound;
    static Scanner scanner;
    static StartScreen startScreen;

    static Ball[] GenerateBallSet(int n, int upperBound, int lowerBound){
        Random random = new Random();
        random.setSeed(System.currentTimeMillis());
        Ball[] ballSet = new Ball[n];
        for (int i = 0; i < n; i++) {
            do{
                ballSet[i] = new Ball(random.nextDouble() * (upperBound * n) / 4 - upperBound * n / 8.0,
                                      random.nextDouble() * (upperBound * n) / 4 - upperBound * n / 8.0,
                                   random.nextDouble() * (upperBound - lowerBound) + lowerBound);
            }while(ballSet[i].CollideJudge(ballSet, i));
            ballSet[i].setVelocityX(random.nextDouble() * 80 - 40);
            ballSet[i].setVelocityY(random.nextDouble() * 80 - 40);
            //System.out.println("Create Ball! No."+i+": " + ballSet[i].toString());
        }
        return ballSet;
    }

    static Ball[] GenerateBallSet(Conf conf){
        int totalBall = conf.getTotalBall();
        int lowerBound = conf.getLowerBound();
        int upperBound = conf.getUpperBound();
        return GenerateBallSet(totalBall, upperBound, lowerBound);
    }
//
//    static void initializeParameters(){
//        scanner = new Scanner(System.in);
//        System.out.println("Input a integer n as the number of the balls. (1 < n <= 50)");
//        System.out.println("If you input 0, load from default.");
//        total = scanner.nextInt();
//        while (total > 50 || total < 1) {
//            if (total == 0) {
//                System.out.println("Load from default");
//                total = 10;
//                upperBound = 300;
//                lowerBound = 100;
//                return;
//            }
//            else {
//                System.out.println("Number too large or incorrect, input again!");
//                total = scanner.nextInt();
//            }
//        }
//        System.out.println("Input 2 integers as the upper and lower bound of the diameters. (10 <= lowerBound < upperBound <= 500)");
//        upperBound = scanner.nextInt();
//        lowerBound = scanner.nextInt();
//        while (upperBound > 500 || upperBound <= lowerBound || lowerBound < 10) {
//            System.out.println("Input incorrect or inappropriate, input again!");
//            upperBound = scanner.nextInt();
//            lowerBound = scanner.nextInt();
//        }
//    }

    public static void main(String[] args){
        startScreen = new StartScreen();
        //initializeParameters();
        //Ball[] ballSet = GenerateBallSet(total);
        //GUI gui = new GUI(1920, 1080, "Title", ballSet, total);
        //ballSet[0].Collide(ballSet[1]);
        //gui.init();
    }

    public static void Launch(Conf conf){
        Dimension ScreenDimension = Toolkit.getDefaultToolkit().getScreenSize();
        ScreenDimension.setSize(ScreenDimension.getWidth() - 210, ScreenDimension.getHeight() - 100);
        Ball[] ballSet = GenerateBallSet(conf);
        MainScreen gui = new MainScreen((int)ScreenDimension.getWidth(), (int)ScreenDimension.getHeight(), ballSet, conf.getTotalBall());
        gui.init();
    }

    public static boolean LaunchFromFile(Ball[] ballSet){
        if (ballSet != null){
            if (MainScreen.frame != null) {
                MainScreen.frame.setVisible(false);
            }
            Dimension ScreenDimension = Toolkit.getDefaultToolkit().getScreenSize();
            ScreenDimension.setSize(ScreenDimension.getWidth() - 210, ScreenDimension.getHeight() - 100);
            MainScreen gui = new MainScreen((int) ScreenDimension.getWidth(), (int) ScreenDimension.getHeight(), ballSet, ballSet.length);
            gui.init();
            return true;
        }
        else return false;
    }
}
