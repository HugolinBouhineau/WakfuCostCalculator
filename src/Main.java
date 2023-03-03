import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

public class Main {
    public static void main(String[] args){
        String data = "";
        try {
            data = new String(Files.readAllBytes(Paths.get("C:\\Users\\Hugolin\\Downloads\\shopping.txt")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        String[] firstSplit = data.split("\n\n");

        // Couts
        HashMap<String, Integer> componentsCosts = new HashMap<>();
        String[] components = firstSplit[0].split("\n");
        for (String comp:components) {
            String name = comp.split(" x")[0].trim();
            String[] splitComp = comp.split(" ");
            int cost = Integer.parseInt(splitComp[splitComp.length-1]);
            componentsCosts.put(name,cost);
            //System.out.println(name+" "+cost);
        }

        // Prix
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
                }
                cost += componentsCosts.get(compName)*amount;
            }
            System.out.println(itemName+" "+String.format("%,d", cost));
            itemsCosts.put(itemName, cost);
            itemName = comps[comps.length-1].split(" x")[0];
            i++;
        }

    }
}


