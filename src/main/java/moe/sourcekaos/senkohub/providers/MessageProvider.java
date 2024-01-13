package moe.sourcekaos.senkohub.providers;

import moe.sourcekaos.senkohub.SenkoHub;
import moe.sourcekaos.senkohub.storage.MessageTypes;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.HashMap;
import java.util.Set;

public class MessageProvider {
    static private final HashMap<String, String> messagesMap = new HashMap<>();

    static public void load(SenkoHub plugin) {
        String messagesFileName = "messages.yml";

        plugin.getLogger().info("Loading messages...");

        File messagesFile = new File(plugin.getDataFolder(), messagesFileName);

        if (!messagesFile.exists()) {
            plugin.saveResource(messagesFileName, false);
        }

        FileConfiguration messagesConfig = YamlConfiguration.loadConfiguration(messagesFile);
        Set<String> messageKeys = messagesConfig.getKeys(false);

        messageKeys.forEach((msgKey) -> {
            String msgContent = messagesConfig.getString(msgKey);

            setMessage(msgKey, msgContent);
        });
    }

    static public String getMessage(MessageTypes msgType) {
        return messagesMap.get(msgType.key());
    }

    static public void setMessage(String msgKey, String msgContent) {
        messagesMap.put(msgKey, msgContent);
    }
}
