package kingsbutbad.kingsbutbad.utils;

public class DiscordUtils {
    public static String deformat(String text) {
        text = text.replaceAll("_", "\\\\_");  // Escapes underscores
        text = text.replaceAll("@", "\\\\@");  // Escapes @ symbols to prevent mentions
        return text;
    }
}
