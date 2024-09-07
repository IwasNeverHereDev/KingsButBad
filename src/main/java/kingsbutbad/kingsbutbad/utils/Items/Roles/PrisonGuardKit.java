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

public class PrisonGuardKit {
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
        ItemStack itemStack = Item.createItem(Material.LEATHER_HELMET, "<blue>PrisonGuard's Helmet", new ArrayList<>(), null);
        Item.applyDye(itemStack, Color.BLUE);
        return itemStack;
    }
    private static ItemStack getChestplate(){
        ItemStack itemStack = Item.createItem(Material.IRON_CHESTPLATE, "<blue>PrisonGuard's Chestplate", new ArrayList<>(), null);
        return itemStack;
    }
    private static ItemStack getLeggings(){
        ItemStack itemStack = Item.createItem(Material.CHAINMAIL_LEGGINGS, "<blue>PrisonGuard's Leggings", new ArrayList<>(), null);
        return itemStack;
    }
    private static ItemStack getBoots(){
        ItemStack itemStack = Item.createItem(Material.LEATHER_BOOTS, "<blue>PrisonGuard's Boots", new ArrayList<>(), null);
        Item.applyDye(itemStack, Color.BLUE);
        return itemStack;
    }
    private static ItemStack getSword(){
        ItemStack itemStack = Item.createItem(Material.STONE_SWORD, "<blue>PrisonGuard's Dagger", new ArrayList<>(), null);
        return itemStack;
    }
    private static ItemStack getBow(){
        ItemStack itemStack = Item.createItem(Material.CROSSBOW, "<blue>PrisonGuard's Crossbow", new ArrayList<>(),null);
        return itemStack;
    }
}
