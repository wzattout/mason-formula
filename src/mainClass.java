public class mainClass {

    public static void main(String[] args) {
        graph x = new graph();
        System.out.println("Forward Paths");
        for (String s : x.forwardPaths) {
            System.out.println(s);
        }
        System.out.println("Loops");
        for (String s : x.loops) {
            System.out.println(s);
        }
        System.out.println("Non Touching Loops");
        for (String s : x.nonTouchingLoops) {
            System.out.println(s);
        }
    }
}
