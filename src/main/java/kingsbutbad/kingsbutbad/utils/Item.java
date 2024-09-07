package kingsbutbad.kingsbutbad.utils;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SuppressWarnings("deprecation")
public class Item {

    public static ItemStack createItem(Material material, String name, List<String> lore, Map<Enchantment, Integer> enchantments) {
        if (material == null || material == Material.AIR) {
            throw new IllegalArgumentException("Material cannot be null or AIR.");
        }

        ItemStack item = new ItemStack(material);
        ItemMeta itemMeta = item.getItemMeta();

        if (itemMeta != null) {
            itemMeta.setUnbreakable(true);

            if (name != null && !name.isEmpty())
                itemMeta.setDisplayName(CreateText.addColors(name));

            if (lore != null && !lore.isEmpty()) {
                List<String> coloredLore = new ArrayList<>();
                for (String line : lore) {
                    coloredLore.add(ChatColor.translateAlternateColorCodes('&', CreateText.addColors(ChatColor.stripColor(line))));
                }
                itemMeta.setLore(coloredLore);
            }

            if (enchantments != null && !enchantments.isEmpty()) {
                for (Map.Entry<Enchantment, Integer> entry : enchantments.entrySet()) {
                    itemMeta.addEnchant(entry.getKey(), entry.getValue(), true);
                }
            }

            item.setItemMeta(itemMeta);
        }

        return item;
    }
    public static void applyDye(ItemStack itemStack, Color dye){
        LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) itemStack.getItemMeta();
        leatherArmorMeta.setColor(dye);
        itemStack.setItemMeta(leatherArmorMeta);
    }
    public static void applyEnchmanent(ItemStack itemStack, Enchantment enchantment, int lvl){
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.addEnchant(enchantment, lvl, false);
        itemStack.setItemMeta(itemMeta);
    }
}
