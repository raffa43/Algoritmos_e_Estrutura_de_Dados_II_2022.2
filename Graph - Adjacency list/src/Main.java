import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Graph graph = new Graph(7);
        graph.addEdge(0,1,2);
        graph.addEdge(0,3,3);
        graph.addEdge(0,2,3);

        graph.addEdge(1,2,4);
        graph.addEdge(1,4,3);

        graph.addEdge(2,3,5);
        graph.addEdge(2,4,1);
        graph.addEdge(2,5,6);

        graph.addEdge(3,5,7);

        graph.addEdge(4,5,8);

        graph.addEdge(5,6,9);

        System.out.println(graph.toString());
        System.out.println(graph.dijkstra(0,6) + "\n");

        Graph prim = graph.prim(0);
        System.out.println("\n" + prim.toString());
    }
}