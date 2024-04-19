package com.liferay.convert.tools.util;

/**
 * @author Albett Gomes Cabral
 */
public class PrintUtil {

    public static void print(String color, String word) {
        System.out.println(color + _STRING_WHITE_SPACE +
                word +  RESET + "\n");
    }

    private static final String _STRING_WHITE_SPACE = " ";
    public static final String GREEN = "\u001B[32m";
    public static final String LIGHT_BLUE = "\u001B[94m";
    public static final String RED = "\u001B[31m";
    public static final String RESET = "\u001B[0m";
    public static final String YELLOW = "\u001B[33m";

}
