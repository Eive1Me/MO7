import java.util.ArrayList;

public class Edge {

    private int x,y,weight;

    public Edge(int x, int y, int weight) {
        this.x = x;
        this.y = y;
        this.weight = weight;
    }

    public Edge(){}

    public Edge getWeightFromList(int x, int y, ArrayList<Edge> list){
        for (Edge e :
                list) {
            if (e.getX() == x && e.getY() == y) return e;
        }
        return null;
    }

    @Override
    public String toString() {
        return "Edge{" +
                "x=" + x +
                ", y=" + y +
                ", weight=" + weight +
                '}';
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
