import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class WrongBehaviour {
    static int WRONG_INPUT = 1;
    static int FILE_WRONG = 2;

    public WrongBehaviour(int errorType){
        if (errorType == WRONG_INPUT) {
            final Frame frame = new Frame("Wrong Input");
            frame.setAlwaysOnTop(true);
            frame.setBackground(new Color(0xF5DA81));
            frame.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 50));
            frame.setSize(new Dimension(800, 300));
            frame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    super.windowClosing(e);
                    frame.setVisible(false);
                }
            });

            Label label = new Label("Wrong input, please check your input");
            label.setFont(new Font("Serif", Font.PLAIN, 50));
            label.setForeground(Color.BLACK);
            frame.add(label);

            frame.setVisible(true);
        }
        if (errorType == FILE_WRONG){

        }
    }
}
