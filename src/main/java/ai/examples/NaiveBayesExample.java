package ai.examples;

import ai.classifiers.NaiveBayes;
import ai.dataobjects.NaiveBayesKnowledgeBase;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.*;

public class NaiveBayesExample {
    private static final String config = "/home/jackiechan/Documents/JetBrains/IdeaProjects/Diploma/graduate-work/src/main/resources/datasets/Conf.txt";

    private static ArrayList getConf() {
        Scanner scanner = null;

        try {
            scanner = new Scanner(new File(config));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        ArrayList<String> arrayList = new ArrayList<>();

        while (scanner.hasNext()) {
            arrayList.add(scanner.next());
        }

        return arrayList;
    }

    public static String[] readLines(URL url) throws IOException {

        Reader fileReader = new InputStreamReader(url.openStream(), Charset.forName("UTF-8"));
        List<String> lines;
        try (BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            lines = new ArrayList<String>();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                lines.add(line);
            }
        }
        return lines.toArray(new String[lines.size()]);
    }

    public static String start(String string) throws IOException {
        Map<String, URL> trainingFiles = new HashMap<String, URL>();
        ArrayList<String> arrayList = getConf();
        String temp = "";
        for (int i = 0; i < arrayList.size(); i++) {
            if (i % 2 == 0) temp = arrayList.get(i);
            else trainingFiles.put(temp, NaiveBayesExample.class.getResource("/datasets/" + arrayList.get(i)));
        }

        Map<String, String[]> trainingExamples = new HashMap<String, String[]>();
        for (Map.Entry<String, URL> entry : trainingFiles.entrySet()) {
            trainingExamples.put(entry.getKey(), readLines(entry.getValue()));
        }

        NaiveBayes nb = new NaiveBayes();
        nb.setChisquareCriticalValue(6.63);//6.63
        nb.train(trainingExamples);
        NaiveBayesKnowledgeBase knowledgeBase = nb.getKnowledgeBase();

        nb = null;
        trainingExamples = null;
        //System.out.println(knowledgeBase.logPriors);
        //System.out.println(knowledgeBase.logLikelihoods);
        nb = new NaiveBayes(knowledgeBase);
        String exampleEn = string.toLowerCase();//ключове слова
        exampleEn = exampleEn.replace(".", " ");
        exampleEn = exampleEn.replace(",", " ");
        exampleEn = exampleEn.replace("!", " ");
        exampleEn = exampleEn.replace("?", " ");
        String outputEn = nb.predict(exampleEn);

        return outputEn;
    }

}
