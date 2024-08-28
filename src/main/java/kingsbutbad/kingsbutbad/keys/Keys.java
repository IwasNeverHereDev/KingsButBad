package kingsbutbad.kingsbutbad.keys;

import kingsbutbad.kingsbutbad.KingsButBad;
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
    Key<Boolean> displayRoleStats = new Key<>("displayRoleStats", new NamespacedKey(KingsButBad.pl, "displayRoleStats"), KeyTypes.BOOLEAN);
    Key<Boolean> showMineMessages = new Key<>("showMineMessages", new NamespacedKey(KingsButBad.pl, "showMineMessages"), KeyTypes.BOOLEAN);
    // Builders
    Key<Boolean> isBuilderMode = new Key<>("builder", new NamespacedKey(KingsButBad.pl, "builder"), KeyTypes.BOOLEAN);
    // Staff
    Key<Boolean> vanish = new Key<>("vanish", new NamespacedKey(KingsButBad.pl, "vanish"), KeyTypes.BOOLEAN);
    // Stats
    Key<Double> PEASANTTicks = new Key<>("PEASANTTicks", new NamespacedKey(KingsButBad.pl, "PEASANTicks"), KeyTypes.DOUBLE);
    Key<Double> PRISONERTicks = new Key<>("PRISONERTicks", new NamespacedKey(KingsButBad.pl, "PRISONERicks"), KeyTypes.DOUBLE);
    Key<Double> SERVANTTicks = new Key<>("SERVANTTicks", new NamespacedKey(KingsButBad.pl, "SERVANTTicks"), KeyTypes.DOUBLE);
    Key<Double> CRIMINALTicks = new Key<>("CRIMINALTicks", new NamespacedKey(KingsButBad.pl, "CRIMINALTicks"), KeyTypes.DOUBLE);
    Key<Double> OUTLAWTicks = new Key<>("OUTLAWTicks", new NamespacedKey(KingsButBad.pl, "OUTLAWTicks"), KeyTypes.DOUBLE);
    Key<Double> KNIGHTTicks = new Key<>("KNIGHTTicks", new NamespacedKey(KingsButBad.pl, "KNIGHTTicks"), KeyTypes.DOUBLE);
    Key<Double> BODYGUARDTicks = new Key<>("BODYGUARDTicks", new NamespacedKey(KingsButBad.pl, "BODYGUARDTicks"), KeyTypes.DOUBLE);
    Key<Double> PRISON_GUARDTicks = new Key<>("PRISON_GUARDTicks", new NamespacedKey(KingsButBad.pl, "PRISON_GUARDTicks"), KeyTypes.DOUBLE);
    Key<Double> KINGTicks = new Key<>("KINGTicks", new NamespacedKey(KingsButBad.pl, "KINGTicks"), KeyTypes.DOUBLE);
    Key<Double> PRINCETicks = new Key<>("PRINCETicks", new NamespacedKey(KingsButBad.pl, "PRINCETicks"), KeyTypes.DOUBLE);
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
