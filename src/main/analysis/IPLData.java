package src.main.analysis;

import java.util.List;

public class IPLData {

    final static String[] teams = {"Mumbai Indians", "Sunrisers Hyderabad", "Pune Warriors", "Rajasthan Royals", "Royal Challengers Bangalore", "Kolkata Knight Riders", "Gujarat Lions", "Rising Pune Supergiant", "Kochi Tuskers Kerala", "Kings XI Punjab", "Deccan Chargers", "Delhi Daredevils", "Rising Pune Supergiants", "Chennai Super Kings"};

    public static void setCricketMatches(List<String> cricketMatches) {
        IPLData.cricketMatches = cricketMatches;
    }

    public static void setBallDeliveries(List<String> ballDeliveries) {
        IPLData.ballDeliveries = ballDeliveries;
    }

    public static List<String> cricketMatches;

    public static List<String> ballDeliveries;
}
