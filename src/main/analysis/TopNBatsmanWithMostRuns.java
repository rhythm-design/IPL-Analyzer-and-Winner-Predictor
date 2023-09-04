package src.main.analysis;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;

public class TopNBatsmanWithMostRuns {

    public static void setBallDeliveries(List<String> ballDeliveries) {
        TopNBatsmanWithMostRuns.ballDeliveries = ballDeliveries;
    }

    static List<String> ballDeliveries;

    static Map<String,Integer> cricketersRuns;

    static List<Batsman> batsmanSortedAccordingToRuns;

    private static List<Batsman> cricketersWithMostRuns(int n){
        // 6 and 15
        if(cricketersRuns == null){
            cricketersRuns = new HashMap<>();
            batsmanSortedAccordingToRuns = new ArrayList<>();
            for(String ballDelivery: ballDeliveries){
                String ballDetails[] = ballDelivery.split(",");
                cricketersRuns.put(ballDetails[6], cricketersRuns.getOrDefault(ballDetails[6],0) + Integer.parseInt(ballDetails[15]));
            }
            List<Map.Entry<String,Integer>> entryList = new ArrayList<>(cricketersRuns.entrySet());
            entryList.sort((a,b)-> b.getValue().compareTo(a.getValue()));
            for(Map.Entry<String,Integer> entry: entryList){
                batsmanSortedAccordingToRuns.add(new Batsman(entry.getKey(),entry.getValue()));
            }
        }
        return batsmanSortedAccordingToRuns.subList(0,n);
    }

    public static void getTopNBatsman(BufferedReader br) throws IOException {
        while (true) {
            System.out.println("How much top Batsman you want to get?");
            System.out.println("You can also press [p] to go to previous menu and [q] to exit program");
            String input = br.readLine();

            try{
                int n = Integer.parseInt(input);
                if(n >= 1 && n <= 461){
                    System.out.println("Top " + n + "batsman -> "+ cricketersWithMostRuns(n));
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
    }

    static class Batsman{
        String name;

        int runs;

        public Batsman(String name, int runs) {
            this.name = name;
            this.runs = runs;
        }

        @Override
        public String toString(){
            return this.name + " -> " + this.runs;
        }
    }
}
