package kingsbutbad.kingsbutbad.utils;

public class DiscordUtils {
    public static String deformat(String text) {
        text = text.replaceAll("_", "\\\\_");
        text = text.replaceAll("@", "`@`");
        text = text.replaceAll("\\\\`", "");
        text = text.replaceAll("`", "");
        return text;
    }
    public static String deformatMsg(String text) {
        text = text.replaceAll("\\\\`", "");
        text = text.replaceAll("`", "");
        return text;
    }
}
