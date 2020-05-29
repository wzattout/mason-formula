package sample;

public class masonFormula {

    private int setDelta(graph graph, String x) {
        int result = 1;
        for (int i = 0; i < graph.loopsGains.size(); i++) {
            if (graph.isNonTouching(graph.loops.get(i), x)) {
                result -= graph.loopsGains.get(i);
            }
        }
        boolean finish = false, add = false;
        int order = 2, temp;
        while (!finish) {
            finish = true;
            for (int i = 0; i < graph.nonTouchingLoops.size(); i++) {
                String[] target = graph.nonTouchingLoops.get(i).split(",");
                temp = 1;
                if (target.length == order) {
                    for (String loop : target) {
                        if (graph.isNonTouching(x, loop)) {
                            temp *= graph.loopsGains.get(Integer.parseInt(loop) - 1);
                            add = true;
                        } else {
                            add = false;
                            break;
                        }
                    }
                    finish = false;
                    if (add) {
                        result += Math.pow(-1, order) * temp;
                    }
                }
            }
            order++;
        }
        return result;
    }

    private int[] setDeltas(graph graph) {
        int[] result = new int[graph.forwardPaths.size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = setDelta(graph, graph.forwardPaths.get(i));
        }
        return result;
    }

    public void getTransferFunction(graph graph) {
        int delta = setDelta(graph, "");
        int[] deltas = setDeltas(graph);
        int sigma = 0;
        for (int i = 0; i < deltas.length; i++) {
            sigma += (deltas[i] * graph.forwardPathsGains.get(i));
        }
        System.out.print("Transfer function = ");
        System.out.println(sigma / (double) delta);
    }
}
