package kingsbutbad.kingsbutbad.tasks;

import kingsbutbad.kingsbutbad.Advancements.AdvancementManager;
import kingsbutbad.kingsbutbad.KingsButBad;
import kingsbutbad.kingsbutbad.keys.Keys;
import kingsbutbad.kingsbutbad.utils.CreateText;
import kingsbutbad.kingsbutbad.utils.Role;
import kingsbutbad.kingsbutbad.utils.RoleManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class PrinceTask extends BukkitRunnable {
    @Override
    public void run() {
            if(KingsButBad.king == null)
                if(KingsButBad.king2 != null){
                    setKing(KingsButBad.king2);
                    KingsButBad.king2 = null;
                }else {
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        if (KingsButBad.lastKing == p.getUniqueId() || KingsButBad.lastKing2 == p.getUniqueId()) continue;
                        if (KingsButBad.roles.getOrDefault(p, Role.PEASANT) == Role.PRINCE) {
                            setKing(p);
                            return;
                        }
                    }
                }
            Bukkit.getOnlinePlayers().forEach(p -> {
                checkIfIsPrince(p);
                checkIfKingIsOver2HoursOfTime(p);
                checkIfIsSideKick(p);
            });
    }
    private void checkIfIsPrince(Player p){
        if(KingsButBad.roles.getOrDefault(p, Role.PEASANT) != Role.PRINCE) return;
        AdvancementManager.giveAdvancement(p, "soonkingdom");
    }
    private void checkIfIsSideKick(Player p){
        if(KingsButBad.roles.getOrDefault(p, Role.PEASANT) != Role.KING) return;
        if(KingsButBad.king2 == p)
            AdvancementManager.giveAdvancement(p, "ourkingdom");
    }
    private void checkIfKingIsOver2HoursOfTime(Player p){
        if(Keys.KINGTicks.get(p, 0.0) >= 20*60*60*2)
            AdvancementManager.giveAdvancement(p, "reignofterror");
    }
    public static void setKing(Player p){
        KingsButBad.thirst.put(p, 300F);
        KingsButBad.invitations.clear();
        KingsButBad.king = p;
        KingsButBad.roles.put(p, Role.KING);
        RoleManager.showKingMessages(p, Role.KING.objective);
        RoleManager.givePlayerRole(p);
        KingsButBad.kingPrefix = "King";
        KingsButBad.taxesPerctage = 0;
        AdvancementManager.giveAdvancement(p, "king");

        for (Player pe : Bukkit.getOnlinePlayers()) {
            if(pe.isInsideVehicle())
                pe.getVehicle().removePassenger(pe);
            if(pe == KingsButBad.king) continue;
            Role role = KingsButBad.roles.getOrDefault(pe, Role.PEASANT);
            if(role.isPowerful){
                KingsButBad.roles.put(pe, Role.PEASANT);
                RoleManager.givePlayerRole(pe);
            }
            pe.sendTitle(
                    CreateText.addColors("<gradient:#FFFF52:#FFBA52><b>KING " + p.getName().toUpperCase()), ChatColor.GREEN + "is your new overlord!"
            );
        }
    }
}
