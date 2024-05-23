import java.io.InputStreamReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;

public class ListGraph {
    public Node[] adjacencyList;

    // constructor for ListGraph
    public ListGraph(Node[] adjList) {
        adjacencyList = adjList;
    }

    // constructor for Node
    public static class Node {
        int key;
        Node next;

        public Node(int key) {
            this.key = key;
            next = null;
        }
    }

    // method to return the size of a given ListGraph
    public int size() {
        return adjacencyList.length;
    }

    // method to read graph structure from a formatted text file into  an adjacency list
    public static ListGraph read(String filepath) throws IOException {
        InputStream istr = new FileInputStream(filepath);
        BufferedReader br = new BufferedReader(new InputStreamReader(istr));

        // reading first line of file to retrieve numVertices and create adjacency list
       int numVertices = Integer.parseInt(br.readLine());
       Node[] adjList = new Node[numVertices];
       int listIndex = 0;

       String line = br.readLine();

        // iterating through file, line by line
        while (line != null) {
            if (!line.equals("")) {

                // splitting the current line into array of number strings
                String[] adjVertices = line.split(" ");

                // for each number string in the array of the current line
                for (String adjVertex : adjVertices) {
                    // create a new node of the current vertex
                    Node newNode = new Node(Integer.parseInt(adjVertex));

                    // set the head to be the first element of linked list at adjList[listIndex]
                    Node head = adjList[listIndex];

                    // if linked list is empty, make the new node the head of the list
                    if (head == null) {
                        adjList[listIndex] = newNode;

                        // otherwise, iterate to end of linked list until null is found
                    } else {
                        while (head.next != null) {
                            head = head.next;
                        }

                        // append the new node to the end of the linked list
                        head.next = newNode;
                    }
                }
            } else {
                adjList[listIndex] = null;
            }

            // move to next line of the file and the next index of the adjacency list
            line = br.readLine();
            listIndex++;
        }

        br.close();
        istr.close();

        return new ListGraph(adjList);
    }
}
