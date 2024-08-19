package agmas.kingsbutbad.listeners;

import agmas.kingsbutbad.keys.Keys;
import agmas.kingsbutbad.utils.CreateText;
import io.papermc.paper.event.player.PlayerPickItemEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupArrowEvent;

public class PlayerPickArrowListener implements Listener {
    @EventHandler
    public void PlayerPickArrowEvent(PlayerPickupArrowEvent event){
        if(Keys.vanish.get(event.getPlayer(), false)) {
            event.getPlayer().sendMessage(CreateText.addColors("<gray>You can't pickup arrows in Vanish!"));
            event.setCancelled(true);
            return;
        }
    }
}
