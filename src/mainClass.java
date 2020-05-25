public class mainClass {

    public static void main(String[] args) {
        graph graph = new graph();
        graph.forwardPaths();
        graph.loops();
        graph.nonTouchingLoops();
        masonFormula calculator = new masonFormula();
        calculator.getTransferFunction(graph);
    }
}
