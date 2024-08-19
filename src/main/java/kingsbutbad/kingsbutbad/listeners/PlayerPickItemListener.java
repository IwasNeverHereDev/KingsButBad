package kingsbutbad.kingsbutbad.listeners;

import kingsbutbad.kingsbutbad.keys.Keys;
import kingsbutbad.kingsbutbad.utils.CreateText;
import io.papermc.paper.event.player.PlayerPickItemEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerPickItemListener implements Listener {
    @EventHandler
    public void PlayerPickItemEvent(PlayerPickItemEvent event){
        if(Keys.vanish.get(event.getPlayer(), false)) {
            event.getPlayer().sendMessage(CreateText.addColors("<gray>You can't pickup items in Vanish!"));
            event.setCancelled(true);
            return;
        }
    }
}
