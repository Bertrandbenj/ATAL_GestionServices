package genielogiciel.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.functions;
import org.apache.spark.sql.types.DataTypes;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import genielogiciel.dal.DAO;
import genielogiciel.dal.SparkConfigI;
import genielogiciel.dal.SparkDAO;
import genielogiciel.model.Contrat;
import genielogiciel.model.Departement;
import genielogiciel.model.Enseignant;
import genielogiciel.model.Enseignement;
import genielogiciel.model.Intervention;
import genielogiciel.model.Module;
import genielogiciel.model.Parcours;
import genielogiciel.model.Souhait;
import genielogiciel.model.Voeu;
import scala.Tuple2;

@Configuration
public class LocalDao implements DAO {


	public Departement Departement() {
		
		Contrat contratPro = new Contrat(300,500);
		
		Enseignant e = new Enseignant("toto", "alibaba", "prof",contratPro);
		Enseignant e2 = new Enseignant("tata", "superman", "prof",contratPro);
		Enseignant e3 = new Enseignant("tutu", "abcd", "prof",contratPro);

		// Module file
		Module m = new Module("Reseau", Arrays.asList(new Enseignement("CM", 15.0),	new Enseignement("TD", 20.0), new Enseignement("TP", 15.0), new Enseignement("TP", 15.0)));

		Module m2 = new Module("Based de Donnee", Arrays.asList(new Enseignement("CM", 18.0), new Enseignement("TD", 15.0), new Enseignement("TP", 15.0)));

		Module m3 = new Module("Anglais", Arrays.asList(new Enseignement("CM", 10.0), new Enseignement("TD", 20.0)));

		Module m4 = new Module("Magyar", Arrays.asList(new Enseignement("CM", 15.0), new Enseignement("TD", 15.0)));

		
		// Souhait 
		Souhait d = new Voeu(m.getEnseignements().get(0), 34);
		
		Parcours p = new Parcours("L2 Info", m, m2, m3);
		Parcours p2 = new Parcours("L3 Info", m, m2, m3, m4);

		Departement dep = new Departement("Licence Informatique", p, p2);
		dep.setEnseignant(Arrays.asList(e,e2,e3));
		
		
		return dep;
		
	}
	
	private List<Departement> departements;

	@Override
	public List<Departement> departements() {
		if(departements == null)
			departements = Arrays.asList(Departement());
			
		return departements;
		 
	}

	@Override
	public List<Parcours> parcours(Object departement) {
		return departements()
				.stream()
				.filter(d-> departement== null || d.equals(departement))
				.flatMap(d-> d.getParcours().stream())
				.collect(Collectors.toList());
	}

	@Override
	public List<Module> modules(Object departement, Object parcours) {
		return departements()
				.stream()
				.filter(d-> departement== null || d.equals(departement))
				.flatMap(d-> d.getParcours().stream())
				.filter(p-> parcours==null || p.equals(parcours))
				.flatMap(p->p.getModules().stream())
				.collect(Collectors.toList());
	}

	@Override
	public List<Enseignement> enseignements(Object departement, Object parcours, Object module) {
		return departements()
				.stream()
				.filter(d-> departement== null || d.equals(departement))
				.flatMap(d-> d.getParcours().stream())
				.filter(p-> parcours==null || p.equals(parcours))
				.flatMap(p->p.getModules().stream())
				.filter(m-> module==null || m.equals(module))
				.flatMap(e->e.getEnseignements().stream())
				.collect(Collectors.toList());
	}

	@Override
	public void showEnseignement() {
		departements().stream().forEach(d -> System.out.println(d));
	}

	@Override
	public void demandeDeService(String dep, String par, String mod) {
		enseignements(dep, par, mod)
			.stream()
			.forEach(e -> System.out.println(e));
	}

	@Override
	public void affecte(Souhait... s) {
		List<Souhait> sou = Arrays.asList(s);
		List<Intervention> inte = Stream.of(s).map(ss -> ss.affecte()).collect(Collectors.toList());
		
		departements.forEach(d -> {
			d.getEnseignant().forEach(e -> {
				e.getDemandes().removeAll(sou);
				e.getService().getInterventions().addAll(inte);
			});
		});
		
	}

	@Override
	public List<Enseignant> enseignants(Object departement) {
		return departements
				.stream()
				.flatMap(d -> d.getEnseignant().stream())
				.filter(d -> departement==null || d.equals(departement) )
				.collect(Collectors.toList())
				;
	}

	@Override
	public List<Souhait> souhaits(Object departement, Object enseignant) {
		return enseignants(departement)
				.stream()
				.flatMap(e-> e.getDemandes().stream())
				.filter(e -> enseignant ==null || e.equals(enseignant) )
				.collect(Collectors.toList())
				;
	}

	@Override
	public List<Intervention> intervention(Object departement, Object enseignant) {
		return enseignants(departement)
				.stream()
				.flatMap(e-> e.getService().getInterventions().stream())
				.filter(e -> enseignant ==null || e.equals(enseignant) )
				.collect(Collectors.toList())
				;
	}
	

	
}
