package kingsbutbad.kingsbutbad.keys;

import kingsbutbad.kingsbutbad.KingsButBad;
import org.bukkit.NamespacedKey;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

public interface Keys {
    Key<Boolean> inPrison = new Key<>("inPrison", new NamespacedKey(KingsButBad.pl, "inPrison"), KeyTypes.BOOLEAN);
    Key<Double> money = new Key<>("money", new NamespacedKey(KingsButBad.pl, "money"), KeyTypes.DOUBLE);
    Key<String> link = new Key<>("link", new NamespacedKey(KingsButBad.pl, "link"), KeyTypes.STRING);
    Key<Boolean> isAutoShoutEnabled = new Key<>("isAutoShoutEnabled", new NamespacedKey(KingsButBad.pl, "isAutoShoutEnabled"), KeyTypes.BOOLEAN);
    Key<Boolean> selectedChat = new Key<>("selectedChat", new NamespacedKey(KingsButBad.pl, "selectedChat"), KeyTypes.BOOLEAN);
    Key<Boolean> displayRoleStats = new Key<>("displayRoleStats", new NamespacedKey(KingsButBad.pl, "displayRoleStats"), KeyTypes.BOOLEAN);
    Key<Boolean> showMineMessages = new Key<>("showMineMessages", new NamespacedKey(KingsButBad.pl, "showMineMessages"), KeyTypes.BOOLEAN);
    Key<Boolean> PING_NOISES = new Key<>("PING_NOISES", new NamespacedKey(KingsButBad.pl, "PING_NOISES"), KeyTypes.BOOLEAN);
    Key<Boolean> SHORTEN_SHOUTMSG = new Key<>("SHORTEN_SHOUTMSG", new NamespacedKey(KingsButBad.pl, "SHORTEN_SHOUTMSG"), KeyTypes.BOOLEAN);
    Key<Boolean> SHORTEN_ROLES_MSG = new Key<>("SHORTEN_ROLES_MSG", new NamespacedKey(KingsButBad.pl, "SHORTEN_ROLES_MSG"), KeyTypes.BOOLEAN);
    Key<Boolean> SHORTEN_RANKS_MSG = new Key<>("SHORTEN_RANKS_MSG", new NamespacedKey(KingsButBad.pl, "SHORTEN_RANKS_MSG"), KeyTypes.BOOLEAN);
    Key<Boolean> isBuilderMode = new Key<>("builder", new NamespacedKey(KingsButBad.pl, "builder"), KeyTypes.BOOLEAN);
    Key<Boolean> vanish = new Key<>("vanish", new NamespacedKey(KingsButBad.pl, "vanish"), KeyTypes.BOOLEAN);
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
    Key<Double> PEASANTkills = new Key<>("PEASANTkills", new NamespacedKey(KingsButBad.pl, "PEASANkills"), KeyTypes.DOUBLE);
    Key<Double> PRISONERkills = new Key<>("PRISONERkills", new NamespacedKey(KingsButBad.pl, "PRISONERicks"), KeyTypes.DOUBLE);
    Key<Double> SERVANTkills = new Key<>("SERVANTkills", new NamespacedKey(KingsButBad.pl, "SERVANTkills"), KeyTypes.DOUBLE);
    Key<Double> CRIMINALkills = new Key<>("CRIMINALkills", new NamespacedKey(KingsButBad.pl, "CRIMINALkills"), KeyTypes.DOUBLE);
    Key<Double> OUTLAWkills = new Key<>("OUTLAWkills", new NamespacedKey(KingsButBad.pl, "OUTLAWkills"), KeyTypes.DOUBLE);
    Key<Double> KNIGHTkills = new Key<>("KNIGHTkills", new NamespacedKey(KingsButBad.pl, "KNIGHTkills"), KeyTypes.DOUBLE);
    Key<Double> BODYGUARDkills = new Key<>("BODYGUARDkills", new NamespacedKey(KingsButBad.pl, "BODYGUARDkills"), KeyTypes.DOUBLE);
    Key<Double> PRISON_GUARDkills = new Key<>("PRISON_GUARDkills", new NamespacedKey(KingsButBad.pl, "PRISON_GUARDkills"), KeyTypes.DOUBLE);
    Key<Double> KINGkills = new Key<>("KINGkills", new NamespacedKey(KingsButBad.pl, "KINGkills"), KeyTypes.DOUBLE);
    Key<Double> PRINCEkills = new Key<>("PRINCEkills", new NamespacedKey(KingsButBad.pl, "PRINCEkills"), KeyTypes.DOUBLE);
    Key<Double> PEASANTDeaths = new Key<>("PEASANTDeaths", new NamespacedKey(KingsButBad.pl, "PEASANDeaths"), KeyTypes.DOUBLE);
    Key<Double> PRISONERDeaths = new Key<>("PRISONERDeaths", new NamespacedKey(KingsButBad.pl, "PRISONERicks"), KeyTypes.DOUBLE);
    Key<Double> SERVANTDeaths = new Key<>("SERVANTDeaths", new NamespacedKey(KingsButBad.pl, "SERVANTDeaths"), KeyTypes.DOUBLE);
    Key<Double> CRIMINALDeaths = new Key<>("CRIMINALDeaths", new NamespacedKey(KingsButBad.pl, "CRIMINALDeaths"), KeyTypes.DOUBLE);
    Key<Double> OUTLAWDeaths = new Key<>("OUTLAWDeaths", new NamespacedKey(KingsButBad.pl, "OUTLAWDeaths"), KeyTypes.DOUBLE);
    Key<Double> KNIGHTDeaths = new Key<>("KNIGHTDeaths", new NamespacedKey(KingsButBad.pl, "KNIGHTDeaths"), KeyTypes.DOUBLE);
    Key<Double> BODYGUARDDeaths = new Key<>("BODYGUARDDeaths", new NamespacedKey(KingsButBad.pl, "BODYGUARDDeaths"), KeyTypes.DOUBLE);
    Key<Double> PRISON_GUARDDeaths = new Key<>("PRISON_GUARDDeaths", new NamespacedKey(KingsButBad.pl, "PRISON_GUARDDeaths"), KeyTypes.DOUBLE);
    Key<Double> KINGDeaths = new Key<>("KINGDeaths", new NamespacedKey(KingsButBad.pl, "KINGDeaths"), KeyTypes.DOUBLE);
    Key<Double> PRINCEDeaths = new Key<>("PRINCEDeaths", new NamespacedKey(KingsButBad.pl, "PRINCEDeaths"), KeyTypes.DOUBLE);
    Key<String> activePact = new Key<>("activePact", new NamespacedKey(KingsButBad.pl, "activePact"), KeyTypes.STRING);
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
