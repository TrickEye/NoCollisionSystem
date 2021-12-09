import java.awt.*;

public class Ball {
    private double positionX, positionY;
    private double velocityX, velocityY;
    private double accelerationX, accelerationY;
    private double diameter;
    private final double[][] History = new double[Globals.HistoryMemorySize][2];
    private int HistoryNowAt = 0;
    private int NotCollidedCycles;
    static double CENTER_POS_X, CENTER_POS_Y;

    public Ball (double posX, double posY, double diameter){
        this.setPositionX(posX);
        this.setPositionY(posY);
        this.setDiameter(diameter);
        this.setVelocityX(0);
        this.setVelocityY(0);
        this.setAccelerationX(0);
        this.setAccelerationY(0);
        for (int i = 0; i < Globals.HistoryMemorySize; i++) {
            this.History[i][0] = this.getPositionX();
            this.History[i][1] = this.getPositionY();
        }
        this.HistoryNowAt = 0;
        this.NotCollidedCycles = 0;
    }

    public Ball() {
        this(0, 0, 0);
    }

    public double getPositionX() {
        return positionX;
    }

    public void setPositionX(double positionX) {
        this.positionX = positionX;
    }

    public double getPositionY() {
        return positionY;
    }

    public void setPositionY(double positionY) {
        this.positionY = positionY;
    }

    public double getVelocityX() {
        return velocityX;
    }

    public void setVelocityX(double velocityX) {
        this.velocityX = velocityX;
    }

    public double getVelocityY() {
        return velocityY;
    }

    public void setVelocityY(double velocityY) {
        this.velocityY = velocityY;
    }

    public double getAccelerationX() {
        return accelerationX;
    }

    public void setAccelerationX(double accelerationX) {
        this.accelerationX = accelerationX;
    }

    public double getAccelerationY() {
        return accelerationY;
    }

    public void setAccelerationY(double accelerationY) {
        this.accelerationY = accelerationY;
    }

    public double getDiameter() {
        return diameter;
    }

    public void setDiameter(double diameter) {
        this.diameter = diameter;
    }

    public double getVelocity(){
        return Math.sqrt(this.getVelocityX()*this.getVelocityX() + this.getVelocityY()*this.getVelocityY());
    }

    public double[][] getHistory() {
        return History;
    }

    public int getHistoryNowAt(){
        return HistoryNowAt;
    }

    @Override
    public String toString() {
        return  "ball:Diam = "+this.diameter+'\n'+
                "     Pos = {"+this.positionX+", "+this.positionY+"}\n"+
                "     Vel = {"+this.velocityX+", "+this.velocityY+"}\n"+
                "     Acc = {"+this.accelerationX+", "+this.accelerationY+"}\n";
    }

    public boolean CollideJudge(Ball b1){
        if (this == b1) return false;
        double deltaX = this.getPositionX() - b1.getPositionX();
        double deltaY = this.getPositionY() - b1.getPositionY();
        double sigmaR = this.getDiameter() / 2 + b1.getDiameter() / 2;
        return deltaX * deltaX + deltaY * deltaY < 0.99 * sigmaR * sigmaR;
    }

    public boolean CollideJudge(Ball[] ballSet, int n) {
        for (int i = 0; i < n; i++) {
            if (this.CollideJudge(ballSet[i])) {
                return true;
            }
        }
        return false;
    }

    public Color getColor(){
//        if (this.NotCollidedCycles > Globals.NotCollidedMemorySize) return Color.GREEN;
//        else {
//            Color red = Color.RED;
//            Color green = Color.GREEN;
//            return new Color((int)(((double) this.NotCollidedCycles / (double)Globals.NotCollidedMemorySize) * (green.getRGB() - red.getRGB()) )+ red.getRGB());
//        }
        int completeness = this.NotCollidedCycles * (Globals.colorArrayLen - 1) / Globals.NotCollidedMemorySize;
        if (completeness < 0) completeness = 0;
        if (completeness >= Globals.colorArrayLen) completeness = Globals.colorArrayLen - 1;
        return Globals.colorArray[completeness];
    }

