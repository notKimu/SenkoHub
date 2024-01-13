package moe.sourcekaos.senkohub.events;

import moe.sourcekaos.senkohub.SenkoHub;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerJoin implements Listener {
    SenkoHub pluginInstance;
    Configuration pluginConfig;

    public PlayerJoin(SenkoHub plugin) {
        pluginInstance = plugin;
        pluginConfig = pluginInstance.getConfig();
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        // Inventory items logic
        if (pluginConfig.getBoolean("clear-inventory")) {
            player.getInventory().clear();
        }

        // Teleport to spawn logic
        // Scheduled task to wait for the player login
        if(pluginConfig.getBoolean("teleport-to-spawn")) {
            Bukkit.getScheduler().scheduleSyncDelayedTask(pluginInstance, () -> {
                Location spawnLocation = pluginConfig.getLocation("spawn-location");
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
        if (pluginConfig.getBoolean("heal-player")) {
            player.setHealth(20);
        }

        // Custom join message logic
        String joinMessage = pluginConfig.getString("join-message");
        if (joinMessage != null && !joinMessage.isEmpty()) {
            String replacedJoinMessage = joinMessage.replace("{player}", player.getName());
            event.setJoinMessage(replacedJoinMessage);
        }
    }
}
