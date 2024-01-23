package moe.sourcekaos.senkohub.providers;

import moe.sourcekaos.senkohub.references.ActionTypes;
import moe.sourcekaos.senkohub.utils.ReplaceUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Logger;

/**
 * Handles the actions the user can call
 * from the configuration files to execute
 * commands on certain events
 * */
public class ActionProvider {
    public String cmdName;
    public String cmdBody;
    public Player playerInstance;
    public Server serverInstance;
    public Logger logger;

    public ActionProvider(@NotNull String cmd, @NotNull Player player) {
        playerInstance = player;
        serverInstance = playerInstance.getServer();
        logger = playerInstance.getServer().getLogger();

        String[] cmdInstruction = cmd.split(" ", 2);
        if (cmdInstruction.length < 2) {
            logger.warning("Incorrect command at `" + cmd + "`");
            return;
        }

        cmdName = cmdInstruction[0];
        cmdBody = cmdInstruction[1];
    }

    public void execute() {
        switch (cmdName) {
            case ActionTypes.COMMAND:
                commandAsPlayerHandler(cmdBody, playerInstance);
                break;

            case ActionTypes.CONSOLE:
                commandAsConsoleHandler(cmdBody, playerInstance);
                break;

            case ActionTypes.MESSAGE:
                messageHandler(cmdBody, playerInstance);
                break;

            case ActionTypes.POTION_EFFECT:
                effectHandler(cmdBody, playerInstance);
                break;

            case ActionTypes.SERVER:
                sendToServerHandler(cmdBody, playerInstance);
                break;

            case ActionTypes.TITLE:
                titleHandler(cmdBody, playerInstance);
                break;

            default:
                logger.warning("Unknown command action: `" + cmdName + "`");
                break;
        }
    }

    // Handlers for each action
    // So the switch isn't full of code wahsajh

    /**
     * Handles executing a command as a player
     * */
    private void commandAsPlayerHandler(@NotNull String cmdBody, @NotNull Player player) {
        String formattedCommand = ReplaceUtil.replacePlayerName(cmdBody, player.getName());
        player.performCommand(formattedCommand);
    }

    /**
     * Handles executing a command as the server's console
     * */
    private void commandAsConsoleHandler(@NotNull String cmdBody, @NotNull Player player) {
        String formattedCommand = ReplaceUtil.replacePlayerName(cmdBody, player.getName());
        serverInstance.dispatchCommand(serverInstance.getConsoleSender(), formattedCommand);
    }

    /**
     * Handles sending a private message to a player
     * */
    private void messageHandler(@NotNull String cmdBody, @NotNull Player player) {
        String message = ReplaceUtil.replacePlayerName(cmdBody, player.getName());
        player.sendMessage(message);
    }

    /**
     * Handles adding a potion effect to a player
     * <p>
     * The potion effect lasts forever ever ever
     * */
    private void effectHandler(@NotNull String cmdBody, @NotNull Player player) {
        String[] effectConfig = cmdBody.split(";", 2);
        if (effectConfig.length < 2) {
            logger.warning("Invalid effect at `" + cmdBody + "`");
            return;
        }

        String effectName = effectConfig[0];
        PotionEffectType potionEffect = PotionEffectType.getByName(effectName);
        if (potionEffect == null) {
            logger.warning("`" + effectName + "` is not a valid potion effect name");
            return;
        }

        // The strength of the applied potion effect, we subtract one
        // as the PotionEffect class interprets it as the amplifier
        int effectStrength = Integer.parseInt(effectConfig[1]) - 1;

        PotionEffect effect = new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, effectStrength);

        player.addPotionEffect(effect);
    }

    /**
     * Handles sending a player to a different server, based on it's
     * registered name in the proxy
     * */
    private void sendToServerHandler(@NotNull String cmdBody, @NotNull Player player) {
        // TODO: Connect the player to another server in the network
    }

    /**
     * Handles showing a title to a player
     * <p>
     * The title uses the default duration values
     * */
    private void titleHandler(@NotNull String cmdBody, @NotNull Player player) {
        String[] titleConfig = cmdBody.split(";", 3);
        if (titleConfig.length < 2) {
            logger.warning("Invalid title at `" + cmdBody + "`");
            return;
        }

        // My logic told me that it was better to replace the names
        // and other stuff after splitting the raw title, to avoid
        // weird parsing errors later
        // Feel free to modify :3
        String titleText = ReplaceUtil.replacePlayerName(titleConfig[0], player.getName());
        String subtitleText = ReplaceUtil.replacePlayerName(titleConfig[1], player.getName());

        Component titleComponent = Component.text(titleText);
        Component subtitleComponent = Component.text(subtitleText);

        Title title = Title.title(titleComponent, subtitleComponent);
        player.showTitle(title);
    }
}