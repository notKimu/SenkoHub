package moe.sourcekaos.senkohub.commands;

import moe.sourcekaos.senkohub.SenkoHub;
import moe.sourcekaos.senkohub.providers.MessageProvider;
import moe.sourcekaos.senkohub.references.MessageTypes;
import moe.sourcekaos.senkohub.references.PermissionTypes;
import moe.sourcekaos.senkohub.references.SettingsOptions;
import moe.sourcekaos.senkohub.utils.ReplaceUtil;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

/**
 * Command to set the spawn point where
 * to teleport players when they join
 * */
public class SetSpawn extends Command {
    final SenkoHub pluginInstance;
    final Configuration pluginConfig;

    public SetSpawn(SenkoHub plugin) {
        super("set-spawn", "Sets the spawn location to which teleport players when they join (if enabled)", "/set-spawn", Arrays.asList("sp", "setsp"));
        this.setPermission(PermissionTypes.SET_SPAWN);
        pluginInstance = plugin;
        pluginConfig = pluginInstance.getConfig();
    }

    @Override
    public boolean execute(@NotNull CommandSender commandSender, @NotNull String s, @NotNull String[] strings) {
        final Player player = ((Player) commandSender).getPlayer();
        if(player == null) {
            commandSender.sendMessage(MessageTypes.PLAYER_ONLY_COMMAND);
            return true;
        }

        final Location spawnLocation = player.getLocation();
        pluginConfig.set(SettingsOptions.SPAWN_LOCATION, spawnLocation);
        pluginInstance.saveConfig();

        // I didn't want to do all of this in one line :p
        final String spawnSetMessage = MessageProvider.getMessage(MessageTypes.SPAWN_SET);
        final String formattedSpawnSetMessage = ReplaceUtil.replaceLocation(spawnSetMessage, spawnLocation);
        player.sendMessage(formattedSpawnSetMessage);

        // If the user sets the spawn point but
        // teleporting players when they join is disabled,
        // warn them
        if (!(pluginConfig.getBoolean(SettingsOptions.SHOULD_TELEPORT_TO_SPAWN))) {
            final String spawnSetButTeleportDisabled = MessageProvider.getMessage(MessageTypes.SPAWN_SET_BUT_TELEPORT_DISABLED);
            player.sendMessage(spawnSetButTeleportDisabled);
        }
        return false;
    }
}
