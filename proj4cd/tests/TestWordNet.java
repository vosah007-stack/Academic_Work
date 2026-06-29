import main.BST;
import main.WN;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;

public class TestWordNet {
    private static final String PREFIX = "./data/";

    /** NGrams Files */
    public static final String WORD_HISTORY_EECS_FILE = PREFIX + "word_history_eecs.csv";
    public static final String WORD_HISTORY_SIZE3_FILE = PREFIX + "word_history_size3.csv";
    public static final String WORD_HISTORY_SIZE4_FILE = PREFIX + "word_history_size4.csv";
    public static final String WORD_HISTORY_SIZE1291_FILE = PREFIX + "word_history_size1291.csv";
    public static final String WORD_HISTORY_SIZE14377_FILE = PREFIX + "word_history_size14377.csv";
    public static final String YEAR_HISTORY_FILE = PREFIX + "year_history.csv";

    /** Wordnet Files */
    public static final String SYNSETS_EECS_FILE = PREFIX + "synsets_eecs.txt";
    public static final String HYPONYMS_EECS_FILE = PREFIX + "hyponyms_eecs.txt";
    public static final String SYNSET_SIZE16_FILE = PREFIX + "synsets_size16.txt";
    public static final String HYPONYM_SIZE16_FILE = PREFIX + "hyponyms_size16.txt";
    public static final String SYNSET_SIZE1000_FILE = PREFIX + "synsets_size1000.txt";
    public static final String HYPONYM_SIZE1000_FILE = PREFIX +  "hyponyms_size1000.txt";
    public static final String SYNSETS_SIZE82191_FILE = PREFIX + "synsets_size82191.txt";
    public static final String HYPONYMS_SIZE82191_FILE = PREFIX +  "hyponyms_size82191.txt";

    @Test
    public void testContructor() {
        WN wN = new WN(SYNSET_SIZE16_FILE, HYPONYM_SIZE16_FILE, WORD_HISTORY_SIZE3_FILE, YEAR_HISTORY_FILE);
        assertThat(wN.size()).isEqualTo(16);

    }
    @Test
    public void testFindHyponymsZero() {
        WN wN = new WN(SYNSET_SIZE16_FILE, HYPONYM_SIZE16_FILE, WORD_HISTORY_SIZE3_FILE, YEAR_HISTORY_FILE);
        List<String> testChange = new ArrayList<>();
        testChange.add("change");
        testChange.add("demotion");
        testChange.add("variation");
        testChange.add("modification");
        testChange.add("alteration");
        testChange.add("increase");
        testChange.add("transition");
        testChange.add("jump");
        testChange.add("leap");
        testChange.add("saltation");
        assertThat(wN.findHyponyms("change")).containsExactlyElementsIn(testChange);
        List<String> testDemotion = new ArrayList<>();
        testDemotion.add("demotion");
        assertThat(wN.findHyponyms("demotion")).containsExactlyElementsIn(testDemotion);
        List<String> testAction = new ArrayList<>();
        testAction.add("action");
        testAction.add("change");
        testAction.add("demotion");
        testAction.add("variation");
        assertThat(wN.findHyponyms("action")).containsExactlyElementsIn(testAction).inOrder();
        List<String> testAdjustment = new ArrayList<>();
        testAdjustment.add("adjustment");
        testAdjustment.add("alteration");
        testAdjustment.add("modification");
        testAdjustment.add("conversion");
        testAdjustment.add("mutation");
        assertThat(wN.findHyponyms("adjustment")).containsExactlyElementsIn(testAdjustment);
        assertThat(wN.findHyponyms("asdfg")).isEmpty();
        List<String> testChangeTransition = new ArrayList<>();
        testChangeTransition.add("transition");
        testChangeTransition.add("leap");
        testChangeTransition.add("jump");
        testChangeTransition.add("saltation");
        assertThat(wN.findHyponymsZero(new String[] {"change", "transition"})).containsExactlyElementsIn(testChangeTransition);
        assertThat(wN.findHyponymsZero(new String[] {"asdf", "change"})).isEmpty();
    }

    @Test
    public void testFindHyponymsNonZero() {
        WN wN = new WN(SYNSETS_SIZE82191_FILE,HYPONYMS_SIZE82191_FILE, WORD_HISTORY_SIZE1291_FILE, YEAR_HISTORY_FILE);
        List<String> testFoodCake = new ArrayList<>();
        testFoodCake.add("cake");
        testFoodCake.add("cookie");
        testFoodCake.add("kiss");
        testFoodCake.add("snap");
        testFoodCake.add("wafer");
        wN.ngm.countHistory("cookie", 1470, 2019);
        assertThat(wN.findHyponymsNonZero(new String[] {"cake", "food"}, 4, 1470, 2019))
                .containsExactlyElementsIn(testFoodCake);

    }
}
