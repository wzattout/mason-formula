package sample;

import com.brunomnsilva.smartgraph.graph.Digraph;
import com.brunomnsilva.smartgraph.graph.DigraphEdgeList;
import com.brunomnsilva.smartgraph.graphview.SmartCircularSortedPlacementStrategy;
import com.brunomnsilva.smartgraph.graphview.SmartGraphPanel;
import com.brunomnsilva.smartgraph.graphview.SmartPlacementStrategy;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class draw extends Application {

    graph graph;

    @Override
    public void start(Stage primaryStage) {
        this.graph = mainClass.graph;
        Digraph<String, String> g = new DigraphEdgeList<>();
        for (int i = 0; i < graph.adjacencyMatrix.length; i++){
            g.insertVertex(String.valueOf(i + 1));
        }
        StringBuilder space = new StringBuilder();
        for (int i = 0; i < graph.adjacencyMatrix.length; i++){
            for (int j = 0; j < graph.adjacencyMatrix.length; j++){
                if (graph.adjacencyMatrix[i][j] != (int) Double.POSITIVE_INFINITY){
                    g.insertEdge(String.valueOf(i + 1), String.valueOf(j + 1), space + String.valueOf(graph.adjacencyMatrix[i][j]) + space);
                    space.append(" ");
                }
            }
        }
        SmartPlacementStrategy strategy = new SmartCircularSortedPlacementStrategy();
        SmartGraphPanel<String, String> graphView = new SmartGraphPanel<>(g, strategy);
        Scene scene = new Scene(graphView, 1024, 768);
        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setTitle("Signal Flow Graph");
        stage.setScene(scene);
        stage.show();
        graphView.init();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
