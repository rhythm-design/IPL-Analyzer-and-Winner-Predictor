package src.main.analysis;

import java.util.List;

public class IPLData {

    final protected static String[] teams = {"Mumbai Indians", "Sunrisers Hyderabad", "Pune Warriors", "Rajasthan Royals", "Royal Challengers Bangalore", "Kolkata Knight Riders", "Gujarat Lions", "Rising Pune Supergiant", "Kochi Tuskers Kerala", "Kings XI Punjab", "Deccan Chargers", "Delhi Daredevils", "Rising Pune Supergiants", "Chennai Super Kings"};

    public static void setCricketMatches(final List<String> cricketMatches) {
        IPLData.cricketMatches = cricketMatches;
    }

    public static void setBallDeliveries(final List<String> ballDeliveries) {
        IPLData.ballDeliveries = ballDeliveries;
    }

    protected static List<String> cricketMatches;

    protected static List<String> ballDeliveries;
}
