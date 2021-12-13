public class StringOperations {
    public static boolean isNum(char chr){
        return chr >= '0' && chr <= '9';
    }

    public static int toInt(String str){
        if (str.length() == 0) return 0;
        char[] strChr = str.toCharArray();
        int len = str.length();
        int readValue = 0;
        int sign = 1;
        for (int i = 0; i < len; i++){
            if (strChr[i] == '-') sign = -1;
            else if (isNum(strChr[i])) readValue = readValue * 10 + strChr[i] - '0';
            else return sign * readValue;
        }
        return sign * readValue;
    }

    public static double toDouble(String str){
        if (str.length() == 0) return 0;
        char[] strChr = str.toCharArray();
        int len = str.length();
        double readValue_integerPart = 0;
        double readValue_decimalPart = 0;
        double sign = 1;
        int i;
        for (i = 0; i < len && strChr[i] != '.'; i++) {
            if (strChr[i] == '-') sign = -1;
            if (isNum(strChr[i])) readValue_integerPart = readValue_integerPart * 10 + strChr[i] - '0';
        }
        if (i >= len || strChr[i] != '.') return readValue_integerPart;
        double mult = 0.1;
        for (i = i + 1; i < len; i++, mult /= 10){
            if (isNum(strChr[i])) readValue_decimalPart = readValue_decimalPart + mult * (strChr[i] - '0');
        }
        return sign * (readValue_integerPart+readValue_decimalPart);
    }

    public static void main(String[] args){
        System.out.println(toDouble("123haha"));
    }
}
