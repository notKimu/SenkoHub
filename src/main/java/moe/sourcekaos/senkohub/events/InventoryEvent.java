package moe.sourcekaos.senkohub.events;

import io.papermc.paper.event.player.PlayerPickItemEvent;
import moe.sourcekaos.senkohub.providers.MessageProvider;
import moe.sourcekaos.senkohub.storage.MessageTypes;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.*;

public class InventoryEvent implements Listener {
    Configuration pluginConfig;

    public InventoryEvent(Configuration config) {
        pluginConfig = config;
    }

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent event) {
        if (!pluginConfig.getBoolean("freeze-inventory")) {
            return;
        }

        Player player = event.getPlayer();
        if (player.hasPermission("senkohub.inventory.bypass")) {
            return;
        }

        event.setCancelled(true);

        String inventoryFrozenMsg = MessageProvider.getMessage(MessageTypes.INVENTORY_FROZEN);
        player.sendMessage(inventoryFrozenMsg);
    }

    @EventHandler
    public void onItemPick(PlayerPickItemEvent event) {
        if (!pluginConfig.getBoolean("freeze-inventory")) {
            return;
        }

        Player player = event.getPlayer();
        if (player.hasPermission("senkohub.inventory.bypass")) {
            return;
        }

        event.setCancelled(true);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!pluginConfig.getBoolean("freeze-inventory")) {
            return;
        }

        if (event.getWhoClicked().hasPermission("senkohub.inventory.bypass")) {
            return;
        }

        event.setCancelled(true);
    }
}
