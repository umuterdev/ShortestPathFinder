import java.util.*;


public class WeightedGraph {
    private int numVertices;
    private List<List<Edge>> adjLists;

    public WeightedGraph(int numVertices) {
        this.numVertices = numVertices;
        adjLists = new ArrayList<>();
        for (int i = 0; i < numVertices; i++) {
            adjLists.add(new LinkedList<>());
        }
    }

    public void addEdge(int src, int dest, int distance) {
        adjLists.get(src).add(new Edge(dest, distance));
        adjLists.get(dest).add(new Edge(src, distance));
    }

    public List<Integer> bfs(int start, int end) {
        boolean[] visited = new boolean[numVertices];
        LinkedList<Integer> queue = new LinkedList<>();
        int[] dist = new int[numVertices];
        int[] prev = new int[numVertices];

        Arrays.fill(dist, Integer.MAX_VALUE);
        Arrays.fill(prev, -1);
        dist[start] = 0;

        visited[start] = true;
        queue.add(start);

        while (!queue.isEmpty()) {
            int current = queue.poll();
            if (current == end) {
                return reconstructPath(prev, end);
            }
            for (Edge edge : adjLists.get(current)) {
                int dest = edge.destination;
                int newDist = dist[current] + edge.distance;
                if (newDist < dist[dest]) {
                    dist[dest] = newDist;
                    prev[dest] = current;
                    if (!visited[dest]) {
                        visited[dest] = true;
                        queue.add(dest);
                    }
                }
            }
        }
        return null;
    }

    public List<Integer> dfs(int start, int end) {
        boolean[] visited = new boolean[numVertices];
        int[] dist = new int[numVertices];
        int[] prev = new int[numVertices];

        Arrays.fill(dist, Integer.MAX_VALUE);
        Arrays.fill(prev, -1);
        dist[start] = 0;

        Stack<Integer> stack = new Stack<>();
        stack.push(start);

        while (!stack.isEmpty()) {
            int current = stack.pop();
            if (current == end) {
                return reconstructPath(prev, end);
            }
            for (Edge edge : adjLists.get(current)) {
                int dest = edge.destination;
                int newDist = dist[current] + edge.distance;
                if (newDist < dist[dest]) {
                    dist[dest] = newDist;
                    prev[dest] = current;
                    if (!visited[dest]) {
                        visited[dest] = true;
                        stack.push(dest);
                    }
                }
            }
        }
        return null;
    }


    private List<Integer> reconstructPath(int[] prev, int end) {
        List<Integer> path = new ArrayList<>();
        for (int at = end; at != -1; at = prev[at]) {
            path.add(at);
        }
        java.util.Collections.reverse(path);
        return path;
    }

    private static class Edge {
        int destination;
        int distance;

        Edge(int destination, int distance) {
            this.destination = destination;
            this.distance = distance;
        }
    }
}