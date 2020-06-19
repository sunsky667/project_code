import java.io.UnsupportedEncodingException;

public class Test {

    public static String hex2Str(String str) throws UnsupportedEncodingException {
        String strArr[] = str.split("\\\\"); // 分割拿到形如 xE9 的16进制数据
        byte[] byteArr = new byte[strArr.length - 1];
        for (int i = 1; i < strArr.length; i++) {
            Integer hexInt = Integer.decode("0" + strArr[i]);
            byteArr[i - 1] = hexInt.byteValue();
        }

        return new String(byteArr, "UTF-8");
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        System.out.println(hex2Str("\\xE9\\x95\\xBF\\xE8\\x99\\xB9\\xE6\\x99\\xBA\\xE8\\x83\\xBD\\xE7\\x94\\xB5\\xE8\\xA7\\x86"));
    }

}
