package dev.phonis.factions_tweaks.tweaks;

public class HidePlayers
{

    public static final HidePlayers INSTANCE = new HidePlayers();

    private boolean hidePlayers = false;

    public boolean togglePlayers()
    {
        this.hidePlayers = !this.hidePlayers;
        return this.hidePlayers;
    }

    public boolean shouldHidePlayers()
    {
        return this.hidePlayers;
    }

}
