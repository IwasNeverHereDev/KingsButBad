package kingsbutbad.kingsbutbad.utils.Items;

import kingsbutbad.kingsbutbad.utils.CreateText;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class GetOutOfJailFreeCard {
    public static ItemStack get(){
        ItemStack card = new ItemStack(Material.PAPER);
        ItemMeta cardMeta = card.getItemMeta();
        cardMeta.setDisplayName(CreateText.addColors("<blue>Get-Out-Of-Jail-Free Card"));
        card.setItemMeta(cardMeta);
        return card;
    }
}
