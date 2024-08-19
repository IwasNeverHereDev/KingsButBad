package kingsbutbad.kingsbutbad.listeners;

import kingsbutbad.kingsbutbad.KingsButBad;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class PlayerDropListener implements Listener {
    @EventHandler
    public void cancelDrop(PlayerDropItemEvent event) {
        if (KingsButBad.roles.get(event.getPlayer()).isPowerful) {
            event.getItemDrop().remove();
        }
    }
}
