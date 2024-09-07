package kingsbutbad.kingsbutbad.Advancements;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.advancement.Advancement;
import org.bukkit.entity.Player;
import org.bukkit.advancement.AdvancementProgress;

public class AdvancementManager {
    public static final String NAMESPACE_KBB = "kbb";

    public static void giveAdvancement(Player player, String advancementKey) {
        NamespacedKey key = new NamespacedKey(NAMESPACE_KBB, advancementKey);
        Advancement advancement = Bukkit.getAdvancement(key);

        if (advancement == null) {
            Bukkit.getLogger().warning("Advancement " + key + " does not exist.");
            return;
        }

        AdvancementProgress progress = player.getAdvancementProgress(advancement);

        if (progress.isDone())
            return;

        for (String criterion : progress.getRemainingCriteria())
            progress.awardCriteria(criterion);

        Bukkit.getLogger().info("Granted advancement " + key + " to player " + player.getName());
    }
}
