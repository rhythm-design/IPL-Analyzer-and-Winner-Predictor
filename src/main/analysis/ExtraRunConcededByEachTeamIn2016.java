package src.main.analysis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExtraRunConcededByEachTeamIn2016 {
    private static List<String> ballDeliveries;

    public static void setBallDeliveries(List<String> ballDeliveries) {
        ExtraRunConcededByEachTeamIn2016.ballDeliveries = ballDeliveries;
    }
    private static Map<String,Integer> extraRunByTeams;

    public static int getExtraRunsIn2016ForTeam(String team){
        // get match ids for year 2016     636 to 577
//        System.out.println(ballDeliveries.get(0).split(",")[16]);
        if(extraRunByTeams == null){
            extraRunByTeams = new HashMap<>();
            int index = ballDeliveries.size() - 1;

            while(true){
                String[] ballDelivery = ballDeliveries.get(index).split(",");
                int matchId = Integer.parseInt(ballDelivery[0]);
                if(matchId < 577) break;
                // 16 for extra, 2nd for team
                String battingTeam = ballDelivery[2];
                int extraRuns = Integer.parseInt(ballDelivery[16]);
                extraRunByTeams.put(battingTeam,extraRunByTeams.getOrDefault(battingTeam, 0) + 1);
                index--;
            }
        }
        System.out.println(extraRunByTeams);
        return extraRunByTeams.get(team);
    }
}
