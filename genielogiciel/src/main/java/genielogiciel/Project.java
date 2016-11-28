package genielogiciel;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.apache.spark.ml.feature.Interaction;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoder;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.SQLContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import genielogiciel.dal.LocalDAO;
import genielogiciel.model.Demande;
import genielogiciel.model.Departement;
import genielogiciel.model.Enseignant;
import genielogiciel.model.Enseignement;
import genielogiciel.model.Intervention;
import genielogiciel.model.Module;
import genielogiciel.model.Parcours;

public class Project {

	
	public Project() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		
		ApplicationContext springContext = new AnnotationConfigApplicationContext("genielogiciel.config");
		SQLContext sql = (SQLContext) springContext.getBean("sqlContext");
		
		
		LocalDAO dao = new LocalDAO(sql);
		//createDS(sql);
		//sql.createDataset(Arrays.asList(d), Demande.Encoder).write().csv("demande.csv");
	
		System.out.println("===========================showEnseignement===========================");
		//dao.showEnseignement();
		System.out.println("===========================showEnseignement2===========================");
		//dao.showEnseignement2();
		System.out.println("===========================Parcours===========================");
		//dao.parcours(null).show();
		System.out.println("===========================Demande de Service===========================");
		dao.demandeDeService(null,null,null);
		System.out.println("===========================Cassic java toString===========================");
		dao.loadDepartement().collectAsList().forEach(d -> System.out.println(d.toString()));

		try {
			Thread.sleep(1000*60*5);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}
	
	static void createDS(SQLContext sql ){
		Demande d = new Demande(true,34);
		
		Enseignant e = new Enseignant("Gerson", "Szunye","prof");
		
		// Module file
		Module m = new Module("Genie Logiciel", Arrays.asList(
				new Enseignement("CM",15.0),
				new Enseignement("TD",15.0),
				new Enseignement("TP",15.0),
				new Enseignement("TP",15.0)));
		
		Module m2 = new Module("COD", Arrays.asList(
				new Enseignement("CM",15.0),
				new Enseignement("TD",15.0),
				new Enseignement("TP",15.0)));
		
		Module m3 = new Module("Anglais", Arrays.asList(
				new Enseignement("CM",15.0),
				new Enseignement("TD",15.0)));
		
		Module m4 = new Module("Test", Arrays.asList(
				new Enseignement("CM",15.0),
				new Enseignement("TD",15.0)));
		
		Parcours p = new Parcours("M1 ATAL", m, m2, m3);
		Parcours p2 = new Parcours("M1 ALMA", m, m2, m3, m4);
		
		Departement dep = new Departement("Master Informatique", p,p2 );
		
		sql.createDataset(Arrays.asList(dep), Departement.Encoder).repartition(1).write().json("departement.json");
		
		// Demande File
		//sql.createDataset(Arrays.asList(d), Demande.Encoder).repartition(1).write().json("demande.json");
		
		// Intervention file
		//sql.createDataset(Arrays.asList(e), Enseignant.Encoder).repartition(1).write().json("enseignant.json");
		
		
	}

}
