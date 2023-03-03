import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void addComponentCost(String name){
        File file = new File("C:\\Users\\Hugolin\\Downloads\\costs.txt");
        boolean found = false;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = br.readLine()) != null)  // classic way of reading a file line-by-line
                    if (line.replaceAll("\\d", "").strip().matches(name)) {
                        found = true;
                        break;  // if the text is present, we do not have to read the rest after all
                    }
        } catch (IOException e) {
                e.printStackTrace();
        }

        if(!found){  // if the text is not found, it has to be written
            try(PrintWriter pw=new PrintWriter(new FileWriter(file,true))){  // it works with non-existing files too
                pw.println(name);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public static void loadComponents(String components){
        String[] componentsList = components.split("\n");
        for(String comp : componentsList){
            String name = comp.split(" x")[0];
            addComponentCost(name);
        }
    }


    public static void main(String[] args){
        String data = "";
        try {
            data = new String(Files.readAllBytes(Paths.get("C:\\Users\\Hugolin\\Downloads\\shopping.txt")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        String[] mainSplit = data.split("\n\n");

        loadComponents(mainSplit[0]);

        // Fill components cost
        HashMap<String, Integer> componentsCosts = new HashMap<>();
        File file = new File("C:\\Users\\Hugolin\\Downloads\\costs.txt");
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null){  // classic way of reading a file line-by-line
                String[] lineSplit = line.split(" ");
                String cost = lineSplit[lineSplit.length-1];
                String name = line.substring(0, line.length()-1-cost.length());
                //System.out.println(name+"="+cost);
                componentsCosts.put(name, Integer.parseInt(cost));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Calculate items costs
        HashMap<String, Integer> itemCosts = new HashMap<>();
        String[] lines = mainSplit[1].split("\n");
        String itemName = "";
        int itemCost = 0;
        for(String line:lines){
            if(!line.contains("\t")){
                System.out.println(itemName+" "+String.format("%,d", itemCost));
                itemCosts.put(itemName, itemCost);
                itemName = line.split(" x")[0];
                itemCost = 0;
            } else if(!line.contains("Niv. ")){
                String componentName = line.split(" x")[0].trim();
                int componentAmount = Integer.parseInt(line.split(" x")[1]);
                //System.out.println(componentName+"="+componentAmount);
                if(componentsCosts.get(componentName)!=null){
                    itemCost += componentsCosts.get(componentName) * componentAmount;
                }else{
                    //System.out.println(componentName);
                }
            }
        }

    }
}


