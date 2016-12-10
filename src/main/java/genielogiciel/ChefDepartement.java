package genielogiciel;

import java.util.List;
import java.util.stream.Stream;

import genielogiciel.model.Enseignant;
import genielogiciel.model.Intervention;
import genielogiciel.model.Module;
import genielogiciel.model.Souhait;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class ChefDepartement implements ChefDepartementActor {

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public void publierVoeux(Souhait d, Souhait... ds) {
		// TODO Auto-generated method stub

	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public List<Intervention> affecter(Souhait... souhaits) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Stream<Enseignant> analyseSousService() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Stream<Module> analyseModuleNonPourvu() {
		// TODO Auto-generated method stub
		return null;
	}

}
