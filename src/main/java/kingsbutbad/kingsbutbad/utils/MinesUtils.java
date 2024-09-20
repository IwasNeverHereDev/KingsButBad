package kingsbutbad.kingsbutbad.utils;

import org.bukkit.Material;

public class MinesUtils {
    public static boolean isMineable(Material material){
        if(material == Material.COAL_ORE) return true;
        if(material == Material.IRON_ORE) return true;
        if(material == Material.GOLD_ORE) return true;
        return false;
    }
}
