package moe.sourcekaos.senkohub;

import moe.sourcekaos.senkohub.commands.SetSpawn;
import moe.sourcekaos.senkohub.handlers.EventHandler;
import moe.sourcekaos.senkohub.providers.MessageProvider;
import org.bukkit.plugin.java.JavaPlugin;

public final class SenkoHub extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic

        saveDefaultConfig();

        // Messages handler
        MessageProvider.load(this);

        // Register Events
        new EventHandler(this).register();

        // Register Commands
        getServer().getCommandMap().register("set-spawn", new SetSpawn(this));
        getLogger().info("Registered the commands");

        getLogger().info("SenkoHub v0.0.1 is up and running!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("Going to sleep... goodbye!");
    }
}