    int cycle = 0;
    public void refresh(Ball[] ballSet, int n) {
        // record history

        if (this.cycle % 5 == 0) {
            this.History[this.HistoryNowAt][0] = this.positionX;
            this.History[this.HistoryNowAt][1] = this.positionY;
            HistoryNowAt++;
            if (HistoryNowAt >= Globals.HistoryMemorySize) HistoryNowAt -= Globals.HistoryMemorySize;
        }
        this.cycle++; if (cycle > 5) cycle-=5;


        // apply a -> v refresh.
        if (this.accelerationX != 0) {
            this.velocityX += accelerationX * MainScreen.PHYSICAL_TIMESCALE / 1000;
        }
        if (this.accelerationY != 0) {
            this.velocityY += accelerationY * MainScreen.PHYSICAL_TIMESCALE / 1000;
        }

        // apply v -> x refresh.
        if (this.velocityX != 0) {
            this.positionX += velocityX * MainScreen.PHYSICAL_TIMESCALE / 1000;
        }
        if (this.velocityY != 0) {
            this.positionY += velocityY * MainScreen.PHYSICAL_TIMESCALE / 1000;
        }

        // apply a refresh.
        double angle = Math.atan2(this.getPositionY(), this.getPositionX());
        //angle += 0.25 * Math.atan(1);
        double accX = -Math.cos(angle) * Math.sqrt((this.positionX) * (this.positionX) + this.positionY * this.positionY) * Globals.Gravity_Ratio;
        double accY = -Math.sin(angle) * Math.sqrt((this.positionX) * (this.positionX) + this.positionY * this.positionY) * Globals.Gravity_Ratio;
        this.setAccelerationX(accX);
        this.setAccelerationY(accY);
//        double distanceSquared = (this.positionX - CENTER_POS_X) * (this.positionX - CENTER_POS_X) + (this.positionY - CENTER_POS_Y) * (this.positionY - CENTER_POS_Y);
//        double dist = Math.sqrt(distanceSquared);
//        double mass = this.getMass();
//        double acc = - MASS_CENTER / distanceSquared / mass - dist * MASS_CENTER / mass / 10000000;
//        if (dist < 100) acc = 0;
//        this.setAccelerationX(acc * (this.positionX - CENTER_POS_X) / dist);
//        this.setAccelerationY(acc * (this.positionY - CENTER_POS_Y) / dist);

        // apply collision
        for (int i = 0; i < n; i++) {
            if (this.CollideJudge(ballSet[i])) {
                if (this.NotCollidedCycles > Globals.NotCollidedMemorySize) Globals.NotCollidedMemorySize = (2 * this.NotCollidedCycles);
                if (ballSet[i].NotCollidedCycles > Globals.NotCollidedMemorySize) Globals.NotCollidedMemorySize = (2 * ballSet[i].NotCollidedCycles);
                this.NotCollidedCycles = 0;
                ballSet[i].NotCollidedCycles = 0;
                double[] deltaPos = {this.getPositionX() - ballSet[i].getPositionX(), this.getPositionY() - ballSet[i].getPositionY()};
                double distance = Math.sqrt(deltaPos[0] * deltaPos[0] + deltaPos[1] * deltaPos[1]);
                double minDistance = (this.getDiameter() + ballSet[i].getDiameter()) / 2;

                double[] collideAxe = {deltaPos[0] / distance, deltaPos[1] / distance};
                this.setPositionX(this.getPositionX() + 0.5 * (minDistance - distance) * collideAxe[0]);
                this.setPositionY(this.getPositionY() + 0.5 * (minDistance - distance) * collideAxe[1]);
                ballSet[i].setPositionX(ballSet[i].getPositionX() - 0.5 * (minDistance - distance) * collideAxe[0]);
                ballSet[i].setPositionY(ballSet[i].getPositionY() - 0.5 * (minDistance - distance) * collideAxe[1]);
            }
            else {
                this.NotCollidedCycles++;
            }
        }
    }

    public boolean isComplete(){
        return this.NotCollidedCycles > (int)(1.2 * Globals.NotCollidedMemorySize);
    }

}
