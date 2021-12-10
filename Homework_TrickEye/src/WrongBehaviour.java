import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class WrongBehaviour {
    static int WRONG_INPUT = 1;
    static int WRONG_FILE = 2;
    static int SCREEN_TOO_SMALL = 3;

    public WrongBehaviour(int errorType){
        if (errorType == WRONG_INPUT) {
            final Frame frame = new Frame(Globals.NoticeWrongInput[0]);
            frame.setAlwaysOnTop(true);
            frame.setBackground(Globals.ErrorWBColor);
            frame.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 50));
            frame.setSize(new Dimension(1000, 600));
            frame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    super.windowClosing(e);
                    frame.setVisible(false);
                }
            });

            for (int i = 1; i < Globals.NoticeWrongInput.length; i++) {
                Label label = new Label(Globals.NoticeWrongInput[i]);
                label.setAlignment(Label.LEFT);
                label.setFont(new Font("Serif", Font.PLAIN, 50));
                label.setForeground(Color.BLACK);
                frame.add(label);
            }

            frame.setVisible(true);
        }
        if (errorType == WRONG_FILE){
            final Frame frame = new Frame(Globals.NoticeWrongFile[0]);
            frame.setAlwaysOnTop(true);
            frame.setBackground(Globals.ErrorWBColor);
            frame.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 50));
            frame.setSize(new Dimension(1000, 600));
            frame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    super.windowClosing(e);
                    frame.setVisible(false);
                }
            });

            for (int i = 1; i < Globals.NoticeWrongFile.length; i++){
                Label label = new Label(Globals.NoticeWrongFile[i]);
                label.setFont(new Font("Serif", Font.PLAIN, 50));
                label.setForeground(Color.BLACK);
                frame.add(label);
            }

            frame.setVisible(true);
        }
        if (errorType == SCREEN_TOO_SMALL) {
            final Frame frame = new Frame(Globals.NoticeSmallScreen[0]);
            frame.setAlwaysOnTop(true);
            frame.setBackground(Globals.ErrorWNColor);
            frame.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 20));
            frame.setSize(new Dimension(900, 600));
            frame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    super.windowClosing(e);
                    frame.setVisible(false);
                }
            });

            for (int i = 1; i < Globals.NoticeSmallScreen.length; i++){
                Label label = new Label(Globals.NoticeSmallScreen[i]);
                label.setFont(new Font("Serif", Font.PLAIN, 40));
                label.setForeground(Color.BLACK);
                frame.add(label);
            }

            frame.setVisible(true);
        }
    }
}
