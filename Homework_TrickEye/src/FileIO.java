import java.io.*;

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
        char temp = 0;
        int len = 0;

        while(reader.ready() && (temp = (char) reader.read()) != '\n'){
            str[len++] = temp;
            stringBuffer.append(temp);
        }
        stringBuffer.append(temp);
        str[len] = '\0';
        if (!(new String(str, 0, len)).equals("/*** Copyright TrickEye ***/")) {
            Notice wb = new Notice(Notice.WRONG_FILE);
            System.out.println(new String(str, 0, len));
            return null;
        }

        len = 0;
        while(reader.ready() && (temp = (char) reader.read()) != '\n'){
            str[len++] = temp;
            stringBuffer.append(temp);
        }
        stringBuffer.append(temp);
        str[len] = '\0';
        Ball[] ballSet = new Ball[StringOperations.toInt(new String(str, 0, len))];
        for (int i = 0; i < ballSet.length; i++) {
            ballSet[i] = new Ball();
            len = 0;
            while(reader.ready() && (temp = (char) reader.read()) != ' '){
                str[len++] = temp;
                stringBuffer.append(temp);
            }
            stringBuffer.append(temp);
            str[len] = '\0';
            ballSet[i].setDiameter(StringOperations.toDouble(new String(str, 0, len)));

            len = 0;
            while(reader.ready() && (temp = (char) reader.read()) != ' '){
                str[len++] = temp;
                stringBuffer.append(temp);
            }
            stringBuffer.append(temp);
            str[len] = '\0';
            ballSet[i].setPositionX(StringOperations.toDouble(new String(str, 0, len)));

            len = 0;
            while(reader.ready() && (temp = (char) reader.read()) != '\n'){
                str[len++] = temp;
                stringBuffer.append(temp);
            }
            stringBuffer.append(temp);
            str[len] = '\0';
            ballSet[i].setPositionY(StringOperations.toDouble(new String(str, 0, len)));

            len = 0;
            while(reader.ready() && (temp = (char) reader.read()) != ' '){
                str[len++] = temp;
                stringBuffer.append(temp);
            }
            stringBuffer.append(temp);
            str[len] = '\0';
            ballSet[i].setVelocityX(StringOperations.toDouble(new String(str, 0, len)));

            len = 0;
            while(reader.ready() && (temp = (char) reader.read()) != '\n'){
                str[len++] = temp;
                stringBuffer.append(temp);
            }
            stringBuffer.append(temp);
            str[len] = '\0';
            ballSet[i].setVelocityY(StringOperations.toDouble(new String(str, 0, len)));

            len = 0;
            while(reader.ready() && (temp = (char) reader.read()) != ' '){
                str[len++] = temp;
                stringBuffer.append(temp);
            }
            stringBuffer.append(temp);
            str[len] = '\0';
            ballSet[i].setAccelerationX(StringOperations.toDouble(new String(str, 0, len)));

            len = 0;
            while(reader.ready() && (temp = (char) reader.read()) != '\n'){
                str[len++] = temp;
                stringBuffer.append(temp);
            }
            stringBuffer.append(temp);
            str[len] = '\0';
            ballSet[i].setAccelerationY(StringOperations.toDouble(new String(str, 0, len)));

        }
//        System.out.println(ballSet[2].getDiameter());
//        System.out.println(ballSet[9].getAccelerationY());
//        System.exit(0);

        //System.out.println(stringBuffer.toString());
        stringBuffer.append((char) reader.read());
        System.out.println(stringBuffer.toString());
        int HashCode = stringBuffer.toString().hashCode() % 10000007;

        len = 0;
        while(reader.ready() && (temp = (char) reader.read()) != '\n'){
            str[len++] = temp;
            stringBuffer.append(temp);
        }
        str[len] = '\0';

        int HashCodeOfFile = StringOperations.toInt(new String(str, 0, len));

        reader.close();
        f.close();

        if (HashCode == HashCodeOfFile) {
            return ballSet;
        }
        else {
            Notice wb = new Notice(Notice.WRONG_FILE);
            return null;
        }
    }

    public static void OutputInit(Ball[] ballSet) throws IOException {
        OutputStream f = new FileOutputStream("scene.ncl");
        OutputStreamWriter writer = new OutputStreamWriter(f);
        StringBuffer stringBuffer = new StringBuffer();

        writer.append("/*** Copyright TrickEye ***/\n");
        stringBuffer.append("/*** Copyright TrickEye ***/\n");
        writer.append(String.format("%d\n", ballSet.length));
        stringBuffer.append(String.format("%d\n", ballSet.length));
        for (int i = 0; i < ballSet.length; i++) {
            writer.append(String.format("%f %f %f\n", ballSet[i].getDiameter(), ballSet[i].getPositionX(), ballSet[i].getPositionY()));
            stringBuffer.append(String.format("%f %f %f\n", ballSet[i].getDiameter(), ballSet[i].getPositionX(), ballSet[i].getPositionY()));
            writer.append(String.format("%f %f\n", ballSet[i].getVelocityX(), ballSet[i].getVelocityY()));
            stringBuffer.append(String.format("%f %f\n", ballSet[i].getVelocityX(), ballSet[i].getVelocityY()));
            writer.append(String.format("%f %f\n", ballSet[i].getAccelerationX(), ballSet[i].getAccelerationY()));
            stringBuffer.append(String.format("%f %f\n", ballSet[i].getAccelerationX(), ballSet[i].getAccelerationY()));
            writer.append("\n");
            stringBuffer.append("\n");
        }
        System.out.println(stringBuffer.toString());
        System.out.println("haha");
        writer.append(String.format("%d", stringBuffer.toString().hashCode() % 10000007));
        writer.close();
        f.close();
    }
    public static void main(String[] args) throws IOException {
        InputInit();
    }
}
