package kingsbutbad.kingsbutbad.commands.Leaderboards;

import com.comphenix.protocol.metrics.Statistics;
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
    PLAYTIME(Statistic.PLAY_ONE_MINUTE, null),
    KING_DEATHS(null, Keys.KINGDeaths),
    PRINCE_DEATHS(null, Keys.PRINCEDeaths),
    BODYGUARD_DEATHS(null, Keys.BODYGUARDDeaths),
    SERVANT_DEATHS(null, Keys.SERVANTDeaths),
    KNIGHT_DEATHS(null, Keys.KNIGHTDeaths),
    PRISON_GUARD_DEATHS(null, Keys.PRISON_GUARDDeaths),
    OUTLAW_DEATHS(null, Keys.OUTLAWDeaths),
    CRIMINAL_DEATHS(null, Keys.CRIMINALDeaths),
    PRISONER_DEATHS(null, Keys.PRISONERDeaths),
    PEASANT_DEATHS(null, Keys.PEASANTDeaths),
    KING_KILLS(null, Keys.KINGkills),
    PRINCE_KILLS(null, Keys.PRINCEkills),
    BODYGUARD_KILLS(null, Keys.BODYGUARDkills),
    SERVANT_KILLS(null, Keys.SERVANTkills),
    KNIGHT_KILLS(null, Keys.KNIGHTkills),
    PRISON_GUARD_KILLS(null, Keys.PRISONERkills),
    OUTLAW_KILLS(null, Keys.OUTLAWkills),
    CRIMINAL_KILLS(null, Keys.CRIMINALkills),
    PRISONER_KILLS(null, Keys.PRISONERkills),
    PEASANT_KILLS(null, Keys.PEASANTkills),
    FISH(Statistic.FISH_CAUGHT, null),
    GANG(null, null),
    JUMP(Statistic.JUMP, null),
    ARCHER(Statistic.TARGET_HIT, null),
    DROP(Statistic.DROP_COUNT, null),
    BELLS(Statistic.BELL_RING, null);

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
