package kingsbutbad.kingsbutbad.utils.Items.Roles;

import kingsbutbad.kingsbutbad.utils.Item;
import kingsbutbad.kingsbutbad.utils.Items.RoleItems;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.ArrayList;

public class KnightKit {
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
        return Item.createItem(Material.CHAINMAIL_HELMET, "<gray>Knight's Helmet", new ArrayList<>(), null);
    }
    private static ItemStack getChestplate(){
        return Item.createItem(Material.IRON_CHESTPLATE, "<gray>Knight's Chestplate", new ArrayList<>(), null);
    }
    private static ItemStack getLeggings(){
        return Item.createItem(Material.CHAINMAIL_LEGGINGS, "<gray>Knight's Leggings", new ArrayList<>(), null);
    }
    private static ItemStack getBoots(){
        return Item.createItem(Material.CHAINMAIL_BOOTS, "<gray>Knight's Boots", new ArrayList<>(), null);
    }
    private static ItemStack getSword(){
        return Item.createItem(Material.STONE_SWORD, "<gray>Knight's Dagger", new ArrayList<>(), null);
    }
    private static ItemStack getBow(){
        return Item.createItem(Material.BOW, "<gray>Knight's Bow", new ArrayList<>(),null);
    }
}
