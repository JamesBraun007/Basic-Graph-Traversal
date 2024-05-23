
public class DirectedCheck {

    public static int[] computeInDegrees(ListGraph a) {
        int[] inDeg = new int[a.size()];

        for (int i = 0; i < a.size(); i++) {
            ListGraph.Node vertexOutList = a.adjacencyList[i];
            while (vertexOutList != null) {
                int vertexIn = vertexOutList.key;
                inDeg[vertexIn] = inDeg[vertexIn] + 1;

                vertexOutList = vertexOutList.next;
            }
        }

        return inDeg;
    }

    public static int[] dagCheck(ListGraph a) {
        boolean isDAG;
        int[] sourcesAndSinks = new int[2];

        boolean[] marked = new boolean[a.size()];

        isDAG = DAGDFScheck(a, marked, 0, 0);
        if (!isDAG) {
            sourcesAndSinks[0] = -1;
            sourcesAndSinks[1] = -1;
            return sourcesAndSinks;
        }

        int edgeTo[] = new int[a.size()];
        for (int i = 0; i < edgeTo.length; i++) {
            edgeTo[i] = -1;
        }

        for (int i = 0; i < a.size(); i++) {
            ListGraph.Node vertexNeighbors = a.adjacencyList[i];
            while (vertexNeighbors != null) {
                edgeTo[vertexNeighbors.key] = i;
                vertexNeighbors = vertexNeighbors.next;
            }
        }

        for (int i = 0; i < a.size(); i++) {
            ListGraph.Node vertexNeighbors = a.adjacencyList[i];
            if (vertexNeighbors == null) {
                sourcesAndSinks[1]++;
            }
            if (edgeTo[i] == -1) {
                sourcesAndSinks[0]++;
            }
        }


        return sourcesAndSinks;
    }

    public static boolean DAGDFScheck(ListGraph a, boolean[] marked, int vertex, int original) {
        marked[vertex] = true;
        ListGraph.Node vertexNeighbor = a.adjacencyList[vertex];

        // for every neighbor of vertex
        while (vertexNeighbor != null) {
            // call DFS if not visited
            if (!marked[vertexNeighbor.key]) {

                if (!DAGDFScheck(a, marked, vertexNeighbor.key, original)) {
                    return false;
                }

            } else if (vertexNeighbor.key == original) {
                return false;
            }
            vertexNeighbor = vertexNeighbor.next;
        }

        return true;
    }
}
