import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args){
        String data = "";
        try {
            data = new String(Files.readAllBytes(Paths.get("C:\\Users\\Hugolin\\Downloads\\shopping.txt")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        String[] firstSplit = data.split("\n\n");

        // Costs
        HashMap<String, Integer> componentsCosts = new HashMap<>();
        String[] components = firstSplit[0].split("\n");
        for (String comp:components) {
            String name = comp.split(" x")[0].trim();
            String[] splitComp = comp.split(" ");
            int cost = Integer.parseInt(comp.split(" x")[1]);
            componentsCosts.put(name,cost);
            //System.out.println(name+" "+cost);
        }

        // Costs file
        File file = new File("C:\\Users\\Hugolin\\Downloads\\costs.txt");
        boolean found = false;
        for (Map.Entry<String, Integer> comp : componentsCosts.entrySet()) {
            found = false;  // flag for target txt being present
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = br.readLine()) != null)  // classic way of reading a file line-by-line
                    if (line.contains(comp.getKey())) {

                        found = true;
                        break;  // if the text is present, we do not have to read the rest after all
                    }
            } catch (IOException e) {
                e.printStackTrace();
            }

            if(!found){  // if the text is not found, it has to be written
                try(PrintWriter pw=new PrintWriter(new FileWriter(file,true))){  // it works with
                    // non-existing files too
                    pw.println(comp.getKey());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        // Fill components cost
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null){  // classic way of reading a file line-by-line
                String[] lineSplit = line.split(" ");
                String cost = lineSplit[lineSplit.length-1];
                String name = line.substring(0, line.length()-1-cost.length());
                System.out.println(name+"="+cost);
                componentsCosts.put(name, Integer.parseInt(cost));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Prices
        HashMap<String, Integer> itemsCosts = new HashMap<>();
        String[] items = firstSplit[1].split("\\(");
        String itemName = items[0].split(" x")[0];
        int i = 1;
        while(i < items.length){
            String[] comps = items[i].split("\n");
            int cost = 0;
            for(int j=1; j<comps.length-1; j++){
                String[] comp = comps[j].split(" x");
                String compName = comp[0].trim();
                //System.out.println(compName+" "+comp[1]);
                int amount = Integer.parseInt(comp[1]);
                if(componentsCosts.get(compName)==null){
                    System.out.println(compName);
                }else{
                    cost += componentsCosts.get(compName)*amount;
                }
            }
            System.out.println(itemName+" "+String.format("%,d", cost));
            itemsCosts.put(itemName, cost);
            itemName = comps[comps.length-1].split(" x")[0];
            i++;
        }

    }
}


