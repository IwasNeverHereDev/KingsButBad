package kingsbutbad.kingsbutbad.keys;

import org.bukkit.persistence.PersistentDataType;

public interface KeyTypes {
    KeyType<String> STRING = new KeyType<>(PersistentDataType.STRING, KeyTypes::parseString);
    KeyType<Integer> INTEGER = new KeyType<>(PersistentDataType.INTEGER, KeyTypes::parseInteger);
    KeyType<Double> DOUBLE = new KeyType<>(PersistentDataType.DOUBLE, KeyTypes::parseDouble);
    KeyType<Boolean> BOOLEAN = new KeyType<>(PersistentDataType.BOOLEAN, KeyTypes::parseBoolean);

    static Double parseDouble(String input) {
        try {
            return Double.parseDouble(input);
        } catch (NumberFormatException exception) {
            return null;
        }
    }
    static Boolean parseBoolean(String input) {
        try {
            return Boolean.parseBoolean(input);
        } catch (NumberFormatException exception) {
            return null;
        }
    }

    static Integer parseInteger(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException exception) {
            return null;
        }
    }

    static String parseString(String input) {
        return input;
    }
}
