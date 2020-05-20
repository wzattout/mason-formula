import java.util.*;

public class masonFormula {

    int[][] takeInput() {
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

    ArrayList<String> paths(int[][] adjacencyMatrix, boolean[] visit, int start, int end, String path, ArrayList<String> result) {
        boolean[] visited = Arrays.copyOf(visit, visit.length);
        path = path.concat(String.valueOf(start) + ',');
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
        return result;
    }

    public static void main(String[] args) {
        masonFormula x = new masonFormula();
        int[][] adjacencyMatrix = x.takeInput();
        boolean[] visit = new boolean[adjacencyMatrix.length];
        for (int i = 0; i < adjacencyMatrix.length; i++) {
            visit[i] = false;
        }
        ArrayList<String> y = new ArrayList<>();
        y = x.paths(adjacencyMatrix, visit, 0, adjacencyMatrix.length - 1, "", y);
        System.out.println("Forward Paths");
        for (String s : y) {
            System.out.println(s + (adjacencyMatrix.length - 1));
        }
        y.clear();
        for (int i = 0; i < adjacencyMatrix.length; i++) {
            y = x.paths(adjacencyMatrix, visit, i, i, "", y);
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
            boolean found = false; int index = 0;
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
        System.out.println("Loops");
        for (String s : y) {
            System.out.println(s + s.charAt(0));
        }
    }
}
