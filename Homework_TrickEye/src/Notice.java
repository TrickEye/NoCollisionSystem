import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Notice {
    static int WRONG_INPUT = 1;
    static int WRONG_FILE = 2;
    static int SCREEN_TOO_SMALL = 3;
    static int SOFTWARE_INFO = 4;
    static int AUTHOR_INFO = 5;

    public Notice(int noticeType){
        if (noticeType == WRONG_INPUT) {
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
        else if (noticeType == WRONG_FILE){
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
        else if (noticeType == SCREEN_TOO_SMALL) {
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
        else if (noticeType == SOFTWARE_INFO){
            final Frame frame = new Frame(Globals.NoticeSoftwareInformation[0]);
            frame.setAlwaysOnTop(true);
            frame.setBackground(Globals.InfoColor);
            frame.setLayout(new GridLayout(Globals.NoticeSoftwareInformation.length-1, 1, 0, 20));
            frame.setSize(new Dimension(1200, 800));
            frame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    super.windowClosing(e);
                    frame.setVisible(false);
                }
            });

            for (int i = 1; i < Globals.NoticeSoftwareInformation.length; i++){
                Label label = new Label(Globals.NoticeSoftwareInformation[i]);
                label.setFont(new Font("Serif", Font.PLAIN, 40));
                label.setForeground(Color.WHITE);
                frame.add(label);
            }

            frame.setVisible(true);
        }
        else if (noticeType == AUTHOR_INFO){
            final Frame frame = new Frame(Globals.NoticeAuthorInformation[0]);
            frame.setAlwaysOnTop(true);
            frame.setBackground(Globals.InfoColor);
            frame.setLayout(new GridLayout(Globals.NoticeAuthorInformation.length-1, 1, 0, 20));
            frame.setSize(new Dimension(1200, 800));
            frame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    super.windowClosing(e);
                    frame.setVisible(false);
                }
            });

            for (int i = 1; i < Globals.NoticeAuthorInformation.length; i++){
                Label label = new Label(Globals.NoticeAuthorInformation[i]);
                label.setFont(new Font("Serif", Font.PLAIN, 40));
                label.setForeground(Color.WHITE);
                frame.add(label);
            }

            frame.setVisible(true);
        }
    }
}
