package src.main.analysis;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Collections;


public class TopSortedEconomicalBowlersIn2015 {
    //518-576

    private static List<String> ballDeliveries;

    public static void setBallDeliveries(List<String> ballDeliveries) {
        TopSortedEconomicalBowlersIn2015.ballDeliveries = ballDeliveries;
    }
    static Map<String,int[]> bowlerAnalysis;

    static List<Bowler> sortedBowlersEconomies;

    public static List<Bowler> topNEconomyBowlers(int n){
        // maximum size is 100
        constructBowlersData();
        return sortedBowlersEconomies.subList(0,n);
    }

    public static void getTopNEconomyBowlers(BufferedReader br) throws IOException {
        while (true) {
            System.out.println("How much top economical bowlers you want to get?");
            System.out.println("You can also press [p] to go to previous menu and [q] to exit program");
            String input = br.readLine();

            try{
                int n = Integer.parseInt(input);
                if(n >=1 && n <=100){
                    System.out.println("Top " + n + "economical bowlers -> "+topNEconomyBowlers(n));
                }else{
                    System.out.println("Not a valid year input between 1 to 100 inclusively, please try again");
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

    private static void constructBowlersData(){
        if(bowlerAnalysis == null){
            bowlerAnalysis = new HashMap<>();
            sortedBowlersEconomies = new ArrayList<>();
            //122712- 136363
            for(int i = 122712; i <= 136363; i++){
                String ballDetails[] = ballDeliveries.get(i).split(",");
                if(!bowlerAnalysis.containsKey(ballDetails[8])){
                    // 0th index for runs and 1st index for balls
                    bowlerAnalysis.put(ballDetails[8],new int[]{0,0});
                }
                putItem(ballDetails,bowlerAnalysis);
            }

            for(String bowler: bowlerAnalysis.keySet()){
                int[] bowlerData = bowlerAnalysis.get(bowler);
                double economy = (double)bowlerData[0]/ ((double)bowlerData[1]/6d);
                sortedBowlersEconomies.add(new Bowler(bowler,economy));
            }
            Collections.sort(sortedBowlersEconomies);
        }
    }

    private static void putItem(String[] ballDetails, Map<String,int[]> bowlerAnalysis){
        /*
            > Bowler name at 8th index
            > Wide bowl at 10th index
            > Bye at 11th index
            > LegBye at 12th index
            > NO ball at 13th index
            > Batsman Run at 15th index
        */

        // bye and leg-byes  not calculated for analysis but ball is counted
        // wide and no-balls runs are added but ball is not counted
        // batsman-run both ball and runs would be added
        // Wikipedia definition- https://en.wikipedia.org/wiki/Economy_rate#:~:text=Byes%20and%20leg%20byes%20are%20not%20charged%20to%20the%20bowler%27s%20analysis%2C%20and%20so%20do%20not%20harm%20their%20economy%20rate.%20On%20the%20other%20hand%2C%20the%20bowler%20is%20penalised%20for%20wides%20and%20no%2Dballs%2C%20though%20neither%20adds%20a%20ball%20to%20the%20over.
        if(!ballDetails[10].equals("0") || !ballDetails[13].equals("0")){
            // Wide and no ball case
            int[] bowlerData = bowlerAnalysis.get(ballDetails[8]);
            bowlerData[0] = bowlerData[0] + Integer.parseInt(ballDetails[10]) + Integer.parseInt(ballDetails[13]);
            bowlerAnalysis.put(ballDetails[8],bowlerData);
        }else if(!ballDetails[11].equals("0") || !ballDetails[12].equals("0")){
            // bye and leg byes
            int[] bowlerData = bowlerAnalysis.get(ballDetails[8]);
            bowlerData[1]++;
            bowlerAnalysis.put(ballDetails[8],bowlerData);
        }else{
            int[] bowlerData = bowlerAnalysis.get(ballDetails[8]);
            bowlerData[0] = bowlerData[0] + Integer.parseInt(ballDetails[15]);
            bowlerData[1]++;
            bowlerAnalysis.put(ballDetails[8],bowlerData);
        }
    }

    static class Bowler implements Comparable<Bowler>{
        String bowler;
        double economy;

        Bowler(String bowler, double economy){
            this.bowler = bowler;
            this.economy = economy;
        }

        public int compareTo(Bowler obj){
            if(this.economy < obj.economy){
                return -1;
            }else if(obj.economy < this.economy){
                return 1;
            }else{
                return 0;
            }
        }

        @Override
        public String toString(){
            return this.bowler + " -> " + String.format("%.2f", this.economy);
        }
    }
}
