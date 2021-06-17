
public class Main {

    public static void main(String[] args) throws Exception {


        int endFlag = 0;
        String[] array = new String[3];
        //while (endFlag != 1) {
            String resStringConstruction = Intervals.intervalConstruction(array);
            String resStringIdentification = Intervals.intervalIdentification(array);
            System.out.println(resStringConstruction);
            System.out.println(resStringIdentification);
        //}
    }
}