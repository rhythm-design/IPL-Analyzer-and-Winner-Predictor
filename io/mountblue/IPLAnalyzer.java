package io.mountblue;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.FileReader;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Arrays;

public class IPLAnalyzer {

    private static IPLAnalyzer iplAnalyzer;

    private IPLAnalyzer(){};

    public static IPLAnalyzer getIPLAnalyzerInstance(){
        if(iplAnalyzer == null){
            iplAnalyzer = new IPLAnalyzer();
        }
        return iplAnalyzer;
    }
    private final int MATCH_ID = 0;
    private final int MATCH_YEAR = 1;
    private final int MATCH_TEAM1 = 4;
    private final int MATCH_TEAM2 = 5;
    private final int MATCH_WINNER_TEAM = 10;

    private final int DELIVERY_MATCH_ID = 0;
    private final int DELIVERY_TEAM1 = 2;
    private final int DELIVERY_TEAM2 = 3;
    private final int DELIVERY_BATSMAN = 6;
    private final int DELIVERY_WIDE_RUNS = 10;
    private final int DELIVERY_NOBALL_RUNS = 13;
    private final int DELIVERY_BYE_RUNS = 11;
    private final int DELIVERY_LEGBYE_RUNS = 12;
    private final int DELIVERY_BATSMAN_RUNS = 15;
    private final int DELIVERY_EXTRA_RUNS = 16;
    private final int DELIVERY_BOWLER_NAME = 8;

    private int[] matchesPlayedPerYear;

    private Map<String,Integer> matchesWonByEachTeam;
    private Map<String,Integer> matchesPlayedByEachTeam;

    private Map<String,Integer> extraRunByTeams;

    private List<Map.Entry<String,Integer>> batsmanSortedAccordingToRuns;

    private int[] finalMatchIds;

    private List<Map.Entry<String,Double>> bowlerDataSorted;

    final private String[] IPLTeams = {"Mumbai Indians", "Sunrisers Hyderabad", "Pune Warriors",
            "Rajasthan Royals", "Royal Challengers Bangalore", "Kolkata Knight Riders", "Gujarat Lions",
            "Rising Pune Supergiant", "Kochi Tuskers Kerala", "Kings XI Punjab", "Deccan Chargers",
            "Delhi Daredevils", "Rising Pune Supergiants", "Chennai Super Kings"};

