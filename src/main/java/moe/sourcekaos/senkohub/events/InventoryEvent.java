package moe.sourcekaos.senkohub.events;

import io.papermc.paper.event.player.PlayerPickItemEvent;
import moe.sourcekaos.senkohub.providers.MessageProvider;
import moe.sourcekaos.senkohub.references.MessageTypes;
import moe.sourcekaos.senkohub.references.PermissionTypes;
import moe.sourcekaos.senkohub.references.SettingsOptions;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.jetbrains.annotations.NotNull;

/**
 * Handles events related to the user's inventory
 * or throwing / picking items
 * */
public class InventoryEvent implements Listener {
    Configuration pluginConfig;

    public InventoryEvent(Configuration config) {
        pluginConfig = config;
    }

    @EventHandler
    public void onItemBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (!pluginConfig.getBoolean(SettingsOptions.IS_INVENTORY_FROZEN) || player.hasPermission(PermissionTypes.INVENTORY_BYPASS)) {
            return;
        }

        event.setCancelled(true);

        String inventoryFrozenMsg = MessageProvider.getMessage(MessageTypes.BREAKING_DISABLED);
        player.sendMessage(inventoryFrozenMsg);
    }

    @EventHandler
    public void onItemPlace(@NotNull BlockPlaceEvent event) {
        Player player = event.getPlayer();
        if (!pluginConfig.getBoolean(SettingsOptions.IS_INVENTORY_FROZEN) || player.hasPermission(PermissionTypes.INVENTORY_BYPASS)) {
            return;
        }

        event.setCancelled(true);

        String inventoryFrozenMsg = MessageProvider.getMessage(MessageTypes.PLACING_DISABLED);
        player.sendMessage(inventoryFrozenMsg);
    }

    @EventHandler
    public void onItemDrop(@NotNull PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        if (!pluginConfig.getBoolean(SettingsOptions.IS_INVENTORY_FROZEN) || player.hasPermission(PermissionTypes.INVENTORY_BYPASS)) {
            return;
        }

        event.setCancelled(true);

        String inventoryFrozenMsg = MessageProvider.getMessage(MessageTypes.INVENTORY_FROZEN);
        player.sendMessage(inventoryFrozenMsg);
    }

    @EventHandler
    public void onItemPick(@NotNull PlayerPickItemEvent event) {
        Player player = event.getPlayer();
        if (!pluginConfig.getBoolean(SettingsOptions.IS_INVENTORY_FROZEN) || player.hasPermission(PermissionTypes.INVENTORY_BYPASS)) {
            return;
        }

        event.setCancelled(true);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!pluginConfig.getBoolean(SettingsOptions.IS_INVENTORY_FROZEN)) {
            return;
        }

        if (event.getWhoClicked().hasPermission(PermissionTypes.INVENTORY_BYPASS)) {
            return;
        }

        event.setCancelled(true);
    }
}
