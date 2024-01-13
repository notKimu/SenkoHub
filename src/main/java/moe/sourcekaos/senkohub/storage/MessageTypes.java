package moe.sourcekaos.senkohub.storage;

public enum MessageTypes {
    CANCEL_INTERACTIONS("cancel-interactions"),
    BREAKING_DISABLED("breaking-disabled"),
    PLACING_DISABLED("placing-disabled"),

    INVENTORY_FROZEN("inventory-frozen"),

    SPAWN_SET("spawn-set"),

    PLAYER_ONLY_COMMAND("player-only-command");

    private final String text;

    MessageTypes(String s) {
        this.text = s;
    }

    public String key() {
        return text;
    }
}
