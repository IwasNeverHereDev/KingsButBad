package kingsbutbad.kingsbutbad.Loaders;

import kingsbutbad.kingsbutbad.KingsButBad;
import org.bukkit.Bukkit;

public class RemoveAdvancements {
    public static void init(){
        Bukkit.getScheduler().scheduleSyncDelayedTask(KingsButBad.pl, () -> {
            Bukkit.advancementIterator().forEachRemaining(advancement -> {
                if (advancement.getKey().getNamespace().equals("minecraft"))
                    Bukkit.getUnsafe().removeAdvancement(advancement.getKey());
            });
        }, 20*5);
    }
}
