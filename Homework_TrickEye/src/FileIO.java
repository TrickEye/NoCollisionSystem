import java.io.*;
import java.util.Arrays;

public class FileIO {
    public static void SaveCurrentScene(Ball[] ballSet) throws IOException {
        MainScreen.setTextField("Current Scene is saved in /scene.ncl", "SET");
        OutputInit(ballSet);
    }
    public static Ball[] InputInit() throws IOException {
        FileInputStream f = new FileInputStream("scene.ncl");
        InputStreamReader reader = new InputStreamReader(f);
        StringBuffer stringBuffer = new StringBuffer();
        char[] str = new char[1000];
        char temp;
        int len = 0;

        while(reader.ready() && (temp = (char) reader.read()) != '\n'){
            str[len++] = temp;
            stringBuffer.append(temp);
        }
        str[len] = '\0';
        if (!(new String(str, 0, len)).equals("/*** Copyright TrickEye ***/")) {
            WrongBehaviour wb = new WrongBehaviour(WrongBehaviour.FILE_WRONG);
            System.out.println(new String(str, 0, len));
            return null;
        }

        len = 0;
        while(reader.ready() && (temp = (char) reader.read()) != '\n'){
            str[len++] = temp;
            stringBuffer.append(temp);
        }
        str[len] = '\0';
        Ball[] ballSet = new Ball[StringOperations.toInt(new String(str, 0, len))];
        for (int i = 0; i < ballSet.length; i++) {
            ballSet[i] = new Ball();
            len = 0;
            while(reader.ready() && (temp = (char) reader.read()) != ' '){
                str[len++] = temp;
                stringBuffer.append(temp);
            }
            str[len] = '\0';
            ballSet[i].setDiameter(StringOperations.toDouble(new String(str, 0, len)));

            len = 0;
            while(reader.ready() && (temp = (char) reader.read()) != ' '){
                str[len++] = temp;
                stringBuffer.append(temp);
            }
            str[len] = '\0';
            ballSet[i].setPositionX(StringOperations.toDouble(new String(str, 0, len)));

            len = 0;
            while(reader.ready() && (temp = (char) reader.read()) != '\n'){
                str[len++] = temp;
                stringBuffer.append(temp);
            }
            str[len] = '\0';
            ballSet[i].setPositionY(StringOperations.toDouble(new String(str, 0, len)));

            len = 0;
            while(reader.ready() && (temp = (char) reader.read()) != ' '){
                str[len++] = temp;
                stringBuffer.append(temp);
            }
            str[len] = '\0';
            ballSet[i].setVelocityX(StringOperations.toDouble(new String(str, 0, len)));

            len = 0;
            while(reader.ready() && (temp = (char) reader.read()) != '\n'){
                str[len++] = temp;
                stringBuffer.append(temp);
            }
            str[len] = '\0';
            ballSet[i].setVelocityY(StringOperations.toDouble(new String(str, 0, len)));

            len = 0;
            while(reader.ready() && (temp = (char) reader.read()) != ' '){
                str[len++] = temp;
                stringBuffer.append(temp);
            }
            str[len] = '\0';
            ballSet[i].setAccelerationX(StringOperations.toDouble(new String(str, 0, len)));

            len = 0;
            while(reader.ready() && (temp = (char) reader.read()) != '\n'){
                str[len++] = temp;
                stringBuffer.append(temp);
            }
            str[len] = '\0';
            ballSet[i].setAccelerationY(StringOperations.toDouble(new String(str, 0, len)));

        }
//        System.out.println(ballSet[2].getDiameter());
//        System.out.println(ballSet[9].getAccelerationY());
//        System.exit(0);

        //System.out.println(stringBuffer.toString());
        reader.close();
        f.close();

        return ballSet;
    }

    public static void OutputInit(Ball[] ballSet) throws IOException {
        OutputStream f = new FileOutputStream("scene.ncl");
        OutputStreamWriter writer = new OutputStreamWriter(f);

        writer.append("/*** Copyright TrickEye ***/\n");
        writer.append(String.format("%d\n", ballSet.length));
        for (int i = 0; i < ballSet.length; i++) {
            writer.append(String.format("%f %f %f\n", ballSet[i].getDiameter(), ballSet[i].getPositionX(), ballSet[i].getPositionY()));
            writer.append(String.format("%f %f\n", ballSet[i].getVelocityX(), ballSet[i].getVelocityY()));
            writer.append(String.format("%f %f\n", ballSet[i].getAccelerationX(), ballSet[i].getAccelerationY()));
            writer.append("\n");
        }
        writer.close();
        f.close();
    }
    public static void main(String[] args) throws IOException {
        InputInit();
    }
}
