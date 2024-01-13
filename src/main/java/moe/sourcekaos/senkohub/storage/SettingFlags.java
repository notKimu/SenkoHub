package moe.sourcekaos.senkohub.storage;

public enum SettingFlags {

    INVENTORY_FROZEN("inventory-frozen"),
    DISABLE_DAMAGE("disable-damage"),
    DISABLE_HUNGER("disable-hunger"),

    SPAWN_SET("spawn-set"),

    PLAYER_ONLY_COMMAND("player-only-command");

    private final String text;

    SettingFlags(String s) {
        this.text = s;
    }

    @Override
    public String toString() {
        return text;
    }
}
