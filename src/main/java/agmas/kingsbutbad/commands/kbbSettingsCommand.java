package agmas.kingsbutbad.commands;

import agmas.kingsbutbad.keys.Keys;
import agmas.kingsbutbad.utils.CreateText;
import agmas.kingsbutbad.utils.Item;
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
        Inventory inv = Bukkit.createInventory(null, 9*3, CreateText.addColors("<gold>KingsButBad Settings"));
        boolean isAutoShoutEnabled = Keys.isAutoShoutEnabled.get(p, true);
        List<String> loreShout = new ArrayList<>();
        loreShout.add(isEnabled(isAutoShoutEnabled));
        ItemStack comingSoon = clearData(Item.createItem(Material.BARRIER, "<red>Coming soon", new ArrayList<>(), null));
        ItemStack shoutItem = clearData(Item.createItem(Material.BLACK_CANDLE, "<yellow>Auto Shout", loreShout, null));
        ItemStack chatItem = clearData(Item.createItem(Material.PAPER, "<yellow>Chat Selected", getChatSelectedLore(p), null));
        inv.setItem(10,shoutItem);
        inv .setItem(13, chatItem);
        inv.setItem(16, comingSoon);
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
    private String isEnabled(boolean isEnabled){
        if(!isEnabled) return "\n<gray>Click to <green>Enable <gray>Auto Shout!\n";
        return "\n<gray>Click to <red>Disable <gray>Auto Shout!\n";
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
