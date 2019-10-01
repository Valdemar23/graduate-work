package controllers;
//Implements for correct work and hz
import entity.Webpages;
import entity.Websites;
import hibernate.HibernateConfig;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class URLController {
    private String domen;
    private String page;

    public String getPage() {
        return page;
    }
    public String getDomen() {
        return domen;
    }


    public void setURLForDB(String urlField) {
        boolean flagSites=true;
        boolean flagPages=true;
        Websites website = new Websites();
        Webpages webpages = new Webpages();

        char[] chArray = urlField.toCharArray();//part code for cut domen
        int j = 0;
        for (int i = 0; i < 3; ) {
            domen += chArray[j++];
            if (chArray[j] == '/') i++;
        }
        domen=domen.substring(4);
        page=urlField;

        SessionFactory sessionFactory = HibernateConfig.getSessionFactory();//prepearing to get data from db
        Session session = sessionFactory.openSession();
        Criteria criteriaWebsites = session.createCriteria(Websites.class);
        List<Websites> websitesList = criteriaWebsites.list();
        Criteria criteriaWebpages = session.createCriteria(Webpages.class);
        List<Webpages> webpagesList = criteriaWebpages.list();

        for (Websites siteForCycle : websitesList) {//get unique websites from db
            if (siteForCycle.getWebsite().equals(domen)) {
                flagSites= false;
                break;
            }
        }

        for (Webpages pageForCycle : webpagesList) {//get unique webpages from db
            if (pageForCycle.getWebpage().equals(page)) {
                flagPages = false;
                break;
            }
        }

        if(flagSites){
            try {
                session.beginTransaction();
                website.setWebsite(domen);
                session.save(website);
                session.getTransaction().commit();
                //session.close();
            } catch (Exception e) {
                e.printStackTrace();
                session.close();
            }
        }

        if(flagPages){
            try {
                session.beginTransaction();
                webpages.setWebpage(page);
                webpages.setWebsites(website);
                session.save(webpages);
                session.getTransaction().commit();
                session.close();
            } catch (Exception e) {
                e.printStackTrace();
                session.close();
            }
        }
    }
}
