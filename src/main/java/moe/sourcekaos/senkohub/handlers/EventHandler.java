package moe.sourcekaos.senkohub.handlers;

import moe.sourcekaos.senkohub.SenkoHub;
import moe.sourcekaos.senkohub.events.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.jetbrains.annotations.NotNull;

/**
 * Handles the registration of events
 * */
public abstract class EventHandler {
    public static void register(@NotNull SenkoHub pluginInstance) {
        PluginManager pluginManager = pluginInstance.getServer().getPluginManager();
        FileConfiguration pluginConfig = pluginInstance.getConfig();

        pluginManager.registerEvents(new PlayerJoinEvent(pluginInstance), pluginInstance);
        pluginManager.registerEvents(new InventoryEvent(pluginConfig), pluginInstance);
        pluginManager.registerEvents(new InteractionEvent(pluginConfig), pluginInstance);
        pluginManager.registerEvents(new EntityDamage(pluginConfig), pluginInstance);
        pluginManager.registerEvents(new EntityFoodLevel(), pluginInstance);

        pluginInstance.getServer().getLogger().info("Registered the events");
    }
}
