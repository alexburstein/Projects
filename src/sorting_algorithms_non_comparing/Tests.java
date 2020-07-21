package sorting_algorithms_non_comparing;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static sorting_algorithms_non_comparing.NonCompareSort.countingSort;
import static sorting_algorithms_non_comparing.NonCompareSort.radixSort;

public class Tests {


   private List<countableUnit> initTestArray(){
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

   @Test
   public void countSortTest(){
       List<countableUnit> list = initTestArray();
       countingSort(list);

       for(int i = 0; i < list.size(); ++i){
           System.out.println(i + "= [" + list.get(i).getIntValue() + "," + list.get(i).getO() + "]");
       }
    }

    @Test
    public void radixSortTest(){
        List<countableUnit> list = initTestArray();
        radixSort(list);

        for(int i = 0; i < list.size(); ++i){
            System.out.println(i + "= [" + list.get(i).getIntValue() + "," + list.get(i).getO() + "]");
        }
    }


    public static class countableUnit implements Countable {
        private final int number;
        private final Object o;

        public countableUnit(int number, Object o){
            this.o= o;
            this.number = number;
        }

        @Override
        public int getIntValue() {
            return number;
        }

        public Object getO() {
            return o;
        }
    }
}

