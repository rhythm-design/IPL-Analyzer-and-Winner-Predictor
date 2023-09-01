package src.main.analysis;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TotalMatchesWonByEachTeam {

    static List<String> cricketMatches;

    public static void setCricketMatches(final List<String> cricketMatches) {
        TotalMatchesWonByEachTeam.cricketMatches = cricketMatches;
    }
    static Map<String,Integer> matchesWonByEachTeam;

    static Map<String,Integer> matchesPlayedByEachTeam;

    public static String cricketMatchWonByTeam(String team){
        if(matchesWonByEachTeam == null){
            matchesWonByEachTeam = new HashMap<>();
            matchesPlayedByEachTeam = new HashMap<>();
            for(String cricketMatch: cricketMatches){
                String[] matchDetail = cricketMatch.split(",");
                matchesPlayedByEachTeam.put(matchDetail[4], matchesPlayedByEachTeam.getOrDefault(matchDetail[4],0) + 1);
                matchesPlayedByEachTeam.put(matchDetail[5], matchesPlayedByEachTeam.getOrDefault(matchDetail[5],0) + 1);
                matchesWonByEachTeam.put(matchDetail[10], matchesWonByEachTeam.getOrDefault(matchDetail[10],0) + 1);
            }
        }
        return String.format("Total Matches played: %s \nTotal Matches Won: %s \n", matchesPlayedByEachTeam.get(team), matchesWonByEachTeam.get(team));
    }
}
