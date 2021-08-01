package michalec.connor.TheGoodCompetition.challenges;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.plugin.java.JavaPlugin;

import michalec.connor.TheGoodCompetition.Challenge;

public class FindDiamonds extends Challenge {


    public FindDiamonds(Player registered, JavaPlugin plugin) {
        super(registered, plugin, "FindDiamonds");
    }

    @Override
    public int getChallengePoints() {
        return(6);
    }

    @Override
    public void effect() {

    }

    @EventHandler
    public void onDiamondBlockBreak(BlockBreakEvent e) {
        e.getBlock().getType().equals(Material.DIAMOND_ORE);
        this.challengeComplete("", e.getPlayer(), this);
    }


    
}
