import javax.swing.*;
import java.awt.*;
import java.awt.dnd.DropTarget;
import java.awt.event.*;
import java.util.Scanner;

public class GUI {
    static int TABLE_WIDTH, TABLE_HEIGHT;
    static int PHYSICAL_TIMESCALE = 2;
    static int REFRESH_TIMESCALE = 33;
    static double OBSERVE_SCALE = 1.0;
    static int STATE_STOPPED = 1;
    static int STATE_NORMAL= 0;
    static int CURRENT_STATE = STATE_NORMAL;
    static Frame frame;
    static int totalBall;
    static Ball[] ballSet;
    MyCanvas myCanvas;
    static Button btn1, btn2, btn3, btn4;

    public GUI(int width, int height, String Title, Ball[] bSet, int n){
        TABLE_WIDTH = width;
        TABLE_HEIGHT = height;
        ballSet = bSet;
        totalBall = n;
        frame = new Frame(Title);
        frame.setBackground(Color.BLACK);

        myCanvas = new MyCanvas();
        myCanvas.setBalls(bSet, totalBall);

    }

    public static class MyCanvas extends Canvas{
        int totalBall;
        static Ball[] ballSet;

        void setBalls(Ball[] bSet, int n){
            totalBall = n;
            ballSet = bSet;
        }

        @Override
        public void paint(Graphics g){
            g.setColor(Color.RED);
            g.drawLine(0, TABLE_HEIGHT/2, TABLE_WIDTH, TABLE_HEIGHT/2);
            g.drawLine(TABLE_WIDTH/2, TABLE_HEIGHT, TABLE_WIDTH/2, 0);
            g.setColor(Color.GREEN);
            g.fillOval(TABLE_WIDTH/2 + (int)Ball.CENTER_POS_X - 5, TABLE_HEIGHT/2 + (int)Ball.CENTER_POS_Y - 5, 10, 10);
            g.setColor(Color.WHITE);
            for (int i = 0; i < totalBall; i++) {
                int posX = (int) (ballSet[i].getPositionX()/OBSERVE_SCALE);
                int posY = (int) (ballSet[i].getPositionY()/OBSERVE_SCALE);
                int diam = (int) (ballSet[i].getDiameter()/OBSERVE_SCALE);
                g.drawOval(TABLE_WIDTH/2 + posX - diam/2, TABLE_HEIGHT/2 + posY - diam/2, diam, diam);
            }
        }
    }

    void init(){
        KeyListener k1 = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                int keyCode = e.getKeyCode();
                if (keyCode == KeyEvent.VK_RIGHT) {
                    for (int i = 0; i < totalBall; i++) {
                        ballSet[i].setPositionX(ballSet[i].getPositionX()+5);
                    }
                }
                if (keyCode == KeyEvent.VK_ENTER) {
                    System.exit(0);
                }
                if (keyCode == KeyEvent.VK_UP) {
                    OBSERVE_SCALE *= 1.1;
                }
                if (keyCode == KeyEvent.VK_DOWN) {
                    OBSERVE_SCALE /= 1.1;
                }
            }
        };
        frame.addKeyListener(k1);
        myCanvas.addKeyListener(k1);

        ActionListener ballListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //System.out.println("Refresh!");
                if (CURRENT_STATE == STATE_NORMAL) {
                    for (int i = 0; i < totalBall; i++) {
                        ballSet[i].refresh(ballSet, totalBall);
                    }
                    Ball.refreshCenter();
                    //myCanvas.repaint();
                }
            }
        };
        ActionListener refreshListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                myCanvas.repaint();
            }
        };
        Timer physical_timer = new Timer(PHYSICAL_TIMESCALE, ballListener);
        physical_timer.start();
        Timer refresh_timer = new Timer(REFRESH_TIMESCALE, refreshListener);
        refresh_timer.start();

        ActionListener btn1Listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //System.out.println("End Program.");
                System.exit(0);
                //Main.startScreen.BackToLife();
            }
        };
        ActionListener btn2Listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Stop.");
                if (CURRENT_STATE == STATE_NORMAL) btn2.setLabel("Resume");
                if (CURRENT_STATE == STATE_STOPPED) btn2.setLabel("Stop");
                CURRENT_STATE = 1 - CURRENT_STATE;
            }
        };
        ActionListener btn3Listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                OBSERVE_SCALE *= 1.1;
            }
        };
        ActionListener btn4Listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                OBSERVE_SCALE /= 1.1;
            }
        };
        btn1 = new Button("Close");
        btn2 = new Button("Stop");
        btn3 = new Button("Zoom out");
        btn4 = new Button("Zoom in");
        btn1.setPreferredSize(new Dimension(200, 100));
        btn1.setFont(new Font("Centaur", Font.PLAIN,40));
        btn2.setPreferredSize(new Dimension(200, 100));
        btn2.setFont(new Font("Centaur", Font.PLAIN,40));
        btn3.setPreferredSize(new Dimension(100, 100));
        btn3.setFont(new Font("Centaur", Font.PLAIN,20));
        btn4.setPreferredSize(new Dimension(100, 100));
        btn4.setFont(new Font("Centaur", Font.PLAIN,20));
        btn1.addActionListener(btn1Listener);
        btn2.addActionListener(btn2Listener);
        btn3.addActionListener(btn3Listener);
        btn4.addActionListener(btn4Listener);

        Panel panel = new Panel(new FlowLayout());
        panel.setPreferredSize(new Dimension(210, TABLE_HEIGHT));
        panel.add(btn1);
        panel.add(btn2);
        panel.add(btn3);
        panel.add(btn4);

        frame.setLayout(new BorderLayout());
        myCanvas.setPreferredSize(new Dimension(TABLE_WIDTH, TABLE_HEIGHT));
        frame.add(panel, BorderLayout.EAST);
        frame.add(myCanvas, BorderLayout.WEST);


        TextArea textField = new TextArea("TextField", 2, 4, Scrollbar.VERTICAL);
        textField.setPreferredSize(new Dimension(1920, 200));
        textField.setVisible(true);
        textField.setBackground(Color.BLACK);
        textField.setForeground(Color.WHITE);
        textField.setFont(new Font("Centaur", Font.PLAIN, 30));
        frame.add(textField, BorderLayout.SOUTH);

        frame.pack();
        frame.setVisible(true);
        frame.setAlwaysOnTop(true);

//        Scanner scanner = new Scanner();
//        MenuBar menuBar = new MenuBar();
//        menuBar.add(new Menu("Haha"));
//        frame.setMenuBar(menuBar);
    }

}
