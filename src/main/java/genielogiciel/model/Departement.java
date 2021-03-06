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
	 * TODO 
	 * REQ 13 (Affectation)
	 * Confirmation, par le chef du département, d’un souhait d’un enseignant.
	 */
	public void affecter(Souhait d){
		
		enseignant.stream()
			.flatMap(e -> e.getDemandes().stream())
			.filter(dd-> dd.equals(d));
		parcours.stream().flatMap(p -> p.getModules().stream());
	}
	
	public void publieDemande(Souhait d) {
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
		parcours.forEach(p -> p.dep=this);
		this.parcours = parcours;
	}

	public String toString() {
		return "Departement: " + name + "\n"
				+ parcours.stream().map(x -> x.toString()).collect(Collectors.joining("\n"));
	}

	@Override
	public boolean equals(Object o) {
		if (o == null)
			return false; 
		
		if(o instanceof Departement) {
			return ((Departement) o).getName().equals(name);
		}
		
		if(o instanceof String) {
			return ((String) o).equals(name);
		}
		
		return false;
	}

	public List<Enseignant> getEnseignant() {
		return enseignant;
	}

	public void setEnseignant(List<Enseignant> enseignant) {
		enseignant.forEach(e -> e.dep = this);
		this.enseignant = enseignant;
	}

}
