package genielogiciel;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import genielogiciel.dal.DAO;
import genielogiciel.model.Departement;
import genielogiciel.model.Enseignant;
import genielogiciel.model.Intervention;
import genielogiciel.model.InterventionLocale;
import genielogiciel.model.Module;
import genielogiciel.model.Souhait;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class ChefDepartement implements ChefDepartementActor {
	private DAO dao;
	public ChefDepartement(DAO dao){
		this.dao=dao;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public void publierVoeux(Souhait d, Souhait... ds) {
		// TODO Auto-generated method stub

	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public void affecter(Souhait... souhaits) {
		dao.affecte(souhaits);
	}

	@Override
	public Stream<Enseignant> analyseSousService() {

		System.out.println("================= Enseignants en Sous-Service ================= ");
		return dao.departements()
				.stream()
				.flatMap(d->d.getEnseignant().stream())
				.filter(e -> e.getContrat().getMin() > e.getService().getTotVolume(null))
				;
	}

	@Override
	public Stream<Module> analyseModuleNonPourvu() {
		System.out.println("================= Modules Non Pourvu =================");
		
		// les Enseignements apparaissant dans les listes d'interventions (enseignements pourvu) 
		List<String> ensPourvu = dao.departements()
				.stream()
				.flatMap(d->d.getEnseignant().stream())
				.map(e->e.getService())
				.flatMap(p -> p.getInterventions().stream())			
				.filter(i->i instanceof InterventionLocale)
				.flatMap(i->((InterventionLocale) i).getEnseignements().stream())
				.map(e->e.getUuid())
				.collect(Collectors.toList());
		
		// les Enseignement qui existent
		Stream<Module> existingModules = dao.departements()
				.stream()
				.flatMap(d->d.getParcours().stream())
				.flatMap(p -> p.getModules().stream())
				// On filtre les module dont tout les enseignements sont pourvu 
				.filter(m -> m.getEnseignements().stream()
						.map(e->e.getUuid())
						.allMatch(e-> ensPourvu.contains(e)));
		
		return existingModules;
		
	}

}
