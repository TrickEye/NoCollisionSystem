import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class MainScreen {
    static int TABLE_WIDTH, TABLE_HEIGHT;
    static int PHYSICAL_TIMESCALE = 2;
    static int REFRESH_TIMESCALE = 40;
    static double OBSERVE_SCALE = 1.0;
    static int STATE_STOPPED = 1;
    static int STATE_NORMAL= 0;
    static int CURRENT_STATE = STATE_NORMAL;
    static boolean SHOW_TAIL = false;
    static Frame frame;
    static TextArea textField;
    static Panel panel;
    static MenuBar menuBar;
    static int totalBall;
    static Ball[] ballSet;
    static MyCanvas myCanvas;
    static Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9;
    static Timer ConsoleTimer;
    static int Hold = 0;

    public MainScreen(int width, int height, Ball[] bSet, int n){
        TABLE_WIDTH = width;
        TABLE_HEIGHT = height;
        OBSERVE_SCALE = 1.0;
        SHOW_TAIL = false;
        Hold = 0;

        ballSet = bSet;
        totalBall = n;

    }

    public static void setTextField(String text, String op){
        if (op.equals("APPEND")){
            textField.append("\n" + text);
            textField.setEditable(false);
            MainScreen.Hold = 5;
        }
        if (op.equals("SET")){
            textField.setText(text);
            textField.setEditable(false);
            MainScreen.Hold = 15;
        }
    }

    public static class MyCanvas extends Canvas{
        static int totalBall;
        static Ball[] ballSet;

        void setBalls(Ball[] bSet, int n){
            totalBall = n;
            ballSet = bSet;
        }

        @Override
        public void paint(Graphics g){
            int width = this.getWidth();
            int height = this.getHeight();
            /* Draw Axis
             */
            g.setColor(Color.RED);
            g.drawLine(0, height/2, width, height/2);
            g.drawLine(width/2, height, width/2, 0);
            /* Draw Point O
             */
            // g.setColor(Color.GREEN);
            // g.fillOval(TABLE_WIDTH/2 + (int)Ball.CENTER_POS_X - 5, TABLE_HEIGHT/2 + (int)Ball.CENTER_POS_Y - 5, 10, 10);
            /* Draw History
             */
            if (SHOW_TAIL){
                g.setColor(Color.gray);
                for (int i = 0; i < totalBall; i++) {
                    double[][] history = ballSet[i].getHistory();
                    int historyNowAt = ballSet[i].getHistoryNowAt();
                    for (int j = 0; j < Globals.HistoryMemorySize; j++) {
                        int paintNowAt = (historyNowAt + j) % Globals.HistoryMemorySize;
                        int posX = (int) (history[paintNowAt][0] / OBSERVE_SCALE);
                        int posY = (int) (history[paintNowAt][1] / OBSERVE_SCALE);
                        int diam = (int) (3 / OBSERVE_SCALE);
                        g.drawOval(width / 2 + posX - diam / 2, height / 2 + posY - diam / 2, diam, diam);
                    }
                }
            }
            /* Draw Circles
             */
//            g.setColor(Color.WHITE);
            boolean zoomOut = false;
            for (int i = 0; i < totalBall; i++) {
                g.setColor(ballSet[i].getColor());
                int posX = (int) (ballSet[i].getPositionX()/OBSERVE_SCALE);
                int posY = (int) (ballSet[i].getPositionY()/OBSERVE_SCALE);
                int diam = (int) (ballSet[i].getDiameter()/OBSERVE_SCALE);
                if (ballSet[i].isComplete())
                    g.fillOval(width/2 + posX - diam/2, height/2 + posY - diam/2, diam, diam);
                else
                    g.drawOval(width/2 + posX - diam/2, height/2 + posY - diam/2, diam, diam);
                if (Math.abs(posX) > (int)(0.4 * width)) zoomOut = true;
                if (Math.abs(posY) > (int)(0.4 * height)) zoomOut = true;
            }
            if (zoomOut && Globals.ignoreZoom == 0 && !Globals.ignoreAdaptiveZoom) {
                MainScreen.setTextField("Adaptively zoom out; Observe Scale Now At: " + String.format("%.2f", OBSERVE_SCALE), "SET");
                OBSERVE_SCALE *= 1.1;
            }
        }
    }

    void canvasSetup(){
        myCanvas = new MyCanvas();
        myCanvas.setPreferredSize(new Dimension(TABLE_WIDTH, TABLE_HEIGHT));
        myCanvas.setBalls(ballSet, totalBall);
        KeyListener k1 = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                int keyCode = e.getKeyCode();
                if (keyCode == KeyEvent.VK_SPACE) {
                    MainScreen.setTextField((CURRENT_STATE == STATE_NORMAL) ? "Stop." : "Resume.", "SET");
                    if (CURRENT_STATE == STATE_NORMAL) btn2.setLabel("Resume");
                    if (CURRENT_STATE == STATE_STOPPED) btn2.setLabel("Stop");
                    CURRENT_STATE = 1 - CURRENT_STATE;
                }
                if (keyCode == KeyEvent.VK_UP) {
                    OBSERVE_SCALE *= 1.1;
                }
                if (keyCode == KeyEvent.VK_DOWN) {
                    OBSERVE_SCALE /= 1.1;
                    Globals.ignoreZoom = 100;
                }
                if (keyCode == KeyEvent.VK_RIGHT) {
                    if (Globals.SlowDownFactor == 1) {
                        Globals.SpeedFactor = Math.min(Globals.SpeedFactor + 1, 100);
                        MainScreen.setTextField("SpeedFactor : " + Globals.SpeedFactor, "SET");
                        if (Globals.SpeedFactor >= 10 && SHOW_TAIL) {
                            MainScreen.setTextField("We don't advice showing tail at this speed, tail hided.", "APPEND");
                            SHOW_TAIL = false;
                            MainScreen.btn7.setLabel("Show Tail");
                        }
                    }
                    else {
                        Globals.SlowDownFactor--;
                        if (Globals.SlowDownFactor >= 2) MainScreen.setTextField("SpeedFactor : 1 / " + Globals.SlowDownFactor, "SET");
                        else MainScreen.setTextField("SpeedFactor : 1", "SET");
                    }
                }
                if (keyCode == KeyEvent.VK_LEFT) {
                    if (Globals.SpeedFactor > 1) {
                        Globals.SpeedFactor = Math.max(Globals.SpeedFactor - 1, 1);
                        MainScreen.setTextField("SpeedFactor : " + Globals.SpeedFactor, "SET");
                    }
                    else {
                        Globals.SlowDownFactor = Math.min(10, Globals.SlowDownFactor + 1);
                        MainScreen.setTextField("SpeedFactor : 1 / " + Globals.SlowDownFactor, "SET");
                    }
                }
                if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_S){
                    try {
                        FileIO.SaveCurrentScene(ballSet);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
                if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_L){
                    try {
                        if (Main.LaunchFromFile(FileIO.InputInit()))
                            MainScreen.setTextField("Welcome Back!", "SET");
                    } catch (IOException ex) {
                        Notice wb = new Notice(Notice.WRONG_FILE);
                        //ex.printStackTrace();
                    }
                }
                if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_I) {
                    Notice n = new Notice(Notice.SOFTWARE_INFO);
                }
                if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_A) {
                    Notice n = new Notice(Notice.AUTHOR_INFO);
                }
                if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_B) {
                    MainScreen.frame.setVisible(false);
                    Main.startScreen.BackToLife();
                }
                if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_T) {
                    MainScreen.SHOW_TAIL = !MainScreen.SHOW_TAIL;
                    if (MainScreen.SHOW_TAIL) MainScreen.btn7.setLabel("Hide Tail");
                    else MainScreen.btn7.setLabel("Show Tail");
                }
                if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_Z) {
                    if (Globals.ignoreAdaptiveZoom == false) {
                        Globals.ignoreAdaptiveZoom = true;
                        MainScreen.setTextField("Adaptive Zoom Disabled.", "SET");
                    }
                    else if (Globals.ignoreAdaptiveZoom == true) {
                        Globals.ignoreAdaptiveZoom = false;
                        Globals.ignoreZoom = 0;
                        MainScreen.setTextField("Adaptive Zoom Enabled.", "SET");
                    }
                }
            }
        };
