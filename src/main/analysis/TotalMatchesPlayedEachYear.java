package src.main.analysis;

import lombok.Setter;

import java.util.List;
public class TotalMatchesPlayedEachYear {

    @Setter
    private static List<String> cricketMatches;

    static int[] matchesPlayedPerYear;

    public static int totalMatchInYear(int year){
        if(matchesPlayedPerYear == null){
            matchesPlayedPerYear = new int[10];  //2008-2017
            for(String cricketMatch: cricketMatches){
                int commaIndex = cricketMatch.indexOf(',');
                int matchYear = Integer.parseInt(cricketMatch.substring(commaIndex+1,commaIndex+5));
                matchesPlayedPerYear[matchYear - 2008]++;
            }
        }
        return matchesPlayedPerYear[year - 2008];
    }

}
