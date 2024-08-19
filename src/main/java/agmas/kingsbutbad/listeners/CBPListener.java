package agmas.kingsbutbad.listeners;

import agmas.kingsbutbad.utils.CBP.CBPUtils;
import me.coralise.spigot.API.events.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class CBPListener implements Listener {
    @EventHandler
    public void onBan(PostBanEvent event) {
        CBPUtils.getBanEmbed(event.getPlayerName(), event.getStaffName(), event.getReason(), event.getDuration(), System.currentTimeMillis());
    }

    @EventHandler
    public void onMute(PostMuteEvent event) {
        CBPUtils.getMuteEmbed(event.getPlayerName(), event.getStaffName(), event.getReason(), event.getDuration(), System.currentTimeMillis());
    }
    @EventHandler
    public void onKick(PostKickEvent event){
        CBPUtils.getKickEmbed(event.getPlayerName(), event.getStaffName(), event.getReason());
    }
    @EventHandler
    public void onWarn(PostWarnEvent event){
        CBPUtils.getWarnEmbed(event.getPlayerName(), event.getStaffName(), event.getReason());
    }
    /*@EventHandler
    public void onReport(ReportEvent event){
        CBPUtils.getReportEmbed(event.getPlayerName(), event.getReport(), event.getReporter());
    }*/

    @EventHandler
    public void onUnban(UnbanEvent event) {
        CBPUtils.getUnBanEmbed(event.getPlayerName(), event.getStaffName());
    }

    @EventHandler
    public void onUnmute(UnmuteEvent event) {
        CBPUtils.getUnmuteEmbed(event.getPlayerName(), event.getStaffName());
    }
}
