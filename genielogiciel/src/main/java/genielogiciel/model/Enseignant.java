package genielogiciel.model;

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
	private String nom;
	private String prenom;
	private String status;
	
	public static Encoder<Enseignant> Encoder = Encoders.bean(Enseignant.class);

	public Enseignant(String nom, String prenom, String status) {
		super();
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

}