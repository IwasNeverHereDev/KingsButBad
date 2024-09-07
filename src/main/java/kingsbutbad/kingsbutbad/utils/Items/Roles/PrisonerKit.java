package kingsbutbad.kingsbutbad.utils.Items.Roles;

import kingsbutbad.kingsbutbad.utils.CreateText;
import kingsbutbad.kingsbutbad.utils.Item;
import kingsbutbad.kingsbutbad.utils.Items.RoleItems;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.ArrayList;

public class PrisonerKit {
    public static void giveKit(Player p){
        PlayerInventory inv = p.getInventory();
        inv.setChestplate(getChestplate());
        inv.setLeggings(getLeggings());
        inv.setBoots(getBoots());
    }
    private static ItemStack getChestplate(){
        ItemStack itemStack = Item.createItem(Material.LEATHER_CHESTPLATE, "<gold>Prisoner's Chestplate", new ArrayList<>(), null);
        Item.applyDye(itemStack, Color.fromRGB(208, 133, 22));
        return itemStack;
    }
    private static ItemStack getLeggings(){
        ItemStack itemStack = Item.createItem(Material.LEATHER_LEGGINGS, "<gold>Prisoner's Leggings", new ArrayList<>(), null);
        Item.applyDye(itemStack, Color.fromRGB(208, 133, 22));
        return itemStack;
    }
    private static ItemStack getBoots(){
        ItemStack itemStack = Item.createItem(Material.LEATHER_BOOTS, "<gold>Prisoner's Boots", new ArrayList<>(), null);
        Item.applyDye(itemStack, Color.fromRGB(40, 20, 2));
        return itemStack;
    }
}
