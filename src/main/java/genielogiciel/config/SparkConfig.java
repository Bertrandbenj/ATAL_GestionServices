package genielogiciel.config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.hadoop.mapred.SequenceFileInputFilter.PercentFilter;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.DataTypes;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import genielogiciel.dal.SparkConfigI;
import genielogiciel.model.Contrat;
import genielogiciel.model.Departement;
import genielogiciel.model.Enseignant;
import genielogiciel.model.Enseignement;
import genielogiciel.model.Intervention;
import genielogiciel.model.Module;
import genielogiciel.model.Parcours;
import genielogiciel.model.Souhait;
import genielogiciel.model.Voeu;

@Configuration
public class SparkConfig extends SparkDAOImpl implements SparkConfigI {
	
	@Value(value = "${remote.name:Remote}")
	private String APP_NAME;

	@Value(value = "${remote.master:local[2]}")
	private String master;
	
	@Override
	@Bean
	public SparkSession sparkSession() {
		System.out.println("Connecting to "+master+" "+APP_NAME);
		return SparkSession
				.builder()
				.master(master)
				.config("spark.driver.memory", "12g")
				.appName(APP_NAME)
				.getOrCreate();
	}
	
	@Override
	@Bean 
	public Dataset<Departement> dsDepartement() {
		
		if(!Files.exists(Paths.get("src/main/resources/departement"))){
			try {
				createDS();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		Dataset<Departement> res = sqlContext()
				.read()
				.json("src/main/resources/departement/*.json")
				.as(Departement.Encoder)
				.cache();
		System.out.println("==================== Spark Data Structure ====================");
		res.printSchema();
		return res;
	}

	@Override
	@Bean
	public SQLContext sqlContext() {
		// The context itself
		SQLContext res =  sparkSession().sqlContext();
		
		// SQL function equivalentTD. registered on the cluster for use 
		res.udf().register("eqTD", (String tpe, Double vol) -> {
			Double volEqTD = vol ;
			volEqTD*=(tpe.equals("CM")?1.5:tpe.equals("TP")?1.0/1.5:1.0);
			return  Math.round(volEqTD*100)/100.0;
		}, DataTypes.DoubleType);
		
		// SQL function registered 
		res.udf().register("_1", (x) -> 1, DataTypes.IntegerType);
		return res;
	}

	@Override
	public void affecte(Souhait... s) {
		
		List<Souhait> sou = Arrays.asList(s);
		List<Intervention> inte = Stream.of(s).map(ss -> ss.affecte()).collect(Collectors.toList());
		
		int x = new Random().nextInt(999999);
		
		String persistFile = "src/main/resources/"+x;
		
		
		
		dsDepartement()
			.map(d -> {
				d.getEnseignant().forEach(e -> {
					e.getDemandes().removeAll(sou);
					e.getService().getInterventions().addAll(inte);
				});
				return d;
			}, Departement.Encoder)
			.repartition(1)
			.write().json(persistFile);
		
		try {
			Path res = Files.createSymbolicLink(
					Paths.get("src/main/resources/huhu.json"), 
					Paths.get(Paths.get(persistFile).toFile().list((dir,name)-> name.endsWith(".json"))[0]));
		} catch (IOException e) {
			e.printStackTrace();
		}
			
	}

	@Override
	public List<Enseignant> enseignants(Object departement) {
		return dsDepartement()
				.flatMap(d -> d.getEnseignant().iterator(), Enseignant.Encoder)
				.collectAsList()
				;
	}

	@Override
	public List<Souhait> souhaits(Object departement, Object enseignant) {
		return dsDepartement()
				.flatMap(d -> d.getEnseignant().iterator(), Enseignant.Encoder)
				.flatMap(e -> e.getDemandes().iterator(), Souhait.Encoder)
				.collectAsList();
	}

	@Override
	public List<Intervention> intervention(Object departement, Object enseignant) {
		return dsDepartement()
				.flatMap(d -> d.getEnseignant().iterator(), Enseignant.Encoder)
				.flatMap(e -> e.getService().getInterventions().iterator(), Intervention.Encoder)
				.collectAsList();
	}
	
	void createDS() throws IOException {
		Files.deleteIfExists(Paths.get("src/main/resources/departement.json"));
		Contrat contratTA = new Contrat(20,50);
		Contrat contratUnique = new Contrat(50,120);
		
		Enseignement glCM=new Enseignement("CM", 15.0);
		Enseignement glTD=new Enseignement("TD", 20.0);
		Enseignement glTP=new Enseignement("TP", 15.0);
		Enseignement glTP2=new Enseignement("TP", 15.0);
		
		Enseignement codCM=new Enseignement("CM", 20.0);
		Enseignement codTD=new Enseignement("TD", 15.0);
		Enseignement codTP=new Enseignement("TP", 15.0);
		Enseignement codTP2=new Enseignement("TP", 15.0);
		
		Enseignement teCM=new Enseignement("CM", 20.0);
		Enseignement teTD=new Enseignement("TD", 15.0);
		Enseignement teTP=new Enseignement("TP", 15.0);
		
		Enseignement anCM1=new Enseignement("CM", 40.0);
		Enseignement anCM2=new Enseignement("CM", 40.0);
		
		
		Enseignant eta = new Enseignant("phd", "student", "TA", contratTA);
		eta.getDemandes().add(new Voeu(glTP2,1));
		//eta.getDemandes().add(new Voeu(codTP2,1));
		
		Enseignant e = new Enseignant("Gerson", "Szunye", "prof",contratUnique);
		e.getDemandes().add(new Voeu(glTP,1));
		e.getDemandes().add(new Voeu(glTD,1));
		e.getDemandes().add(new Voeu(glCM,1));
		
		Enseignant e2 = new Enseignant("Hala", "Skaf", "prof",contratUnique);
		e2.getDemandes().add(new Voeu(codCM,1));
		e2.getDemandes().add(new Voeu(codTP,2));
		e2.getDemandes().add(new Voeu(codTD,1));
		
		Enseignant e3 = new Enseignant("Labarbe", "Laurie", "prof",contratUnique);
		e3.getDemandes().add(new Voeu(anCM1,1));

		// Module file
		Module m = new Module("Genie Logiciel", Arrays.asList(glCM,	glTD,glTP, glTP2));
		Module m2 = new Module("WebSem",Arrays.asList(codCM,codTD,codTP,codTP2) );
		Module m3 = new Module("Anglais", Arrays.asList(anCM1, anCM2));
		Module m4 = new Module("Test", Arrays.asList(teCM, teTD, teTP));

		
		// Souhait 
		Souhait d = new Voeu(m.getEnseignements().get(0), 34);
		
		Parcours p = new Parcours("M1 ATAL", m, m2, m3);
		Parcours p2 = new Parcours("M1 ALMA", m, m2, m3, m4);

		Departement dep = new Departement("Master Informatique", p, p2);
		dep.setEnseignant(Arrays.asList(e,e2,e3));

		sqlContext().createDataset(Arrays.asList(dep), Departement.Encoder)
			.write().
			json("src/main/resources/departement");

	}

}
