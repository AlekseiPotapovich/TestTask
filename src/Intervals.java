import java.util.LinkedList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Intervals {


    static Pattern startingNoteForIdentification = Pattern.compile("(^[A-G][b#][b]$)|(^[A-G]$)|(^[A-G][b#]$)");
    static Pattern startingNoteForConstruction = Pattern.compile("(^[A-G][b#]$)|(^[A-G]$)|(^[A-G][b#]$)");
    static String[] intervalName = new String[]{"m2", "M2", "m3", "M3", "P4", "P5", "m6", "M6", "m7", "M7", "P8"};
    static String line = ("C--D--E-F--G--A--B-");
    static String[] direction = new String[]{"asc", "dsc"};
    static LinkedList<String> lineNote = new LinkedList<>();
    static LinkedList<String> lineNoteForChange = new LinkedList<>();
    static String accidentals = "";
    static int flag = 0;

    public static String[] checkArrayForConstruction(String[] intervalName, String[] direction, Pattern regularExpression) throws Exception {
        String[] resultArray = new String[3];
        Scanner in = new Scanner(System.in);
        String inputString = in.nextLine();
        String[] inputArray = inputString.split(" ");
        if(inputArray.length > 3)
            throw new Exception("Illegal number of elements in input array");

        for (int j = 0; j < intervalName.length; j++) {
            if (inputArray[0].equals(intervalName[j])) {
                resultArray[0] = inputArray[0];
                Matcher matcher = regularExpression.matcher(inputArray[1]);
                if (matches1(direction, resultArray, inputArray, matcher))
                    return resultArray;
            }
        }
        return null;
    }
    public static String[] checkArrayForIdentification(Pattern regularExpression, String[] direction) throws Exception {
        String[] resultArray = new String[3];
        Scanner in = new Scanner(System.in);
        String inputString = in.nextLine();
        String[] inputArray = inputString.split(" ");
        if (inputArray.length > 3)
            throw new Exception("Illegal number of elements in input array");

        Matcher matcher = regularExpression.matcher(inputArray[0]);
        if (matcher.matches()) {
            resultArray[0] = inputArray[0];
            matcher = regularExpression.matcher(inputArray[1]);
            if (matches1(direction, resultArray, inputArray, matcher))
                return resultArray;
        }
        return null;
    }

    public static boolean matches1(String[] direction, String[] resultArray, String[] inputArray, Matcher matcher) {
        if (matcher.matches()) {
            resultArray[1] = inputArray[1];
            if (inputArray.length > 2) {
                for (int k = 0; k < direction.length; k++) {
                    if (inputArray[2].equals(direction[k])) {
                        resultArray[2] = inputArray[2];
                        return true;
                    }
                }
            } else {
                resultArray[2] = "asc";
                return true;
            }
        }
        return false;
    }


    public static void setLineNote(String line, LinkedList<String> lineNote){
        String note;
        for(int i = 0; i < line.length(); i++){
            note = line.substring(i, i + 1);
            lineNote.add(note);
        }
    }

    public static int getDegree(String[] array){
        String elem = array[0];
        elem = elem.substring(1,2);
        int index;
        index = Integer.parseInt(elem);
        return index;
    }

    public static int getSemitone(String[] array){
        int index ;
        for(index = 0; index < intervalName.length; index++){
            if(array[0].equals(intervalName[index])) {
                if(index > 4)
                    index++;
                index++;
                return index;
            }
        }
        return 0;
    }

    public static String getStartNote(String[] array){
        String startNote = null;//array[1];
        accidentals = "";
        if(array[1].length() >= 1)
            startNote = array[1].substring(0,1);

        switch (array[1].length()){
            case 2:
                accidentals = array[1].substring(1,2);
                break;
            case 3:
                accidentals = array[1].substring(1,3);
                break;
        }
        return startNote;
    }

    public static int getIndexOfStartNote(LinkedList<String> lineNote, String startNote){
        for(int i = 0; i < lineNote.size(); i++){
            if(lineNote.get(i).equals(startNote)){
                return i;
            }
        }
        return 0;
    }


    public static String getLastNote(int indexOfStartNote, int indexOfLastNote, int degree, int semitone, int flag){
        int countOfSemitones = 0;
        int i = indexOfStartNote;
        String lastNote;
        int index = 0;

        while(index != degree){
            if(i == lineNote.size() && flag == 0)
                i = 0;
            if(!lineNote.get(i).equals("-")){
                index++;
            }else
                countOfSemitones++;
            if(flag == 0)
                i++;
            else if(i <= 0){
                i = 18;
            }else
                i--;
        }

        if(flag == 1){
            if(accidentals.equals("#")){
                countOfSemitones++;
            }else if(accidentals.equals("b"))
                countOfSemitones--;

            countOfSemitones = -countOfSemitones;
            semitone = -semitone;

            lastNote = lineNote.get(i + 1);
        }else{
            if(accidentals.equals("#")){
                countOfSemitones--;
            }else if(accidentals.equals("b"))
                countOfSemitones++;

            lastNote = lineNote.get(i - 1);
        }

        accidentals = "";
        semitone -= countOfSemitones;

        if(semitone > 0){
            accidentals = "#";

        }else if(semitone < 0)
            accidentals = "b";

        for(int j = 1; j < semitone; j++)
            accidentals += accidentals;

        for(int j = -1; j > semitone; j--)
            accidentals += accidentals;

        return lastNote + accidentals;
    }

    public static void setFlag(String[] args){
        if(args[2].equals("dsc")){
            flag = 1;
        }
        else{
            flag = 0;
        }
    }

    public static String getNote(String[] array, int i){
        String note = array[i];
        return note;
    }

    public static int getIndexOfNote(LinkedList<String> array, String note){
        int indexOfNote = 0;
        //lineNoteForChange = new LinkedList<>();
        for(int i = 0; i < array.size(); i++){
            if(note.substring(0,1).equals(array.get(i))){
                indexOfNote = i;
                break;
            }
        }
        switch (note.length()) {
            case 2 -> {
                accidentals = note.substring(1, 2);
                if (accidentals.equals("#")){
                    indexOfNote++;

                    if(!lineNote.get(indexOfNote - 1).equals("-")){
                        lineNoteForChange.set((indexOfNote - 1),"-");
                    }


                }
                else{
                    indexOfNote--;
                    if(!lineNote.get(indexOfNote + 1).equals("-")){
                        lineNoteForChange.set((indexOfNote + 1),"-");
                    }
                }

            }
            case 3 -> {
                accidentals = note.substring(1, 3);
                if (accidentals.equals("##")){
                    indexOfNote += 2;
                    if(!lineNote.get(indexOfNote - 2).equals("-")){
                        lineNoteForChange.set((indexOfNote - 2),"-");
                    }
                }

                else{
                    indexOfNote -=2;
                    if(!lineNote.get(indexOfNote + 2).equals("-")){
                        lineNoteForChange.set((indexOfNote + 2),"-");
                    }
                }

            }
        }

        return indexOfNote;
    }

    public static int getIndexOfInterval(int indexOfFirstNote, int indexOfLastNote) {

        int countOfSemitones = 0;
        int i = indexOfFirstNote;
        switch (flag) {
            case 0 -> {

                while(i < 0){
                    countOfSemitones++;
                    i++;
                }

                lineNoteForChange.set(i, "#");
                lineNoteForChange.set(indexOfLastNote, "#");


                if (indexOfFirstNote > indexOfLastNote) {
                    indexOfLastNote += indexOfFirstNote;
                }

                for (; i < indexOfLastNote; i++) {
                    if (i == 19) {
                        i = 0;
                        indexOfLastNote -= indexOfFirstNote;
                    }

                    if (lineNoteForChange.get(i).equals("-")) {
                        countOfSemitones++;
                    }
                }
            }
            case 1 -> {

                if(i < 0){
                    i = 19 + i;
                }
                lineNoteForChange.set(i, "#");

                if (i < indexOfLastNote) {
                    indexOfLastNote = -indexOfLastNote;

                }

                for (; i > indexOfLastNote; i--) {
                    if(i < 0){
                        i = 18;
                        indexOfLastNote = -indexOfLastNote;
                    }

                    if (lineNoteForChange.get(i).equals("-")) {
                        countOfSemitones++;
                    }
                }
            }
        }
        return countOfSemitones;
    }

    public static String getNameOfInterval(int index) throws Exception{

        String nameOfInterval = null;
        if (index > 5){
            index--;
        }
        try{
            nameOfInterval = intervalName[index - 1];
        }catch (IndexOutOfBoundsException e){
            throw new Exception("Cannot identify the interval");
            //System.out.println("Cannot identify the interval");
        }

        return nameOfInterval;
    }


    public static String intervalConstruction(String[] args) throws Exception {
        
        args = checkArrayForConstruction(intervalName,direction, startingNoteForConstruction);
        if(args == null){
            throw new Exception("Illegal input");
        }
        int degree = getDegree(args);
        int semitone = getSemitone(args);
        setLineNote(line, lineNote);
        int indexOfStartNote = getIndexOfStartNote(lineNote, getStartNote(args));
        int indexOfLastNote = (indexOfStartNote + degree + semitone);
        setFlag(args);
        String lastNote = getLastNote(indexOfStartNote, indexOfLastNote, degree, semitone, flag);
        String resultString = lastNote;
        return resultString;
    }

    public static String intervalIdentification(String[] args) throws Exception {

        lineNoteForChange = new LinkedList<>();
        args = checkArrayForIdentification(startingNoteForIdentification, direction);
        if(args == null){
            throw new Exception("Illegal input");
        }
        String resultString;
        setLineNote(line, lineNote);
        setLineNote(line, lineNoteForChange);
        String firstNote = getNote(args,0);
        String lastNote = getNote(args,1);
        setFlag(args);
        int indexOfInterval = getIndexOfInterval(getIndexOfNote(lineNote, firstNote), getIndexOfNote(lineNote, lastNote));
        resultString = getNameOfInterval(indexOfInterval);
        return resultString;
    }
}