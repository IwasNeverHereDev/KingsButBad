package kingsbutbad.kingsbutbad.commands.Builders;

import kingsbutbad.kingsbutbad.Kingdom.Kingdom;
import kingsbutbad.kingsbutbad.Kingdom.KingdomsReader;
import kingsbutbad.kingsbutbad.utils.CreateText;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

public class MapsCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        Inventory inv = Bukkit.createInventory(null, 9*3, CreateText.addColors("<yellow>Builder Menu"));
        for(Kingdom kingdom : KingdomsReader.kingdomsList){
            ItemStack itemStack = new ItemStack(Material.valueOf(kingdom.getMaterial()));
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.setUnbreakable(true);
            itemMeta.setDisplayName(CreateText.addColors(kingdom.getDisplayName()));
            itemStack.setItemMeta(itemMeta);
            inv.addItem(itemStack);
        }
        if(commandSender instanceof Player p){
            p.openInventory(inv);
        }
        return false;
    }
}
