package agmas.kingsbutbad.keys;

import agmas.kingsbutbad.KingsButBad;
import org.bukkit.NamespacedKey;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

public interface Keys {
    Key<Boolean> inPrison = new Key<>("inPrison", new NamespacedKey(KingsButBad.pl, "inPrison"), KeyTypes.BOOLEAN);
    Key<Double> money = new Key<>("money", new NamespacedKey(KingsButBad.pl, "money"), KeyTypes.DOUBLE);
    Key<String> link = new Key<>("link", new NamespacedKey(KingsButBad.pl, "link"), KeyTypes.STRING);
    // settings
    Key<Boolean> isAutoShoutEnabled = new Key<>("isAutoShoutEnabled", new NamespacedKey(KingsButBad.pl, "isAutoShoutEnabled"), KeyTypes.BOOLEAN);
    Key<Boolean> selectedChat = new Key<>("selectedChat", new NamespacedKey(KingsButBad.pl, "selectedChat"), KeyTypes.BOOLEAN);
    // Builders
    Key<Boolean> isBuilderMode = new Key<>("builder", new NamespacedKey(KingsButBad.pl, "builder"), KeyTypes.BOOLEAN);
    // Staff
    Key<Boolean> vanish = new Key<>("vanish", new NamespacedKey(KingsButBad.pl, "vanish"), KeyTypes.BOOLEAN);
    static @Nullable Key<?> valueOf(String name) {
        try {
            var field = Keys.class.getDeclaredField(name);
            return (Key<?>) field.get(null);
        } catch (ReflectiveOperationException e) {
            return null;
        }
    }

    static Key<?>[] values() {
        return Arrays.stream(Keys.class.getDeclaredFields())
                .filter((f) -> f.getType().equals(Key.class))
                .map((f) -> {
                    try {
                        return (Key<?>) f.get(null);
                    } catch (ReflectiveOperationException exception) {
                        KingsButBad.pl.getLogger().severe(String.format(
                                "An error occurred fetching all keys.\n%s",
                                exception.getMessage()
                        ));
                        return null;
                    }
                })
                .toArray(Key[]::new);
    }
}
