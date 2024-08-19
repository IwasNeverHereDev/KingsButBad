package agmas.kingsbutbad.listeners;

import java.util.Random;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerFishEvent.State;
import org.bukkit.inventory.ItemStack;

public class PlayerFishListener implements Listener {
   @EventHandler
   public void onPlayerFish(PlayerFishEvent event) {
      if (event.getState().equals(State.CAUGHT_FISH)) {
         event.getCaught().remove();
         Player player = event.getPlayer();
         if (!player.hasCooldown(Material.FISHING_ROD)) {
            player.getInventory().addItem(new ItemStack[]{new ItemStack(Material.SALMON)});
            if (player.getItemInHand().getEnchantments().containsKey(Enchantment.LOOT_BONUS_BLOCKS)) {
               player.getInventory().addItem(new ItemStack[]{new ItemStack(Material.SALMON, new Random().nextInt(0, 3))});
            }
         }
      }
   }
}
