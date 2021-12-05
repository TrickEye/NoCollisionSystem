import java.sql.Time;
import java.util.*;

public class Main {
    static int total;
    static int upperBound, lowerBound;
    static Scanner scanner;

    static Ball[] GenerateBallSet(int n){
        Random random = new Random();
        random.setSeed(System.currentTimeMillis());
        Ball[] ballSet = new Ball[n];
        for (int i = 0; i < n; i++) {
            do{
                ballSet[i] = new Ball(random.nextDouble() * (960) * 2 - 960,
                                      random.nextDouble() * (540) * 2 - 540,
                                   random.nextDouble() * (upperBound - lowerBound) + lowerBound);
            }while(ballSet[i].CollideJudge(ballSet, i));
            ballSet[i].setVelocityX(random.nextDouble() * 80 - 40);
            ballSet[i].setVelocityY(random.nextDouble() * 80 - 40);
            //System.out.println("Create Ball! No."+i+": " + ballSet[i].toString());
        }
        return ballSet;
    }

    static void initializeParameters(){
        scanner = new Scanner(System.in);
        System.out.println("Input a integer n as the number of the balls. (1 < n <= 50)");
        System.out.println("If you input 0, load from default.");
        total = scanner.nextInt();
        while (total > 50 || total < 1) {
            if (total == 0) {
                System.out.println("Load from default");
                total = 10;
                upperBound = 300;
                lowerBound = 100;
                return;
            }
            else {
                System.out.println("Number too large or incorrect, input again!");
                total = scanner.nextInt();
            }
        }
        System.out.println("Input 2 integers as the upper and lower bound of the diameters. (10 <= lowerBound < upperBound <= 500)");
        upperBound = scanner.nextInt();
        lowerBound = scanner.nextInt();
        while (upperBound > 500 || upperBound <= lowerBound || lowerBound < 10) {
            System.out.println("Input incorrect or inappropriate, input again!");
            upperBound = scanner.nextInt();
            lowerBound = scanner.nextInt();
        }
    }

    public static void main(String[] args){
        initializeParameters();
        Ball[] ballSet = GenerateBallSet(total);
        GUI gui = new GUI(1920, 1080, "Title", ballSet, total);
        //ballSet[0].Collide(ballSet[1]);
        gui.init();
    }
}
