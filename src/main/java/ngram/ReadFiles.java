package ngram;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ReadFiles {

    public static String read(String file) {//перший метод зчитування з файлу

        BufferedReader br = null;
        FileReader fr = null;
        String str = "";

        try {

            //br = new BufferedReader(new FileReader(file));
            fr = new FileReader(file);
            br = new BufferedReader(fr);

            String sCurrentLine;

            while ((sCurrentLine = br.readLine()) != null) {
                str += sCurrentLine;
            }

        } catch (IOException e) {

            e.printStackTrace();

        } finally {

            try {

                if (br != null)
                    br.close();

                if (fr != null)
                    fr.close();

            } catch (IOException ex) {
                ex.printStackTrace();
            }

        }
        return str;
    }


    ArrayList<String> openAndReadFile(String file) {//інший метод зчитування з файлу
        ArrayList<String> arrayList = new ArrayList();

        try {
            Scanner scanner = new Scanner(new File(file));

            while (scanner.hasNext()) {//наскільки памятаю це порядкове зчитування, обгортка try-catch не вимагається
                arrayList.add(scanner.next());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "File not found");
        }

        return arrayList;
    }

    Map<String, Integer> readNGramsFile(ArrayList<String> arrayList) {
        Map<String, Integer> map = new HashMap<String, Integer>();
        String key = "";
        Integer value;

        for (int i = 0; i < arrayList.size(); i++) {
            if (i % 2 == 0) {
                key = arrayList.get(i);
                //System.out.println("key = "+key);
            } else {
                value = Integer.valueOf(arrayList.get(i));
                //System.out.println("value = "+value);
                map.put(key,value);
            }
        }

        System.out.println(map);
        return map;
    }

    /*public static void act(String[] args) {
        openFile();readFile();readNGramsFile();
    }*/
}