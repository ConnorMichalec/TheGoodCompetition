package michalec.connor.TheGoodCompetition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.plugin.java.JavaPlugin;

import michalec.connor.TheGoodCompetition.challenges.FindDiamonds;

public class TheGoodCompetition extends JavaPlugin {

    /*
    
    Excuse the jankiness of the code, was made quickly without a care about it's neatness
    
    */

    public YamlDataHandler dataHandler;

    public HashMap<String, ArrayList<String>> teams = new HashMap<>();
    
    public ArrayList<Challenge> challenges = new ArrayList<>();

    @Override
    public void onEnable() {
        dataHandler = new YamlDataHandler(this);
        dataHandler.createDirectoryIfMissing("plugins/TheGoodCompetition/");
        dataHandler.copyTemplateIfMissing("config.yml", "plugins/TheGoodCompetition/config.yml");
        dataHandler.copyTemplateIfMissing("state.yml", "plugins/TheGoodCompetition/state.yml");
        dataHandler.addFile("config", "plugins/TheGoodCompetition/config.yml");
        dataHandler.addFile("state", "plugins/TheGoodCompetition/state.yml");
        dataHandler.loadFileYAML("config");
        dataHandler.loadFileYAML("state");

        fetchTeamData();

        registerChallenges();
    }


    public void registerChallenges() {
        registerChallenge(new FindDiamonds(this));

    }

    public void registerChallenge(Challenge challenge) {
        String state = getState(challenge.getChallengeName());
        challenge.setState(state);
        challenges.add(challenge);
    }

    public static void standardEffect() {
        //standard effect for getting reward
    }


    public void completedChallenge(String player, String challengeName) {
        dataHandler.setYAMLStringField("config", "teams."+getPlayerTeam(player), player);
    }

    public String  getState(String challengeName) {
        String state;
        if(dataHandler.YAMLPathExists("state", "challenges."+challengeName)) {
            state = dataHandler.getYAMLStringField("state", "challenges."+challengeName+".accomplished");
        }
        else {
            state = null;
        }
        return(state);
    }

    public void setState(String challengeName, String player) {
        dataHandler.setYAMLStringField("state", "challenges."+challengeName+".accomplished", player);
        dataHandler.update("state");
    }

    public void fetchTeamData() {
        if(dataHandler.YAMLPathExists("config", "teams")) {

            ArrayList<String> fetchedteams = new ArrayList<>(dataHandler.getConfigurationSections("config", "teams"));

            for(int i = 0; i<fetchedteams.size(); i++) {
                ArrayList<String> team = new ArrayList<>();
                ArrayList<String> fetchedteam = (ArrayList<String>) dataHandler.getYAMLListField("config", "teams."+fetchedteams.get(i));

                for(String fetchedplayer : fetchedteam) {
                    team.add(fetchedplayer);
                }

                teams.put(fetchedteams.get(i), team);
            }

        }

    }

    public String getPlayerTeam(String player) {
        for(Map.Entry<String, ArrayList<String>> pair : teams.entrySet()) {
            String thisteam = pair.getKey();

            for(String thisplayer : pair.getValue()) {
                if(thisplayer.equals(player)) {
                    return(thisteam);
                }
            }
        }
        return(null);
    }
}