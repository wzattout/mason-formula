package sample;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class graph {

    public final int[][] adjacencyMatrix;
    private final boolean[] visit;
    public ArrayList<String> forwardPaths;
    public ArrayList<Integer> forwardPathsGains;
    public ArrayList<String> loops;
    public ArrayList<Integer> loopsGains;
    public ArrayList<String> nonTouchingLoops = new ArrayList<>();
    private boolean[][] nonTouchingLoopsArray;

    public graph() {
        adjacencyMatrix = takeInput();
        visit = new boolean[adjacencyMatrix.length];
        for (int i = 0; i < adjacencyMatrix.length; i++) {
            visit[i] = false;
        }
    }

    private int[][] takeInput() {
        int vertices, edges;
        Scanner input = new Scanner(System.in);
        System.out.println("Enter the number of vertices:");
        vertices = input.nextInt();
        System.out.println("Vertex '1' is the source and Vertex '" + vertices + "' is the destination");
        System.out.println("Enter the number of edges:");
        edges = input.nextInt();
        int[][] adjacencyMatrix = new int[vertices][vertices];
        for (int i = 0; i < vertices; i++) {
            for (int j = 0; j < vertices; j++) {
                adjacencyMatrix[i][j] = (int) Double.POSITIVE_INFINITY;
            }
        }
        int firstVertex, secondVertex, vertexWeight;
        System.out.println("Enter the number of edges details (vertex1 vertex2 weight):");
        System.out.println("All details must be integers");
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
        System.out.println("Forward Paths:");
        int i = 1;
        if (forwardPaths.size() == 0){
            System.out.println("No Forward Paths");
        }
        for (String s : forwardPaths) {
            System.out.print(i++ + ") ");
            System.out.println(s);
        }
        forwardPathsGains = getGains(forwardPaths);
    }

    public void loops() {
        ArrayList<String> y = new ArrayList<>();
        for (int i = 0; i < adjacencyMatrix.length; i++) {
            paths(adjacencyMatrix, visit, i, i, "", y);
        }
        loops = handleRepetitions(y);
        for (int i = 0; i < y.size(); i++) {
            String temp = y.remove(i);
            temp += temp.split(",")[0];
            y.add(i, temp);
        }
        System.out.println("Loops:");
        if (loops.size() == 0){
            System.out.println("No Loops");
        }
        int i = 1;
        for (String s : loops) {
            System.out.print(i++ + ") ");
            System.out.println(s);
        }
        loopsGains = getGains(loops);
    }

    private void nonTouchingLoopsArray() {
        nonTouchingLoopsArray = new boolean[loops.size()][loops.size()];
        for (int i = 0; i < loops.size(); i++) {
            for (int j = 0; j < loops.size(); j++) {
                nonTouchingLoopsArray[i][j] = false;
            }
        }
        for (int i = 0; i < nonTouchingLoopsArray.length; i++) {
            for (int j = i + 1; j < nonTouchingLoopsArray.length; j++) {
                nonTouchingLoopsArray[i][j] = isNonTouching(loops.get(i), loops.get(j));
                nonTouchingLoopsArray[j][i] = nonTouchingLoopsArray[i][j];
            }
        }
    }

    public void nonTouchingLoops() {
        nonTouchingLoopsArray();
        for (int i = 0; i < nonTouchingLoopsArray.length; i++) {
            for (int j = i + 1; j < nonTouchingLoopsArray.length; j++) {
                if (nonTouchingLoopsArray[i][j]) {
                    boolean[] visited = new boolean[nonTouchingLoopsArray.length];
                    for (int k = 0; k < nonTouchingLoopsArray.length; k++) {
                        visited[k] = false;
                    }
                    visited[i] = true;
                    getNonTouchingLoops(j, visited, String.valueOf(i + 1));
                }
            }
        }
        handleRepetitions(nonTouchingLoops);
        System.out.println("Non Touching Loops:");
        if (nonTouchingLoops.size() == 0){
            System.out.println("No Non Touching Loops");
        }
        int i = 1;
        for (String s : nonTouchingLoops) {
            System.out.print(i++ + ") ");
            System.out.println(s);
        }
    }

    private void getNonTouchingLoops(int loop, boolean[] visited, String recentNonTouchingLoops) {
        boolean[] newVisited = Arrays.copyOf(visited, visited.length);
        recentNonTouchingLoops += ("," + (loop + 1));
        nonTouchingLoops.add(recentNonTouchingLoops);
        newVisited[loop] = true;
        for (int i = 0; i < newVisited.length; i++) {
            if (nonTouchingLoopsArray[loop][i] && !newVisited[i] && isOkToAllPreviousLoops(recentNonTouchingLoops, i)) {
                getNonTouchingLoops(i, newVisited, recentNonTouchingLoops);
            }
        }
    }

    private boolean isOkToAllPreviousLoops(String recentNonTouchingLoops, int loop) {
        String[] previousLoops = recentNonTouchingLoops.split(",");
        for (String previousLoop : previousLoops) {
            if (!nonTouchingLoopsArray[Integer.parseInt(previousLoop) - 1][loop]) {
                return false;
            }
        }
        return true;
    }

    protected boolean isNonTouching(String A, String B) {
        if (A.length() == 0 || B.length() == 0)
            return true;
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

    private ArrayList<String> handleRepetitions(ArrayList<String> y) {
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
        return y;
    }

    private ArrayList<Integer> getGains(ArrayList<String> paths) {
        ArrayList<Integer> result = new ArrayList<>();
        for (String s : paths) {
            int temp = 1;
            String[] array = s.split(",");
            for (int i = 1; i < array.length; i++) {
                temp *= adjacencyMatrix[Integer.parseInt(array[i - 1]) - 1][Integer.parseInt(array[i]) - 1];
            }
            result.add(temp);
        }
        return result;
    }
}
