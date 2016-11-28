package genielogiciel.model;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.spark.sql.Encoder;
import org.apache.spark.sql.Encoders;

public class Module {

	public static final Encoder<Module> Encoder = Encoders.bean(Module.class);
	
	private List<Enseignement> enseignements ;
	private String name;
	
	public Module() {	
	}
	
	public Module(String n , List<Enseignement> l) {
		enseignements=l;
		name=n;
	}

	public List<Enseignement> getEnseignements() {
		return enseignements;
	}

	public void setEnseignements(List<Enseignement> list) {
		this.enseignements = list;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String toString(){
		return "Module: "+name+"["+enseignements.stream().map(x->x.toString()).collect(Collectors.joining(","))+"]";
	}

}
