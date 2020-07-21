package sorting_algorithms_non_comparing;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Tests {

   private List<countableUnit> naiveList(){
       List<countableUnit> list = new ArrayList<>(10);
       list.add(new countableUnit(6, " I "));
       list.add(new countableUnit(-1, " name "));
       list.add(new countableUnit(45999999, " ! "));
       list.add(new countableUnit(-2, " my "));
       list.add(new countableUnit(2, " is "));
       list.add(new countableUnit(7, " love "));
       list.add(new countableUnit(4, " and "));
       list.add(new countableUnit(-44, " hello "));
       list.add(new countableUnit(2, " alex "));
       list.add(new countableUnit(9, " cake "));

       return list;
   }

    public void naiveTest(Consumer<List<? extends Countable>> consumer){
        List<countableUnit> list = naiveList();
        consumer.accept(list);

        for(int i = 0; i < list.size(); ++i){
            System.out.println(i + "= [" + list.get(i).getIntValue() + "," + list.get(i).getObject() + "]");
        }
    }

    public void test1(Consumer<List<? extends Countable>> consumer){
        List<countableUnit> list = Arrays.asList(new countableUnit(-87, 1),
                new countableUnit(-9999999, 0),
                new countableUnit(-1, 2)
        );

        consumer.accept(list);

        for (int i = 0; i < 3; ++i){
            assertEquals(list.get(i).getObject(),i);
        }
    }

    public void test2(Consumer<List<? extends Countable>> consumer){
        List<countableUnit> list = Arrays.asList(new countableUnit(-87, 1),
                new countableUnit(-9999999, 0),
                new countableUnit(99, 5),
                new countableUnit(-1, 2),
                new countableUnit(765, 6),
                new countableUnit(1, 4),
                new countableUnit(-1, 3),
                new countableUnit(3456, 7)

        );

        consumer.accept(list);

        for (int i = 0; i < 7; ++i){
            assertEquals(list.get(i).getObject(),i);
        }
    }

    @Test
    public void testNonComparingSorts(){
        naiveTest(NonCompareSort::radixSort);
        naiveTest(NonCompareSort::countingSort);
        test1(NonCompareSort::radixSort);
        test1(NonCompareSort::countingSort);
        test2(NonCompareSort::radixSort);
        test2(NonCompareSort::countingSort);
   }



    public static class countableUnit implements Countable {
        private final int number;
        private final Object object;

        public countableUnit(int number, Object o){
            this.object = o;
            this.number = number;
        }

        @Override
        public int getIntValue() {
            return number;
        }

        public Object getObject() {
            return object;
        }
    }
}

