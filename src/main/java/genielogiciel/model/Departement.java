package genielogiciel.model;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.spark.sql.Encoder;
import org.apache.spark.sql.Encoders;

public class Departement {
	
	public static final Encoder<Departement> Encoder = Encoders.bean(Departement.class);
	
	private String name;
	private List<Parcours> parcours;
	
	public Departement(){
		
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
	
	public String toString(){
		return "Departement: "+name+"\n"+parcours.stream().map(x->x.toString()).collect(Collectors.joining("\n"));
	}
	
	@Override
	public boolean equals(Object o){
		if(o!=null && o instanceof Departement){
			if(((Departement)o).getName().equals(name)){
				return true;
			}
		}
		return false;
	}

}
