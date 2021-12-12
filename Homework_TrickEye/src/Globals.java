import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * This file records global values that are needed
 * and used in other files.
 * Some labels, for example, may fetch from the
 * notices in this file to display the information.
 * @see StartScreen
 * @see Ball
 * @see MainScreen
 * @see Notice
 */
public class Globals {
    /**
     * Below are notices used in the Start Screen.
     * The notice will be used in StartScreen.java
     * @see StartScreen
     */
    static String Title = "No-Collision System Illustrator-v1.3";
    static String[] description = {
            "Author: Cui Yikai | Stu ID: 20373866 | Contact: trickeye@buaa.edu.cn",
            "This is a java program aimed at generating a no-collision-system from a randomly ",
            "    generated status of distribution of the balls. [V1.3]",
            "This this a demo product submitted as the product of the homework project of my ",
            "    Java Lecture (by lecturer: Mr.Li Bo)"
    };
    static String[] guide = {
            "Input an integer n as the number of the balls. (1 < n <= 50)",
            "Input an integer as the upperbound of diameters.",
            "Input lowerBound. (10 < lowerBound < upperBound <= 500)"
    };
    static String[] defaultInput = {
            "10",
            "300",
            "100"
    };

    /**
     * Below are values to be used in Ball class.
     * @see Ball
     */

    static int HistoryMemorySize = 100;
    static int NotCollidedMemorySize = 40000;
    static double Gravity_Ratio = 10.0;
    static Color[] colorArray = {
            new Color(255, 0, 0),
            new Color(239, 16, 0),
            new Color(223, 32, 0),
            new Color(207, 48, 0),
            new Color(191, 64, 0),

            new Color(175, 80, 0),
            new Color(159, 96, 0),
            new Color(143, 112, 0),
            new Color(127, 128, 0),
            new Color(111, 144, 0),

            new Color(95, 160, 0),
            new Color(79, 176, 0),
            new Color(63, 192, 0),
            new Color(47, 208, 0),
            new Color(31, 224, 0),

            new Color(15, 240, 0),
            new Color(0, 255, 0),
    };
    static int colorArrayLen = 17;

    static int SpeedFactor = 1;
    static int SlowDownFactor = 1;
    static int ignoreZoom = 0;
    static boolean ignoreAdaptiveZoom = false;

