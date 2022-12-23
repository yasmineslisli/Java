package models;
public class Job {
    private String titre;
    private String ville;
    private String nomEntreprise;
    private String nivEtudes;
    private String nivExp;
    private int nbrePostes;
    private String typeContrat;
    private String datePublication;
    private String competences;
    private String traits;
    private String site;
    private String url;

    public Job(String titre, String ville, String nomEntreprise, String nivEtudes, String nivExp, int nbrePostes, String typeContrat, String datePublication, String competences, String traits, String site, String url) {
        this.titre = titre;
        this.ville = ville;
        this.nomEntreprise = nomEntreprise;
        this.nivEtudes = nivEtudes;
        this.nivExp = nivExp;
        this.nbrePostes = nbrePostes;
        this.typeContrat = typeContrat;
        this.datePublication = datePublication;
        this.competences = competences;
        this.traits = traits;
        this.site = site;
        this.url = url;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getNomEntreprise() {
        return nomEntreprise;
    }

    public void setNomEntreprise(String nomEntreprise) {
        this.nomEntreprise = nomEntreprise;
    }

    public String getNivEtudes() {
        return nivEtudes;
    }

    public void setNivEtudes(String nivEtudes) {
        this.nivEtudes = nivEtudes;
    }

    public String getNivExp() {
        return nivExp;
    }

    public void setNivExp(String nivExp) {
        this.nivExp = nivExp;
    }

    public int getNbrePostes() {
        return nbrePostes;
    }

    public void setNbrePostes(int nbrePostes) {
        this.nbrePostes = nbrePostes;
    }

    public String getTypeContrat() {
        return typeContrat;
    }

    public void setTypeContrat(String typeContrat) {
        this.typeContrat = typeContrat;
    }

    public String getDatePublication() {
        return datePublication;
    }

    public void setDatePublication(String datePublication) {
        this.datePublication = datePublication;
    }

    public String getCompetences() {
        return competences;
    }

    public void setCompetences(String competences) {
        this.competences = competences;
    }

    public String getTraits() {
        return traits;
    }

    public void setTraits(String traits) {
        this.traits = traits;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Job{" +
                "titre='" + titre + '\'' +
                ", ville='" + ville + '\'' +
                ", nomEntreprise='" + nomEntreprise + '\'' +
                ", nivEtudes='" + nivEtudes + '\'' +
                ", nivExp='" + nivExp + '\'' +
                ", nbrePostes=" + nbrePostes +
                ", typeContrat='" + typeContrat + '\'' +
                ", datePublication='" + datePublication + '\'' +
                ", competences='" + competences + '\'' +
                ", traits='" + traits + '\'' +
                ", site='" + site + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
