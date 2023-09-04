package src.main.analysis;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TotalMatchesWonByEachTeam {

    final static String[] teams = {"Mumbai Indians", "Sunrisers Hyderabad", "Pune Warriors", "Rajasthan Royals", "Royal Challengers Bangalore", "Kolkata Knight Riders", "Gujarat Lions", "Rising Pune Supergiant", "Kochi Tuskers Kerala", "Kings XI Punjab", "Deccan Chargers", "Delhi Daredevils", "Rising Pune Supergiants", "Chennai Super Kings"};

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
        return String.format("Total Matches played by %s: %s \nTotal Matches Won by %s: %s \n", team, matchesPlayedByEachTeam.get(team), team, matchesWonByEachTeam.get(team));
    }

    public static void getTotalIPLMatchesWonByTeam(BufferedReader br) throws IOException {
        while (true) {
            System.out.println("Enter the number aligned to the team or press [p] to return to previous menu");
            printTeams();
            String input = br.readLine();
            try{
                int teamIdx = Integer.parseInt(input);
                if(teamIdx >=1 && teamIdx <=14){
                    System.out.println(cricketMatchWonByTeam(teams[teamIdx - 1]));
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
