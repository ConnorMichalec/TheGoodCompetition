package michalec.connor.TheGoodCompetition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class TheGoodCompetition extends JavaPlugin {

    public static YamlDataHandler dataHandler;

    public static HashMap<String, ArrayList<Player>> teams = new HashMap<>();

    @Override
    public void onEnable() {
        dataHandler = new YamlDataHandler(this);
        dataHandler.createDirectoryIfMissing("plugins/TheGoodCompetition/");
        dataHandler.copyTemplateIfMissing("config.yml", "plugins/TheGoodCompetition/config.yml");
        dataHandler.copyTemplateIfMissing("state.yml", "plugins/TheGoodCompetition/state.yml");
        dataHandler.addFile("config", "plugins/TheGoodCompetition/config.yml");
        dataHandler.addFile("state", "plugins/TheGoodCompetition/state.yml");

        fetchTeamData();

    }

    public static void standardEffect() {
        //standard effect for getting reward
    }

    public static void completedChallenge(Player player, String challengeName) {
        dataHandler.setYAMLStringField("config", "teams."+getPlayerTeam(player), player.getName());
    }

    public void fetchTeamData() {
        ArrayList<String> fetchedteams = (ArrayList<String>) dataHandler.getYAMLListField("config", "teams");

        for(int i = 0; i<fetchedteams.size(); i++) {
            ArrayList<Player> team = new ArrayList<>();
            ArrayList<String> fetchedteam = (ArrayList<String>) dataHandler.getYAMLListField("config", "teams."+fetchedteams.get(i));

            for(String fetchedplayer : fetchedteam) {
                team.add(Bukkit.getPlayer(fetchedplayer));
            }
            
            teams.put(fetchedteams.get(i), team);
        }

    }

    public static String getPlayerTeam(Player player) {
        ArrayList<Player>  playerTeam = new ArrayList<>();
        for(Map.Entry<String, ArrayList<Player>> pair : teams.entrySet()) {
            String thisteam = pair.getKey();
            for(Player thisplayer : pair.getValue()) {
                if(thisplayer.equals(player)) {
                    return(thisteam);
                }
            }
        }
        return(null);
    }
}