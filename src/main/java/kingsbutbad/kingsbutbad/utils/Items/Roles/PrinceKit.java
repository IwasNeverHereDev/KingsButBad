package kingsbutbad.kingsbutbad.utils.Items.Roles;

import kingsbutbad.kingsbutbad.utils.CreateText;
import kingsbutbad.kingsbutbad.utils.Item;
import kingsbutbad.kingsbutbad.utils.Items.RoleItems;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class PrinceKit {
    public static void giveKit(Player p){
        PlayerInventory inv = p.getInventory();
        inv.setHelmet(getHelmet());
        inv.setChestplate(getChestplate());
        inv.setLeggings(getLeggings());
        inv.setBoots(getBoots());
        inv.addItem(getSword());
        inv.addItem(RoleItems.getKeyCards());
        inv.addItem(RoleItems.geHandcuffs());
        inv.addItem(getBow());
        inv.addItem(RoleItems.getArrows(64));
    }
    public static ItemStack getHelmet(){
        ItemStack itemStack = Item.createItem(Material.LEATHER_HELMET, "<gold>Prince's Helmet", new ArrayList<>(), null);
        Item.applyDye(itemStack, Color.ORANGE);
        return itemStack;
    }
    private static ItemStack getChestplate(){
        ItemStack itemStack = Item.createItem(Material.GOLDEN_CHESTPLATE, "<gold>Prince's Chestplate", new ArrayList<>(), null);
        return itemStack;
    }
    private static ItemStack getLeggings(){
        ItemStack itemStack = Item.createItem(Material.LEATHER_LEGGINGS, "<gold>Prince's Leggings", new ArrayList<>(), null);
        Item.applyDye(itemStack, Color.ORANGE);
        return itemStack;
    }
    private static ItemStack getBoots(){
        ItemStack itemStack = Item.createItem(Material.LEATHER_BOOTS, "<gold>Prince's Boots", new ArrayList<>(), null);
        Item.applyDye(itemStack, Color.ORANGE);
        return itemStack;
    }
    private static ItemStack getSword(){
        ItemStack itemStack = Item.createItem(Material.GOLDEN_SWORD, "<gold>Prince's Dagger", new ArrayList<>(), null);
        Item.applyEnchmanent(itemStack, Enchantment.DAMAGE_ALL, 3);
        return itemStack;
    }
    private static ItemStack getBow(){
        ItemStack itemStack = Item.createItem(Material.BOW, "<gold>Prince's Bow", new ArrayList<>(),null);
        return itemStack;
    }
}
