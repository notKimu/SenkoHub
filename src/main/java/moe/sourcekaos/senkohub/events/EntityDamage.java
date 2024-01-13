package moe.sourcekaos.senkohub.events;

import org.bukkit.Location;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class EntityDamage implements Listener {
    Configuration pluginConfig;

    public EntityDamage(Configuration config) {
        pluginConfig = config;
    }

    @EventHandler
    public void onPlayerJoin(EntityDamageEvent event) {
        boolean doCancelDamage = pluginConfig.getBoolean("disable-damage");
        boolean doRespawnOnVoid = pluginConfig.getBoolean("respawn-on-void");

        if (doCancelDamage) {
            event.setCancelled(true);
        }

        if (doRespawnOnVoid) {
            if (event.getCause() != EntityDamageEvent.DamageCause.VOID) {
                return;
            }

            Player player = ((Player) event.getEntity()).getPlayer();
            if (player == null) {
                return;
            }

            Location spawnPoint = pluginConfig.getLocation("spawn-location");
            if (spawnPoint == null) {
                spawnPoint = player.getWorld().getSpawnLocation();
            }

            event.setCancelled(true);
            player.teleport(spawnPoint);
        }
    }
}
