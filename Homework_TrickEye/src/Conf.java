import java.awt.*;

public class Conf {
    int totalBall;
    int upperBound, lowerBound;

    public Conf(int totalBall, int upperBound, int lowerBound) {
        this.totalBall = totalBall;
        this.upperBound = upperBound;
        this.lowerBound = lowerBound;
    }

    public boolean validation(){
        if (this.totalBall > 50 || this.totalBall < 2) return false;
        if (this.upperBound < this.lowerBound) return false;
        if (this.upperBound > 500) return false;
        return this.lowerBound >= 10;
    }

    public Conf(String str1, String str2, String str3){
        this.totalBall = StringOperations.toInt(str1);
        int num1 = StringOperations.toInt(str2);
        int num2 = StringOperations.toInt(str3);
        this.upperBound = Math.max(num1,num2);
        this.lowerBound = Math.min(num1,num2);
    }

    public int getTotalBall() {
        return totalBall;
    }

    public void setTotalBall(int totalBall) {
        this.totalBall = totalBall;
    }

    public int getUpperBound() {
        return upperBound;
    }

    public void setUpperBound(int upperBound) {
        this.upperBound = upperBound;
    }

    public int getLowerBound() {
        return lowerBound;
    }

    public void setLowerBound(int lowerBound) {
        this.lowerBound = lowerBound;
    }

    @Override
    public String toString(){
        return "Conf : <totalBall : " + this.totalBall +
                ">, <lowerBound : " + this.lowerBound +
                ">, <upperBound : " + this.upperBound + ">";
    }
}
