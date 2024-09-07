package kingsbutbad.kingsbutbad.utils.Items.Roles;

import kingsbutbad.kingsbutbad.utils.CreateText;
import kingsbutbad.kingsbutbad.utils.Item;
import kingsbutbad.kingsbutbad.utils.Items.RoleItems;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.ArrayList;

public class BodyGuardKit {
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
    private static ItemStack getHelmet(){
        ItemStack itemStack = Item.createItem(Material.LEATHER_HELMET, "<dark_gray>BodyGuard's Helmet", new ArrayList<>(), null);
        Item.applyDye(itemStack, Color.BLACK);
        return itemStack;
    }
    private static ItemStack getChestplate(){
        ItemStack itemStack = Item.createItem(Material.NETHERITE_CHESTPLATE, "<gold>BodyGuard's Chestplate", new ArrayList<>(), null);
        Item.applyEnchmanent(itemStack, Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        return itemStack;
    }
    private static ItemStack getLeggings(){
        ItemStack itemStack = Item.createItem(Material.LEATHER_LEGGINGS, "<dark_gray>BodyGuard's Leggings", new ArrayList<>(), null);
        Item.applyDye(itemStack, Color.BLACK);
        return itemStack;
    }
    private static ItemStack getBoots(){
        ItemStack itemStack = Item.createItem(Material.LEATHER_BOOTS, "<dark_gray>BodyGuard's Boots", new ArrayList<>(), null);
        Item.applyDye(itemStack, Color.BLACK);
        return itemStack;
    }
    private static ItemStack getSword(){
        ItemStack itemStack = Item.createItem(Material.IRON_SWORD, "<dark_gray>BodyGuard's Dagger", new ArrayList<>(), null);
        return itemStack;
    }
    private static ItemStack getBow(){
        ItemStack itemStack = Item.createItem(Material.CROSSBOW, "<dark_gray>BodyGuard's Crossbow", new ArrayList<>(),null);
        return itemStack;
    }
}
