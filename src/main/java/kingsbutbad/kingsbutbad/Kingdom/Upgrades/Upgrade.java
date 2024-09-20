package kingsbutbad.kingsbutbad.Kingdom.Upgrades;

import org.bukkit.inventory.ItemStack;

import java.nio.file.Path;

public class Upgrade {
    private final UpgradeTypes type;
    private final ItemStack itemStack;
    private final double cost;
    private final String displayName;
    private final Path pathOfUpgrade;
    public Upgrade(UpgradeTypes type, ItemStack itemStack, double cost, String displayName, Path pathOfUpgrade){
        this.type = type;
        this.itemStack = itemStack;
        this.cost = cost;
        this.displayName = displayName;
        this.pathOfUpgrade = pathOfUpgrade;
    }
    public double getCost() {
        return cost;
    }
    public String getDisplayName() {
        return displayName;
    }
    public ItemStack getItemStack() {
        return itemStack;
    }
    public Path getPathOfUpgrade() {
        return pathOfUpgrade;
    }
    public UpgradeTypes getType() {
        return type;
    }
}
