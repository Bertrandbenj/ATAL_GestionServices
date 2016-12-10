package genielogiciel;

import java.util.List;
import java.util.stream.Stream;

import genielogiciel.model.Enseignant;
import genielogiciel.model.Intervention;
import genielogiciel.model.Module;
import org.springframework.context.annotation.Lazy;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import genielogiciel.model.Souhait;

public interface ChefDepartementActor {

	/**
	 * Action de rendre public (i.e. accessible à tous les utili- sateurs du
	 * système, qu’ils soient enseignant ou chef de département) un ou des
	 * souhaits formulés par un ou des enseignants.
	 * 
	 * REQ 14 (Publication des interventions) Le système doit permettre au chef
	 * du département de publier des interventions, des souhaits et des conflits
	 * 
	 * @param d
	 *            une demande
	 * @param ds
	 *            plusieurs demandes
	 */
	void publierVoeux(Souhait d, Souhait... ds);

	/**
	 * REQ 13 (Affectation) Confirmation, par le chef du département, d’un
	 * souhait d’un enseignant.
	 * 
	 * @param souhaits
	 * @return
	 */
	List<Intervention> affecter(Souhait... souhaits);

	/**
	 * REQ 15 (Analyse) Le système doit aider le chef du département à analyser
	 * les souhaits pour détecter des enseignants en sous-services et des
	 * modules non pourvus.
	 * 
	 * @return un Stream d'enseignants en sous-services
	 */
	public Stream<Enseignant> analyseSousService();

	/**
	 * REQ 15 (Analyse) Le système doit aider le chef du département à analyser
	 * les souhaits pour détecter des enseignants en sous-services et des
	 * modules non pourvus.
	 * 
	 * @return un Stream de Modules non pourvus
	 */
	public Stream<Module> analyseModuleNonPourvu();

}
