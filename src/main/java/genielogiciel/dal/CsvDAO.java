package genielogiciel.dal;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.SQLContext;

import genielogiciel.model.Souhait;
import genielogiciel.model.Departement;
import genielogiciel.model.Enseignant;
import genielogiciel.model.Enseignement;
import genielogiciel.model.Intervention;

public class CsvDAO implements PersistInterface{

	public Dataset<Departement> loadDepartement() {
		// TODO Auto-generated method stub
		return null;
	}

}