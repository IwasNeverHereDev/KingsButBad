package kingsbutbad.kingsbutbad.listeners;

import kingsbutbad.kingsbutbad.Discord.BotManager;
import kingsbutbad.kingsbutbad.KingsButBad;
import kingsbutbad.kingsbutbad.keys.DatabaseManager;
import kingsbutbad.kingsbutbad.keys.Key;
import kingsbutbad.kingsbutbad.keys.Keys;
import kingsbutbad.kingsbutbad.utils.CreateText;
import kingsbutbad.kingsbutbad.utils.DiscordUtils;
import kingsbutbad.kingsbutbad.utils.TabUtils;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.util.UUID;

public class PlayerQuitListener implements Listener {
    @EventHandler
    @SuppressWarnings("deprecation")
    public void onPlayerQuit(PlayerQuitEvent event) {
        DatabaseManager dbManager = DatabaseManager.loadData(new File(DatabaseManager.getDataFolder(), "playerdata.db").getPath());
        UUID playerId = event.getPlayer().getUniqueId();
        for (Key<?> key : Keys.values()) {
            Object value = key.get(event.getPlayer());
            if (value != null) {
                dbManager.setValue(playerId, key.name(), value);
            }
        }
        dbManager.saveData(new File(DatabaseManager.getDataFolder(), "playerdata.db").getPath());
        event.getPlayer().eject();
        event.getPlayer().getPassengers().clear();
        TabUtils.reload();
        if(KingsButBad.king == event.getPlayer()) {
            KingsButBad.lastKing = event.getPlayer().getUniqueId();
            KingsButBad.cooldown = 20*5;
        }
        if(KingsButBad.king2 == event.getPlayer()) {
            KingsButBad.lastKing2 = event.getPlayer().getUniqueId();
            KingsButBad.cooldown = 20*5;
        }
        for(Entity entity : event.getPlayer().getPassengers())
            event.getPlayer().removePassenger(entity);
        if(event.getPlayer().isInsideVehicle())
            if(event.getPlayer().getVehicle() instanceof Player target) {
                target.removePassenger(event.getPlayer());
                target.setSneaking(true);
                event.getPlayer().eject();
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "warn "+event.getPlayer().getName()+" Logging out while handcuffed! [AUTOMATED]");
            }
        if(!event.getPlayer().getPassengers().isEmpty())
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "warn "+event.getPlayer().getName()+" Logging out while handcuffing someone! [AUTOMATED]");
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
        event.setQuitMessage(
                LegacyComponentSerializer.legacySection()
                        .serialize(MiniMessage.miniMessage().deserialize("<#D49B63>" + event.getPlayer().getName() + " ran away somewhere else..."))
        );
        BotManager.getInGameChatChannel().sendMessage("**" + DiscordUtils.deformat(event.getPlayer().getName()) + "**" + " ran away somewhere else...").queue();
    }
}
