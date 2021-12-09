import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

public class StartScreen {

    int TABLE_WIDTH = 1920, TABLE_HEIGHT = 1080;
    Frame frame;
    Label TitleLabel;
    Label[] DescriptionLabel = new Label[Globals.description.length];
    Label[] InputBoxLabel = new Label[Globals.guide.length];
    static TextField[] InputTextField = new TextField[Globals.defaultInput.length];
    Button btnLaunch = new Button();
    Button btnClose  = new Button();
    Button btnLaunchFromFile = new Button();
    Dimension ScreenDimension = Toolkit.getDefaultToolkit().getScreenSize();

    Panel upperPanel, middlePanel, lowerPanel;


    public void FakeDestroy(){
        this.frame.setVisible(false);
    }

    public void BackToLife() {
        this.frame.setVisible(true);
    }

    public void FrameInit() {
        this.frame = new Frame("Start Screen");
        this.frame.setBackground(Color.BLACK);
        this.frame.setForeground(Color.WHITE);
        this.frame.setLayout(new FlowLayout(FlowLayout.CENTER, 1000, 50));
        this.frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                System.exit(0);
            }
        });
//        this.frame.setSize(new Dimension(TABLE_WIDTH, TABLE_HEIGHT));
        this.frame.setSize(ScreenDimension);
    }

//    public void tfInit(){
//        this.tf = new TextField(20);
//        this.tf.setFont(new Font("", Font.PLAIN, 20));
//        this.tf.setBackground(Color.WHITE);
//        this.tf.setForeground(Color.BLACK);
//        this.tf.setText("123123");
//        this.tf.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                System.out.println(this.tf.getText());
//            }
//        });
//        this.frame.add(this.tf, -1);
//    }

//    public static void taInit(){
//        ta = new TextArea("", 1, 0, TextArea.SCROLLBARS_NONE);
//        ta.setPreferredSize(new Dimension(300, 100));
//        ta.setBackground(Color.BLACK);
//        ta.setForeground(Color.WHITE);
//        ta.setFont(new Font("", Font.PLAIN, 50));
//        ta.setText("Text!");
//
//        ta.setEditable(false);
//        frame.add(ta, 0);
//    }

    /**
     * This initializes the upper half of the start screen.
     * You can find contents in Globals.java
     * @see Globals
     */
    public void UpperPanelInit(){
        this.TitleLabel = new Label();
        this.TitleLabel.setSize(new Dimension(300, 100));
        this.TitleLabel.setBackground(Color.BLACK);
        this.TitleLabel.setForeground(Color.WHITE);
        this.TitleLabel.setFont(new Font("Serif", Font.PLAIN, 50));
        this.TitleLabel.setAlignment(Label.CENTER);
        this.TitleLabel.setText(Globals.Title);
        this.TitleLabel.setEnabled(true);
        this.upperPanel.add(this.TitleLabel);

        for (int i = 0; i < Globals.description.length; i++) {
            this.DescriptionLabel[i] = new Label();
            this.DescriptionLabel[i].setSize(new Dimension(500, 40));
            this.DescriptionLabel[i].setBackground(Color.BLACK);
            this.DescriptionLabel[i].setForeground(Color.WHITE);
            this.DescriptionLabel[i].setFont(new Font("Monospaced", Font.PLAIN, 20));
            this.DescriptionLabel[i].setAlignment(Label.LEFT);
            this.DescriptionLabel[i].setText(Globals.description[i]);
            this.DescriptionLabel[i].setEnabled(true);
            this.upperPanel.add(this.DescriptionLabel[i]);

        }
    }

    public void MiddlePanelInit(){
        int len = Globals.guide.length;
        for (int i = 0; i < len; i++){
            this.InputBoxLabel[i] = new Label();
            this.InputBoxLabel[i].setSize(new Dimension(200, 60));
            this.InputBoxLabel[i].setBackground(Color.BLACK);
            this.InputBoxLabel[i].setForeground(Color.WHITE);
            this.InputBoxLabel[i].setFont(new Font("Monospaced", Font.BOLD, 20));
            this.InputBoxLabel[i].setText(Globals.guide[i]);
            this.InputBoxLabel[i].setEnabled(true);
            this.middlePanel.add(this.InputBoxLabel[i]);

            InputTextField[i] = new TextField(12);
            InputTextField[i].setMaximumSize(new Dimension((int)ScreenDimension.getWidth() / 4, (int)ScreenDimension.getHeight()));
            InputTextField[i].setFont(new Font("Monospaced", Font.PLAIN, 30));
            InputTextField[i].setBackground(Color.BLACK);
            InputTextField[i].setForeground(Color.WHITE);
            InputTextField[i].setEditable(true);
            InputTextField[i].setText(Globals.defaultInput[i]);
            this.middlePanel.add(InputTextField[i]);
        }
    }

    static Conf inputConfiguration(){
        return new Conf(1,2,2);
    }

    public void LowerPanelInit(){
        this.btnLaunch = new Button("Launch");
        this.btnLaunch.setBackground(Color.BLACK);
        this.btnLaunch.setForeground(Color.WHITE);
        this.btnLaunch.setFont(new Font("Monospaced", Font.BOLD, 30));
        ActionListener launchListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Conf InputConf = new Conf(InputTextField[0].getText(), InputTextField[1].getText(), InputTextField[2].getText());
//                System.out.println(InputConf.toString());
                if (InputConf.validation()){
                    Main.Launch(InputConf);
                    Main.startScreen.FakeDestroy();
                }
                else {
                    WrongBehaviour Wb = new WrongBehaviour(WrongBehaviour.WRONG_INPUT);
                }
            }
        };
        this.btnLaunch.addActionListener(launchListener);
        this.btnClose = new Button("End Program");
        this.btnClose.setBackground(Color.BLACK);
        this.btnClose.setForeground(Color.WHITE);
        this.btnClose.setFont(new Font("Monospaced", Font.BOLD, 30));
        ActionListener closeListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        };
        this.btnClose.addActionListener(closeListener);
        this.btnLaunchFromFile = new Button("Load File scene.ncl");
        this.btnLaunchFromFile.setBackground(Color.BLACK);
        this.btnLaunchFromFile.setForeground(Color.WHITE);
        this.btnLaunchFromFile.setFont(new Font("Monospaced", Font.BOLD, 30));
        ActionListener launchFromFileListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Main.LaunchFromFile(FileIO.InputInit());
                    Main.startScreen.FakeDestroy();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        };
        this.btnLaunchFromFile.addActionListener(launchFromFileListener);
        this.lowerPanel.add(btnLaunch);
        this.lowerPanel.add(btnClose);
        this.lowerPanel.add(btnLaunchFromFile);
    }

    public void PanelInit(){
        this.upperPanel = new Panel(new GridLayout(Globals.description.length + 1, 1, 0, 0));
        this.upperPanel.setBackground(Color.BLACK);


        this.middlePanel = new Panel(new GridLayout(3, 2, 10, 20));
        this.middlePanel.setBackground(Color.BLACK);


        this.lowerPanel = new Panel(new GridLayout(3, 1, 0, 20));
        this.lowerPanel.setBackground(Color.BLACK);
    }

    public StartScreen(){
        this.PanelInit();
        this.FrameInit();
        this.UpperPanelInit();
        this.MiddlePanelInit();
        this.LowerPanelInit();
        //tfInit();
        this.frame.add(this.upperPanel);
        this.frame.add(this.middlePanel);
        this.frame.add(this.lowerPanel);
        this.frame.setVisible(true);

    }


}
