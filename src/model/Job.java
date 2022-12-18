package model;
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

    public Job(String titre, String nomEntreprise, String ville, String nivEtudes, String nivExp, int nbrePostes,
               String datePublication, String typeContrat, String competences) {
        this.titre = titre;
        this.nomEntreprise = nomEntreprise;
        this.ville = ville;
        this.nivEtudes = nivEtudes;
        this.nivExp = nivExp;
        this.nbrePostes = nbrePostes;
        this.datePublication = datePublication;
        this.typeContrat = typeContrat;
        this.competences = competences;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getNomEntreprise() {
        return nomEntreprise;
    }

    public void setNomEntreprise(String nomEntreprise) {
        this.nomEntreprise = nomEntreprise;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
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

    public String getDatePublication() {
        return datePublication;
    }

    public void setDatePublication(String datePublication) {
        this.datePublication = datePublication;
    }

    public String getTypeContrat() {
        return typeContrat;
    }

    public void setTypeContrat(String typeContrat) {
        this.typeContrat = typeContrat;
    }

    public String getCompetences() {
        return competences;
    }

    public void setCompetences(String competences) {
        this.competences = competences;
    }

    @Override
    public String toString() {
        return "Job{" +
                "titre='" + titre + '\'' +
                ", nomEntreprise='" + nomEntreprise + '\'' +
                ", ville='" + ville + '\'' +
                ", nivEtudes='" + nivEtudes + '\'' +
                ", nivExp='" + nivExp + '\'' +
                ", nbrePostes=" + nbrePostes +
                ", datePublication='" + datePublication + '\'' +
                ", typeContrat='" + typeContrat + '\'' +
                ", compétences='" + competences + '\'' +
                '}';
    }
}