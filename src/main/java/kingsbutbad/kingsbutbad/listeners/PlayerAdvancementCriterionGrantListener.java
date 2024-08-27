package kingsbutbad.kingsbutbad.listeners;

import com.destroystokyo.paper.event.player.PlayerAdvancementCriterionGrantEvent;
import kingsbutbad.kingsbutbad.Discord.BotManager;
import kingsbutbad.kingsbutbad.utils.DiscordUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerAdvancementCriterionGrantListener implements Listener {
    @EventHandler
    public void PlayerAdvancementCriterionGrantEvent(PlayerAdvancementCriterionGrantEvent event){
        BotManager.getInGameChatChannel().sendMessage(DiscordUtils.deformat(event.getPlayer().getName()) + " has made the Advancement " + event.getAdvancement().getDisplay().displayName()).queue();
    }
}
