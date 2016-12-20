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
	private Departement dep ;
	
	public Departement getDep() {
		return dep;
	}

	public void setDep(Departement dep) {
		this.dep = dep;
	}

	public ChefDepartement(DAO dao){
		this.dao=dao;
	}

	public ChefDepartement(DAO dao, Departement d){
		this.dao=dao;
		setDep(d);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public void publierVoeux(Souhait d, Souhait... ds) {
		if(dep==null){
			System.err.println("Pas de departement associer");
		}
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
		return dao.enseignants(dep)
				.stream()
				.filter(e -> e.getContrat().getMin() > e.getService().getTotVolume(null))
				;
	}

	@Override
	public Stream<Module> analyseModuleNonPourvu() {
		
		System.out.println("================= Modules Non Pourvu =================");

		// les Enseignements apparaissant dans les listes d'interventions (enseignements pourvu) 
		List<String> ensPourvu = dao.intervention(dep, null)
				.stream()		
				.filter(i->i instanceof InterventionLocale)
				.flatMap(i->((InterventionLocale) i).getEnseignements().stream())
				.map(e->e.getUuid())
				.collect(Collectors.toList());
		
		// les Enseignement qui existent
		Stream<Module> existingModules = dao.modules(dep,null)
				.stream()
				// On filtre les module dont tout les enseignements sont pourvu 
				.filter(m -> m.getEnseignements()
								.stream()
								.map(e->e.getUuid())
								.noneMatch(e-> ensPourvu.contains(e)));
		
		return existingModules;
		
	}

}
