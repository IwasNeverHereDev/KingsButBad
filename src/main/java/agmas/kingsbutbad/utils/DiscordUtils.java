package agmas.kingsbutbad.utils;

public class DiscordUtils {
    public static String deformat(String text) {
        text = text.replaceAll("_", "`_`");
        text = text.replaceAll("@", "`@`");
        return text;
    }
}
