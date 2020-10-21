
public class Intervals {

    public static String intervalConstruction(String[] args) {
        String resultString ;
        IntervalMethods im = new IntervalMethods();
        int degree = im.getDegree(args);
        int semitone = im.getSemitone(args);
        im.setLineNote(im.line, im.lineNote);
        int indexOfStartNote = im.getIndexOfStartNote(im.lineNote, im.getStartNote(args));
        int indexOfLastNote = (indexOfStartNote + degree + semitone);
        im.setFlag(args);
        String lastNote = im.getLastNote(indexOfStartNote, indexOfLastNote, degree, semitone, im.flag);
        resultString = lastNote;
        return resultString;
    }

    public static String intervalIdentification(String[] args) throws Exception {
        IntervalMethods im = new IntervalMethods();
        String resString;
        im.setLineNote(im.line, im.lineNote);
        im.setLineNote(im.line, im.lineNoteForchange);
        String firstNote = im.getNote(args,0);
        String lastNote = im.getNote(args,1);
        im.setFlag(args);
        int indexOfInterval = im.getIndexOfInterval(im.getIndexOfNote(im.lineNote, firstNote), im.getIndexOfNote(im.lineNote, lastNote));
        resString = im.getNameOfInterval(indexOfInterval);
        return resString;
    }
}