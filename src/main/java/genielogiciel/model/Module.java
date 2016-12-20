package genielogiciel.model;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.spark.sql.Encoder;
import org.apache.spark.sql.Encoders;

public class Module {

	public static final Encoder<Module> Encoder = Encoders.bean(Module.class);
	
	private List<Enseignement> enseignements ;
	private String name;

	transient Parcours par;
	
	
	public Module() {	
	}
	
	public Module(String n , List<Enseignement> l) {
		setEnseignements(l);
		setName(n);
	}

	public List<Enseignement> getEnseignements() {
		return enseignements;
	}

	public void setEnseignements(List<Enseignement> list) {
		list.forEach(e -> e.mod=this);
		this.enseignements = list;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	@Override
	public boolean equals(Object o) {
		if (o == null)
			return false; 
		
		if(o instanceof Module) {
			return ((Module) o).getName().equals(name);
		}
		
		if(o instanceof String) {
			return ((String) o).equals(name);
		}
		return false;
	}
	
	public String toString(){
		return "["+enseignements.stream().map(x->x.toString()).collect(Collectors.joining(" , "))+"]";
	}

}
