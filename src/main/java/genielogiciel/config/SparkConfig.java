package genielogiciel.config;

import java.nio.file.Files;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.spark.SparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.functions;
import org.apache.spark.sql.types.DataTypes;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import genielogiciel.dal.SparkDAO;
import genielogiciel.model.Departement;
import genielogiciel.model.Enseignement;
import genielogiciel.model.Module;
import genielogiciel.model.Parcours;

/**
 * REQ 20 (Protocoles ouverts) La communication entre les différents composants
 * du système doit utiliser des protocoles ouverts et disponibles sur
 * différentes plateformes.
 * 
 * @author ben
 *
 */

@Configuration
public class SparkConfig implements SparkDAO {

	@Value(value = "${appName:GestionEnseignements}")
	private String APP_NAME;

	@Value(value = "${master:local[*]}")
	private String master;

	@Bean
	public SparkContext sparkContext() {
		return sparkSession().sparkContext();
	}

	@Bean
	public SparkSession sparkSession() {
		return SparkSession
				.builder()
				.master(master)
				.appName(APP_NAME)
				.getOrCreate();
	}

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
	
	public void showEnseignement() {
		dsDepartement().selectExpr("name as D","explode(parcours) as p")
						.selectExpr("D", "p.name as P", "explode(p.modules) as m")
						.selectExpr("D", "P", "m.name as M", "explode(m.enseignements) as e")
						.selectExpr("D as Departement", "P as Parcours", "M as Module", "e.type as Type", "e.volume as Volume" )
						.show();
	}
	
	@Bean
	public Dataset<Departement> dsDepartement() {
		
		Dataset<Departement> res = sqlContext()
				.read()
				.json("src/main/resources/departement.json")
				.as(Departement.Encoder)
				.cache();
		res.printSchema();
		return res;
	}
	
	public Dataset<Parcours> dsParcours(Object d){
		return dsDepartement()
				.filter(dep -> d==null || dep.equals(d))
				.flatMap(dep -> dep.getParcours().iterator(), Parcours.Encoder);
	}
	
	public Dataset<Module> dsModules(Object d, Object p){
		System.out.println(d +" "+p);
		return dsParcours(d)
				.filter(par -> p==null || par.equals(p))
				.flatMap(par -> par.getModules().iterator(), Module.Encoder);
		
	}

	public Dataset<Enseignement> dsEnseignements(Object d, Object p, Object m){
		return dsModules(d,p)
				.filter(mod -> m==null || mod.equals(m))
				.flatMap(mod -> mod.getEnseignements().iterator(), Enseignement.Encoder);
	}
	
	public void demandeDeService(String dep, String par, String mod){
		dsDepartement()
			.selectExpr("name as Dep","explode(parcours) as p")
			//.where("Dep = "+dep)
			.selectExpr("Dep","p.name as Par" ,"explode(p.modules) as m")
			//.where("Par = "+par)
			.selectExpr("Dep","Par","m.name as Module","explode(m.enseignements) as e")
			//.where("Module = "+mod)
			.selectExpr("Dep","Par","Module","e.type","e.volume","eqTD(e.type, e.volume) as eqTD", "_1(e.type) as nbGroup")
			.groupBy("Dep","Par","Module","type")
			.sum("eqTD","nbGroup")
			.show();
	}
	
	/**
	 * this is just meant to explain showEnseignement()
	 */
	private void showEnseignement1() {
		Dataset<Departement>  res = dsDepartement();
		Dataset<Row> parc = res.select(res.col("name").as("Departement"),
				functions.explode(res.col("parcours")).as("parcour"));
		 parc.show();

		Dataset<Row> modules = parc.select(parc.col("Departement"), parc.col("parcour.modules").as("modules"),
				parc.col("parcour.name").as("Parcours"));
		// modules.show();

		Dataset<Row> enseignements = modules
				.select(modules.col("Departement"), modules.col("Parcours"),
						functions.explode(modules.col("modules")).as("Module"))
				.select("Departement", "Parcours", "Module.name", "Module.enseignements");
		// enseignements.show();

		enseignements.select(enseignements.col("Departement"), enseignements.col("Parcours"),
				enseignements.col("name").as("Module"), functions.explode(enseignements.col("enseignements")).as("ens"))
				.select("Departement", "Parcours", "Module", "ens.type", "ens.volume").show();
		
	}

	@Override
	public List<Departement> loadDepartement() {
		return dsDepartement().collectAsList();
	}

	@Override
	public List<Parcours> parcours(Object departement) {
		return dsParcours(departement).collectAsList();
	}

	@Override
	public List<Module> modules(Object departement, Object parcours) {
		return dsModules(departement, parcours).collectAsList();
	}

	@Override
	public List<Enseignement> enseignements(Object departement, Object parcours, Object module) {
		return dsEnseignements(departement, parcours, module).collectAsList();
	}


	
}
