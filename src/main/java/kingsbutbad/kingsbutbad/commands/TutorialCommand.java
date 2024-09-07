package kingsbutbad.kingsbutbad.commands;

import kingsbutbad.kingsbutbad.utils.CreateText;
import kingsbutbad.kingsbutbad.utils.Item;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class TutorialCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        Inventory inv = Bukkit.createInventory(null, 9*6, CreateText.addColors("<gold>Tutorial GUI"));
        if(!(commandSender instanceof Player p)) return true;
        commandSender.sendMessage("Coming soon...");
        List<String> kingCommandTutorial = new ArrayList<>();
        List<String> JobsTutorial = new ArrayList<>();
        List<String> waterTutorial = new ArrayList<>();
        Item.createItem(Material.GOLDEN_HELMET, "King", kingCommandTutorial, null);
        Item.createItem(Material.WOODEN_HOE, "Jobs", JobsTutorial, null);
        Item.createItem(Material.POTION, "Water", waterTutorial, null);
        return false;
    }
}
