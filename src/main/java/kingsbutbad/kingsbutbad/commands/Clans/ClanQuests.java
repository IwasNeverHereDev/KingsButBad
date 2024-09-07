package kingsbutbad.kingsbutbad.commands.Clans;

public enum ClanQuests {
    RIOT("CHARGE!!", "Have your clan kill all Roles Combined as Outlaw", "Medium"),
    PARTY("Clan Party", "have 75% members your clan online at the same time", "Hard/Easy");

    private final String name;
    private final String description;
    private final String difficulty;

    ClanQuests(String name, String description, String difficulty) {
        this.name = name;
        this.description = description;
        this.difficulty = difficulty;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getDifficulty() {
        return difficulty;
    }
}

