package kingsbutbad.kingsbutbad.listeners;

import kingsbutbad.kingsbutbad.KingsButBad;
import kingsbutbad.kingsbutbad.utils.Role;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMoveListener implements Listener {
    @EventHandler
    public void cancelDrop(PlayerMoveEvent event) {
        if (KingsButBad.roles.get(event.getPlayer()).equals(Role.PRISONER) && event.hasChangedBlock())
            event.getPlayer().getWorld().playSound(event.getPlayer(), Sound.ENTITY_IRON_GOLEM_STEP, 1.0F, 0.75F);
    }
}
