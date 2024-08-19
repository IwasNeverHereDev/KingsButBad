package kingsbutbad.kingsbutbad.keys;

import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataHolder;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.Nullable;

public record Key<Z>(String name, NamespacedKey key, KeyType<Z> type) {
    public @Nullable Z get(PersistentDataHolder player) {
        var pdc = player.getPersistentDataContainer();
        return pdc.get(key, type.dataType());
    }

    public Z get(PersistentDataHolder player, Z defaultValue) {
        var value = get(player);

        if (value == null)
            return defaultValue;
        return value;
    }

    public boolean has(PersistentDataHolder player) {
        var pdc = player.getPersistentDataContainer();
        return pdc.has(key);
    }

    public void set(PersistentDataHolder player, Z value) {
        var pdc = player.getPersistentDataContainer();
        pdc.set(key, type.dataType(), value);
    }
    public void addInt(PersistentDataHolder player, Z value) {
        var pdc = player.getPersistentDataContainer();
        int currentValue = pdc.getOrDefault(key, PersistentDataType.INTEGER, 0);
        int newValue = currentValue + (Integer) value;
        pdc.set(key, PersistentDataType.INTEGER, newValue);
    }
    public void addDouble(PersistentDataHolder player, Z value) {
        var pdc = player.getPersistentDataContainer();
        double currentValue = pdc.getOrDefault(key, PersistentDataType.DOUBLE, 0.0);
        double newValue = currentValue + (double) value;
        pdc.set(key, PersistentDataType.DOUBLE, newValue);
    }
    public void subtractDouble(PersistentDataHolder player, Z value) {
        var pdc = player.getPersistentDataContainer();
        double currentValue = pdc.getOrDefault(key, PersistentDataType.DOUBLE, 0.0);
        double newValue = currentValue - (double) value;
        pdc.set(key, PersistentDataType.DOUBLE, newValue);
    }

    public void remove(PersistentDataHolder holder) {
        var pdc = holder.getPersistentDataContainer();
        pdc.remove(key);
    }
}
