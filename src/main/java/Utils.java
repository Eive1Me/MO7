import java.util.ArrayList;

public class Utils {
    public static ArrayList<Edge> setData(){
        return new ArrayList<>(){
            {
                add(new Edge(1,2,11));
                add(new Edge(1,3,15));
                add(new Edge(1,4,9));
                add(new Edge(1,6,13));
                add(new Edge(2,5,12));
                add(new Edge(2,6,12));
                add(new Edge(3,4,11));
                add(new Edge(3,6,5));
                add(new Edge(4,7,14));
                add(new Edge(5,8,10));
                add(new Edge(6,8,14));
                add(new Edge(6,9,13));
                add(new Edge(7,9,11));
                add(new Edge(8,9,10));
            }
        };
    }
}
