public class Ball {
    private double positionX, positionY;
    private double velocityX, velocityY;
    private double accelerationX, accelerationY;
    private double diameter;
    static double MASS_QUOTIENT = 0.0001;
    static double MASS_CENTER = 10000000000.0;
    static double ROTATE_CENTER_ANGLE = 0;
    static double ROTATE_CENTER_W = 0;
    static double ROTATE_LENGTH = 0;
    static double CENTER_POS_X, CENTER_POS_Y;


    public Ball (double posX, double posY, double diameter){
        this.setPositionX(posX);
        this.setPositionY(posY);
        this.setDiameter(diameter);
        this.setVelocityX(0);
        this.setVelocityY(0);
        this.setAccelerationX(0);
        this.setAccelerationY(0);
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
        if (deltaX * deltaX + deltaY * deltaY < sigmaR * sigmaR) {
            //System.out.println("Collide!:\n"+this.toString()+b1.toString());
            return true;
        }
        return false;
    }

    public boolean CollideJudge(Ball[] ballSet, int n) {
        for (int i = 0; i < n; i++) {
            if (this.CollideJudge(ballSet[i])) {
                return true;
            }
        }
        return false;
    }

    public double getMass(){
        return 1;//MASS_QUOTIENT * this.diameter * this.diameter * this.diameter;
    }

    public static void refreshCenter(){
        ROTATE_CENTER_ANGLE += ROTATE_CENTER_W * GUI.PHYSICAL_TIMESCALE / 1000;
        CENTER_POS_X = Math.sin(ROTATE_CENTER_ANGLE) * ROTATE_LENGTH;
        CENTER_POS_Y = Math.cos(ROTATE_CENTER_ANGLE) * ROTATE_LENGTH;
    }

