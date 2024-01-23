package moe.sourcekaos.senkohub.utils;

import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

/**
 * Replaces certain placeholders from
 * the text the user inputs or configures
 * */
public abstract class ReplaceUtil {
    private final static String PLAYER_NAME_PLACEHOLDER = "%player%";

    private final static String LOCATION_X_PLACEHOLDER = "%x%";
    private final static String LOCATION_Y_PLACEHOLDER = "%y%";
    private final static String LOCATION_Z_PLACEHOLDER = "%z%";
    private final static String LOCATION_PITCH_PLACEHOLDER = "%pitch%";
    private final static String LOCATION_YAW_PLACEHOLDER = "%yaw%";

    public static String replacePlayerName(@NotNull String text, @NotNull String playerName) {
        return text.replaceAll(PLAYER_NAME_PLACEHOLDER, playerName);
    }

    public static String replaceLocation(@NotNull String text, @NotNull Location location) {
        double posX = location.getX();
        double posY = location.getY();
        double posZ = location.getZ();
        double pitch = location.getPitch();
        double yaw = location.getYaw();

        return text
                .replace(LOCATION_X_PLACEHOLDER, Double.toString(posX))
                .replace(LOCATION_Y_PLACEHOLDER, Double.toString(posY))
                .replace(LOCATION_Z_PLACEHOLDER, Double.toString(posZ))
                .replace(LOCATION_PITCH_PLACEHOLDER, Double.toString(pitch))
                .replace(LOCATION_YAW_PLACEHOLDER, Double.toString(yaw));
    }
}
