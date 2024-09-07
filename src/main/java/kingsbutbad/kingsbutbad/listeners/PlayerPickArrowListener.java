package kingsbutbad.kingsbutbad.listeners;

import kingsbutbad.kingsbutbad.keys.Keys;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupArrowEvent;

public class PlayerPickArrowListener implements Listener {
    @EventHandler
    public void PlayerPickArrowEvent(PlayerPickupArrowEvent event){
        if(Keys.vanish.get(event.getPlayer(), false))
            event.setCancelled(true);
    }
}
