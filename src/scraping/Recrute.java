package scraping;

import models.Job;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import static controllers.JobController.addJob;

public class Recrute {
    static String baseURL = "https://www.rekrute.com";
    static int nbrePages = 67;

    public static ArrayList<String> getJobLinks() throws IOException {
        ArrayList<String> jobLinks = new ArrayList<>();
        Document document = null;

        for(int i = 1; i <= nbrePages; i++) {
            try {
                document = Jsoup.connect(baseURL + "/offres" +
                        ".html?p="+ i +"&s=1&o=1&positionId%5B0%5D=13&positionId%5B1%5D=19&positionId%5B2%5D=23").get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            assert document != null;
            Elements jobSections = document.getElementsByClass("post-id");
            for (Element jobSection : jobSections) {
                Elements linkElements = jobSection.select("a.titrejob");
                if (!linkElements.isEmpty()) {
                    jobLinks.add(linkElements.get(0).attr("href"));
                    System.out.println(i);
                }
            }
        }
        return jobLinks;
    }

    public static ArrayList<String> getTechnologies() throws RuntimeException{
        ArrayList<String> techs = new ArrayList<>();
        File techFile = new File("src/scraping/techs.txt");
        Scanner sc = null;
        try {
            sc = new Scanner(techFile);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        while(sc.hasNextLine()) {
            techs.add(sc.nextLine());
        }
        return techs;
    }

    public static ArrayList<Job> getJobs() throws IOException {
        ArrayList<String> jobLinks = getJobLinks();

        ArrayList<Job> jobs = new ArrayList<>();

        for(String link : jobLinks) {
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
            String site = "Recrute";
            String url = link;

            Document document = null;
            try {
                document = Jsoup.connect(baseURL + link).get();
            } catch (IOException e) {
                e.printStackTrace();
            }

            assert document != null;
            Elements jobBlock = document.getElementsByClass("contentbloc");

            Elements titreElements = jobBlock.select("h1");
            if(!titreElements.isEmpty()) {
                titre = titreElements.get(0).text().split(" - ")[0];
            }

            Elements villeElements = jobBlock.select("h1");
            if(!villeElements.isEmpty()) {
                ville = villeElements.get(0).text().split(" - ")[1];
            }

            Elements nomEntrepriseElements = jobBlock.select(".foruloffemp h4");
            if(!nomEntrepriseElements.isEmpty()) {
                nomEntreprise = nomEntrepriseElements.get(0).text();
                nomEntreprise = nomEntreprise.replace("Les dernières offres d’emploi de « ","");
                nomEntreprise = nomEntreprise.replace(" »","");
            }

            Elements nivEtudesElements = jobBlock.select(".featureInfo:first-of-type > li:nth-child(3)");
            if(!nivEtudesElements.isEmpty()) {
                nivEtudes = nivEtudesElements.get(0).text();
            }

            Elements nivExpElements = jobBlock.select(".featureInfo:first-of-type > li:nth-child(1)");
            if(!nivExpElements.isEmpty()) {
                nivExp = nivExpElements.get(0).text();
            }

            Elements nbrePostesElements = jobBlock.select(".featureInfo:first-of-type > li:nth-child(2) b");
            if(!nbrePostesElements.isEmpty()) {
                nbrePostes = Integer.parseInt(nbrePostesElements.get(0).text());
            }

            Elements typeContratElements = jobBlock.select(".featureInfo .tagContrat");
            if(!typeContratElements.isEmpty()) {
                typeContrat = typeContratElements.get(0).text();
            }

            Elements datePubElements = jobBlock.select(".newjob");
            if(!datePubElements.isEmpty()) {
                datePublication = datePubElements.get(0).text();
                datePublication = datePublication.substring(7,datePublication.indexOf("sur")).trim();
            }

            Elements competencesElements = jobBlock.select("div.blc");
            ArrayList<String> compArray = new ArrayList<>();
            if(!competencesElements.isEmpty()) {
                for(Element compElement : competencesElements) {
                    if(compElement.text().contains("Profil recherché :")) {
                        String description = compElement.text().toLowerCase();
                        for(String comp : getTechnologies()) {
                            if(description.contains(comp)) {
                                if(!compArray.contains(comp)) {
                                    compArray.add(comp);
                                }
                            }
                        }
                        competences = String.join(",",compArray);
                    }
                }

            }

            Elements traitsElements = jobBlock.select(".jobdetail .row .tagSkills");
            ArrayList<String> traitsArray = new ArrayList<>();
            if(!traitsElements.isEmpty()) {
                for(Element traitsElement : traitsElements) {
                    traitsArray.add(traitsElement.text());
                }
                traits = String.join(",", traitsArray);
            }

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



