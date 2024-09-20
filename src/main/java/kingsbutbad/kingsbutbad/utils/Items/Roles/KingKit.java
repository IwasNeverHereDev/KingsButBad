package kingsbutbad.kingsbutbad.utils.Items.Roles;

import kingsbutbad.kingsbutbad.listeners.PlayerInteractListener;
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
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.ArrayList;

public class KingKit {
    public static void giveKit(Player p){
        PlayerInventory inv = p.getInventory();
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
        ItemStack crown = new ItemStack(Material.GOLDEN_HELMET);
        ItemMeta crownmeta = crown.getItemMeta();
        crownmeta.setUnbreakable(true);
        crownmeta.setDisplayName(CreateText.addColors("<color:#ffff00><b>King's <gradient:#ff4046:#ffff00>Crown</b></color>"));
        ArrayList<String> crownlore = new ArrayList<>();
        crownlore.add(ChatColor.GRAY + "A crown worn by mighty kings while ruling their kingdom.");
        crownlore.add(ChatColor.GRAY + "");
        crownlore.add(
                CreateText.addColors(
                        "<color:#ff0400><i><b>Drop <gradient:#ffdd00:#ffff6e>this<color:#ff2f00> to <color:#910005>Resign.</color> </b></i></color>"
                )
        );
        crownmeta.setLore(crownlore);
        crownmeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 5, true);
        crownmeta.addItemFlags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE);
        crown.setItemMeta(crownmeta);
        return crown;
    }
    public static ItemStack getOtherHelmet(){
        return Item.createItem(Material.CHAINMAIL_HELMET, "Monarch's Helmet", new ArrayList<>(), null);
    }
    private static ItemStack getChestplate(){
        ItemStack itemStack = Item.createItem(Material.LEATHER_CHESTPLATE, "<gold>Monarch's Chestplate", new ArrayList<>(), null);
        Item.applyEnchmanent(itemStack, Enchantment.PROTECTION_PROJECTILE, 5);
        Item.applyDye(itemStack, Color.ORANGE);
        return itemStack;
    }
    private static ItemStack getLeggings(){
        ItemStack itemStack = Item.createItem(Material.CHAINMAIL_LEGGINGS, "<gold>Monarch's Leggings", new ArrayList<>(), null);
        Item.applyEnchmanent(itemStack, Enchantment.PROTECTION_ENVIRONMENTAL, 5);
        return itemStack;
    }
    private static ItemStack getBoots(){
        ItemStack itemStack = Item.createItem(Material.LEATHER_BOOTS, "<gold>Monarch's Boots", new ArrayList<>(), null);
        Item.applyEnchmanent(itemStack, Enchantment.PROTECTION_ENVIRONMENTAL, 5);
        Item.applyDye(itemStack, Color.ORANGE);
        return itemStack;
    }
    private static ItemStack getSword(){
        ItemStack itemStack = Item.createItem(Material.GOLDEN_SWORD, "<gold>Monarch's Dagger", new ArrayList<>(), null);
        Item.applyEnchmanent(itemStack, Enchantment.DAMAGE_ALL, 5);
        return itemStack;
    }
    private static ItemStack getBow(){
        return Item.createItem(Material.BOW, "<gold>Monarch's Bow", new ArrayList<>(),null);
    }
}
