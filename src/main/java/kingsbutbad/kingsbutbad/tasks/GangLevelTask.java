package kingsbutbad.kingsbutbad.tasks;

import kingsbutbad.kingsbutbad.commands.Clans.ClansDB;
import kingsbutbad.kingsbutbad.commands.Clans.Clans;
import kingsbutbad.kingsbutbad.keys.DatabaseManager;
import kingsbutbad.kingsbutbad.keys.Keys;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;

public class GangLevelTask extends BukkitRunnable {
    ClansDB databaseManager = ClansDB.loadData(new File(ClansDB.getDataFolder(), "gangs.gz").getPath());
    @Override
    public void run() {
        for(String gangNames : databaseManager.getGangs().keySet()){
            Clans gangs = databaseManager.getGangs().get(gangNames);
            if(gangs.getExp() >= gangs.getLevel() * 115){
                gangs.setLevel(gangs.getLevel()+1);
                gangs.setExp(gangs.getExp() - (gangs.getLevel() * 115));
                gangs.broadcast("Clan's Level is now "+gangs.getLevel());
            }
        }
    }
}
