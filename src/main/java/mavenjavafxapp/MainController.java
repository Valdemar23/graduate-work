package mavenjavafxapp;

import controllers.URLController;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ngram.MainInL4;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import ai.examples.NaiveBayesExample;
import entity.Description;
import entity.Tegs;
import entity.Webpages;
import hibernate.HibernateConfig;
import javafx.fxml.FXML;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.io.*;
import java.util.*;

/**
 * Created by HP on 29.04.2018.
 */


public class MainController {
    @FXML
    private TextField urlField;
    @FXML
    private TextField tegField;
    @FXML
    private Label label;
    @FXML
    private CheckBox noClass;
    @FXML
    private ListView tegsListView;
    @FXML
    static RadioButton bigram;
    @FXML
    static RadioButton threegram;
    @FXML
    static RadioButton fourgram;
    @FXML
    RadioButton ngramRadioButton;
    /*@FXML
    RadioButton naiveRadioButton;*/


    public void getReport(String url) {
        SessionFactory sessionFactory = HibernateConfig.getSessionFactory();
        Session session = sessionFactory.openSession();
        Criteria criteriaDescription = session.createCriteria(Description.class);
        List<Description> descriptionListList = criteriaDescription.list();
        List<String> descriptionStrings = new ArrayList<>();
        int amountDescription = criteriaDescription.list().size();
        List<Description> descriptionList = new ArrayList<>();
        Set<String> uniqueSet = new HashSet<>();
        int sumUniqueComments = 0;
        for (Description descriptionForCycle : descriptionListList) {//Для кожного тега буде своє
            if (!descriptionForCycle.getValue().equals("~")) {
                sumUniqueComments++;
                uniqueSet.add(descriptionForCycle.getValue());
                descriptionList.add(descriptionForCycle);
            }
        }

        System.out.println("\n\nREPORT-----------------------------------------------------------");
        for (Description description : descriptionList) {
            uniqueSet.add(description.getValue());
            System.out.println(description.getValue());
        }
        System.out.println("UNIQUE---------------------------------------------------------------\nAmount all comments - " + amountDescription + "\nAmount classificed comments - " + sumUniqueComments);
        descriptionStrings.addAll(uniqueSet);
        int amountLibraries = descriptionStrings.size();

        String html = "<html><head><titile></title><link rel=\"stylesheet\" href=\"style.css\" type=\"text/css\"></head>" +
                "<body><div id=\"header\"><h1>REPORT</h1><p>Report for [" + url + "]</p><p>Amount all comments - " + amountDescription +
                "</p><p>Amount unique comments - " + sumUniqueComments + "</p><p>Amount libraries - " + amountLibraries + "</p></div>" +
                "<div id=\"content\"> <h2>UNIQUE</h2><div id=\"text\">";


        for (String str : descriptionStrings) {
            String htmlLi = "";

            int amount = 0;
            String temp = "";
            for (Description description : descriptionList) {
                if (description.getValue().equals(str)) {
                    amount++;
                    temp += description.getAttribute() + "\n";
                    htmlLi += "<li>" + description.getAttribute();
                }
            }
            System.out.println(str + ":\nAmount - " + amount + "\n" + "Value - " + temp);
            html += "<h3>" + str + " - " + amount + "</h3><ul>" + htmlLi + "</ul>";
        }
        html += "</div></div></body></html>";
        try {
            FileWriter nFile = new FileWriter("report.html");
            nFile.write(html);
            nFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public int actOnDB(Document document, String nameTeg, String url) throws IOException {
        Tegs teg = new Tegs();
        NaiveBayesExample object = new NaiveBayesExample();//add to DB
        SessionFactory sessionFactory = HibernateConfig.getSessionFactory();
        Session session = sessionFactory.openSession();
        Elements commentCopy = document.select(nameTeg);//class
        int num = 0;
        Criteria criteriaWebpages = session.createCriteria(Webpages.class);
        List<Webpages> webpagesList = criteriaWebpages.list();

        for (Webpages pageForCycle : webpagesList) {//Для кожного тега буде своє
            if (pageForCycle.getWebpage().equals(url)) {
                teg.setWebpages(pageForCycle);
                break;
            }
        }

        teg.setTeg(nameTeg);
        Criteria criteriaTegs = session.createCriteria(Tegs.class);
        List<Tegs> tegsList = criteriaTegs.list();

        for (Tegs tegForCycle : tegsList) {
            if (tegForCycle.getWebpages().equals(teg.getWebpages()) && tegForCycle.getTeg().equals(teg.getTeg())) {
                System.out.println("THIS IS TEG EXISTS");
                label.setStyle("-fx-text-fill: red");
                label.setText("This teg exists");
                return -1;
            }
        }

        try {
            session.beginTransaction();
            session.save(teg);
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
            session.close();
        }

        if (!ngramRadioButton.isSelected()) {
            Description description = new Description();//сюди варто додати поділ на триграми разом із наївним баєсом
            for (Element element : commentCopy) {
                session = sessionFactory.openSession();
                String content;
                if (!element.text().equals("")) {
                    String line = object.start(element.text());
                    if (element.text().length() > 255) {
                        content = element.text();
                        content = content.substring(0, 254);
                    } else {
                        content = element.text();
                    }
                    try {
                        session.beginTransaction();
                        description.setAttribute(content);
                        description.setValue(line);
                        description.setTegs(teg);
                        session.save(description);
                        session.getTransaction().commit();
                        session.close();
                    } catch (Exception e) {
                        session.close();
                        e.printStackTrace();
                    }
                    System.out.format("The sentense \"%s\" was classified as \"%s\".%n", element.text(), line);
                }
            }
        } else {
            MainInL4 mainInL4 = new MainInL4();
            for (Element element : commentCopy) {

                //if(element.text().length()>)
                mainInL4.act(element.text());//transfer comments
            }
        }


        return num;
    }


    @FXML
    public void startParsing() throws IOException {
        if (urlField.getText().equals("") || tegField.getText().equals("")) {
            label.setStyle("-fx-text-fill: red");
            label.setText("Empty text field");
        } else {

            String url = urlField.getText();

            Document document = Jsoup.connect(url).get();
            System.out.println("TITLE: " + document.title());

            URLController urlController = new URLController();
            urlController.setURLForDB(url);

            String domen = urlController.getDomen();
            String page = urlController.getPage();
            System.out.println("DOMEN: " + domen + "   PAGE:" + page);

            String nameTeg = "";
            if (noClass.isSelected()) nameTeg = tegField.getText() + ":not([class])";
            else nameTeg = tegField.getText();

            label.setStyle("-fx-text-fill: green");
            label.setText("Job done");
            actOnDB(document, nameTeg, url);
            getReport(url);
        }
    }

    @FXML
    public void chooseNGram(ActionEvent event) {
        Stage stage = new Stage();
        String fxmlFile = "/fxml/chooseNGram.fxml";
        FXMLLoader loader = new FXMLLoader();
        Parent root = null;
        try {
            root = loader.load(getClass().getResourceAsStream(fxmlFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setTitle("Select ngram");
        stage.setScene(new Scene(root));
        stage.initModality(Modality.WINDOW_MODAL);//відкриє вікно
        stage.initOwner(((Node) event.getSource()).getScene().getWindow());//робить вікно модальним
        stage.show();
    }
}
