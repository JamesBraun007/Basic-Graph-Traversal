
public class UndirectedCheck {

    public static boolean treeCheck(ListGraph a) {
        boolean[] marked = new boolean[a.size()];

        boolean connected = true;
        boolean DFScheck = DFSCycleCheck(a, marked, 0, -1);

        // checking if any vertices were not visited (graph is not connected)
        for (int i = 0; i < a.size(); i++) {
            if (!marked[i]) {
                connected = false;
                break;
            }
        }

       return connected && DFScheck;
    }

    public static int countTriangles(MatrixGraph a) {
        int numTriangles = 0;
        // iterate through column
        for (int vertexOne = 0; vertexOne < a.size(); vertexOne++) {
            // iterate through row
            for (int vertexTwo = 0; vertexTwo < a.size(); vertexTwo++) {
                // check if edge exists from vertexOne to vertexTwo
                if (a.adjacencyMatrix[vertexOne][vertexTwo] == 1) {
                    // if edge exists, loop through columns of vertexOne and vertexTwo
                    for (int vertexThree = 0; vertexThree < a.size(); vertexThree++) {
                        // if edge exists from vertexOne to vertexThree and vertexTwo to vertexThree
                        if (a.adjacencyMatrix[vertexOne][vertexThree] == 1 && a.adjacencyMatrix[vertexTwo][vertexThree] == 1) {
                            numTriangles++;
                        }
                    }
                }
            }
        }

        // must divide by 6 because there are 2 "copies" of each vertex in the matrix, and 3 possible triangles using
        // the 3 vertices
        return numTriangles / 6;
    }

    public static double vertexClusterCoeff(ListGraph a, int u) {
        int numNeighbors = 0;
        ListGraph.Node currentVertex = a.adjacencyList[u];

        // iterate through the neighbors of u to calculate number of neighbors (which is # of edges)
        while (currentVertex != null) {
            numNeighbors++;
            currentVertex = currentVertex.next;
        }

        if (numNeighbors < 2) {
            return 0.0;
        }

        // create array for neighbors of u
        int[] neighbors = new int[numNeighbors];

        currentVertex = a.adjacencyList[u];
        int neighborIndex = 0;

        // iterate through neighbors of u and assign them to neighbors array
        while (currentVertex != null) {
            neighbors[neighborIndex] = currentVertex.key;
            neighborIndex++;
            currentVertex = currentVertex.next;
        }

        int friends = 0;

        // iterating through neighbors of u
        for (int neighbor : neighbors) {
            // for each neighbor of u, need to check vertices adjacent
            ListGraph.Node listOfVNeighbors = a.adjacencyList[neighbor];
            while (listOfVNeighbors != null) {
                for (int neighborNeighbor : neighbors ) {
                    int neighborOfV = listOfVNeighbors.key;
                    if (neighborOfV == neighborNeighbor) {
                        friends++;
                        break;
                    }
                }
                listOfVNeighbors = listOfVNeighbors.next;
            }
        }

        return ((double) friends) / (numNeighbors * (numNeighbors - 1));

    }

    public static double graphClusterCoeff(ListGraph a) {
        double graphClusterResult = 0.0;

        // iterating from 0 to n vertices in adjacency list
        for (int i = 0; i < a.size(); i++) {
            graphClusterResult = graphClusterResult + vertexClusterCoeff(a, i);
        }

        return graphClusterResult / a.size();
    }

    public static boolean DFSCycleCheck(ListGraph a, boolean[] marked, int vertex, int predecessor) {
        marked[vertex] = true;
        ListGraph.Node vertexNeighbor = a.adjacencyList[vertex];

        // for every neighbor of vertx
        while (vertexNeighbor != null) {
            // call DFS if not visited
            if (!marked[vertexNeighbor.key]) {
                DFSCycleCheck(a, marked, vertexNeighbor.key, vertex);

            // if visited and the neighbor is not equal to the predecessor of the node, there is a cycle
            } else if (marked[vertexNeighbor.key] && vertexNeighbor.key != predecessor) {
                return false;
            }
            vertexNeighbor = vertexNeighbor.next;
        }

        return true;
    }
}
