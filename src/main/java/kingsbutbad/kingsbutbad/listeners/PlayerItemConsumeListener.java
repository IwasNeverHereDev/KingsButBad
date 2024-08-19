package kingsbutbad.kingsbutbad.listeners;

import kingsbutbad.kingsbutbad.KingsButBad;
import kingsbutbad.kingsbutbad.utils.CreateText;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;

public class PlayerItemConsumeListener implements Listener {
   @EventHandler
   public void onPlayerConsume(PlayerItemConsumeEvent event) {
      Player player = event.getPlayer();
      Material itemType = event.getItem().getType();
      switch (itemType) {
         case POTION:
            KingsButBad.thirst.put(player, KingsButBad.thirst.get(player) + 100);
            break;
         case COOKED_COD:
            player.sendMessage(CreateText.addColors("<gray><b>|<green><b> +5 HP<gray><b> |"));
            player.setHealth(Math.min(player.getHealth() + 5.0, player.getMaxHealth()));
            break;
         case GOLDEN_APPLE:
            player.sendMessage(CreateText.addColors("<gray><b>|<green><b> +15 HP<gray><b> |"));
            player.setHealth(Math.min(player.getHealth() + 15.0, player.getMaxHealth()));
            break;
         case BEETROOT_SOUP:
            player.sendMessage(CreateText.addColors("<gray><b>|<green><b> +2 HP<gray><b> |"));
            player.setHealth(Math.min(player.getHealth() + 2.0, player.getMaxHealth()));
      }
   }
}
