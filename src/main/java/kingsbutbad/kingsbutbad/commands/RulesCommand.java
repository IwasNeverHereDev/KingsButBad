package kingsbutbad.kingsbutbad.commands;

import kingsbutbad.kingsbutbad.utils.CreateText;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class RulesCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        String rules = """
                1. No spamming. **1d Mute**
                2. No disrespect towards other members. This includes **but is not limited to**:
                  a. Harassment. **5d Mute**
                  b. Making uncomfortable or intentionally triggering comments. **3d Mute**
                  c. Purposefully misgendering. **1d Mute**
                3. No inappropriate behavior. This includes **but is not limited to**:\s
                  a. NSFW conversations, references, or media. **1 Year Mute**
                  b. Jokes about terrorism, traumatic events, or other triggering topics.  **1 Year Mute**
                  c. Heated political discussions. **1d Mute**
                4. No discussion, reenactment, or carrying out of illegal activities. This includes **but is not limited to**:
                  a. Drugs or bomb/weapon making. **Perm ban**
                  b. Threatening/wishing harm upon members. **15d Mute**
                  c. Threatening to or releasing personal information about a member **15d Mute**
                  d. Predatory or grooming behavior. **1 Year Mute**
                5. No offensive conversation or behavior. This includes **but is not limited to**:
                  a. Using slurs/hate speech. **1 Year Mute**
                  b. Discrimination towards certain groups or minorities. **1d mute**
                  c. Using Cuss words **1d Mute**
                6. No engaging in behavior that may harm the server or another member's experience. This includes **but is not limited to**:
                  a. Abusing bugs or unintended behavior. **15d ban**
                  b. Lagging the server. **1d ban**
                  c. Advertising other servers. **1d Mute**
                  d. Using modifications to gain a competitive edge over other members, such as:
                      a. Hacking. **30d Ban**
                      b. Automatically running /king to always be the king. **1d Ban**
                  e. Repetitively killing members after they spawn in without aggression from said member.* **1d Ban**
                  g. Using alts to evade solitary, become /king between your main and alternate account, or using /king while your main account is dead. **1d Ban**
                  h. Using macros / auto clickers to automate jobs. **1d Ban**
                7. Ban evasion **Perm Ban (Alt) Double Main Account Ban**
                                
                **Rules Punishment are Doubled everytime it is done!**
                **These rules are subject to change at any time!**
                """;
            commandSender.sendMessage(CreateText.addColors("<gray>"+parseFormatting(rules)));
        return true;
    }
    public static String parseFormatting(String message) {
        return message.replaceAll("\\*\\*(.*?)\\*\\*", "<b>$1</b>");
    }
}

