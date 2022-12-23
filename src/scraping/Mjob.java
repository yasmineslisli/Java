package scraping;

import models.Job;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import static controllers.JobController.addJob;
import static scraping.Recrute.getTechnologies;

public class Mjob {
    static String baseURL = "https://www.m-job.ma";
    static int nbrePages = 2;

    public static ArrayList<String> getJobLinks() throws IOException {
        ArrayList<String> jobLinks = new ArrayList<>();
        Document document = null;

        for(int i = 1; i <= nbrePages; i++) {
            try {
                document = Jsoup.connect(baseURL + "/recherche/informatique+_+internet+_+multimedia?page=" + i).get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            assert document != null;
            Elements jobSections = document.getElementsByClass("offer-box");
            for (Element jobSection : jobSections) {
                Elements linkElements = jobSection.select(".offer-title a");
                if (!linkElements.isEmpty()) {
                    jobLinks.add(linkElements.get(0).attr("href"));
                    System.out.println(i);
                }
            }
        }
        return jobLinks;
    }

    public static ArrayList<Job> getJobs() throws IOException {
        ArrayList<String> jobLinks = getJobLinks();

        ArrayList<Job> jobs = new ArrayList<>();

        for(String link : jobLinks) {
            ArrayList<String> compArray = new ArrayList<>();

            String titre = "";
            String ville = "";
            String nomEntreprise = "";
            String nivEtudes = "";
            String nivExp = "";
            int nbrePostes = 0;
            String typeContrat = "";
            String datePublication = "";
            String competences = "";
            String traits = "";
            String site = "M-job";
            String url = link;

            Document document = null;
            try {
                document = Jsoup.connect(link).get();
            } catch (IOException e) {
                e.printStackTrace();
            }

            assert document != null;
            Elements jobBlock = document.getElementsByClass("details-content");

            Elements titreElements = jobBlock.select(".offer-title");
            if(!titreElements.isEmpty()) {
                titre = titreElements.get(0).text();
            }

            Elements villeElements = jobBlock.select(".location");
            if(!villeElements.isEmpty()) {
                ville = villeElements.get(0).text();
            }

            Elements nomEntrepriseElements = jobBlock.select(".header-info .list-details li:first-child h3");
            if(!nomEntrepriseElements.isEmpty()) {
                nomEntreprise = nomEntrepriseElements.get(0).text();
            }

            Elements typeContratElements = jobBlock.select(".header-info .list-details li:nth-child(2) h3");
            if(!typeContratElements.isEmpty()) {
                typeContrat = typeContratElements.get(0).text();
            }

            Elements commonElements = jobBlock.select(".the-content h3");
            if(!commonElements.isEmpty()) {
                for(int i = 0; i < commonElements.size(); i++ ) {
                    if(commonElements.get(i).text().contains("Niveau d'expériences")) {
                        int index = i + 1;
                        nivExp = jobBlock.select(".the-content div:nth-of-type(" + index + ")").text();
                    }
                    if(commonElements.get(i).text().contains("Niveau d'études exigé")) {
                        int index = i + 1;
                        nivEtudes = jobBlock.select(".the-content div:nth-of-type(" + index + ")").text();
                    }
                }

            }


            Elements details = jobBlock.select(".the-content");
            if(!details.isEmpty()) {
                String description = details.text().toLowerCase();
                for (String comp : getTechnologies()) {
                    if (description.contains(comp)) {
                        if (!compArray.contains(comp)) {
                            compArray.add(comp);
                        }
                    }
                }
            }
            competences = String.join(",",compArray);

            Job job = new Job(titre,ville,nomEntreprise,nivEtudes,nivExp,nbrePostes,typeContrat,datePublication,
                    competences,traits,site,url);
            jobs.add(job);
        }
        return jobs;
    }

    public static void main(String[] args) throws IOException, SQLException {
        for(Job job : getJobs()) {
            addJob(job);
        }
    }
}



