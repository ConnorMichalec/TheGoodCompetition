package michalec.connor.TheGoodCompetition;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import net.md_5.bungee.api.ChatColor;

public abstract class Challenge implements Listener {
    private String state;
    private String challengeName;
    private String challengeDescriptor;
    private TheGoodCompetition plugin;

    public Challenge(TheGoodCompetition plugin, String challengeName, String challengeDescriptor) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        this.challengeName = challengeName;
        this.challengeDescriptor = challengeDescriptor;
        this.plugin = plugin;
    }

    public void setState(String registered) {
        state = registered; //if this isn't null, player has already got this challenge, loaded from yml    
    }

    public abstract void effect();

    public abstract int getChallengePoints();

    public void challengeComplete(String message, Player whoCompleted, Challenge childObject) {
        
        this.effect();
        
        plugin.completedChallenge(whoCompleted.getName(), challengeName);
        
        this.state = whoCompleted.getName();
        plugin.setState(challengeName, whoCompleted.getName());
        this.announce(message.replaceAll("&", "§"));
    }

    public void announce(String message) {
        String pteam = plugin.getPlayerTeam(state);
        //Announce to all
        for(Player p : Bukkit.getOnlinePlayers()) {
            String an;
            switch(pteam.toUpperCase()) { //to ignore case
                case "RED":
                    an = "[§c§l"+pteam+"§r] ";
                    break;
                case "BLUE":
                    an = "[§9§l"+pteam+"§r] ";
                    break;
                default:
                    an = "[§a§l"+pteam+"§r] ";
                    break;
            }

            if(!(p.equals(Bukkit.getPlayer(state)))) {
                an = an+state+" §rhas completed the challenge: §a"+challengeDescriptor;
                if(plugin.getPlayerTeam(p.getName()).equals(plugin.getPlayerTeam(state))) {
                    p.playSound(p.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.MASTER, 1.0f, 1.0f);
                }
                else {
                    p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_PLACE, SoundCategory.MASTER, 1.0f, 0.7f);
                }
            }
            else {
                an = "§6You have completed the challenge: §a"+challengeDescriptor;
                p.playSound(p.getLocation(), Sound.ITEM_TOTEM_USE, SoundCategory.MASTER, 1.0f, 0.7f);
            }

            p.sendMessage(an);
        }

    }

    public String getChallengeName() {
        return(challengeName);
    }
    
}
