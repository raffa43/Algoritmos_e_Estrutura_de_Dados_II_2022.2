import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

public class Graph {
    public LinkedList<Integer[]>[] adj;
    public int vertices;
    public int edges;

    public Graph(int nodes){
        this.vertices = nodes;
        this.edges = 0;
        this.adj = new LinkedList[nodes];
        for (int i = 0; i < vertices; i++) {
            adj[i] = new LinkedList<Integer[]>();
        }
    }

    public void addEdge(int u, int v, int weight){
        adj[u].add(new Integer[]{v, weight});
        adj[v].add(new Integer[]{u, weight});
        edges++;
    }

    public int getEdges() {
        return edges;
    }

    public String dijkstra(int start, int end){
        ArrayList<Integer> costTo = new ArrayList<>();
        ArrayList<Boolean> visited = new ArrayList<>();
        ArrayList<Integer> path = new ArrayList<>();

        for (int i = 0; i < vertices; i++) {
            costTo.add(Integer.MAX_VALUE);
            visited.add(false);
            path.add(null);
        }
        costTo.set(start, 0);
        path.set(start, start);
        int index = start;
        int last = index;
        while (visited.contains(false)){
            visited.set(index, true);

            int smallestCost = Integer.MAX_VALUE;
            int auxInt = index;

            for (Integer[] edge : adj[index]) {
                if(costTo.get(edge[0]) > costTo.get(index) + edge[1]){
                    costTo.set(edge[0], costTo.get(index) + edge[1]);
                    path.set(edge[0], index);
                }
                if(edge[1] < smallestCost && !(visited.get(edge[0]))){
                    smallestCost = edge[1];
                    auxInt = edge[0];
                }
            }
            if(index == auxInt){
                index = last;
            }else{
                last = index;
                index = auxInt;
            }
        }
        return "Path: " + pathFinder(path, end, start) + "\nCost: " + costTo.get(end);
    }

    private String pathFinder(ArrayList<Integer> path, int goal, int start){
        StringBuilder pathString = new StringBuilder();
        int pos = goal;
        while (pos != start){
            pathString.insert(0, " - " + pos);
            pos = path.get(pos);
        }
        pathString.insert(0, " - " + pos);
        pos = path.get(pos);

        return pathString.toString().substring(2);
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Vertices: ").append(vertices).append("\n").append("Edges: ").append(edges).append("\n");
        for (int i = 0; i < vertices; i++) {
            sb.append(i).append(": ");
            for (int j = 0; j < adj[i].size(); j++) {
                if(j != (adj[i].size()-1)) sb.append(Arrays.toString(adj[i].get(j))).append(", ");
                else sb.append(Arrays.toString(adj[i].get(j)));
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
