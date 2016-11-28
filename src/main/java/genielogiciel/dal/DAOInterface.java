package genielogiciel.dal;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.SQLContext;

import genielogiciel.model.Demande;
import genielogiciel.model.Departement;
import genielogiciel.model.Enseignant;
import genielogiciel.model.Enseignement;
import genielogiciel.model.Intervention;

public interface DAOInterface {

	public Dataset<Departement> loadDepartement();
	
	
}