    static String[][] MenuLabel = {
            {
                "No-Collision System Illustrator",
                "File",
                "Control"
            },
            {
                "Software Information  Ctrl+I",
                "Author Information    Ctrl+A",
                "Back to start screen  Ctrl+B",
                "Close the software"
            },
            {
                "Save current scene    Ctrl+S",
                "Load from scene.ncl   Ctrl+L"
            },
            {
                "Pause / Resume    Space",
                "Zoom in           Up",
                "Zoom out          Down",
                "Disable / Enable Adaptive Zoom   Ctrl+Z",
                "Speed up          Right",
                "Speed down        Down",
                "Show / Hide tail  Ctrl+T"
            },
    };
    static ActionListener[][] MenuActionListener = {
            {
                    // Software Information
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Notice n = new Notice(Notice.SOFTWARE_INFO);
                    }
                },
                    // Author Information
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Notice n = new Notice(Notice.AUTHOR_INFO);
                    }
                },
                    // Back To Start Screen
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        MainScreen.frame.setVisible(false);
                        Main.startScreen.BackToLife();
                    }
                },
                    // Close the software
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.exit(0);
                    }
                }
            },
            {
                    // Save current scene
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            FileIO.SaveCurrentScene(MainScreen.ballSet);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                },
                    // Load from scene.ncl
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            if (Main.LaunchFromFile(FileIO.InputInit()))
                                MainScreen.setTextField("Welcome Back!", "SET");
                        } catch (IOException ex) {
                            Notice wb = new Notice(Notice.WRONG_FILE);
                            //ex.printStackTrace();
                        }
                    }
                }
            },
            {
                    // Pause / Resume
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        MainScreen.setTextField((MainScreen.CURRENT_STATE == MainScreen.STATE_NORMAL) ? "Stop." : "Resume.", "SET");
                        if (MainScreen.CURRENT_STATE == MainScreen.STATE_NORMAL) MainScreen.btn2.setLabel("Resume");
                        if (MainScreen.CURRENT_STATE == MainScreen.STATE_STOPPED) MainScreen.btn2.setLabel("Stop");
                        MainScreen.CURRENT_STATE = 1 - MainScreen.CURRENT_STATE;
                    }
                },
                    //"Zoom in",
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        MainScreen.OBSERVE_SCALE /= 1.1;
                        Globals.ignoreZoom = 100;
                    }
                },
                //"Zoom out",
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        MainScreen.OBSERVE_SCALE *= 1.1;
                    }
                },
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (ignoreAdaptiveZoom == false) {
                            ignoreAdaptiveZoom = true;
                            MainScreen.setTextField("Adaptive Zoom Disabled.", "SET");
                        }
                        else if (ignoreAdaptiveZoom == true) {
                            ignoreAdaptiveZoom = false;
                            ignoreZoom = 0;
                            MainScreen.setTextField("Adaptive Zoom Enabled.", "SET");
                        }
                    }
                },
                    //"Speed up",
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (Globals.SlowDownFactor == 1) {
                            Globals.SpeedFactor = Math.min(Globals.SpeedFactor + 1, 100);
                            MainScreen.setTextField("SpeedFactor : " + Globals.SpeedFactor, "SET");
                            if (Globals.SpeedFactor >= 10 && MainScreen.SHOW_TAIL) {
                                MainScreen.setTextField("We don't advice showing tail at this speed, tail hided.", "APPEND");
                                MainScreen.SHOW_TAIL = false;
                                MainScreen.btn7.setLabel("Show Tail");
                            }
                        }
                        else {
                            Globals.SlowDownFactor--;
                            if (Globals.SlowDownFactor >= 2) MainScreen.setTextField("SpeedFactor : 1 / " + Globals.SlowDownFactor, "SET");
                            else MainScreen.setTextField("SpeedFactor : 1", "SET");
                        }
                    }
                },
                    //"Speed down",
                new ActionListener() {
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
                },
                    //"Show / Hide tail"
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        MainScreen.SHOW_TAIL = !MainScreen.SHOW_TAIL;
                        if (MainScreen.SHOW_TAIL) MainScreen.btn7.setLabel("Hide Tail");
                        else MainScreen.btn7.setLabel("Show Tail");
                    }
                }
            }
    };

    /**
     * Below are notices that are used in NoticeBehaviour.java
     * These string array provides information to be displayed at the warning window.
     * @see Notice
     */
    static Color ErrorWBColor = new Color(255, 127, 0);
    static Color ErrorWNColor = new Color(192, 192, 127);
    static Color InfoColor = new Color(64,64,64);
    static String [] NoticeWrongInput = {
            "Wrong Input!",
            "Error: Wrong input, please check your input",
            "Your input should meet these requirements: ",
            "0 < n <= 50, ",
            "10 < lowerBound < upperBound <= 500"
    };
    static String [] NoticeWrongFile = {
            "Wrong File!",
            "Error: Wrong file, please check scene.ncl",
            "Or, the file could have been moderated ",
            "  outside this software."
    };
    static String [] NoticeSmallScreen = {
            "Screen too Small!",
            "Warning: Screen too small, please check.",
            "This program runs only on screens that are",
            "larger than 1600 * 900",
            "If this warning still appear on your screen, ",
            "please check if you have enabled 125% zooming",
            "You can close this window and still run this",
            "software, but some features may be influenced."
    };
    static String [] NoticeSoftwareInformation = {
            "Software Information",
            "",
            "    No-Collision-System Illustrator",
            "    Developed by Cui Yikai",
            "    Version: v1.3",
            "    A demo for the Java class homework",
            "    This software, as well as the source code of this, are open-source. Anyone",
            "      is free to download, use, distribute, edit the source code and products.",
            "    If you are interested in my work, please contact me at email",
            "      trickeye@buaa.edu.cn",
            "    For more information of the software, please visit:",
            "https://trickeye.github.io/2021/12/05/No-Collision-System-Dynamic-Update.html",
            "https://github.com/TrickEye/NoCollisionSystem",
            "    Thank you. Cui Yikai, Dec.12, 2021",
            ""
    };

    static String [] NoticeAuthorInformation = {
            "Author Information",
            "",
            "    Cui Yikai (Or TrickEye)",
            "    A sophomore in Beihang University",
            "    Major: Computer Science and Technology",
            "    Email: trickeye@buaa.edu.cn",
            ""
    };
}
