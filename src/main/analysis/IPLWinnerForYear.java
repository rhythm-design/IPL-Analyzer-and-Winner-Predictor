package src.main.analysis;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;

public class IPLWinnerForYear extends IPLData{

    static int[] finalMatchIds;

    private static String iplWinner(int yearWinner){
        // year to id
        // 0- id 1 - year
        if(finalMatchIds == null){
            finalMatchIds = new int[10];
            for(String cricketMatch: cricketMatches){
                String[] matchDetails = cricketMatch.split(",");
                int year = Integer.parseInt(matchDetails[1]);
                int id = Integer.parseInt(matchDetails[0]);
                finalMatchIds[year - 2008] = Math.max(id, finalMatchIds[year - 2008]);
            }
        }
        int matchId = finalMatchIds[yearWinner - 2008];
        return cricketMatches.get(matchId - 1).split(",")[10];
    }

    public static void getIPLWinnerForYear(BufferedReader br) throws IOException {
        while (true) {
            System.out.println("Enter Year to get the winner of IPL [2008 - 2017]");
            System.out.println("You can also press [p] to go to previous menu and [q] to exit program");
            String input = br.readLine();

            try{
                int year = Integer.parseInt(input);
                if(year >= 2008 && year <= 2017){
                    System.out.println(String.format("IPL winner for year %s is: %s \n", year, iplWinner(year)));
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
