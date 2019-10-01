package ngram;

import java.util.HashMap;
import java.util.Map;

public class ProcessingInputFile {

    public Map<String, Integer> createDigramsAndCalculateAmountsDigrams(String str, int n) {
        Map<String, Integer> inputFile = new HashMap<>();
        for (int i = 0; i < str.length() - (n - 1); i++) { //get unique values
            String tmpstr = str.substring(i, i + n);
            int flag = 0;

            for (Map.Entry item : inputFile.entrySet()) {
                if (item.getKey().equals(tmpstr)) {
                    item.setValue(Integer.valueOf(item.getValue().toString()) + 1);
                    flag = 1;
                    break;
                }
            }

            if (flag == 0) {
                inputFile.put(tmpstr, 0);
            }
        }
        return inputFile;
    }
}
       /*for (int i = 0; i < str.length() - 1; i++) { //get unique values
            String tmpstr = str.substring(i, i + 2);
            int flag = 0;

            for (int j = 0; j < inputFile.size(); j++) {
                if (arrayList.get(j).equals(tmpstr)) {
                    flag = 1;
                    break;
                }
            }

            if (flag == 0) {
                arrayList.add(tmpstr);
                counts.add(0);
            }
        }*/
