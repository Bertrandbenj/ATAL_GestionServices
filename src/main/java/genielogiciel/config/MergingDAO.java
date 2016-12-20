package genielogiciel.config;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import genielogiciel.dal.DAO;
import genielogiciel.model.Departement;
import genielogiciel.model.Enseignant;
import genielogiciel.model.Enseignement;
import genielogiciel.model.Intervention;
import genielogiciel.model.Module;
import genielogiciel.model.Parcours;
import genielogiciel.model.Souhait;

import static java.util.stream.Collectors.toList;

public class MergingDAO implements DAO {
	
	DAO[] daos ;
	
	public MergingDAO(DAO...dao){
		daos=dao;
	}

	@Override
	public List<Departement> departements() {
		return Stream.of(daos)
				.flatMap(d->d.departements().stream())
				.collect(toList());
	}

	@Override
	public List<Parcours> parcours(Object departement) {
		return Stream.of(daos)
			.flatMap(d->d.parcours(departement).stream())
			.collect(toList());
	}

	@Override
	public List<Module> modules(Object departement, Object parcours) {
		return Stream.of(daos)
				.flatMap(d->d.modules(departement, parcours).stream())
				.collect(toList());
	}

	@Override
	public List<Enseignement> enseignements(Object departement, Object parcours, Object module) {
		return Stream.of(daos)
				.flatMap(d->d.enseignements(departement, parcours, module).stream())
				.collect(toList());
	}

	@Override
	public void showEnseignement() {
		Stream.of(daos).forEach(DAO::showEnseignement);
	}

	@Override
	public void demandeDeService(String dep, String par, String mod) {
		Stream.of(daos).forEach(d -> d.demandeDeService(dep,par,mod));
	}

	@Override
	public void affecte(Souhait... s) {
		Stream.of(daos).forEach(dao -> dao.affecte(s));
	}

	@Override
	public List<Enseignant> enseignants(Object departement) {
		return Stream.of(daos)
				.flatMap(dao -> dao.enseignants(departement).stream())
				.collect(Collectors.toList());
	
	}

	@Override
	public List<Souhait> souhaits(Object departement, Object enseignant) {
		return Stream.of(daos)
				.flatMap(dao -> dao.souhaits(departement,enseignant).stream())
				.collect(Collectors.toList());
	}

	@Override
	public List<Intervention> intervention(Object departement, Object enseignant) {
		return Stream.of(daos)
				.flatMap(dao -> dao.intervention(departement,enseignant).stream())
				.collect(Collectors.toList());
	}

}
