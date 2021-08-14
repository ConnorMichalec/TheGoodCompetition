package michalec.connor.TheGoodCompetition.challenges;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;

import michalec.connor.TheGoodCompetition.Challenge;
import michalec.connor.TheGoodCompetition.TheGoodCompetition;

public class FindDiamonds extends Challenge {


    public FindDiamonds(TheGoodCompetition plugin) {
        super(plugin, "FindDiamonds", "Mining diamonds");
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
        if(e.getBlock().getType().equals(Material.DIAMOND_ORE)) {
            this.challengeComplete("s", e.getPlayer(), this);
        }
    }


    
}
