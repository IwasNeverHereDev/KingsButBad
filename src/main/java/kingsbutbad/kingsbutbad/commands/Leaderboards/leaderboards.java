package kingsbutbad.kingsbutbad.commands.Leaderboards;

import kingsbutbad.kingsbutbad.keys.Key;
import kingsbutbad.kingsbutbad.keys.Keys;
import org.bukkit.Statistic;

public enum leaderboards {
    KILLS(Statistic.PLAYER_KILLS, null),
    DEATHS(Statistic.DEATHS, null),
    KING_PLAYTIME(null, Keys.KINGTicks),
    PRINCE_PLAYTIME(null, Keys.PRINCETicks),
    BODYGUARD_PLAYTIME(null, Keys.BODYGUARDTicks),
    SERVANT_PLAYTIME(null, Keys.SERVANTTicks),
    KNIGHT_PLAYTIME(null, Keys.KNIGHTTicks),
    PRISON_GUARD_PLAYTIME(null, Keys.PRISON_GUARDTicks),
    OUTLAW_PLAYTIME(null, Keys.OUTLAWTicks),
    CRIMINAL_PLAYTIME(null, Keys.CRIMINALTicks),
    PRISONER_PLAYTIME(null, Keys.PRISONERTicks),
    PEASANT_PLAYTIME(null, Keys.PEASANTTicks),
    MONEY(null, Keys.money),
    PLAYTIME(Statistic.PLAY_ONE_MINUTE, null);

    private final Statistic statistic;
    private final Key<?> key;

    leaderboards(Statistic statistic, Key<?> key) {
        this.statistic = statistic;
        this.key = key;
    }

    public Statistic getStatistic() {
        return statistic;
    }

    public Key<?> getKey() {
        return key;
    }
}