    public void refresh(Ball[] ballSet, int n){
        // apply a -> v refresh.
        if (this.accelerationX != 0){
            this.velocityX += accelerationX * GUI.PHYSICAL_TIMESCALE / 1000;
        }
        if (this.accelerationY != 0) {
            this.velocityY += accelerationY * GUI.PHYSICAL_TIMESCALE / 1000;
        }

        // apply v -> x refresh.
        if (this.velocityX != 0) {
            this.positionX += velocityX * GUI.PHYSICAL_TIMESCALE / 1000;
        }
        if (this.velocityY != 0) {
            this.positionY += velocityY * GUI.PHYSICAL_TIMESCALE / 1000;
        }

        // apply a refresh.
        double angle = Math.atan2(this.getPositionY(), this.getPositionX());
        //angle += 0.25 * Math.atan(1);
        double accX = -Math.cos(angle) * Math.sqrt((this.positionX) * (this.positionX) + this.positionY * this.positionY);
        double accY = -Math.sin(angle) * Math.sqrt((this.positionX) * (this.positionX) + this.positionY * this.positionY);
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
            if (this.CollideJudge(ballSet[i])){
//                System.out.println(this.toString());
//                System.out.println(ballSet[i].toString());
                double[] deltaPos = {this.getPositionX() - ballSet[i].getPositionX(), this.getPositionY() - ballSet[i].getPositionY()};
                double distance = Math.sqrt(deltaPos[0] * deltaPos[0] + deltaPos[1] * deltaPos[1]);
                double minDistance = this.getDiameter() + ballSet[i].getDiameter();

                double[] collideAxe = {deltaPos[0] / distance, deltaPos[1] / distance};
                this.setPositionX(this.getPositionX() + 0.05 * (minDistance - distance) * collideAxe[0]);
                this.setPositionY(this.getPositionY() + 0.05 * (minDistance - distance) * collideAxe[1]);
                ballSet[i].setPositionX(ballSet[i].getPositionX() - 0.05 * (minDistance - distance) * collideAxe[0]);
                ballSet[i].setPositionY(ballSet[i].getPositionY() - 0.05 * (minDistance - distance) * collideAxe[1]);

//                System.out.println(this.toString());
//                System.out.println(ballSet[i].toString());
//                GUI.CURRENT_STATE = GUI.STATE_STOPPED;
//                double vx1 = this.getVelocityX();
//                double vx2 = ballSet[i].getVelocityX();
//                double vy1 = this.getVelocityY();
//                double vy2 = ballSet[i].getVelocityY();
//                double x1 = this.getPositionX();
//                double x2 = ballSet[i].getPositionX();
//                double y1 = this.getPositionY();
//                double y2 = ballSet[i].getPositionY();
//                double m1 = this.getMass();
//                double m2 = ballSet[i].getMass();
//                // 得到正交速度矢量、位移矢量、质量（替代值）
//                double offsetAngle1 = Math.atan2(vy1, vx1);
//                double offsetAngle2 = Math.atan2(vy2, vx2);
//                double len1 = Math.sqrt(vx1 * vx1 + vy1 * vy1);
//                double len2 = Math.sqrt(vx2 * vx2 + vy2 * vy2);
//                // 得到极坐标下偏移角度和长度
//                double offsetAngleP = Math.atan2(y2 - y1, x2 - x1);
//                double distSquared = (x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1);
//                double shadowLen1 = (vx1 * (x2 - x1) + vy1 * (y2 - y1)) / (Math.sqrt(distSquared));
//                double shadowLen2 = (vx2 * (x2 - x1) + vy2 * (y2 - y1)) / (Math.sqrt(distSquared));
//                double commonShadowLen = ((shadowLen1 * m1) + (shadowLen2 * m2)) / (m1 + m2);
//                // 碰撞逻辑：通过位矢差确定法向，法向上完全非弹性碰撞。
//                double deltaShadow1 = commonShadowLen - shadowLen1;
//                double deltaShadow2 = commonShadowLen - shadowLen2;
//                double newVx1 = vx1 + deltaShadow1 * Math.cos(offsetAngleP) - (this.getDiameter() + ballSet[i].getDiameter() - Math.sqrt(distSquared)) * Math.cos(offsetAngleP) / 3;
//                double newVy1 = vy1 + deltaShadow1 * Math.sin(offsetAngleP) - (this.getDiameter() + ballSet[i].getDiameter() - Math.sqrt(distSquared)) * Math.sin(offsetAngleP) / 3;
//                double newVx2 = vx2 + deltaShadow2 * Math.cos(offsetAngleP);
//                double newVy2 = vy2 + deltaShadow2 * Math.sin(offsetAngleP);
//                // 产生新的速度值
//                this.setVelocityX(newVx1);
//                this.setVelocityY(newVy1);

//                System.out.println("ball1:<"+vx1+","+vy1+">");
//                System.out.println("ball2:<"+vx2+","+vy2+">");
//                System.out.println("--->");
//                System.out.println("ball1:<"+newVx1+","+newVy1+">");
//                System.out.println("ball2:<"+newVx2+","+newVy2+">");
//                System.out.printf("shadowLen1 : %f\n", shadowLen1);
//                System.out.printf("shadowLen2 : %f\n", shadowLen2);
//                System.out.printf("commonShadowLen : %f\n", commonShadowLen);
//                System.out.printf("offsetAngleP : %f\n", offsetAngleP);
//                System.out.printf("deltaShadow1 : %f\n", deltaShadow1);
//                System.out.printf("deltaShadow2 : %f\n", deltaShadow2);
//
//                System.out.println("\n\n");
//                GUI.CURRENT_STATE = 1 - GUI.CURRENT_STATE;

            }
        }
    }
    public static void main(String[] args){
        System.out.printf("%f", Math.atan2(-4, 1));
    }
    /*
    * public static void main(String[] args){
    *     Ball ball1 = new Ball(100,200,50);
    *
    *     System.out.println(ball1.toString());
    * }
    */
}
