package moe.sourcekaos.senkohub.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.jetbrains.annotations.NotNull;

/**
 * Handles cancelling the hunger change event
 * */
public class EntityFoodLevelEvent implements Listener {
    @EventHandler
    public void onHungerChange(@NotNull FoodLevelChangeEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            return;
        }

        final Player player = (Player) event.getEntity();
        event.setCancelled(true);
        player.setFoodLevel(20);
    }
}
