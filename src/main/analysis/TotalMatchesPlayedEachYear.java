package src.main.analysis;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
public class TotalMatchesPlayedEachYear extends IPLData{

    static int[] matchesPlayedPerYear;

    private static int totalMatchInYear(int year){
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
    public static void getTotalMatchInYear(BufferedReader br) throws IOException {
        while (true) {
            System.out.println("Enter the year, available data for 2008 - 2017");
            System.out.println("You can also press [p] to go to previous menu and [q] to exit program");
            String input = br.readLine();

            try{
                int year = Integer.parseInt(input);
                if(year >= 2008 && year <= 2017){
                    System.out.println(String.format("IPL Matches played in %s year is: %s \n", year, totalMatchInYear(year)));
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
    }

}
