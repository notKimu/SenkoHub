package moe.sourcekaos.senkohub.events;

import moe.sourcekaos.senkohub.references.SettingsOptions;
import org.bukkit.Location;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

/**
 * Handles cancelling any damage to players and
 * teleporting them to the spawn if they fall
 * to the void
 * */
public class EntityDamageEvent implements Listener {
    final Configuration pluginConfig;

    public EntityDamageEvent(Configuration config) {
        pluginConfig = config;
    }

    @EventHandler
    public void onPlayerJoin(@NotNull org.bukkit.event.entity.EntityDamageEvent event) {
        final boolean doCancelDamage = pluginConfig.getBoolean(SettingsOptions.IS_DAMAGE_DISABLED);
        final boolean doRespawnOnVoid = pluginConfig.getBoolean(SettingsOptions.SHOULD_RESPAWN_ON_VOID);

        if (doCancelDamage) {
            event.setCancelled(true);
        }

        if (doRespawnOnVoid && event.getCause() == org.bukkit.event.entity.EntityDamageEvent.DamageCause.VOID) {
            final Player player = ((Player) event.getEntity()).getPlayer();
            if (player == null) {
                return;
            }

            event.setCancelled(true);

            Location spawnPoint = pluginConfig.getLocation(SettingsOptions.SPAWN_LOCATION);
            if (spawnPoint == null) {
                spawnPoint = player.getWorld().getSpawnLocation();
            }
            player.teleport(spawnPoint);
        }
    }
}
