package genielogiciel.dal;

import java.util.List;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.SQLContext;

import genielogiciel.model.Souhait;
import genielogiciel.model.Departement;
import genielogiciel.model.Enseignant;
import genielogiciel.model.Enseignement;
import genielogiciel.model.Intervention;
import genielogiciel.model.Module;
import genielogiciel.model.Parcours;

public interface DAO {

	/**
	 * 
	 * @return a list of all departement
	 */
	public List<Departement> loadDepartement();
	
	/**
	 * @param departement a Departement or a String to filter on or null 
	 * @return All Parcours within a Departement 
	 */
	public List<Parcours> parcours(Object departement);
	
	/**
	 * @param departement a Departement, a String or null to filter on
	 * @param parcours a Parcours, a String or null to filter on
	 * @return a List of Modules within this Departement and Parcours if not null;
	 */
	public List<Module> modules(Object departement, Object parcours);

	/**
	 * @param departement a Departement, a String or null to filter on
	 * @param parcours a Parcours, a String or null to filter on
	 * @param module a Module, a String or null to filter on
	 * @return a List of Enseignements within the filtering options 
	 */
	public List<Enseignement> enseignements(Object departement, Object parcours, Object module);
	
	/**
	 * Display in the console all Enseignements
	 */
	public void showEnseignement();
	
	/**
	 * Display in the console the requests for Services
	 * @param dep un filtre Departement 
	 * @param par un filtre Parours
	 * @param mod un filtre Module 
	 */
	public void demandeDeService(String dep, String par, String mod);
	
}
