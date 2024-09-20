package kingsbutbad.kingsbutbad.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum Pacts {
    NONE(new ItemStack(Material.BARRIER),"<gray>DEFAULT/NONE PACT", "<white>?", "<white> - <gray>You gain nothing!"),
    PRISONER(new ItemStack(Material.IRON_BARS),"<gold>PRISONER PACT", "<gold>*", "<red> - <gray>Your time goes down half then normal", "<green> - <gray>You gain $15 money mining!"), // done
    PEASANT(new ItemStack(Material.LEATHER_CHESTPLATE),"<#59442B>PEASANT PACT","<#59442B>*", "<red> - <gray>Becomes Criminal on hitting anyone as Peasant", "<green> - <gray>Water drain decrease by half."), // done
    SERVANT(new ItemStack(Material.IRON_NUGGET), "<#867143>SERVANT PACT","<#867143>*", "<red> - <gray>You can't go into NON-KINGDOM roles areas", "<green> - <gray>You spawn with stone sword and Chain-mail Chestplate."), // done
    CRIMINAL(new ItemStack(Material.RED_DYE), "<red>CRIMINAL PACT", "<red>*", "<red> - <gray>You can't become any KINGDOM roles.", "<green> - <gray>You can't be Jailed"), // done
    OUTLAW(new ItemStack(Material.ORANGE_DYE),"<gold>OUTLAW PACT", "<gold>*", "<red> - <gray>You always will be jailed. (Criminal/Outlaw)", "<green> - <gray>On kill player will be framed and put in jail."), // done
    KNIGHT(new ItemStack(Material.IRON_CHESTPLATE), "<gray>KNIGHT PACT", "<gray>*", "<red> - <gray>You can't go into NON-KINGDOM roles areas", "<green> - <gray>Your horse is faster."), // done
    BODYGUARD(new ItemStack(Material.NETHERITE_CHESTPLATE),"<dark_gray>BODYGUARD PACT", "<dark_gray>*", "<red> - <gray>Your slower", "<green> - <gray>Your border is expanded by x2"), // done
    PRISON_GUARD(new ItemStack(Material.SHIELD),"<blue>PRISON GUARD PACT", "<blue>*", "<red> - <gray>Your weaker", "<green> - You get a 500 per prisoner/criminal kill"), // done
    KING(new ItemStack(Material.GOLDEN_HELMET),"<gradient:#FFFF52:#FFBA52><b>KING PACT<b></gradient>", "<gradient:#FFFF52:#FFBA52><b>*<b></gradient>","<red> - <gray>You can only eat Golden Carrots/Appeals", "<green> - <gray>You get 0.25$ per royal around you."), // done
    PRINCE(new ItemStack(Material.GOLD_NUGGET),"<gradient:#FFFF52:#FFBA52>PRINCE PACT</gradient>", "<gradient:#FFFF52:#FFBA52>*</gradient>", "<red> - <gray>You have 3 less HP", "<green> - <gray>You have speed 1"); // done

    private final ItemStack itemStack;
    private final String displayName;
    private final String star;
    private final String[] discription;

    Pacts(ItemStack itemStack, String displayName, String star, String... description) {
        this.itemStack = itemStack;
        this.displayName = displayName;
        this.star = star;
        this.discription = description;
    }
    public ItemStack getItemStack() {
        return itemStack;
    }
    public String getDisplayName() {
        return displayName;
    }
    public String getStar() {
        return star;
    }
    public String[] getDiscription() {
        return discription;
    }
    public String getFormattedDisplayName(){
        return CreateText.addColors(displayName);
    }
    public String getFormattedStar(){
        return CreateText.addColors(star);
    }
}