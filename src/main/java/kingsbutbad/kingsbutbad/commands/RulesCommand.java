package kingsbutbad.kingsbutbad.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class RulesCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        commandSender.sendMessage(ChatColor.GOLD + "Server Rules:");
        commandSender.sendMessage(ChatColor.YELLOW + "1. No spamming. " + ChatColor.RED + "1d Mute");
        commandSender.sendMessage(ChatColor.YELLOW + "2. No disrespect towards other members. This includes but is not limited to:");
        commandSender.sendMessage(ChatColor.YELLOW + "   - a. Harassment. " + ChatColor.RED + "5d Mute");
        commandSender.sendMessage(ChatColor.YELLOW + "   - b. Making uncomfortable or intentionally triggering comments. " + ChatColor.RED + "3d Mute");
        commandSender.sendMessage(ChatColor.YELLOW + "   - c. Purposefully misgendering. " + ChatColor.RED + "1d Mute");
        commandSender.sendMessage(ChatColor.YELLOW + "3. No inappropriate behavior. This includes but is not limited to:");
        commandSender.sendMessage(ChatColor.YELLOW + "   - a. NSFW conversations, references, or media. " + ChatColor.RED + "Perm ban");
        commandSender.sendMessage(ChatColor.YELLOW + "   - b. Jokes about terrorism, traumatic events, or other triggering topics. " + ChatColor.RED + "30d Mute");
        commandSender.sendMessage(ChatColor.YELLOW + "   - c. Heated political discussions. " + ChatColor.RED + "1d Mute");
        commandSender.sendMessage(ChatColor.YELLOW + "   - d. Pedo jokes. " + ChatColor.RED + "Perm ban");
        commandSender.sendMessage(ChatColor.YELLOW + "4. No discussion, reenactment, or carrying out of illegal activities. This includes but is not limited to:");
        commandSender.sendMessage(ChatColor.YELLOW + "   - a. Drugs or bomb/weapon making. " + ChatColor.RED + "Perm ban");
        commandSender.sendMessage(ChatColor.YELLOW + "   - b. Threatening/wishing harm upon members. " + ChatColor.RED + "15d Mute");
        commandSender.sendMessage(ChatColor.YELLOW + "   - c. Threatening to or releasing personal information about a member. " + ChatColor.RED + "15d Mute");
        commandSender.sendMessage(ChatColor.YELLOW + "   - d. Predatory or grooming behavior. " + ChatColor.RED + "Perm ban");
        commandSender.sendMessage(ChatColor.YELLOW + "5. No offensive conversation or behavior. This includes but is not limited to:");
        commandSender.sendMessage(ChatColor.YELLOW + "   - a. Using slurs/hate speech. " + ChatColor.RED + "Perm ban");
        commandSender.sendMessage(ChatColor.YELLOW + "   - b. Discrimination towards certain groups or minorities. " + ChatColor.RED + "1d Mute");
        commandSender.sendMessage(ChatColor.YELLOW + "   - c. Using cuss words. " + ChatColor.RED + "1d Mute");
        commandSender.sendMessage(ChatColor.YELLOW + "6. No engaging in behavior that may harm the server or another member's experience. This includes but is not limited to:");
        commandSender.sendMessage(ChatColor.YELLOW + "   - a. Abusing bugs or unintended behavior. " + ChatColor.RED + "15d Ban");
        commandSender.sendMessage(ChatColor.YELLOW + "   - b. Lagging the server. " + ChatColor.RED + "1d Ban");
        commandSender.sendMessage(ChatColor.YELLOW + "   - c. Advertising other servers. " + ChatColor.RED + "1d Mute");
        commandSender.sendMessage(ChatColor.YELLOW + "   - d. Using modifications to gain a competitive edge over other members, such as:");
        commandSender.sendMessage(ChatColor.YELLOW + "      - a. Hacking. " + ChatColor.RED + "Perm ban");
        commandSender.sendMessage(ChatColor.YELLOW + "      - b. Automatically running /warden to always be the warden. " + ChatColor.RED + "1d Ban");
        commandSender.sendMessage(ChatColor.YELLOW + "   - e. Repetitively killing members after they spawn in without aggression from said member. " + ChatColor.RED + "1d Ban");
        commandSender.sendMessage(ChatColor.YELLOW + "   - f. Repetitively sending members to solitary without aggression from said member. " + ChatColor.RED + "1d Ban");
        commandSender.sendMessage(ChatColor.YELLOW + "   - g. Using alts to evade solitary, pass /warden between your main and alternate account, or using /warden while your main account is dead. " + ChatColor.RED + "1d Ban");
        commandSender.sendMessage(ChatColor.YELLOW + "   - h. Using macros / auto clickers to automate jobs. " + ChatColor.RED + "1d Ban");
        commandSender.sendMessage(ChatColor.YELLOW + "7. Ban evasion. " + ChatColor.RED + "Perm ban");
        commandSender.sendMessage(ChatColor.GOLD + "Rules punishment is doubled every time it is done! These rules are subject to change at any time!");
        commandSender.sendMessage(ChatColor.GOLD + "SCROLL UP!!");
        return true;
    }
}

