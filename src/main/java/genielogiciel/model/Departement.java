package genielogiciel.model;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.spark.sql.Encoder;
import org.apache.spark.sql.Encoders;

public class Departement {

	public static final Encoder<Departement> Encoder = Encoders.bean(Departement.class);

	private String name;
	private List<Parcours> parcours;
	private List<Enseignant> enseignant;

	public Departement() {

	}

	/**
	 * REQ 15 (Analyse) Le système doit aider le chef du département à analyser
	 * les souhaits pour détecter des enseignants en sous-services et des
	 * modules non pourvus.
	 * 
	 * @return un Stream d'enseignants en sous-services 
	 */
	public Stream<Enseignant> analyseSousService() {
		return enseignant.stream().filter(e -> e.getContrat().getMin() > e.getInterventions().getTotVolume());
	}

	/**
	 * REQ 15 (Analyse) Le système doit aider le chef du département à analyser
	 * les souhaits pour détecter des enseignants en sous-services et des
	 * modules non pourvus.
	 * 
	 * @return un Stream de Modules non pourvus 
	 */
	public Stream<Module> analyseModuleNonPourvu() {
		return parcours.stream().flatMap(p -> p.getModules().stream());
	}

	/**
	 * TODO REQ 13 (Affectation)
	 * Confirmation, par le chef du département, d’un souhait d’un enseignant.
	 */
	public void affecter(Demande d){
		
		enseignant.stream()
			.flatMap(e -> e.getDemandes().stream())
			.filter(dd-> dd.equals(d));
		parcours.stream().flatMap(p -> p.getModules().stream());
	}
	
	public void publieDemande(Demande d) {
		d.setPublie(true);
	}

	public Departement(String string, Parcours... p) {
		setName(string);
		setParcours(Arrays.asList(p));
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Parcours> getParcours() {
		return parcours;
	}

	public void setParcours(List<Parcours> parcours) {
		this.parcours = parcours;
	}

	public String toString() {
		return "Departement: " + name + "\n"
				+ parcours.stream().map(x -> x.toString()).collect(Collectors.joining("\n"));
	}

	@Override
	public boolean equals(Object o) {
		if (o != null && o instanceof Departement) {
			if (((Departement) o).getName().equals(name)) {
				return true;
			}
		}
		return false;
	}

	public List<Enseignant> getEnseignant() {
		return enseignant;
	}

	public void setEnseignant(List<Enseignant> enseignant) {
		this.enseignant = enseignant;
	}

}
