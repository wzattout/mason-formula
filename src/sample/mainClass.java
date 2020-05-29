package sample;

public class mainClass {

    public static graph graph = new graph();

    public static void main(String[] args) {
        graph.forwardPaths();
        graph.loops();
        graph.nonTouchingLoops();
        masonFormula calculator = new masonFormula();
        calculator.getTransferFunction(graph);
        draw.launch(draw.class ,args);
    }
}
