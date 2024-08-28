package kingsbutbad.kingsbutbad.commands;

import kingsbutbad.kingsbutbad.keys.Keys;
import kingsbutbad.kingsbutbad.utils.CreateText;
import kingsbutbad.kingsbutbad.utils.Item;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class kbbSettingsCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(!(commandSender instanceof Player p)) return true;
        Inventory inv = Bukkit.createInventory(null, 9*6, CreateText.addColors("<gold>KingsButBad Settings"));
        boolean isAutoShoutEnabled = Keys.isAutoShoutEnabled.get(p, true);
        boolean isRolePlayerTimeDisplayed = Keys.displayRoleStats.get(p, false);
        List<String> loreShout = new ArrayList<>();
        loreShout.add(isEnabled(isAutoShoutEnabled, "Auto Shout"));
        List<String> loreRolePlaytime = new ArrayList<>();
        List<String> loreshowMineMessages = new ArrayList<>();
        loreRolePlaytime.add(isEnabled(isRolePlayerTimeDisplayed, "Auto Shout"));
        ItemStack displayPlaytimeItem = clearData(Item.createItem(Material.CLOCK, "<yellow>Display Role Playtime", loreRolePlaytime, null));
        ItemStack showMineMessagesItem = clearData(Item.createItem(Material.COAL, "<yellow>Show Mine Messages", loreshowMineMessages, null));
        ItemStack comingSoonItem = clearData(Item.createItem(Material.BARRIER, "<red>Coming Soon!", new ArrayList<>(), null));
        ItemStack shoutItem = clearData(Item.createItem(Material.BLACK_CANDLE, "<yellow>Auto Shout", loreShout, null));
        ItemStack chatItem = clearData(Item.createItem(Material.PAPER, "<yellow>Chat Selected", getChatSelectedLore(p), null));
        inv.setItem(10,shoutItem);
        inv .setItem(13, chatItem);
        inv.setItem(16, displayPlaytimeItem);
        inv.setItem(37, showMineMessagesItem);
        inv.setItem(40, comingSoonItem);
        inv.setItem(43, comingSoonItem);
        p.openInventory(inv);
        return false;
    }
    private ItemStack clearData(ItemStack itemStack){
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setUnbreakable(true);
        itemMeta.addItemFlags(ItemFlag.values());
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
    private String isEnabled(boolean isEnabled, String setting){
        if(!isEnabled) return "\n<gray>Click to <green>Enable <gray>"+setting+"!\n";
        return "\n<gray>Click to <red>Disable <gray>"+setting+"!\n";
    }
    private List<String> getChatSelectedLore(Player p){
        List<String> lore = new ArrayList<>();
        lore.add("");
        if(Keys.selectedChat.get(p, false))
            lore.add("<gray>Click to Select <white>Builder Chat <gray>Shortcut!");
        else
            lore.add("<gray>Click to Select <white>Staff Chat <gray>Shortcut!");
        lore.add("");
        return lore;
    }
}
