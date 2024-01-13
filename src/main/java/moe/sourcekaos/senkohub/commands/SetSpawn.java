package moe.sourcekaos.senkohub.commands;

import moe.sourcekaos.senkohub.SenkoHub;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class SetSpawn extends Command {
    SenkoHub pluginInstance;
    Configuration pluginConfig;

    public SetSpawn(SenkoHub plugin) {
        super("set-spawn", "Sets the spawn location to which teleport players when they join (if enabled)", "/set-spawn", Arrays.asList("sp", "setsp"));
        this.setPermission("senkohub.spawn");
        pluginInstance = plugin;
        pluginConfig = pluginInstance.getConfig();
    }

    @Override
    public boolean execute(@NotNull CommandSender commandSender, @NotNull String s, @NotNull String[] strings) {
        Player player = ((Player) commandSender).getPlayer();
        if(player == null) {
            commandSender.sendMessage("You need to be a player to run this command!");
            return true;
        }

        Location spawnLocation = player.getLocation();
        pluginConfig.set("spawn-location", spawnLocation);
        pluginInstance.saveConfig();

        player.sendMessage(String.format("The spawn point was set at X: %f, Y: %f, Z: %f | Pitch: %f Yaw: %f", spawnLocation.getX(), spawnLocation.getY(), spawnLocation.getZ(), spawnLocation.getPitch(), spawnLocation.getYaw()));

        if (!(pluginConfig.getBoolean("teleport-to-spawn"))) {
            player.sendMessage("Warning: Teleporting players to the spawn point when they join is disabled\nEnable it in the config file if needed");
        }
        return false;
    }
}
