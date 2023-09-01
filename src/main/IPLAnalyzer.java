package src.main;

import src.main.analysis.TotalMatchesPlayedEachYear;
import src.main.analysis.TotalMatchesWonByEachTeam;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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
        TotalMatchesPlayedEachYear.setCricketMatches(matches.subList(1,matches.size()));
        TotalMatchesWonByEachTeam.setCricketMatches(matches.subList(1,matches.size()));
//        int ans = TotalMatchesPlayedEachYear.totalMatchInYear(2008);
        System.out.println(TotalMatchesPlayedEachYear.totalMatchInYear(2008));
        System.out.println(TotalMatchesPlayedEachYear.totalMatchInYear(2009));
        System.out.println(TotalMatchesWonByEachTeam.cricketMatchWonByTeam("Royal Challengers Bangalore"));

    }
}
