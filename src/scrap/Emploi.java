package scrap;
import model.Job;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

import static scrap.Recrute.getTechnologies;

public class Emploi {
    static String baseURL = "https://www.emploi.ma";
    static int nbrePages = 6;

    public static ArrayList<String> getJobLinks() throws IOException {
        ArrayList<String> jobLinks = new ArrayList<>();
        Document document = null;

        for(int i = 0; i <= nbrePages; i++) {
            try {
                document = Jsoup.connect(baseURL + "/recherche-jobs-maroc?f%5B0%5D=im_field_offre_metiers%3A31&page=" + i).get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            assert document != null;
            Elements jobSections = document.getElementsByClass("job-description-wrapper");
            for (Element jobSection : jobSections) {
                Elements linkElements = jobSection.select("h5 a");
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
            StringBuilder competences = new StringBuilder();

            Document document = null;
            try {
                document = Jsoup.connect(baseURL + link).get();
            } catch (IOException e) {
                e.printStackTrace();
            }

            assert document != null;
            Element jobBlock = document.getElementById("main-content");

            assert jobBlock != null;
            Elements titreElements = jobBlock.select(".bloc-title h1");
            if(!titreElements.isEmpty()) {
                titre = titreElements.get(0).text().split(" - ")[0];
            }

            Elements datePubElements = jobBlock.select(".job-ad-publication-date");
            if(!datePubElements.isEmpty()) {
                datePublication = datePubElements.get(0).text();
                datePublication = datePublication.substring(10).trim();
            }

            Elements nomEntrepriseElements = jobBlock.select(".company-title");
            if(!nomEntrepriseElements.isEmpty()) {
                nomEntreprise = nomEntrepriseElements.get(0).text();
            }


            Elements commonElements = jobBlock.select(".job-ad-criteria tr");
            if(!commonElements.isEmpty()) {
                for(Element element : commonElements) {
                    if(element.text().contains("Type de contrat")) {
                        typeContrat = element.select("td:nth-child(2)").text();
                    }
                    if(element.text().contains("Ville")) {
                        ville = element.select("td:nth-child(2)").text();
                    }
                    if(element.text().contains("Nombre de poste(s)")) {
                        nbrePostes = Integer.parseInt(element.select("td:nth-child(2)").text());
                    }
                    if(element.text().contains("Niveau d'études")) {
                        nivEtudes = element.select("td:nth-child(2)").text();
                    }
                    if(element.text().contains("Niveau d'expérience")) {
                        nivExp = element.select("td:nth-child(2)").text();
                        if(nivExp.contains("Expérience")) {
                            nivExp = nivExp.replace("Expérience ","");
                        }
                        if(nivExp.contains("Débutant")) {
                            nivExp = nivExp.replace("Débutant ","");
                        }
                    }
                    if(element.text().contains("Compétences clés")) {
                        Elements competencesElements = element.select("td:nth-child(2) .field-item");
                        if(!competencesElements.isEmpty()) {
                            for(Element compElement : competencesElements) {
                                compArray.add(compElement.text().toLowerCase());
                            }
                        }
                    }
                }
            }

            Elements details = jobBlock.select(".jobs-ad-details");
            if(!details.isEmpty()) {
                for(Element compElement : details) {
                    String description = compElement.text().toLowerCase();
                    for(String comp : getTechnologies()) {
                        if(description.contains(comp)) {
                            if(!compArray.contains(comp)) {
                                compArray.add(comp);
                            }
                        }
                    }
                }
            }

            competences = new StringBuilder(String.join(",", compArray));
            System.out.println(competences + link);
            Job job = new Job(titre,nomEntreprise,ville,nivEtudes,nivExp,nbrePostes, datePublication,typeContrat,
                    competences.toString());
            jobs.add(job);
        }
        return jobs;
    }

    public static void main(String[] args) throws IOException {
        getJobs();
    }

}