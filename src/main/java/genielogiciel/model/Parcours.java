package genielogiciel.model;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.spark.sql.Encoder;
import org.apache.spark.sql.Encoders;

public class Parcours {
	
	public static final Encoder<Parcours> Encoder = Encoders.bean(Parcours.class);
	private List<Module> modules;
	private String name;
	
	public Parcours(){
		
	}
	
	public Parcours(String string, Module... m) {
		setName(string);
		setModules(Arrays.asList(m));
	}


	public List<Module> getModules() {
		return modules;
	}


	public void setModules(List<Module> modules) {
		this.modules = modules;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String toString(){
		return "Parcours: "+name+"\n\t"+modules.stream().map(x->x.toString()).collect(Collectors.joining("\n\t"));
	}

}
