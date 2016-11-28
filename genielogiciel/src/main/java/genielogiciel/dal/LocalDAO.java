package genielogiciel.dal;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.TypedColumn;
import org.apache.spark.sql.functions;
import org.apache.spark.sql.catalyst.optimizer.PushProjectThroughSample;
import org.apache.spark.sql.types.DataTypes;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import genielogiciel.Project;
import genielogiciel.model.Demande;
import genielogiciel.model.Departement;
import genielogiciel.model.Enseignant;
import genielogiciel.model.Enseignement;
import genielogiciel.model.Intervention;
import genielogiciel.model.Module;
import genielogiciel.model.Parcours;
import scala.Tuple2;

import org.apache.spark.sql.functions;

@Configuration
public class LocalDAO implements DAOInterface {

	private SQLContext sql;

	public LocalDAO(SQLContext sql) {
		this.sql = sql;
		sql.udf().register("eqTD", (String tpe, Double vol) -> {
			Double res = vol ;
			res*=(tpe.equals("CM")?1.5:tpe.equals("TP")?1.0/1.5:1.0);
			return  Math.round(res*100)/100.0;
		}, DataTypes.DoubleType);
		
		sql.udf().register("_1", (x) -> 1, DataTypes.IntegerType);
	}

	
	public Dataset<Departement> loadDepartement() {
		Dataset<Departement> res = sql.read()
				.json("src/main/resources/departement.json")
				.as(Departement.Encoder)
				.cache();
//		res.printSchema();
//		res.show(false);
		return res;
	}
	
	public Dataset<Parcours> parcours(Departement d){
		Dataset<Departement> res = loadDepartement();
		return res.filter(dep -> d==null || dep.equals(d))
				.flatMap(dep -> dep.getParcours().iterator(), Parcours.Encoder);
	}
	
	public Dataset<Module> modules(Departement d, Parcours p){
		return parcours(d).filter(par -> p==null || p.getName().equals(par.getName()))
				.flatMap(par -> par.getModules().iterator(), Module.Encoder);
		
	}

	public Dataset<Enseignement> enseignements(Departement d, Parcours p, Module m){
		return modules(d,p)
				.filter(mod -> m==null || m.getName().equals(mod.getName()))
				.flatMap(mod -> mod.getEnseignements().iterator(), Enseignement.Encoder);
	}
	
	public void demandeDeService(Departement d, Parcours p, Module m){
		Dataset<Module> mod = modules(d,p);
		mod.selectExpr("name as M","explode(enseignements) as e")
			.selectExpr("M as Module","e.type","e.volume","eqTD(e.type, e.volume) as eqTD", "_1(e.type) as nbGroup")
			.groupBy("Module","type","volume")
			.sum("eqTD","nbGroup")
			.show();
	}
	
	public void showEnseignement() {
		Dataset<Departement>  res = loadDepartement();
		Dataset<Row> parc = res.select(res.col("name").as("Departement"),
				functions.explode(res.col("parcours")).as("parcour"));
		// parc.show();

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

	
	public void showEnseignement2() {
		loadDepartement().selectExpr("name as D","explode(parcours) as p")
						.selectExpr("D", "p.name as P", "explode(p.modules) as m")
						.selectExpr("D", "P", "m.name as M", "explode(m.enseignements) as e")
						.selectExpr("D as Departement", "P as Parcours", "M as Module", "e.type as Type", "e.volume as Volume" )
						.show();
	
	}

}
