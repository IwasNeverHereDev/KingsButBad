package kingsbutbad.kingsbutbad.commands.Misc;

import kingsbutbad.kingsbutbad.Discord.BotManager;
import kingsbutbad.kingsbutbad.KingsButBad;
import kingsbutbad.kingsbutbad.utils.CreateText;
import net.dv8tion.jda.api.entities.Message;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@SuppressWarnings("deprecation")
public class ChangelogCommand implements CommandExecutor, Listener {

    private static final int MAX_MESSAGES = 25;

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(commandSender instanceof Player player)) {
            commandSender.sendMessage("This command can only be used by players.");
            return true;
        }

        // Fetch changelog messages asynchronously
        CompletableFuture.runAsync(() -> {
            List<Message> changelogMessages = getChangelogMessages();
            List<ItemStack> items = parseMessagesIntoItems(changelogMessages);
            Bukkit.getScheduler().runTask(KingsButBad.pl, () -> openChangelogInventory(player, items, 0));
        });

        return true;
    }

    private List<Message> getChangelogMessages() {
        if (BotManager.getUpdateChannel() == null) {
            return new ArrayList<>();
        }
        // Limit to the last 25 messages
        return BotManager.getUpdateChannel().getIterableHistory().cache(false).limit(MAX_MESSAGES).complete();
    }

    private List<ItemStack> parseMessagesIntoItems(List<Message> messages) {
        List<ItemStack> items = new ArrayList<>();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd:MM:yyyy HH:mm");

        for (Message message : messages) {
            String[] parts = message.getContentDisplay().split("# ");
            for (String part : parts) {
                if (part.trim().isEmpty()) continue;

                String[] lines = part.split("\n");
                if (lines.length > 0) {
                    ItemStack item = new ItemStack(Material.PAPER);
                    ItemMeta meta = item.getItemMeta();
                    if (meta != null) {
                        String title = lines[0].trim();
                        String timestamp = dateFormat.format(message.getTimeCreated().toInstant().toEpochMilli());
                        meta.setDisplayName(CreateText.addColors("<yellow>" + title + " (" + timestamp + ")"));

                        List<String> lore = new ArrayList<>();
                        for (int i = 1; i < lines.length; i++) {
                            String line = lines[i].trim();
                            if (line.startsWith("- "))
                                lore.add(CreateText.addColors("<white>" + line.substring(2).trim()));
                        }
                        meta.setLore(lore);
                        item.setItemMeta(meta);
                    }
                    items.add(item);
                }
            }
        }
        return items;
    }

    private void openChangelogInventory(Player player, List<ItemStack> items, int page) {
        int itemsPerPage = 9 * 3;
        int startIndex = page * itemsPerPage;
        int endIndex = Math.min(startIndex + itemsPerPage, items.size());

        Inventory inv = Bukkit.createInventory(null, 9 * 3, CreateText.addColors("<blue>Changelog Page " + (page + 1)));

        for (int i = startIndex; i < endIndex; i++) {
            inv.addItem(items.get(i));
        }

        if (page > 0) {
            ItemStack prevPage = new ItemStack(Material.ARROW);
            ItemMeta meta = prevPage.getItemMeta();
            if (meta != null) {
                meta.setDisplayName(CreateText.addColors("<green>Previous Page"));
                prevPage.setItemMeta(meta);
            }
            inv.setItem(18, prevPage);
        }

        if (endIndex < items.size()) {
            ItemStack nextPage = new ItemStack(Material.ARROW);
            ItemMeta meta = nextPage.getItemMeta();
            if (meta != null) {
                meta.setDisplayName(CreateText.addColors("<green>Next Page"));
                nextPage.setItemMeta(meta);
            }
            inv.setItem(26, nextPage);
        }

        player.openInventory(inv);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) return;
        Inventory inventory = event.getInventory();
        ItemStack clickedItem = event.getCurrentItem();

        if (inventory == null || clickedItem == null || clickedItem.getType() == Material.AIR) return;

        if (!event.getView().getTitle().contains("Changelog")) return;

        event.setCancelled(true);

        if (clickedItem.getType() == Material.ARROW) {
            ItemMeta meta = clickedItem.getItemMeta();
            if (meta != null) {
                int currentPage = extractPageNumber(event.getView().getTitle());

                if (meta.getDisplayName().contains("Next Page")) {
                    openChangelogInventory(player, parseMessagesIntoItems(getChangelogMessages()), currentPage + 1);
                } else if (meta.getDisplayName().contains("Previous Page")) {
                    openChangelogInventory(player, parseMessagesIntoItems(getChangelogMessages()), currentPage - 1);
                }
            }
        }
    }

    private int extractPageNumber(String inventoryName) {
        String[] parts = inventoryName.split(" ");
        return Integer.parseInt(parts[parts.length - 1]) - 1;
    }
}



