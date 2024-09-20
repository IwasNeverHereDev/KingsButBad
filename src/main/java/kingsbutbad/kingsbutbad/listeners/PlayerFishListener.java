package kingsbutbad.kingsbutbad.listeners;

import kingsbutbad.kingsbutbad.Advancements.AdvancementManager;
import kingsbutbad.kingsbutbad.utils.Item;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerFishEvent.State;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class PlayerFishListener implements Listener {
   @EventHandler
   @SuppressWarnings("deprecation")
   public void onPlayerFish(PlayerFishEvent event) {
      if (event.getState().equals(State.CAUGHT_FISH)) {
         if(event.getCaught() != null)
            event.getCaught().remove();
         Player player = event.getPlayer();
         if (!player.hasCooldown(Material.FISHING_ROD)) {
            Map<Enchantment, Integer> enchantments = new HashMap<>();
            enchantments.put(Enchantment.KNOCKBACK, 3);
            enchantments.put(Enchantment.VANISHING_CURSE, 1);
            ItemStack changeForSlapFishy = Item.createItem(Material.COD, "Slap The Fish", new ArrayList<>(), enchantments);
            int changeForSlapFish = 100;
            player.getInventory().addItem(new ItemStack(Material.SALMON));
            if (player.getItemInHand().getEnchantments().containsKey(Enchantment.LURE)) {
               player.getInventory().addItem(new ItemStack(Material.SALMON, new Random().nextInt(0, 3)));
               changeForSlapFish = 50;
            }
            if(new Random().nextInt(0, changeForSlapFish) == 0) {
               player.getInventory().addItem(changeForSlapFishy);
               AdvancementManager.giveAdvancement(player, "slap");
            }
         }
      }
   }
}