//        frame.addKeyListener(k1);
        myCanvas.addKeyListener(k1);
        MouseWheelListener m1 = new MouseAdapter() {
            /**
             * {@inheritDoc}
             *
             * @param e
             * @since 1.6
             */
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                super.mouseWheelMoved(e);
                System.out.println(e.getWheelRotation());
                int e1 = e.getWheelRotation();
                if (e1 > 0.5) {
                    OBSERVE_SCALE *= 1.1;
                }
                if (e1 < -0.5) {
                    OBSERVE_SCALE /= 1.1;
                    Globals.ignoreZoom = 100;
                }
            }
        };
        myCanvas.addMouseWheelListener(m1);
    }

    void frameSetup(){
        frame = new Frame(Globals.Title);
        frame.setUndecorated(true);
        frame.setBackground(Color.BLACK);
        frame.addWindowListener(new WindowAdapter() {
            /**
             * Invoked when a window is in the process of being closed.
             * The close operation can be overridden at this point.
             *
             * @param e
             */
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                frame.setVisible(false);
                Main.startScreen.BackToLife();
            }
        });

    }

    void timerSetup(){
        ActionListener ballListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //System.out.println("Refresh!");
                for (int i = 0; i < Globals.SpeedFactor; i++) {
                    if (CURRENT_STATE == STATE_NORMAL) {
                        for (int j = 0; j < totalBall; j++) {
                            ballSet[j].refresh(ballSet, totalBall);
                        }
                        //myCanvas.repaint();
                    }
                }
            }
        };
        ActionListener refreshListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Globals.ignoreZoom = Math.max(Globals.ignoreZoom - 1, 0);
                myCanvas.repaint();
            }
        };
        Timer physical_timer = new Timer(PHYSICAL_TIMESCALE, ballListener);
        physical_timer.start();
        Timer refresh_timer = new Timer(REFRESH_TIMESCALE, refreshListener);
        refresh_timer.start();
        ConsoleTimer = new Timer(200, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainScreen.Hold = Math.max(MainScreen.Hold - 1 , 0);
                if (MainScreen.Hold == 0) {
                    double completionPercentage = 100 * Ball.getSafetyPercent(MainScreen.ballSet);
                    textField.setText("Estimated percentage of completion : " + String.format("%.2f%%", completionPercentage));
                    char[] completion = new char[100];
                    for (int i = 0; i < 100; i++) {
                        if (i + 1 <= completionPercentage + 0.0001) {
                            completion[i] ='>';
                        }
                        else {
                            completion[i] = '-';
                        }
                    }
                    textField.append("\n" + new String(completion, 0, 100));
                    if (completionPercentage >= 100 - 0.0001) textField.append(" Complete!");
                    textField.setEditable(false);
                }
            }
        });
        ConsoleTimer.start();
    }

    void buttonSetup(){
        ActionListener btn1Listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                Main.startScreen.BackToLife();
            }
        };
        ActionListener btn2Listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainScreen.setTextField((CURRENT_STATE == STATE_NORMAL) ? "Stop." : "Resume.", "SET");
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
                Globals.ignoreZoom = 100;
            }
        };
        ActionListener btn5Listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Globals.SlowDownFactor == 1) {
                    Globals.SpeedFactor = Math.min(Globals.SpeedFactor + 1, 100);
                    MainScreen.setTextField("SpeedFactor : " + Globals.SpeedFactor, "SET");
                    if (Globals.SpeedFactor >= 10 && SHOW_TAIL) {
                        MainScreen.setTextField("We don't advice showing tail at this speed, tail hided.", "APPEND");
                        SHOW_TAIL = false;
                        MainScreen.btn7.setLabel("Show Tail");
                    }
                }
                else {
                    Globals.SlowDownFactor--;
                    if (Globals.SlowDownFactor >= 2) MainScreen.setTextField("SpeedFactor : 1 / " + Globals.SlowDownFactor, "SET");
                    else MainScreen.setTextField("SpeedFactor : 1", "SET");
                }
            }
        };
        ActionListener btn6Listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Globals.SpeedFactor > 1) {
                    Globals.SpeedFactor = Math.max(Globals.SpeedFactor - 1, 1);
                    MainScreen.setTextField("SpeedFactor : " + Globals.SpeedFactor, "SET");
                }
                else {
                    Globals.SlowDownFactor = Math.min(10, Globals.SlowDownFactor + 1);
                    MainScreen.setTextField("SpeedFactor : 1 / " + Globals.SlowDownFactor, "SET");
                }
            }
        };
        ActionListener btn7Listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SHOW_TAIL = !SHOW_TAIL;
                if (SHOW_TAIL) btn7.setLabel("Hide Tail");
                else btn7.setLabel("Show Tail");
            }
        };
        ActionListener btn8Listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    FileIO.SaveCurrentScene(ballSet);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        };
        btn1 = new Button("Close");
        btn2 = new Button("Stop");
        btn3 = new Button("Zoom out");
        btn4 = new Button("Zoom in");
        btn5 = new Button("Speed Up");
        btn6 = new Button("Speed Down");
        btn7 = new Button("Show Tail");
        btn8 = new Button("Save Scene");
        btn1.setPreferredSize(new Dimension(200, 100));
        btn1.setFont(new Font("Monospaced", Font.PLAIN,40));
        btn2.setPreferredSize(new Dimension(200, 100));
        btn2.setFont(new Font("Monospaced", Font.PLAIN,40));
        btn3.setPreferredSize(new Dimension(95, 100));
        btn3.setFont(new Font("Monospaced", Font.PLAIN,20));
        btn4.setPreferredSize(new Dimension(95, 100));
        btn4.setFont(new Font("Monospaced", Font.PLAIN,20));
        btn5.setPreferredSize(new Dimension(200, 100));
        btn5.setFont(new Font("Monospaced", Font.PLAIN,30));
        btn6.setPreferredSize(new Dimension(200, 100));
        btn6.setFont(new Font("Monospaced", Font.PLAIN,30));
        btn7.setPreferredSize(new Dimension(200, 100));
        btn7.setFont(new Font("Monospaced", Font.PLAIN,30));
        btn8.setPreferredSize(new Dimension(200, 100));
        btn8.setFont(new Font("Monospaced", Font.PLAIN,30));
        btn1.addActionListener(btn1Listener);
        btn2.addActionListener(btn2Listener);
        btn3.addActionListener(btn3Listener);
        btn4.addActionListener(btn4Listener);
        btn5.addActionListener(btn5Listener);
        btn6.addActionListener(btn6Listener);
        btn7.addActionListener(btn7Listener);
        btn8.addActionListener(btn8Listener);
    }

    void textFieldSetup(){
        textField = new TextArea("Console", 2, 4, Scrollbar.VERTICAL);
//        textField.setPreferredSize(new Dimension(1920, 200));
        textField.setVisible(true);
        textField.setBackground(Color.BLACK);
        textField.setForeground(Color.WHITE);
        textField.setFont(new Font("Monospaced", Font.PLAIN, 20));
    }

    void panelSetup(){
        panel = new Panel(new FlowLayout(FlowLayout.CENTER, 10,10));
        panel.setPreferredSize(new Dimension(210, TABLE_HEIGHT));
        panel.add(btn1);
        panel.add(btn2);
        panel.add(btn3);
        panel.add(btn4);
        panel.add(btn5);
        panel.add(btn6);
        panel.add(btn7);
        panel.add(btn8);
    }

    void menuSetup(){
        menuBar = new MenuBar();
        menuBar.setFont(new Font("Monospaced", Font.BOLD, 15));
        for (int i = 0; i < Globals.MenuLabel[0].length; i++) {
            Menu menu = new Menu(Globals.MenuLabel[0][i]);

            for (int j = 0; j < Globals.MenuLabel[1 + i].length; j++){
                MenuItem menuItem = new MenuItem(Globals.MenuLabel[1+i][j]);
                menuItem.addActionListener(Globals.MenuActionListener[i][j]);
                menu.add(menuItem);
            }

            menuBar.add(menu);
        }
//        Menu haha = new Menu("");
//        haha.add(new MenuItem());
//        menuBar.add(haha);

    }

    void init(){
        frameSetup();

        canvasSetup();

        timerSetup();

        buttonSetup();

        panelSetup();

        textFieldSetup();

        menuSetup();

        frame.setLayout(new BorderLayout());
        frame.setMenuBar(menuBar);
        frame.add(panel, BorderLayout.EAST);
        frame.add(myCanvas, BorderLayout.CENTER);
        frame.add(textField, BorderLayout.SOUTH);

        frame.pack();
        frame.setVisible(true);
        frame.setAlwaysOnTop(true);

//        Scanner scanner = new Scanner();

    }
}
