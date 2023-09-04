package src.main.analysis;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExtraRunConcededByEachTeamIn2016 extends IPLData{

    private static Map<String,Integer> extraRunByTeams;

    public static String extraRunsIn2016ForTeam(String team){
        // get match ids for year 2016     636 to 577
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
        return "Extra runs conceded by team " + team + " is: " + extraRunByTeams.get(team);
    }

    public static void getExtraRunsIn2016ForTeam(BufferedReader br) throws IOException {
        while (true) {
            System.out.println("Enter the number aligned to the team or press [p] to return to previous menu");
            printTeams();
            String input = br.readLine();
            try{
                int teamIdx = Integer.parseInt(input);
                if(teamIdx >=1 && teamIdx <=14){
                    System.out.println(extraRunsIn2016ForTeam(teams[teamIdx - 1]));
                }else{
                    System.out.println("Please Enter the Number between 1 to 14 inclusively or press [q] to exit program");
                }
            }catch(NumberFormatException e){
                if(input.equals("q") || input.equals("Q")){
                    System.out.println("Exited Program");
                    System.exit(0);
                }else if(input.equals("p") || input.equals("P")){
                    return;
                }else{
                    System.out.println("Either enter number or press q to exit");
                }
            }
        }
    }

    private static void printTeams(){
        int columns = 4; // Number of columns per row

        // Calculate the number of rows required
        int rows = (int) Math.ceil((double) teams.length / columns);

        // Find the maximum width for each index
        int maxIndexWidth = String.valueOf(teams.length).length();

        // Find the maximum width for each team name
        int maxColumnWidth = 0;
        for (String team : teams) {
            maxColumnWidth = Math.max(maxColumnWidth, team.length());
        }

        // Display the array with each string's index (without space before single-digit numbers)
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                int index = i * columns + j;
                if (index < teams.length) {
                    String team = teams[index];
                    String formattedIndex = String.format("(%0" + maxIndexWidth + "d)", index + 1);
                    System.out.printf("%s %-" + (maxColumnWidth + 2) + "s", formattedIndex, team);

                    // Add a separator between columns
                    if (j < columns - 1) {
                        System.out.print("\t");
                    }
                }
            }
            System.out.println(); // Move to the next row
        }
    }
}
