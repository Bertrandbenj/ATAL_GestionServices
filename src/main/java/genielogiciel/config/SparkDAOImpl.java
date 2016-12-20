package genielogiciel.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.functions;
import genielogiciel.dal.DAO;
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

public abstract class SparkDAOImpl implements DAO {


	public abstract SparkSession sparkSession();
	
	public abstract Dataset<Departement> dsDepartement(); 
	
	@Override
	public void showEnseignement() {
		dsDepartement()
				.selectExpr("name as D","explode(parcours) as p")
				.selectExpr("D", "p.name as P", "explode(p.modules) as m")
				.selectExpr("D", "P", "m.name as M", "explode(m.enseignements) as e")
				.selectExpr("D as Departement", "P as Parcours", "M as Module", "e.type as Type", "e.volume as Volume" )
				.groupBy("Departement", "Parcours", "Module")
				.pivot("Type", Arrays.asList("TD","TP","CM"))
				.sum("Volume")
				.show();
	}	
	
	public Dataset<Parcours> dsParcours(Object d){
		return dsDepartement()
				.filter(dep -> d==null || dep.equals(d))
				.flatMap(dep -> dep.getParcours().iterator(), Parcours.Encoder);
	}
	
	public Dataset<Module> dsModules(Object d, Object p){
		return dsParcours(d)
				.filter(par -> p==null || par.equals(p))
				.flatMap(par -> par.getModules().iterator(), Module.Encoder);
		
	}
	
	public Dataset<Enseignement> dsEnseignements(Object d, Object p, Object m){
		return dsModules(d,p)
				.filter(mod -> m==null || mod.equals(m))
				.flatMap(mod -> mod.getEnseignements().iterator(), Enseignement.Encoder);
	}
	
	@Override
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
	
	@Override
	public List<Departement> departements() {
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
