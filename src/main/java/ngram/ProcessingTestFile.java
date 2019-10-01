package ngram;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

public class ProcessingTestFile {

    final Random random = new Random();

    //зчитать випадкові 40 симсолів в циклі 5 раз, визначити максимальне значення
    ArrayList<String> randomSymbols(String stringFromFile, int length) {

        ArrayList<String> randSymbols = new ArrayList<>();
        for (int i = 0; i < 5; i++) {

            String str = "";

            for (int j = 0; j < length; j++) {
                char ch = stringFromFile.charAt(random.nextInt(stringFromFile.length() - 1));
                str += ch;
            }
            //System.out.println(str);
            randSymbols.add(str);
        }

        return randSymbols;
    }

    //зчитать послідовно-випадкові 40 символів в циклі 5 раз, визначити мінімальне значення
    ArrayList<String> sequentialSymbols(String stringFromFile, int length) {//

        ArrayList<String> seqSymbols = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            int temp = stringFromFile.length() - ((length + 1));//%stringFromFile.length()
            //System.out.println("temp=" + temp);
            int number = random.nextInt(temp);
            seqSymbols.add(stringFromFile.substring(number, number + length));
            //System.out.println(stringFromFile.substring(number, number + length));
        }

        return seqSymbols;
    }

    //спробувати знайти середину між 2-ма значенями
    double avg(ArrayList<String> arrayList, Map<String, Integer> inputFile, int n) {

        double avg = 0.0;

        for (int i = 0; i < arrayList.size(); i++) {

            String str = arrayList.get(i).toString();
            double prod = 1.0;

            for (int j = 0; j < str.length() - (n - 1); j++) {
                String tmpstr = str.substring(j, j + n);

                for (Map.Entry item : inputFile.entrySet()) {
                    if (tmpstr.equals(item.getKey())) {
                        prod *= Integer.valueOf(item.getValue().toString());

                    }
                }
            }
            avg += Math.pow(prod, 1.0 / (str.length() - 1));
        }

        return avg / arrayList.size();
    }

    double avg(String str, Map<String, Integer> inputFile, int n) {

        double prod = 1.0;

        for (int j = 0; j < str.length() - (n - 1); j++) {
            String tmpstr = str.substring(j, j + n);

            for (Map.Entry item : inputFile.entrySet()) {
                if (tmpstr.equals(item.getKey())) {
                    prod *= Integer.valueOf(item.getValue().toString());
                    //System.out.println(prod);
                }
            }
        }

        System.out.println("prod = " + prod);
        prod = Math.pow(prod, 1.0 / (str.length() - 1));
        return prod;
    }

    /*double avgSeq(ArrayList<String> arrayList, Map<String, Integer> inputFile) {

        double sum = 0.0;

        for (int i = 0; i < arrayList.size(); i++) {

            String kyryl =arrayList.get(i);
            double prod = 1.0;

            for (int j = 0; j < kyryl.length() - 1; j++) {
                String tmpstr = kyryl.substring(j, j + 2);


                for (Map.Entry item : inputFile.entrySet()) {
                    if (tmpstr.equals(item.getKey())){
                        prod *= Integer.valueOf(item.getValue().toString());
                        sum+=prod;
                    }
                }
            }

        }

        return sum/arrayList.size();
    }*/


    //підсумковий вивід даних
}
