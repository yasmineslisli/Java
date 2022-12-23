package controllers;
import db.Connection;
import models.Job;

import java.sql.*;
import java.util.ArrayList;

public class JobController {
    public static Statement connection = Connection.connectToDb();

    public static ArrayList<Job> getJobs() throws SQLException {
        ArrayList<Job> jobs = new ArrayList<>();
        String request = "SELECT * FROM job";
        ResultSet resultSet = connection.executeQuery(request);
        while (resultSet.next()) {
            String titre = resultSet.getString("titre");
            String nomEntreprise = resultSet.getString("nomEntreprise");
            String ville = resultSet.getString("ville");
            String nivEtudes = resultSet.getString("nivEtudes");
            String nivExp = resultSet.getString("nivExp");
            int nbrePostes = Integer.parseInt(resultSet.getString("nbrePostes"));
            String datePublication = resultSet.getString("datePublication");
            String typeContrat = resultSet.getString("typeContrat");
            String competences = resultSet.getString("competences");
            String traits = resultSet.getString("traits");
            String site = resultSet.getString("site");
            String url = resultSet.getString("url");

            Job job = new Job(titre,nomEntreprise,ville,nivEtudes,nivExp,nbrePostes,datePublication,typeContrat,
                    competences,traits,site,url);
            jobs.add(job);
        }
        return jobs;
    }

    public static void addJob(Job job) throws SQLException {
//        String request = "INSERT INTO job (titre,nomEntreprise,ville,nivEtudes,nivExp,nbrePostes," +
//                "datePublication,typeContrat,compétences,traits,site,url) VALUES (\"" + job.getTitre() + "\",\"" + job.getNomEntreprise() + "\",\""
//                + job.getVille() + "\",\"" + job.getNivEtudes() + "\",\"" + job.getNivExp() + "\"," + job.getNbrePostes() + ",\"" + job.getDatePublication()
//                + "\",\"" + job.getTypeContrat() + "\",\"" + job.getCompetences() + "\",\"" + job.getTraits() + "\",\"" + job.getSite() + "\",\"" + job.getUrl() + "\")" ;
//        connection.execute(request);
        String query = "INSERT INTO job (titre,nomEntreprise,ville,nivEtudes,nivExp,nbrePostes,datePublication," +
                "typeContrat,compétences,traits,site,url) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement pstmt = DriverManager.getConnection("jdbc:mysql://localhost:3306/scrap","root","").prepareStatement(query);
        pstmt.setString(1, job.getTitre());
        pstmt.setString(2, job.getNomEntreprise());
        pstmt.setString(3, job.getVille());
        pstmt.setString(4, job.getNivEtudes());
        pstmt.setString(5, job.getNivExp());
        pstmt.setInt(6, job.getNbrePostes());
        pstmt.setString(7, job.getDatePublication());
        pstmt.setString(8, job.getTypeContrat());
        pstmt.setString(9, job.getCompetences());
        pstmt.setString(10, job.getTraits());
        pstmt.setString(11, job.getSite());
        pstmt.setString(12, job.getUrl());
        pstmt.executeUpdate();
        System.out.println("Job added");
    }
}


