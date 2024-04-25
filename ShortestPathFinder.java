import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class ShortestPathFinder {
    public static void main(String[] args) throws FileNotFoundException {
        WeightedGraph graph = new WeightedGraph(17);

        Scanner sc = new Scanner(new File("Turkish Cities.csv"));
        sc.useDelimiter(",");

        if (sc.hasNextLine()) {
            sc.nextLine();
        }

        int sourceVertex = 0;
        while (sc.hasNextLine()) {
            String[] distances = sc.nextLine().split(",");
            for (int destinationVertex = 1; destinationVertex < distances.length; destinationVertex++) {
                int distance = Integer.parseInt(distances[destinationVertex]);
                if (distance != 99999) {
                    graph.addEdge(sourceVertex, destinationVertex - 1, distance);
                }
            }
            sourceVertex++;
        }
        sc.close();

        Map<String, Integer> cityMap = new HashMap<>();
        cityMap.put("Istanbul", 0);
        cityMap.put("Ankara", 1);
        cityMap.put("Izmir", 2);
        cityMap.put("Bursa", 3);
        cityMap.put("Adana", 4);
        cityMap.put("Gaziantep", 5);
        cityMap.put("Konya", 6);
        cityMap.put("Diyarbakir", 7);
        cityMap.put("Antalya", 8);
        cityMap.put("Mersin", 9);
        cityMap.put("Kayseri", 10);
        cityMap.put("Urfa", 11);
        cityMap.put("Malatya", 12);
        cityMap.put("Samsun", 13);
        cityMap.put("Denizli", 14);
        cityMap.put("Batman", 15);
        cityMap.put("Trabzon", 16);

        Map<Integer, String> reverseCityMap = new HashMap<>();
        for (Map.Entry<String, Integer> entry : cityMap.entrySet()) {
            reverseCityMap.put(entry.getValue(), entry.getKey());
        }

        Scanner userInput = new Scanner(System.in);
        System.out.println("Welcome to the Shortest Path Finder!");

        System.out.println("Available Cities:");
        for (String city : cityMap.keySet()) {
            System.out.println("- " + city);
        }

        System.out.print("Enter the source city name: ");
        String sourceCity = userInput.next();
        System.out.print("Enter the destination city name: ");
        String destinationCity = userInput.next();

        if (!cityMap.containsKey(sourceCity) || !cityMap.containsKey(destinationCity)) {
            System.out.println("Error: Invalid city name entered.");
            return;
        }

        int source = cityMap.get(sourceCity);
        int destination = cityMap.get(destinationCity);

        List<Integer> bfsPath = graph.bfs(source, destination);
        List<String> bfsCityPath = bfsPath.stream().map(reverseCityMap::get).collect(Collectors.toList());

        List<Integer> dfsPath = graph.dfs(source, destination);
        List<String> dfsCityPath = dfsPath.stream().map(reverseCityMap::get).collect(Collectors.toList());

        System.out.println("BFS Shortest Path: " + bfsCityPath);
        System.out.println("DFS Shortest Path: " + dfsCityPath);
    }
}
