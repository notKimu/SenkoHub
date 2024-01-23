package moe.sourcekaos.senkohub.events;

import moe.sourcekaos.senkohub.SenkoHub;
import moe.sourcekaos.senkohub.providers.ActionProvider;
import moe.sourcekaos.senkohub.references.PermissionTypes;
import moe.sourcekaos.senkohub.references.SettingsOptions;
import moe.sourcekaos.senkohub.utils.ReplaceUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Handles the event for when a user joins the server
 * */
public class PlayerJoinEvent implements Listener {
    SenkoHub pluginInstance;
    Configuration pluginConfig;

    public PlayerJoinEvent(SenkoHub plugin) {
        pluginInstance = plugin;
        pluginConfig = pluginInstance.getConfig();
    }

    @EventHandler
    public void onPlayerJoin(@NotNull org.bukkit.event.player.PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (player.hasPermission(PermissionTypes.JOIN_BYPASS)) {
            return;
        }

        // Inventory items logic
        if (pluginConfig.getBoolean(SettingsOptions.SHOULD_CLEAR_INVENTORY)) {
            player.getInventory().clear();
        }

        // Teleport to spawn logic
        // Scheduled task to wait for the player login
        if(pluginConfig.getBoolean(SettingsOptions.SHOULD_TELEPORT_TO_SPAWN)) {
            Bukkit.getScheduler().scheduleSyncDelayedTask(pluginInstance, () -> {
                Location spawnLocation = pluginConfig.getLocation(SettingsOptions.SPAWN_LOCATION);
                if(spawnLocation != null) {
                    player.teleport(spawnLocation);
                }
            }, 1);
        }

        // Set inventory items
        player.getInventory().setItem(0, new ItemStack(Material.STONE));
        player.getInventory().setItem(4, new ItemStack(Material.REDSTONE));
        player.getInventory().setItem(8, new ItemStack(Material.DIAMOND_SWORD));

        // Heal player logic

        if (pluginConfig.getBoolean(SettingsOptions.SHOULD_HEAL_PLAYER)) {
            player.setHealth(20);
            player.setFoodLevel(20);
        }
        // Custom join message logic
        String joinMessage = pluginConfig.getString(SettingsOptions.JOIN_MESSAGE);
        if (joinMessage != null && !joinMessage.isEmpty()) {
            String replacedJoinMessage = ReplaceUtil.replacePlayerName(joinMessage, player.getName());
            event.setJoinMessage(replacedJoinMessage);
        }

        // Join Commands
        List<String> joinCommands = pluginConfig.getStringList(SettingsOptions.JOIN_COMMANDS);
        for(String cmd : joinCommands) {
            new ActionProvider(cmd, player).execute();
        }
    }
}
