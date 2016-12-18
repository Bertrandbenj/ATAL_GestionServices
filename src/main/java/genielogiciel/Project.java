package genielogiciel;

import java.util.Arrays;
import org.apache.spark.sql.SQLContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import genielogiciel.model.Souhait;
import genielogiciel.config.SparkConfig;
import genielogiciel.dal.DAO;
import genielogiciel.dal.SparkDAO;
import genielogiciel.model.Contrat;
import genielogiciel.model.Departement;
import genielogiciel.model.Enseignant;
import genielogiciel.model.Enseignement;
import genielogiciel.model.Module;
import genielogiciel.model.Parcours;
import genielogiciel.model.Voeu;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * REQ 1 (Java) L’application doit être mise en œuvre dans le langages de
 * programmation Java (version ≥ 1.7).
 * 
 * @author ben
 *
 */
public class Project {

	public Project() {
	}

	public static void main(String[] args) {
		System.out.println("main");

		ApplicationContext springContext = new AnnotationConfigApplicationContext("genielogiciel.config");

		SparkConfig dao = springContext.getBean(SparkConfig.class);
		//createDS(dao.sqlContext());
		// sql.createDataset(Arrays.asList(d),
		// Demande.Encoder).write().csv("demande.csv");
		
		new Thread(()->{
			try {
				Thread.sleep(1000 * 60 * 5);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}).start();

		System.out.println("===========================showEnseignement===========================");
		dao.showEnseignement();
		
		System.out.println("===========================Demande de Service===========================");
		dao.demandeDeService(null, null, null);
		
		System.out.println("===========================Parcours===========================");
		dao.parcours(null).forEach(p -> System.out.println(p));
		
		System.out.println("===========================Departement toString===========================");
		dao.loadDepartement().forEach(d -> System.out.println(d));

		System.out.println("===========================Pick a Module===========================");
		dao.enseignements("Master Informatique", "M1 ATAL","Anglais").forEach(m -> System.out.println(m));

	}

	static void createDS(SQLContext sql) {

		Contrat contratUnique = new Contrat(20,50);
		
		Enseignant e = new Enseignant("Gerson", "Szunye", "prof",contratUnique);
		Enseignant e2 = new Enseignant("Hala", "Skaf", "prof",contratUnique);
		Enseignant e3 = new Enseignant("Labarbe", "Laurie", "prof",contratUnique);

		// Module file
		Module m = new Module("Genie Logiciel", Arrays.asList(new Enseignement("CM", 15.0),	new Enseignement("TD", 20.0), new Enseignement("TP", 15.0), new Enseignement("TP", 15.0)));

		Module m2 = new Module("COD", Arrays.asList(new Enseignement("CM", 18.0), new Enseignement("TD", 15.0), new Enseignement("TP", 15.0)));

		Module m3 = new Module("Anglais", Arrays.asList(new Enseignement("CM", 10.0), new Enseignement("TD", 20.0)));

		Module m4 = new Module("Test", Arrays.asList(new Enseignement("CM", 15.0), new Enseignement("TD", 15.0)));

		
		// Souhait 
		Souhait d = new Voeu(m.getEnseignements().get(0), 34);
		
		Parcours p = new Parcours("M1 ATAL", m, m2, m3);
		Parcours p2 = new Parcours("M1 ALMA", m, m2, m3, m4);

		Departement dep = new Departement("Master Informatique", p, p2);
		dep.setEnseignant(Arrays.asList(e,e2,e3));

		sql.createDataset(Arrays.asList(dep), Departement.Encoder).repartition(1).write().json("departement.json");

		// Demande File
		// sql.createDataset(Arrays.asList(d),
		// Demande.Encoder).repartition(1).write().json("demande.json");

		// Intervention file
		// sql.createDataset(Arrays.asList(e),
		// Enseignant.Encoder).repartition(1).write().json("enseignant.json");

	}

}
