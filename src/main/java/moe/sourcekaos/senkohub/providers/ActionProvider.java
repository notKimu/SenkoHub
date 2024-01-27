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

import java.time.Duration;
import java.util.Arrays;
import java.util.logging.Logger;

/**
 * Handles the actions the user can call
 * from the configuration files to execute
 * commands on certain events
 * */
public class ActionProvider {
    public String cmdName;
    public String[] cmdBody;
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
        cmdBody = cmdInstruction[1].split(";");
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
    private void commandAsPlayerHandler(@NotNull String[] cmdBody, @NotNull Player player) {
        final String formattedCommand = ReplaceUtil.replacePlayerName(Arrays.toString(cmdBody), player.getName());
        player.performCommand(formattedCommand);
    }

    /**
     * Handles executing a command as the server's console
     * */
    private void commandAsConsoleHandler(@NotNull String[] cmdBody, @NotNull Player player) {
        final String formattedCommand = ReplaceUtil.replacePlayerName(Arrays.toString(cmdBody), player.getName());
        serverInstance.dispatchCommand(serverInstance.getConsoleSender(), formattedCommand);
    }

    /**
     * Handles sending a private message to a player
     * */
    private void messageHandler(@NotNull String[] cmdBody, @NotNull Player player) {
        final String message = ReplaceUtil.replacePlayerName(Arrays.toString(cmdBody), player.getName());
        player.sendMessage(message);
    }

    /**
     * Handles adding a potion effect to a player
     * <p>
     * The potion effect lasts forever ever ever
     * */
    private void effectHandler(@NotNull String[] cmdBody, @NotNull Player player) {
        if (cmdBody.length < 2) {
            logger.warning("Invalid effect at `" + Arrays.toString(cmdBody) + "`");
            return;
        }

        String effectName = cmdBody[0];
        PotionEffectType potionEffect = PotionEffectType.getByName(effectName);
        if (potionEffect == null) {
            logger.warning("`" + effectName + "` is not a valid potion effect name");
            return;
        }

        // The strength of the applied potion effect, we subtract one
        // as the PotionEffect class interprets it as the amplifier
        final int effectStrength = Integer.parseInt(cmdBody[1]) - 1;

        final PotionEffect effect = new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, effectStrength);

        player.addPotionEffect(effect);
    }

    /**
     * Handles sending a player to a different server, based on it's
     * registered name in the proxy
     * */
    private void sendToServerHandler(@NotNull String[] cmdBody, @NotNull Player player) {
        // TODO: Connect the player to another server in the network
    }

    /**
     * Handles showing a title to a player
     * <p>
     * The title uses the default duration values
     * */
    private void titleHandler(@NotNull String[] cmdBody, @NotNull Player player) {
        if (cmdBody.length < 2) {
            logger.warning("Invalid title at `" + Arrays.toString(cmdBody) + "`");
            return;
        }

        // My logic told me that it was better to replace the names
        // and other stuff after splitting the raw title, to avoid
        // weird parsing errors later
        // Feel free to modify :3
        final String titleText = ReplaceUtil.replacePlayerName(cmdBody[0], player.getName());
        final String subtitleText = ReplaceUtil.replacePlayerName(cmdBody[1], player.getName());

        final Component titleComponent = Component.text(titleText);
        final Component subtitleComponent = Component.text(subtitleText);

        if (cmdBody.length == 5) {
            final int fadeInTime = Integer.parseInt(cmdBody[2]);
            final int showTime = Integer.parseInt(cmdBody[3]);
            final int fadeOutTime = Integer.parseInt(cmdBody[4]);

            final Title.Times titleTiming = Title.Times.times(Duration.ofSeconds(fadeInTime), Duration.ofSeconds(showTime), Duration.ofSeconds(fadeOutTime));

            final Title title = Title.title(titleComponent, subtitleComponent, titleTiming);
            player.showTitle(title);
        } else {
            final Title title = Title.title(titleComponent, subtitleComponent);
            player.showTitle(title);
        }
    }
}