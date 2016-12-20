package genielogiciel.dal;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.SparkSession;

import genielogiciel.model.Departement;
import genielogiciel.model.Enseignement;
import genielogiciel.model.Module;
import genielogiciel.model.Parcours;

/**
 * DAO specific to spark
 * @author ben & salma & kevin & samia
 *
 */
public interface SparkDAO {

	/**
	 * All {@link Departement} objects
	 * 
	 * @return a {@link Dataset} of (practically the whole base)
	 */
	public Dataset<Departement> dsDepartement();
	
	/**
	 * @param departement as a String (name) or as a Departement object
	 * @return
	 */
	public Dataset<Parcours> dsParcours(Object departement);
	
	/**
	 * 
	 * @param departement as a String (name) or as a Departement object
	 * @param parcours as a String (name) or as a Parcours object
	 * @return
	 */
	public Dataset<Module> dsModules(Object departement, Object parcours);

	/**
	 * 
	 * @param departement as a String (name) or as a Departement object
	 * @param parcours as a String (name) or as a Parcours object
	 * @param module as a String (name) or as a Module object
	 * @return
	 */
	public Dataset<Enseignement> dsEnseignements(Object departement, Object parcours, Object module);
	


}
