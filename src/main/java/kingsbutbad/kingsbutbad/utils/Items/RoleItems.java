package kingsbutbad.kingsbutbad.utils.Items;

import kingsbutbad.kingsbutbad.utils.CreateText;
import kingsbutbad.kingsbutbad.utils.Item;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class RoleItems {
    public static ItemStack geHandcuffs(){
        ItemStack handcuffs = new ItemStack(Material.IRON_SHOVEL);
        ItemMeta handmeta = handcuffs.getItemMeta();
        handmeta.setUnbreakable(true);
        handmeta.setDisplayName(CreateText.addColors("<color:#ffff00><b>Handcuffs</color>"));
        handmeta.addEnchant(Enchantment.DAMAGE_ALL, 2, true);
        handmeta.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE});
        handcuffs.setItemMeta(handmeta);
        return handcuffs;
    }
    public static ItemStack getKeyCards(){
        ItemStack card = new ItemStack(Material.TRIPWIRE_HOOK);
        ItemMeta cardm = card.getItemMeta();
        cardm.setDisplayName(ChatColor.BLUE + "Keycard");
        card.setItemMeta(cardm);
        return card;
    }
    public static ItemStack getArrows(int amount){
        return new ItemStack(Material.ARROW, amount);
    }
    public static ItemStack getSteak(int amount) {
        return new ItemStack(Material.COOKED_BEEF, amount);
    }
}
