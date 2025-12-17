package comprehensive;

import java.util.Random;

public class DictionaryAddDefTimingExperiment extends TimingExperiment {
    private static String problemSizeDescription = "Dictionary Size";
    private static int problemSizeMin = 10000;
    private static int problemSizeCount = 100;
    private static int problemSizeStep = 1000  ;
    private static int experimentIterationCount = 25;
    private static Random rng = new Random();
    private static final String[] elements = new String[]{"Test", "noun", "testing"};
    final static String[]  POSARRAY = new String[]{"noun", "verb", "adj", "adv", "pron", "prep", "conj", "interj"};
    private static Dictionary dict = new Dictionary();

    public static void main(String[] args){
        TimingExperiment timingExperiment = new DictionaryAddDefTimingExperiment();
        timingExperiment.printResults();
    }

    /**
     * Constructor to build a general timing experiment.
     */
    public DictionaryAddDefTimingExperiment() {
        super(problemSizeDescription, problemSizeMin, problemSizeCount, problemSizeStep, experimentIterationCount);
    }

    /**
     * Abstract method for setting up the infrastructure for the experiment
     * for a given problem size.
     *
     * @param problemSize - the problem size for one experiment
     */
    @Override
    protected void setupExperiment(int problemSize) {
        dict = makeDictionary(problemSize);
    }

    /**
     * Abstract method to run the computation to be timed.
     */
    @Override
    protected void runComputation() {
        dict.add(elements[0], elements[1], elements[2]);
    }

    private static Dictionary makeDictionary(int problemSize){
        for(int i = 0; i < problemSize; i ++){
            int number = rng.nextInt(problemSize / 4);
            int posRng = rng.nextInt(POSARRAY.length - 1);
            String word = ((Integer)number).toString();
            dict.add(word, POSARRAY[posRng], word + word + word + " " + word);
        }
        return dict;
    }
}

