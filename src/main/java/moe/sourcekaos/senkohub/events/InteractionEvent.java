package moe.sourcekaos.senkohub.events;

import moe.sourcekaos.senkohub.references.PermissionTypes;
import moe.sourcekaos.senkohub.references.SettingsOptions;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.jetbrains.annotations.NotNull;

/**
 * Handles cancelling interactions with items
 * or entities
 * */
public class InteractionEvent implements Listener {
    Configuration pluginConfig;

    public InteractionEvent(Configuration config) {
        pluginConfig = config;
    }

    @EventHandler
    public void onInteraction(@NotNull PlayerInteractEvent event) {
        if (!pluginConfig.getBoolean(SettingsOptions.SHOULD_DISABLE_HUNGER) || event.getPlayer().hasPermission(PermissionTypes.INTERACTION_BYPASS)) {
            return;
        }

        event.setCancelled(true);
    }
}
