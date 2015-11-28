package model;

/**
 * Created by ribra on 11/12/2015.
 */
public class Utils {

    private Utils() {}

    public static boolean isNumber(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

}