    public static void main(String[] args) throws IOException {
        IPLAnalyzer iplAnalyzerInstance = getIPLAnalyzerInstance();
        List<Match> matches = iplAnalyzerInstance.getMatchesData();
        List<Delivery> deliveries = iplAnalyzerInstance.getDeliveriesData();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
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
                    iplAnalyzerInstance.findTotalMatchesPlayedPerYear(matches.subList(1, matches.size()));
                    break;
                case "2":
                    iplAnalyzerInstance.findTotalMatchesWonPerTeam(matches.subList(1, matches.size()));
                    break;
                case "3":
                    iplAnalyzerInstance.findExtraRunsConcededPerTeamIn2016(deliveries);
                    break;
                case "4":
                    List<Map.Entry<String, Double>> topBowlersEconomy= iplAnalyzerInstance.getTopNEconomicBowlers(deliveries);
                    for(Map.Entry<String, Double> entry: topBowlersEconomy){
                        System.out.println(entry.getKey() + " -> " + String.format("%.2f", entry.getValue()));
                    }
                    break;
                case "5":
                    iplAnalyzerInstance.findTopNBatsmanWithMostRuns(deliveries.subList(1, deliveries.size()));
                    break;
                case "6":
                    iplAnalyzerInstance.findIplWinnerPerYear(matches.subList(1,matches.size()));
                    break;
                case "q":
                    System.out.println("Exiting program.");
                    br.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid Input, please enter a valid input between 1 - 6 inclusively");
            }
        }
    }

    private List<Delivery> getDeliveriesData() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("io/mountblue/resources/deliveries.csv"));
        String line;
        List<Delivery> deliveries = new ArrayList<>();
        while ((line = br.readLine()) != null) {
            String[] deliveryData = line.split(",");
            Delivery delivery = new Delivery();
            delivery.setMatchId(deliveryData[DELIVERY_MATCH_ID]);
            delivery.setTeam1(deliveryData[DELIVERY_TEAM1]);
            delivery.setTeam2(deliveryData[DELIVERY_TEAM2]);
            delivery.setBatsman(deliveryData[DELIVERY_BATSMAN]);
            delivery.setWideRuns(deliveryData[DELIVERY_WIDE_RUNS]);
            delivery.setNoBallRuns(deliveryData[DELIVERY_NOBALL_RUNS]);
            delivery.setByeRuns(deliveryData[DELIVERY_BYE_RUNS]);
            delivery.setLegByeRuns(deliveryData[DELIVERY_LEGBYE_RUNS]);
            delivery.setBatsmanRuns(deliveryData[DELIVERY_BATSMAN_RUNS]);
            delivery.setExtraRuns(deliveryData[DELIVERY_EXTRA_RUNS]);
            delivery.setBowlerName(deliveryData[DELIVERY_BOWLER_NAME]);
            deliveries.add(delivery);
        }
        br.close();
        return deliveries;
    }

    private List<Match> getMatchesData() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader("io/mountblue/resources/matches.csv"));
        String line;
        List<Match> matches = new ArrayList<>();
        while ((line = br.readLine()) != null) {
            String[] matchData = line.split(",");
            Match match = new Match();
            match.setMatchId(matchData[MATCH_ID]);
            match.setYear(matchData[MATCH_YEAR]);
            match.setTeam1(matchData[MATCH_TEAM1]);
            match.setTeam2(matchData[MATCH_TEAM2]);
            match.setWinnerTeam(matchData[MATCH_WINNER_TEAM]);
            matches.add(match);
        }
        br.close();
        return matches;
    }

    private void findTotalMatchesPlayedPerYear(List<Match> matches) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter the year, available data for 2008 - 2017 or press 0 for all years list");
        System.out.println("You can also press [p] to go to previous menu and [q] to exit program");
        String input = br.readLine();

        try{
            int year = Integer.parseInt(input);
            if(year == 0 || year >= 2008 && year <= 2017){
                if(matchesPlayedPerYear == null){
                    matchesPlayedPerYear = new int[10];  //2008-2017
                    for(Match match: matches){
                        int matchYear = Integer.parseInt(match.getYear());
                        matchesPlayedPerYear[matchYear - 2008]++;
                    }
                }
                if(year == 0){
                    int startYear = 2008;
                    for(int matchesPlayedInYear: matchesPlayedPerYear){
                        System.out.println("Year: " + startYear++ + " Matches Played " + matchesPlayedInYear);
                    }
                }else{
                    System.out.println(String.format("IPL Matches played in %s year is: %s \n", year, matchesPlayedPerYear[year - 2008]));
                }
            }else{
                System.out.println("Not a valid year input, please try again");
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

    private void findTotalMatchesWonPerTeam(List<Match> matches) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter the number aligned to the team, press [0] for all team list or press [p] to return to previous menu");
        printTeams();
        String input = br.readLine();
        try{
            int teamIdx = Integer.parseInt(input);
            if(teamIdx >=0 && teamIdx <=14){
                if(matchesWonByEachTeam == null){
                    matchesWonByEachTeam = new HashMap<>();
                    matchesPlayedByEachTeam = new HashMap<>();
                    for(Match match: matches){
                        matchesPlayedByEachTeam.put(match.getTeam1(), matchesPlayedByEachTeam.getOrDefault(match.getTeam1(), 0) + 1);
                        matchesPlayedByEachTeam.put(match.getTeam2(), matchesPlayedByEachTeam.getOrDefault(match.getTeam2(), 0) + 1);
                        if(!match.getWinnerTeam().isEmpty())
                            matchesWonByEachTeam.put(match.getWinnerTeam(), matchesWonByEachTeam.getOrDefault(match.getWinnerTeam(), 0) + 1);
                    }
                }
                if(teamIdx == 0){
                    for(String IplTeam: matchesWonByEachTeam.keySet()){
                        System.out.println(String.format("Total Matches played by %s: %s \nTotal Matches Won by %s: %s \n",
                                IplTeam,
                                matchesPlayedByEachTeam.get(IplTeam),
                                IplTeam,
                                matchesWonByEachTeam.get(IplTeam)));
                    }
                }else{
                    String team = IPLTeams[teamIdx - 1];
                    System.out.println(String.format("Total Matches played by %s: %s \nTotal Matches Won by %s: %s \n",
                            team,
                            matchesPlayedByEachTeam.get(team),
                            team,
                            matchesWonByEachTeam.get(team)));
                }
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

    private void findExtraRunsConcededPerTeamIn2016(List<Delivery> deliveries) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter the number aligned to the team, press [0] for all teams list or press [p] to return to previous menu");
        printTeams();
        String input = br.readLine();
        try{
            int teamIdx = Integer.parseInt(input);
            if(teamIdx >=0 && teamIdx <=14){
                if(extraRunByTeams == null){
                    extraRunByTeams = new HashMap<>();
                    for(int i = deliveries.size() - 1; i >= 0; i--){
                        Delivery delivery = deliveries.get(i);
                        int matchId = Integer.parseInt(delivery.getMatchId());
                        if(matchId < 577) break;
                        String battingTeam = delivery.getTeam1();
                        int extraRuns = Integer.parseInt(delivery.getExtraRuns());
                        extraRunByTeams.put(battingTeam,extraRunByTeams.getOrDefault(battingTeam, 0) + extraRuns);
                    }
                }
                if(teamIdx == 0){
                    for(String iplTeam: extraRunByTeams.keySet()){
                        System.out.println("Extra runs conceded by team " + iplTeam + " is: " + extraRunByTeams.get(iplTeam));
                    }
                }else{
                    String team = IPLTeams[teamIdx - 1];
                    System.out.println("Extra runs conceded by team " + team + " is: " + extraRunByTeams.get(team));
                }
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

    private List<Map.Entry<String, Double>> getTopNEconomicBowlers(List<Delivery> deliveries) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("How much top economical bowlers you want to get? Press[0] for top 15 list");
        System.out.println("You can also press [p] to go to previous menu and [q] to exit program");
        String input = br.readLine();

        try{
            int topNEconomicBowlers = Integer.parseInt(input);
            if(topNEconomicBowlers >=0 && topNEconomicBowlers <=100){
                if(bowlerDataSorted == null){
                    Map<String,int[]> bowlerAnalysis = new HashMap<>();
                    for(int i = 122712; i <= 136363; i++){
                        Delivery delivery = deliveries.get(i);
                        if(!bowlerAnalysis.containsKey(delivery.getBowlerName())){
                            bowlerAnalysis.put(delivery.getBowlerName(), new int[]{0,0});
                        }
                        if(!delivery.getWideRuns().equals("0") || !delivery.getNoBallRuns().equals("0")){
                            int[] bowlerData = bowlerAnalysis.get(delivery.getBowlerName());
                            bowlerData[0] += (Integer.parseInt(delivery.getWideRuns()) + Integer.parseInt(delivery.getNoBallRuns()) + Integer.parseInt(delivery.getBatsmanRuns()));
                            bowlerAnalysis.put(delivery.getBowlerName(), bowlerData);
                        }else if(!delivery.getByeRuns().equals("0") || !delivery.getLegByeRuns().equals("0")){
                            int[] bowlerData = bowlerAnalysis.get(delivery.getBowlerName());
                            bowlerData[1]++;
                            bowlerAnalysis.put(delivery.getBowlerName(),bowlerData);
                        }else{
                            int[] bowlerData = bowlerAnalysis.get(delivery.getBowlerName());
                            bowlerData[0] += Integer.parseInt(delivery.getBatsmanRuns());
                            bowlerData[1]++;
                            bowlerAnalysis.put(delivery.getBowlerName(), bowlerData);
                        }
                    }
                    Map<String,Double> bowlersEconomyData = new HashMap<>();
                    for(String bowler: bowlerAnalysis.keySet()){
                        int[] bowlerData = bowlerAnalysis.get(bowler);
                        double economy = (double)bowlerData[0]/ ((double)bowlerData[1]/6d);
                        bowlersEconomyData.put(bowler,economy);
                    }
                    bowlerDataSorted = new ArrayList<>(bowlersEconomyData.entrySet());
                    bowlerDataSorted.sort(Map.Entry.comparingByValue());
                }
                if(topNEconomicBowlers == 0){
                    return bowlerDataSorted.subList(0,15);
                }else{
                    return bowlerDataSorted.subList(0,topNEconomicBowlers);
                }

            }else{
                System.out.println("Not a valid year input between 1 to 100 inclusively, please try again");
            }
        }catch(Exception e){
            System.out.println(Arrays.toString(e.getStackTrace()));
            if(input.equals("q") || input.equals("Q")){
                System.out.println("Exited Program");
                System.exit(0);
            }else if(input.equals("p") || input.equals("P")){
                return null;
            }else{
                System.out.println("Either enter number or press q to exit");
            }
        }
        return null;
    }

    private void findTopNBatsmanWithMostRuns(List<Delivery> deliveries) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("How much top Batsman you want to get? or press [0] for top 10 batsman");
        System.out.println("You can also press [p] to go to previous menu and [q] to exit program");
        String input = br.readLine();

        try{
            int topNBatsman = Integer.parseInt(input);
            if(topNBatsman >= 0 && topNBatsman <= 461){
                if(batsmanSortedAccordingToRuns == null){
                    Map<String,Integer> cricketersRuns = new HashMap<>();
                    batsmanSortedAccordingToRuns = new ArrayList<>();
                    for(Delivery delivery: deliveries){
                        int batsmanRuns = Integer.parseInt(delivery.getBatsmanRuns());
                        cricketersRuns.put(delivery.getBatsman(), cricketersRuns.getOrDefault(delivery.getBatsman(), 0) + batsmanRuns);
                    }

                    List<Map.Entry<String,Integer>> entryList = new ArrayList<>(cricketersRuns.entrySet());
                    entryList.sort((a,b)-> b.getValue().compareTo(a.getValue()));
                    batsmanSortedAccordingToRuns.addAll(entryList);
                }
                if(topNBatsman == 0){
                    System.out.println("Top " + topNBatsman + "batsman -> "+ batsmanSortedAccordingToRuns.subList(0,10));
                }else{
                    System.out.println("Top " + topNBatsman + "batsman -> "+ batsmanSortedAccordingToRuns.subList(0,topNBatsman));
                }
            }else{
                System.out.println("Not a valid year input between 1 to 461 inclusively, please try again");
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

    private void findIplWinnerPerYear(List<Match> matches) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter Year to get the winner of IPL [2008 - 2017] or press [0] for all years list");
        System.out.println("You can also press [p] to go to previous menu and [q] to exit program");
        String input = br.readLine();

        try{
            int year = Integer.parseInt(input);
            if(year == 0 || year >= 2008 && year <= 2017){
                if(finalMatchIds == null){
                    finalMatchIds = new int[10];
                    for(Match match: matches){
                        int yearPlayed = Integer.parseInt(match.getYear());
                        int matchId = Integer.parseInt(match.getMatchId());
                        finalMatchIds[yearPlayed - 2008] = Math.max(matchId, finalMatchIds[yearPlayed -2008]);
                    }
                }
                if(year == 0){
                    int iplStartYear = 2008;
                    for(int finalMatchId: finalMatchIds){
                        System.out.println(String.format("IPL winner for year %s is: %s \n", iplStartYear++, matches.get(finalMatchId - 1).getWinnerTeam()));
                    }
                }else{
                    int matchId = finalMatchIds[year - 2008];
                    String ans = matches.get(matchId - 1).getWinnerTeam();
                    System.out.println(String.format("IPL winner for year %s is: %s ", year, ans));
                }
            }else{
                System.out.println("Not a valid year input, please try again");
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

    private void printTeams(){
        int columns = 4;
        int row = (int)Math.ceil( (double)IPLTeams.length / columns);
        int maxIndexWidth = String.valueOf(IPLTeams.length).length();
        int maxTeamWidth = 0;
        for(String IPLTeam: IPLTeams){
            maxTeamWidth = Math.max(maxTeamWidth,IPLTeam.length());
        }

        for(int i = 0; i < row; i++){
            for(int j = 0; j < columns; j++){
                int index = i * columns + j;
                if(index < IPLTeams.length){
                    String formattedIndex = String.format("(%0" + maxIndexWidth + "d)", index);
                    System.out.printf("%s %-" + (maxTeamWidth + 2) + "s \t", formattedIndex, IPLTeams[index]);
                }
            }
            System.out.println();
        }
    }
}
