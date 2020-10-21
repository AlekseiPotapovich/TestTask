import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {

        String[] array = new String[3];
        //MyException exp = new MyException();
        Scanner sc = new Scanner(System.in);
        String[] intervalName = new String[]{"m2", "M2", "m3", "M3", "P4", "P5", "m6", "M6", "m7", "M7", "P8"};
        String[] startingNoteForIntervalIdentification = new String[]
                {"Cbb", "Cb", "C", "C#", "C##", "Dbb", "Db", "D", "D#", "D##",
                        "Ebb", "Eb", "E", "E#", "E##", "Fbb", "Fb", "F", "F#",
                        "F##", "Gbb", "Gb", "G", "G#", "G##", "Abb", "Ab", "A",
                        "A#", "A##", "Bbb", "Bb", "B", "B#", "B##"};
        String[] startingNoteForIntervalConstruction = new String[]
                {"Cb", "C", "C#", "Db", "D", "D#", "Eb", "E", "E#", "Fb", "F",
                        "F#", "Gb", "G", "G#", "Ab", "A", "A#", "Bb", "B", "B#"};
        String[] direction = new String[]{"asc", "dsc"};
        int endFlag = 0;

        while (endFlag == 0){
            System.out.println("Choose what you want to do: ");
            System.out.println("1. IntervalConstruction.\t\n2. IntervalIdentification.\t\n3. Exit.");
            String choose = sc.next();
            switch (choose){
                case "1":

                    int g = 0;
                    while(g != 1){
                        g = setInputArray("Enter interval for construction", intervalName,
                                startingNoteForIntervalConstruction, array, direction);
                    }
                    String resString = Intervals.intervalConstruction(array);
                    System.out.println(resString);
                    break;

                case "2":
                    int g1 = 0;
                    while(g1 != 1){
                        g1 = setInputArray("Enter interval for identification", startingNoteForIntervalIdentification,
                                startingNoteForIntervalIdentification, array, direction);
                    }
                    String resString1 = Intervals.intervalIdentification(array);
                    System.out.println(resString1);
                    break;
                case "3":
                    endFlag = 1;
                    break;
                default:
                    System.out.println("Incorrect input");
            }

        }

    }

    public static int chekArray(String[] array1, String[] intervalName, String[] startingNoteForIntervalConstruction, String[] direction){

        String elem = array1[0];
        for (int j = 0; j < intervalName.length; j++) {
            if (elem.equals(intervalName[j])) {
                elem = array1[1];
                for (int i = 0; i < startingNoteForIntervalConstruction.length; i++) {
                    if (elem.equals(startingNoteForIntervalConstruction[i])) {
                        if(array1.length > 2){
                            elem = array1[2];
                            for(int k = 0; k < direction.length; k++){
                                if(elem.equals(direction[k]))
                                    return 1;
                            }
                        }
                        return 1;
                    }
                }
                break;
            }

        }

        System.out.println("Incorrect input");
        return 0;
    }
    public static void setArray1(String[] array1, String[] array2) {
        int i = 0;
        for (int j = 0; j < array2.length; j++) {

                array1[i] = array2[j];
                i++;
        }
    }
    public static int setInputArray(String str, String[] intervalName, String[] startingNote, String[] array, String [] direction) throws Exception {
        int g = 0;
        Scanner in = new Scanner(System.in);
        System.out.println(str);
        String inputString = in.nextLine();
        String[] inputArray = inputString.split(" ");

        if(inputArray.length > 3){
            throw new Exception("Illegal number of elements in input array");
            //System.out.println("Illegal number of elements in input array");
            //g = 0;

        }else{
            g = chekArray(inputArray, intervalName, startingNote, direction);
            array[2] = "asc";
            setArray1(array, inputArray);
        }


        return g;
    }
}
