package genielogiciel.model;

import java.time.LocalDateTime;
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
	private Service service;
	private String nom;
	private String prenom;
	private String status;
	transient Departement dep;

	public static Encoder<Enseignant> Encoder = Encoders.bean(Enseignant.class);

	
	public Enseignant(){
		demandes = new ArrayList<Souhait>();
		service = new Service();
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
	
	public Service getService() {
		return service;
	}

	public void setService(List<Intervention> interventions) {
		interventions.forEach(i->i.ens=this);
		this.service = new Service(interventions);
	}

	public void setService(Service service) {
		this.service = service;
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
		demandes.forEach(d -> d.ens=this);
		this.demandes = demandes;
	}

	public Contrat getContrat() {
		return contrat;
	}

	public void setContrat(Contrat contrat) {
		this.contrat = contrat;
	}

	public Integer getVolume() {
		return service.getTotVolume(LocalDateTime.now().getYear()).intValue();
	}
	
	@Override
	public String toString(){
		return status+". "+nom+" "+prenom+" "+contrat;
	}

}