package genielogiciel.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.spark.sql.Encoder;
import org.apache.spark.sql.Encoders;

/**
 * Personne ”physique” travaillant pour le compte d’un département et identifiée
 * par son nom, son prénom et son statut. Un enseignant peut ”intervenir” dans
 * différents départements pour dispenser un certain nombre d’enseignements.
 * C’est un également un acteur du sytème puisqu’il peut effectuer des vœux
 * concernant les enseignements qu’il désire donner
 * 
 * @author ben
 *
 */

public class Enseignant {
	private Contrat contrat;
	private List<Souhait> demandes;
	private Service interventions;
	private String nom;
	private String prenom;
	private String status;

	public static Encoder<Enseignant> Encoder = Encoders.bean(Enseignant.class);

	
	public Enseignant(){
		demandes = new ArrayList<Souhait>();
		interventions = new Service();
	}
	
	public Enseignant(String nom, String prenom, String status, Contrat contratUnique) {
		this();
		this.nom = nom;
		this.prenom = prenom;
		this.status = status;
		contrat=contratUnique;		
	}

	public String getNom() {
		return nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public String getStatus() {
		return status;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public Service getInterventions() {
		return interventions;
	}

	public void setInterventions(List<Intervention> interventions) {
		this.interventions = new Service(interventions);
	}

	public void setInterventions(Service service) {
		this.interventions = service;
	}

	public List<Souhait> getDemandes() {
		return demandes;
	}

	/**
	 * Action, faite par un enseignant, de formuler un certain nombre de
	 * souhaits qu’il pourra décider, par la suite, de publier (de soumettre au
	 * chef de département et aux autres enseignants) ou pas.
	 * 
	 * Nous travaillons en read only {@link #publishDemandes(List)}
	 * 
	 * @param demandes
	 */
	public void setDemandes(List<Souhait> demandes) {
		this.demandes = demandes;
	}

	public Contrat getContrat() {
		return contrat;
	}

	public void setContrat(Contrat contrat) {
		this.contrat = contrat;
	}

	public Integer getVolume() {
		return interventions.getTotVolume().intValue();
	}

}