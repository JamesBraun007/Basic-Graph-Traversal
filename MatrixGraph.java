import java.io.InputStreamReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;

public class MatrixGraph {
    public int[][] adjacencyMatrix;

    public MatrixGraph(int[][] adjMatrix) {
        adjacencyMatrix = adjMatrix;
    }

    public int size() {
        return adjacencyMatrix.length;
    }

    // method to read graph structure from a formatted text file into a matrix graph
    public static MatrixGraph read(String filepath) throws IOException {
        InputStream istr = new FileInputStream(filepath);
        BufferedReader br = new BufferedReader(new InputStreamReader(istr));

        // reading first line of file to retrieve numVertices and create adjacency matrix
        int numVertices = Integer.parseInt(br.readLine());
        int[][] adjMatrix = new int[numVertices][numVertices];
        int currentRow = 0;

        String line = br.readLine();

        // iterating through file, line by line
        while (line != null) {
            if (!line.equals("")) {

                // splitting the current line into array of number strings
                String[] adjVertices = line.split(" ");

                // assigning 1's to the appropriate columns
                for (String adjVertex : adjVertices) {
                    int adjacent = Integer.parseInt(adjVertex);
                    adjMatrix[currentRow][adjacent] = 1;
                }
                // remember that java integer array indexes contain 0s on initialization
                // so if line.equals("") it will be skipped since that row of the matrix already contains 0s
            }

            line = br.readLine();
            currentRow++;
        }

        br.close();
        istr.close();
        return new MatrixGraph(adjMatrix);
    }
}
