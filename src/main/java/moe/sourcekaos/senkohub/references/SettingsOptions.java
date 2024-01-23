package moe.sourcekaos.senkohub.references;

/**
 * Contains the configuration options the user can tweak
 * for easy reference on the code
 * */
public abstract class SettingsOptions {
    // Booleans
    public final static String SHOULD_CLEAR_INVENTORY = "clear-inventory";
    public final static String SHOULD_DISABLE_DAMAGE = "disable-damage";
    public final static String SHOULD_DISABLE_HUNGER = "disable-hunger";
    public final static String SHOULD_HEAL_PLAYER = "heal-player";
    public final static String IS_DAMAGE_DISABLED = "disable-damage";
    public final static String IS_INVENTORY_FROZEN = "freeze-inventory";
    public final static String IS_PLAYER_ONLY_COMMAND = "player-only-command";
    public final static String SHOULD_TELEPORT_TO_SPAWN = "teleport-to-spawn";
    public final static String SHOULD_RESPAWN_ON_VOID = "respawn-on-void";

    // Strings
    public final static String JOIN_MESSAGE = "join-message";
    public final static String LEAVE_MESSAGE = "leave-message";

    // Lists
    public final static String JOIN_COMMANDS = "join-commands";

    // Locations
    public final static String SPAWN_LOCATION = "spawn-location";
}
