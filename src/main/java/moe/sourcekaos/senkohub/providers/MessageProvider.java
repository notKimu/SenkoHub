package moe.sourcekaos.senkohub.providers;

import moe.sourcekaos.senkohub.SenkoHub;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.HashMap;
import java.util.Set;

/**
 * Loads the messages in the messages file to reference
 * them faster from the RAM rather than the disk
 * */
public class MessageProvider {
    static private final String MESSAGES_FILE_NAME = "messages.yml";
    static private final HashMap<String, String> messagesMap = new HashMap<>();

    static public void load(@NotNull SenkoHub plugin) {
        plugin.getLogger().info("Loading messages...");

        File messagesFile = new File(plugin.getDataFolder(), MESSAGES_FILE_NAME);

        if (!messagesFile.exists()) {
            plugin.getLogger().info(MESSAGES_FILE_NAME + " is missing, creating it...");
            plugin.saveResource(MESSAGES_FILE_NAME, false);
        }

        FileConfiguration messagesConfig = YamlConfiguration.loadConfiguration(messagesFile);
        Set<String> messageKeys = messagesConfig.getKeys(false);

        messageKeys.forEach((msgKey) -> {
            String msgContent = messagesConfig.getString(msgKey);

            setMessage(msgKey, msgContent);
        });
    }

    static public String getMessage(String msgKey) {
        return messagesMap.get(msgKey);
    }

    static public void setMessage(String msgKey, String msgContent) {
        messagesMap.put(msgKey, msgContent);
    }
}
