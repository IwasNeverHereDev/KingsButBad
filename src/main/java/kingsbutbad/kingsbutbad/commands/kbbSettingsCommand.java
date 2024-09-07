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
    @SuppressWarnings("deprecation")
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(!(commandSender instanceof Player p)) return true;
        Inventory inv = Bukkit.createInventory(null, 9*6, CreateText.addColors("<gold>KingsButBad Settings"));
        boolean isAutoShoutEnabled = Keys.isAutoShoutEnabled.get(p, true);
        boolean isRolePlayerTimeDisplayed = Keys.displayRoleStats.get(p, false);
        List<String> loreShout = new ArrayList<>();
        loreShout.add(isEnabled(isAutoShoutEnabled, "Auto Shout"));
        List<String> loreRolePlaytime = new ArrayList<>();
        List<String> loreShowMineMessages = new ArrayList<>();
        loreRolePlaytime.add(isEnabled(isRolePlayerTimeDisplayed, "Lore Playtime Displayed"));
        ItemStack displayPlaytimeItem = clearData(Item.createItem(Material.CLOCK, "<yellow>Display Role Playtime", loreRolePlaytime, null));
        ItemStack showMineMessagesItem = clearData(Item.createItem(Material.COAL, "<yellow>Show Mine Messages", loreShowMineMessages, null));
        //ItemStack comingSoonItem = clearData(Item.createItem(Material.BARRIER, "<red>Coming Soon!", new ArrayList<>(), null));
        ItemStack shoutItem = clearData(Item.createItem(Material.BLACK_CANDLE, "<yellow>Auto Shout", loreShout, null));
        ItemStack chatItem = clearData(Item.createItem(Material.PAPER, "<yellow>Chat Selected", getChatSelectedLore(p), null));
        ItemStack shoutShortItem = clearData(Item.createItem(Material.NAME_TAG, "<yellow>Shorten Shout Msg", new ArrayList<>(), null));
        ItemStack pingItem = clearData(Item.createItem(Material.GOLD_INGOT, "<yellow>Ping On Mentioned", new ArrayList<>(), null));
        ItemStack shortRoleItem = clearData(Item.createItem(Material.BOOK, "<yellow>Shorten Roles", new ArrayList<>(), null));
        ItemStack shortPrefixItem = clearData(Item.createItem(Material.WRITABLE_BOOK, "<yellow>Shorten Prefix", new ArrayList<>(), null));
        inv.setItem(10,shoutItem);
        inv .setItem(12, chatItem);
        inv.setItem(14, displayPlaytimeItem);
        inv.setItem(16, showMineMessagesItem);
        inv.setItem(37, shoutShortItem);
        inv.setItem(39, pingItem);
        inv.setItem(41, shortRoleItem);
        inv.setItem(43, shortPrefixItem);
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
