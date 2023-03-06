import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    static ArrayList<Edge> edgeArrayList = Utils.setData();
    static Edge path = new Edge();
    public static void main(String[] args) {

        ArrayList<Integer>[] entersExits = getEntersExits(edgeArrayList);
        System.out.println("Входы и выходы системы:\nВходы системы: " + entersExits[0]);
        System.out.println("Выходы системы: " + entersExits[1] + '\n');

        System.out.println("Ранний срок выполнения событий: ");
        int[] earlyArr = earlyTerm();
        printArr(earlyArr);
        System.out.println("Поздний срок выполнения событий: ");
        int[] lateArr = lateTerm(earlyArr[8]);
        printArr(lateArr);

        System.out.println("\nВремя выполнения проекта (длина критического пути): "+earlyArr[8] + '\n');

        System.out.println("Полные резервы для работы: ");
        int[] fullReserve = fullReserve(edgeArrayList, lateArr, earlyArr);
        printArr(fullReserve);
        System.out.println("Свободные резервы для работы: ");
        int[] freeReserve = freeReserve(edgeArrayList, lateArr, earlyArr);
        printArr(freeReserve);
        System.out.println();

        System.out.println("Критический путь: ");
        ArrayList<Integer> criticPath = criticPath(lateArr, earlyArr);
        System.out.println(Arrays.toString(criticPath.stream().mapToInt(Integer::intValue).toArray()) + "\n");

        for (Edge e : edgeArrayList) {
            if (e.getX() == 6 && e.getY() == 9) {
                e.setWeight(e.getWeight() + 4);
            }
        }

        int [] newEarlyArr = earlyTerm();
        int[] newFullReserve = fullReserve(edgeArrayList, lateArr, earlyArr);

        System.out.println("Как изменится срок выполнения работ: ");
        if (earlyArr[8] == newEarlyArr[8]){
            System.out.println("Не изменится\n");
        } else {
            int value = newEarlyArr[8] - earlyArr[8];
            System.out.println("Изменится на " + value + " единиц\n");
        }

        System.out.println("Как изменится полный резерв времени работ: ");
        printArr(fullReserve);
        printArr(newFullReserve);
    }

    public static void printArr(int[] arr){
        String[] headers = new String[arr.length];
        String[] values = new String[arr.length];
        if (arr.length == edgeArrayList.size()){
            for (int i = 0; i < headers.length; i++){
                headers[i] = edgeArrayList.get(i).getX() + "->" + edgeArrayList.get(i).getY();
                values[i] = String.valueOf(arr[i]);
            }
        } else {
            for (int i = 0; i < arr.length; i++){
                headers[i] = String.valueOf(i + 1);
                values[i] = String.valueOf(arr[i]);
            }
        }
        String[][] table = new String[][]{headers, values};
        for (int i = 0; i < table.length; i++) {
            for (int j = 0; j < table[0].length; j++) {
                System.out.print(String.format("%7s", table[i][j]));
            }
            System.out.println("");
        }
    }

    public static int[] earlyTerm(){
        int[] earlyArr = new int[9];
        for (int i = 0; i<9; i++){
            if (i != 0) {
                int max = Integer.MIN_VALUE;
                if (path.getWeightFromList(1,i+1,edgeArrayList)!=null){
                    max = path.getWeightFromList(1,i+1,edgeArrayList).getWeight();
                }
                for (int j=0; j<i; j++){
                    if (path.getWeightFromList(j+1,i+1,edgeArrayList)!=null &&
                            max<earlyArr[j]+path.getWeightFromList(j+1,i+1,edgeArrayList).getWeight()){
                        max = earlyArr[j]+path.getWeightFromList(j+1,i+1,edgeArrayList).getWeight();
                    }
                }
                earlyArr[i] = max;
            } else {
                earlyArr[i] = 0;
            }
        }
        return earlyArr;
    }

    public static int[] lateTerm(int element){
        int[] lateArr = new int[9];
        Arrays.fill(lateArr, element);
        for (int i=7; i>=0; i--){
            int min = Integer.MAX_VALUE;
            if (path.getWeightFromList(i+1,9,edgeArrayList)!=null){
                min = lateArr[i]-path.getWeightFromList(i+1,9,edgeArrayList).getWeight();
            }
            for (int j=7;j>i;j--){
                if (path.getWeightFromList(i+1,j+1,edgeArrayList)!=null &&
                        min>lateArr[i]-path.getWeightFromList(i+1,j+1,edgeArrayList).getWeight()-(lateArr[8]-lateArr[j])){
                    min = lateArr[i]-path.getWeightFromList(i+1,j+1,edgeArrayList).getWeight()-(lateArr[8]-lateArr[j]);
                }
            }
            lateArr[i] = min;
        }
        return lateArr;
    }

    public static ArrayList<Integer>[] getEntersExits(ArrayList<Edge> list){
        ArrayList<Integer>[] nodeList = new ArrayList[]{new ArrayList<>(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9)), new ArrayList<>(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9))};
        for (Edge e: list) {
            if (nodeList[0].contains(e.getY())){
                nodeList[0].remove((Integer) e.getY());
            }
            if (nodeList[1].contains(e.getX())){
                nodeList[1].remove((Integer) e.getX());
            }
        }
        return nodeList;
    }

    public static int[] fullReserve(ArrayList<Edge> list, int[] lateArr, int[] earlyArr){
        int count = 1;
        int[] fullReserve = new int[list.size()];
        //Значение позднего срока от конечной точки - значение веса ребра - раннего срока от начальной точки
        for (Edge e:list) {
            int res = lateArr[e.getY()-1] - e.getWeight() - earlyArr[e.getX()-1];
            fullReserve[count-1] = res;
            count++;
        }
        return fullReserve;
    }

    public static int[] freeReserve(ArrayList<Edge> list, int[] lateArr, int[] earlyArr){
        int count = 1;
        int[] freeReserve = new int[list.size()];
        //Значение раннего срока от конечной точки - значение веса ребра - раннего срока от начальной точки
        for (Edge e:list) {
            int res = earlyArr[e.getY()-1] - e.getWeight() - earlyArr[e.getX()-1];
            freeReserve[count-1] = res;
            count++;
        }
        return freeReserve;
    }

    public static ArrayList<Integer> criticPath(int[] lateArr, int[] earlyArr){
        ArrayList<Integer> critWay = new ArrayList<>();
        for (int i = 0; i < lateArr.length; i++) {
            if (lateArr[i] == earlyArr[i])
                critWay.add(i + 1);
        }
        return critWay;
    }

}
