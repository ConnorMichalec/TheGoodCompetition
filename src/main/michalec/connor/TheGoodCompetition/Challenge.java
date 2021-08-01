package michalec.connor.TheGoodCompetition;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class Challenge implements Listener {
    private Player state;
    private String challengeName;

    public Challenge(Player registered, JavaPlugin plugin, String challengeName) {
        state = registered; //player has already got this challenge, loaded from yml
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        this.challengeName = challengeName;
    }

    public void Challenge(JavaPlugin plugin, String challengeName) {
        state = null; //not claimed yet.
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        this.challengeName = challengeName;
    }

    public abstract void effect();

    public abstract int getChallengePoints();

    public void challengeComplete(String message, Player whoCompleted, Challenge childObject) {
        this.effect();
        this.announce(message.replaceAll("&", "§"));

        TheGoodCompetition.completedChallenge(whoCompleted, challengeName);
    }

    public void announce(String message) {

    }
    
}
