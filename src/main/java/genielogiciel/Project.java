package genielogiciel;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import org.apache.spark.sql.SQLContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import genielogiciel.model.Souhait;
import genielogiciel.config.LocalDao;
import genielogiciel.config.MergingDAO;
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

import org.springframework.beans.BeansException;
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

	public static void main(String[] args) throws BeansException, IOException {

		ApplicationContext springContext = new AnnotationConfigApplicationContext("genielogiciel.config");
		SparkConfig remote = springContext.getBean(SparkConfig.class);
		LocalDao local = springContext.getBean(LocalDao.class);
		
		
		
		DAO dao = new MergingDAO(local,remote);
		
		System.out.println("===========================Chef De Departement===========================");
		ChefDepartementActor boss = new ChefDepartement(dao);
		boss.analyseModuleNonPourvu().forEach(m->System.out.println(m));
		boss.analyseSousService().forEach(e-> System.out.println(e + 
					" Service : "+e.getService().getTotVolume(null) +
					" Souhait : "+e.getDemandes().stream().mapToDouble(s-> s.getVolume()).sum())
				);
		
		//boss.affecter(new Souhait(true));
		
		// sql.createDataset(Arrays.asList(d),
		// Demande.Encoder).write().csv("demande.csv");
		
		// Timer to keep the Spark gui up and analyse rdd execution
		new Thread(()->{try {Thread.sleep(1000 * 60 * 5);} catch (Exception e) {}}).start();

		System.out.println("===========================showEnseignement===========================");
		dao.showEnseignement();
		
		System.out.println("===========================Demande de Service===========================");
		dao.demandeDeService(null, null, null);
		
		System.out.println("===========================Departement toString===========================");
		dao.departements().forEach(d -> System.out.println(d));

		System.out.println("===========================Pick a Module===========================");
		dao.enseignements("Master Informatique", "M1 ATAL","Anglais").forEach(m -> System.out.println(m));

	}

}
