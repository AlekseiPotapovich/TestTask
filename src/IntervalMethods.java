import java.util.LinkedList;

public class IntervalMethods {
    String[] intervalName = new String[]{"m2", "M2", "m3", "M3", "P4", "P5", "m6", "M6", "m7", "M7", "P8"};
    String line = ("C--D--E-F--G--A--B-");
    LinkedList<String> lineNote = new LinkedList<>();
    LinkedList<String> lineNoteForchange = new LinkedList<>();
    String accidentals = "";
    int flag = 0;


    public  void setLineNote(String line, LinkedList<String> lineNote){
        String note;
        for(int i = 0; i < line.length(); i++){
            note = line.substring(i, i + 1);
            lineNote.add(note);
        }
    }

    public  int getDegree(String[] array){
        String elem = array[0];
        elem = elem.substring(1,2);
        int index;
        index = Integer.parseInt(elem);
        return index;
    }

    public  int getSemitone(String[] array){
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

    public  String getStartNote(String[] array){
        String startNote = null;//array[1];

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

    public  int getIndexOfStartNote(LinkedList<String> lineNote, String startNote){
        for(int i = 0; i < lineNote.size(); i++){
            if(lineNote.get(i).equals(startNote)){
                return i;
            }
        }
        return 0;
    }


    public  String getLastNote(int indexOfStartNote, int indexOfLastNote, int degree, int semitone, int flag){
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

    public void setFlag(String[] args){
        if(args[2].equals("dsc")){
            flag = 1;
        }
    }

    public  String getNote(String[] array, int i){
        String note = array[i];
        return note;
    }

    public int getIndexOfNote(LinkedList<String> array, String note){
        int indexOfNote = 0;
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
                        lineNoteForchange.set((indexOfNote - 1),"-");
                    }


                }
                else{
                    indexOfNote--;
                    if(!lineNote.get(indexOfNote + 1).equals("-")){
                        lineNoteForchange.set((indexOfNote + 1),"-");
                    }
                }

            }
            case 3 -> {
                accidentals = note.substring(1, 3);
                if (accidentals.equals("##")){
                    indexOfNote += 2;
                    if(!lineNote.get(indexOfNote - 2).equals("-")){
                        lineNoteForchange.set((indexOfNote - 2),"-");
                    }
                }

                else{
                    indexOfNote -=2;
                    if(!lineNote.get(indexOfNote + 2).equals("-")){
                        lineNoteForchange.set((indexOfNote + 2),"-");
                    }
                }

            }
        }

        return indexOfNote;
    }

    public  int getIndexOfInterval(int indexOfFirstNote, int indexOfLastNote) {

        int countOfSemitones = 0;
        int i = indexOfFirstNote;
        switch (flag) {
            case 0 -> {

                while(i < 0){
                    countOfSemitones++;
                    i++;
                }

                lineNoteForchange.set(i, "#");
                lineNoteForchange.set(indexOfLastNote, "#");


                if (indexOfFirstNote > indexOfLastNote) {
                    indexOfLastNote += indexOfFirstNote;
                }

                for (; i < indexOfLastNote; i++) {
                    if (i == 19) {
                        i = 0;
                        indexOfLastNote -= indexOfFirstNote;
                    }

                    if (lineNoteForchange.get(i).equals("-")) {
                        countOfSemitones++;
                    }
                }
            }
            case 1 -> {

                if(i < 0){
                    i = 19 + i;
                }
                lineNoteForchange.set(i, "#");

                if (i < indexOfLastNote) {
                    indexOfLastNote = -indexOfLastNote;

                }

                for (; i > indexOfLastNote; i--) {
                    if(i < 0){
                        i = 18;
                        indexOfLastNote = -indexOfLastNote;
                    }

                    if (lineNoteForchange.get(i).equals("-")) {
                        countOfSemitones++;
                    }
                }
            }
        }
        return countOfSemitones;
    }
    public  String getNameOfInterval(int index) throws Exception{

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

}