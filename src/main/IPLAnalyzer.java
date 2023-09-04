package src.main;

import src.main.analysis.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class IPLAnalyzer {

    public static void main(String[] args) throws IOException {
        // 2008- 2017
        BufferedReader br = new BufferedReader(new FileReader("src/main/resources/matches.csv"));
        String line;
        List<String> matches = new ArrayList<>();
        while ((line = br.readLine()) != null) {
            matches.add(line);
        }
        br = new BufferedReader(new FileReader("src/main/resources/deliveries.csv"));
        List<String> deliveries = new ArrayList<>();
        while ((line = br.readLine()) != null) {
            deliveries.add(line);
        }
        IPLData.setCricketMatches(matches.subList(1,matches.size()));
        IPLData.setBallDeliveries(deliveries);
        br = new BufferedReader(new InputStreamReader(System.in));

        while(true){
            System.out.println("What you want to analyze? Available Options:");
            System.out.println("(1) Number of matches played per year of all the years in IPL.\n" +
                    "(2) Number of matches won of all teams over all the years of IPL.\n" +
                    "(3) For the year 2016 get the extra runs conceded per team.\n" +
                    "(4) For the year 2015 get the top economical bowlers.\n" +
                    "(5) Top N batsman with most runs. \n" +
                    "(6) IPL Winner of the year");
            String input = br.readLine();
            switch (input) {
                case "1":
                    TotalMatchesPlayedEachYear.getTotalMatchInYear(br);
                    break;
                case "2":
                    TotalMatchesWonByEachTeam.getTotalIPLMatchesWonByTeam(br);
                    break;
                case "3":
                    ExtraRunConcededByEachTeamIn2016.getExtraRunsIn2016ForTeam(br);
                    break;
                case "4":
                    TopSortedEconomicalBowlersIn2015.getTopNEconomyBowlers(br);
                    break;
                case "5":
                    TopNBatsmanWithMostRuns.getTopNBatsman(br);
                    break;
                case "6":
                    IPLWinnerForYear.getIPLWinnerForYear(br);
                    break;
                default:
                    System.out.println("Exiting program.");
                    br.close();
                    System.exit(0);
            }
        }
    }
}
