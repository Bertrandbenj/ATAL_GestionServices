package genielogiciel;

import java.util.List;
import java.util.stream.Stream;

import org.apache.spark.sql.Dataset;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import genielogiciel.model.Enseignement;
import genielogiciel.model.Intervention;
import genielogiciel.model.Souhait;

public interface EnseignantActor {

	/**
	 * TODO
	 * REQ 16 (Expression de souhaits) Le système doit permettre aux enseignants
	 * d’émettre ses souhaits d’enseignement.
	 * 
	 * @param d
	 */
	public void faireUnSouhait(Souhait d);

	/**
	 * Jouer Terme employé dans la description du domaine. Action, faite par un
	 * enseignant, de formuler un certain nombre de souhaits qu’il pourra
	 * décider, par la suite, de publier (de soumettre au chef de dépar- tement
	 * et aux autres enseignants) ou pas.
	 * 
	 */
	public void jouer(Souhait d);

	/**
	 * REQ 17 (Publication de souhaits) Le système doit permettre aux
	 * enseignants de publier leurs souhaits.
	 * 
	 * @param d
	 *            une demande
	 * @param ds
	 *            plusieurs demandes
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	void publierVoeux(Souhait d, Souhait... ds);

	/**
	 * REQ 18 (Consultation des enseignements) Le système doit permettre aux
	 * enseignants de consulter les enseignements.
	 */
	List<Enseignement> consulterEnseignements();
}
