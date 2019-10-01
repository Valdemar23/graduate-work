package ngram;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class MainInL4 {
    @FXML
    Label outputLabel;
    @FXML
    TextField enterString;
    @FXML
    Label trueOrFalse;
    @FXML
    Label borderLabel;
    @FXML
    Label currentGoals;
    @FXML
    RadioButton bigram;
    @FXML
    RadioButton threegram;
    @FXML
    RadioButton fourgram;

    private static int amountGram;
    private static final String USER="jackiechan";

    private static final String ROOT_PATH = "/home/"+USER+"/n-grams/";//підійдуть тільки біграми і триграми
    private static final String NGRAMS_FILENAME = ROOT_PATH + "4n-gram.txt";//н-грами будуть формуватись із наборів словників
    //і будуть стосуватись наприклад позитивного-негативного-нейтрального відтінків, чим більше відповідності буде по по словнику
    //тим більше комент буде відноситись до даного значення, якщо ж комент не пройде поріг коректності в ряді словників, то можна
    //буде сказать що комент тіп нейтральний, тут короче теж пригодиться конфіг який я давно писав


    private static final String PATH_TO_VERBS = "/home/"+USER+"/Documents/JetBrains/IdeaProjects/Diploma/graduate-work/src/main/resources/datasets/";
    private static final String TEST_POSITIVE = PATH_TO_VERBS + "positive-verb.txt";//тестування на коректність тіп, cюди віднести словники
    private static final String TEST_NEGATIVE = PATH_TO_VERBS + "negative-verb.txt";
    private static final String TEST_PROF = PATH_TO_VERBS + "prof.txt";
    private static final String TEST_NEUTRAL = PATH_TO_VERBS + "training.language.fr.txt";
    //ще є варік
    private static final String TEST_NGRAM = ROOT_PATH + "testGrams.txt";//можна замінить БД
    private static final String MASTER_I_MARGARITA = ROOT_PATH + "master-i-margarita.txt";
    //private static final String INPUT_IN_FILENAME = "/home/jackiechan/n-grams/grams.txt";
    //private static final String READ_FROM_FILENAME = "/home/jackiechan/n-grams/bigrams.txt";

    @FXML
    public void act(String userString) {//cюди потрібно буде передавать коментарі
        //Scanner scanner = new Scanner(System.in);
        System.out.print("What you want to do?\n1) Create n-grams\n2) Read n-grams\n3) Press 0 if you want to quit\n\nYour action: ");
        ReadFiles fileReader = new ReadFiles();
        int act = 2;// scanner.nextInt();
        //int n = chooseNGram();
        System.out.print("Input please simple string: ");
        //String userString = "бабуляхикана";//тут будуть зберігатись коментарі - USERS COMMENTS

        /*if(enterString.getText().equals("")){
            outputLabel.setText("You don't enter string in field");
        }else{
            userString=enterString.getText();
        }*/

        //userString = scanner.next();//приветкакдела|бабуляхикана|лолкекчебурек
//        outputLabel.setText(userString);
        userString = ProcessingText.processingString(userString);
        System.out.println("User string: " + userString);
        Map<String, Integer> inputFile;

        do {
            switch (act) {
                case 1:
                    String str = fileReader.read(NGRAMS_FILENAME);//
                    str = ProcessingText.processingString(str);
                    System.out.println(str);
                    ProcessingInputFile processingInputFile = new ProcessingInputFile();

                    inputFile = processingInputFile.createDigramsAndCalculateAmountsDigrams(str, amountGram);

                    for (Map.Entry item : inputFile.entrySet()) {
                        item.setValue(Integer.valueOf(item.getValue().toString()) + 1);
                        System.out.println(item.getKey() + " - " + item.getValue());
                    }

                    //навчання іншого текста
                    classificateUserString(userString, inputFile, amountGram, TEST_POSITIVE);//positive,negative,prof,neutral TEST_FILENAME
                    classificateUserString(userString, inputFile, amountGram, TEST_NEGATIVE);//positive,negative,prof,neutral TEST_FILENAME
                    classificateUserString(userString, inputFile, amountGram, TEST_PROF);//positive,negative,prof,neutral TEST_FILENAME
                    classificateUserString(userString, inputFile, amountGram, TEST_NEUTRAL);//positive,negative,prof,neutral TEST_FILENAME

                    String stringForWriteInFile = "";//підготовка до запису у файл (переводим все в один рядок)
                    for (Map.Entry k : inputFile.entrySet()) {
                        stringForWriteInFile += k.getKey() + " " + k.getValue() + "\n";
                    }

                    try (FileWriter writer = new FileWriter(TEST_NGRAM, true)) {//ROOT_PATH+n+"testNGram.txt"
                        writer.write(stringForWriteInFile);
                        System.out.println("All right");
                    } catch (IOException ex) {
                        System.out.println(ex.getMessage());
                    }
                    break;
                case 2:
                    //System.out.println("What file you want to read?");


                    String file = ROOT_PATH + amountGram + "gramsEn.txt";

                    ArrayList<String> arrayList = fileReader.openAndReadFile(file);
                    inputFile = fileReader.readNGramsFile(arrayList);

                    classificateUserString(userString, inputFile, amountGram, TEST_POSITIVE);//positive,negative,prof,neutral TEST_FILENAME
                    classificateUserString(userString, inputFile, amountGram, TEST_NEGATIVE);//positive,negative,prof,neutral TEST_FILENAME
                    classificateUserString(userString, inputFile, amountGram, TEST_PROF);//positive,negative,prof,neutral TEST_FILENAME
                    classificateUserString(userString, inputFile, amountGram, TEST_NEUTRAL);//positive,negative,prof,neutral TEST_FILENAME

                    break;
                default:
                    System.out.println("You press undefined symbol, change your choise");
                    break;
            }
        } while (act < 0 || act > 3);
    }

    void classificateUserString(String userString, Map<String, Integer> inputFile, int n, String filename) {
        String testFile = ReadFiles.read(filename);//positive,negative,prof,neutral TEST_FILENAME
        testFile = ProcessingText.processingString(testFile);
        ProcessingTestFile processingTestFile = new ProcessingTestFile();
        ArrayList<String> randList = processingTestFile.randomSymbols(testFile, userString.length());
        double r = processingTestFile.avg(randList, inputFile, n);
        System.out.println("avg rand = " + r);


        ArrayList<String> seqList = processingTestFile.sequentialSymbols(testFile, userString.length());
        double s = processingTestFile.avg(seqList, inputFile, n);
        System.out.println("avg seq = " + s);


        /*double border = r + r / 5;


        System.out.println("Border true - " + border);

        String temp = "Border true - " + border;*/
//        borderLabel.setText(temp);

        double userAVG = processingTestFile.avg(userString, inputFile, n);
        System.out.println("User string AVG ("+userString+"): "+userAVG);
//        currentGoals.setText("AVG - " + userAVG);

        /*if (userAVG > border) {
            System.out.println("This string is true");
//            trueOrFalse.setText("This string is true");
        } else {
            System.out.println("This string is wrong");
//            trueOrFalse.setText("This string is wrong");
        }*/
    }

    public void selectNGram() {
        if (bigram.isSelected()) {
            amountGram = 2;
            System.out.println(2);
        } else {
            if (threegram.isSelected()) {
                amountGram = 3;
                System.out.println(3);
            } else {
                if (fourgram.isSelected()) {
                    amountGram = 4;
                    System.out.println(4);
                }
            }
        }
    }
}
