package kingsbutbad.kingsbutbad.listeners;

import kingsbutbad.kingsbutbad.Discord.BotManager;
import kingsbutbad.kingsbutbad.keys.Keys;
import kingsbutbad.kingsbutbad.utils.CreateText;
import kingsbutbad.kingsbutbad.utils.DiscordUtils;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        event.getPlayer().damage(80.0);
        if(Keys.vanish.get(event.getPlayer(), false)) {
            event.setQuitMessage(null);
            for(Player p : Bukkit.getOnlinePlayers()){
                if(!p.hasPermission("kbb.staff")) continue;
                p.sendMessage(CreateText.addColors("<red>(Staff) "+ event.getPlayer().getName()+" has left in vanish!"));
                BotManager.getStafflogChannel().sendMessage("(Staff) "+event.getPlayer().getName()+" has left in vanish!").queue();
            }
            return;
        }
        for(Entity entity : event.getPlayer().getPassengers())
            event.getPlayer().getPassengers().remove(entity);
        event.setQuitMessage(
                LegacyComponentSerializer.legacySection()
                        .serialize(MiniMessage.miniMessage().deserialize("<#D49B63>" + event.getPlayer().getName() + " ran away somewhere else..."))
        );
        BotManager.getInGameChatChannel().sendMessage("**" + DiscordUtils.deformat(event.getPlayer().getName()) + "**" + " ran away somewhere else...").queue();
    }
}
