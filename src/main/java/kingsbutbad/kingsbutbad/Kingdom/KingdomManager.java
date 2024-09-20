package kingsbutbad.kingsbutbad.Kingdom;

import kingsbutbad.kingsbutbad.Kingdom.Upgrades.Upgrade;
import kingsbutbad.kingsbutbad.keys.Keys;
import kingsbutbad.kingsbutbad.utils.CreateText;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class KingdomManager {
    public static HashMap<Upgrade, Boolean> upgrades = new HashMap<>();
    public void upgrade(Player p, Upgrade upgrade){
        if(upgrades.containsKey(upgrade)){
            p.sendMessage(CreateText.addColors("<red>Sorry, This is already upgraded! <gray>(<white>"+upgrade.getDisplayName()+"<gray>)"));
            return;
        }

        if(Keys.money.get(p, 0.0) >= upgrade.getCost()) {
            Keys.money.subtractDouble(p, upgrade.getCost());
            upgrades.put(upgrade, true);
            p.sendMessage(CreateText.addColors("<green>You have upgraded "+upgrade.getDisplayName()+"<green>!"));
            try {
                runFunctionCommands(readFunctionFile(new File(upgrade.getPathOfUpgrade()+"")));
            } catch (IOException e) {
                p.sendMessage(CreateText.addColors("<red>Pls check Console for the error!"));
                Bukkit.getLogger().severe(e.getMessage());
            }
        }else{
            p.sendMessage(CreateText.addColors("<red>Sorry, couldn't upgrade this due to not enough funding! <gray>(<white>"+upgrade.getDisplayName()+"<gray>)"));
        }
    }
    public void runFunctionCommands(List<String> commands) {
        for (String command : commands)
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
    }

    public List<String> readFunctionFile(File file) throws IOException {
        List<String> commands = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null)
                if (!line.trim().startsWith("#"))
                    commands.add(line.trim());
        }
        return commands;
    }
}
