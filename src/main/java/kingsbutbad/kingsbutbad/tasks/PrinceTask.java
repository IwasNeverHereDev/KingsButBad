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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class PrinceTask extends BukkitRunnable {
    @Override
    public void run() {
            if(KingsButBad.king == null)
                if(KingsButBad.king2 != null){
                    setKing(KingsButBad.king2);
                    KingsButBad.king2 = null;
                }else {
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        if (KingsButBad.lastKing == p || KingsButBad.lastKing2 == p) continue;
                        if (KingsButBad.roles.getOrDefault(p, Role.PEASANT) == Role.PRINCE) {
                            setKing(p);
                            return;
                        }
                    }
                }
            Bukkit.getOnlinePlayers().forEach(this::checkIfKingIsOver2HoursOfTime);
    }
    private void checkIfKingIsOver2HoursOfTime(Player p){
        if(Keys.KINGTicks.get(p, 0.0) >= 20*60*60*2)
            AdvancementManager.giveAdvancement(p, "reignofterror");
    }
    public static void setKing(Player p){
        KingsButBad.thirst.put(p, 300);
        KingsButBad.invitations.clear();
        KingsButBad.king = p;
        KingsButBad.roles.put(p, Role.KING);
        RoleManager.showKingMessages(p, Role.KING.objective);
        RoleManager.givePlayerRole(p);
        KingsButBad.kingGender = "King";
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
