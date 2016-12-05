package genielogiciel.model;

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
	private List<Demande> demandes;
	private Service interventions;
	
	public Service getInterventions() {
		return interventions;
	}

	public void setInterventions(List<Intervention> interventions) {
		this.interventions = new Service(interventions);
	}
	
	public void setInterventions(Service service) {
		this.interventions = interventions;
	}

	
	
	private String nom;
	private String prenom;
	private String status;
	
	public static Encoder<Enseignant> Encoder = Encoders.bean(Enseignant.class);

	public Enseignant(String nom, String prenom, String status) {
		this.nom = nom;
		this.prenom = prenom;
		this.status = status;
	}

	public String getNom() {
		return nom;
	}

	public String getPrenom() {
		return prenom;
	}
	
	
	/**
	 * REQ 16 (Expression de souhaits)
Le système doit permettre aux enseignants d’émettre ses souhaits d’enseignement.
	 * @param d 
	 */
	public void publieDemande(Demande d){
		d.setPublie(true);
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

	public List<Demande> getDemandes() {
		return demandes;
	}

	public void setDemandes(List<Demande> demandes) {
		this.demandes = demandes;
	}

	public Contrat getContrat() {
		return contrat;
	}

	public void setContrat(Contrat contrat) {
		this.contrat = contrat;
	}

	public Integer getVolume() {
		
		return null;
	}
	


}