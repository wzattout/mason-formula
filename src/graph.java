import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class graph {

    public int[][] adjacencyMatrix;
    private final boolean[] visit;
    public ArrayList<String> forwardPaths;
    public ArrayList<String> loops;
    public ArrayList<String> nonTouchingLoops = new ArrayList<>();

    public graph() {
        adjacencyMatrix = takeInput();
        visit = new boolean[adjacencyMatrix.length];
        for (int i = 0; i < adjacencyMatrix.length; i++) {
            visit[i] = false;
        }
        forwardPaths();
        loops();
        nonTouchingLoopsArray();
    }

    private int[][] takeInput() {
        int vertices, edges;
        Scanner input = new Scanner(System.in);
        vertices = input.nextInt();
        edges = input.nextInt();
        int[][] adjacencyMatrix = new int[vertices][vertices];
        for (int i = 0; i < vertices; i++) {
            for (int j = 0; j < vertices; j++) {
                adjacencyMatrix[i][j] = (int) Double.POSITIVE_INFINITY;
            }
        }
        int firstVertex, secondVertex, vertexWeight;
        for (int i = 0; i < edges; i++) {
            firstVertex = input.nextInt();
            secondVertex = input.nextInt();
            vertexWeight = input.nextInt();
            adjacencyMatrix[firstVertex - 1][secondVertex - 1] = vertexWeight;
        }
        return adjacencyMatrix;
    }

    private void paths(int[][] adjacencyMatrix, boolean[] visit, int start, int end, String path, ArrayList<String> result) {
        boolean[] visited = Arrays.copyOf(visit, visit.length);
        path = path.concat(String.valueOf(start + 1) + ',');
        visited[start] = true;
        for (int i = 0; i < adjacencyMatrix.length; i++) {
            if (adjacencyMatrix[start][i] != (int) Double.POSITIVE_INFINITY && i == end) {
                result.add(path);
                break;
            }
            if (adjacencyMatrix[start][i] != (int) Double.POSITIVE_INFINITY && !visited[i]) {
                paths(adjacencyMatrix, visited, i, end, path, result);
            }
        }
    }

    public void forwardPaths() {
        ArrayList<String> y = new ArrayList<>();
        paths(adjacencyMatrix, visit, 0, adjacencyMatrix.length - 1, "", y);
        for (int i = 0; i < y.size(); i++) {
            String temp = y.remove(i);
            temp += (adjacencyMatrix.length);
            y.add(i, temp);
        }
        forwardPaths = y;
    }

    public void loops() {
        ArrayList<String> y = new ArrayList<>();
        for (int i = 0; i < adjacencyMatrix.length; i++) {
            paths(adjacencyMatrix, visit, i, i, "", y);
        }
        ArrayList<String[]> z = new ArrayList<>();
        for (String s : y) {
            String[] temp = s.split(",");
            Arrays.sort(temp);
            z.add(temp);
        }
        for (int i = 0; i < z.size(); i++) {
            for (int j = i + 1; j < z.size(); ) {
                if (Arrays.toString(z.get(i)).compareTo(Arrays.toString(z.get(j))) == 0) {
                    z.remove(j);
                } else {
                    j++;
                }
            }
        }
        for (int i = 0; i < y.size(); ) {
            boolean found = false;
            int index = 0;
            String[] temp = y.get(i).split(",");
            Arrays.sort(temp);
            for (int j = 0; j < z.size(); j++) {
                if (Arrays.toString(z.get(j)).compareTo(Arrays.toString(temp)) == 0) {
                    found = true;
                    index = j;
                    break;
                }
            }
            if (found) {
                z.remove(index);
                i++;
            } else {
                y.remove(i);
            }
        }
        for (int i = 0; i < y.size(); i++) {
            String temp = y.remove(i);
            temp += temp.split(",")[0];
            y.add(i, temp);
        }
        loops = y;
    }

    private void nonTouchingLoopsArray() {
        boolean[][] array = new boolean[loops.size()][loops.size()];
        for (int i = 0; i < loops.size(); i++) {
            for (int j = 0; j < loops.size(); j++) {
                array[i][j] = false;
            }
        }
        for (int i = 0; i < array.length; i++) {
            for (int j = i + 1; j < array.length; j++) {
                array[i][j] = isTouching(loops.get(i), loops.get(j));
                array[j][i] = array[i][j];
                if (array[i][j]){
                    String x = "";
                    x += (i + 1) + "," + (j + 1);
                    nonTouchingLoops.add(x);
                }
            }
        }
    }

    private boolean isTouching(String A, String B) {
        String[] a = A.split(",");
        String[] b = B.split(",");
        Arrays.sort(a);
        Arrays.sort(b);
        for (int i = 0, j = 0; i < a.length && j < b.length; ) {
            if (Integer.parseInt(a[i]) == Integer.parseInt(b[j])) {
                return false;
            } else if (Integer.parseInt(a[i]) < Integer.parseInt(b[j])) {
                i++;
            } else {
                j++;
            }
        }
        return true;
    }
}
