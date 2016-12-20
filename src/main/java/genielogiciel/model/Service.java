package genielogiciel.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoder;
import org.apache.spark.sql.Encoders;

public class Service {

	private static final long serialVersionUID = -2763937560141368234L;
	
	public static Encoder<Service> Encoder = Encoders.bean(Service.class); 

	private List<Intervention> interventions;

	public Service() {
		interventions= new ArrayList<Intervention>();
	}

	public List<Intervention> getInterventions() {
		return interventions;
	}

	public void setInterventions(List<Intervention> interventions) {
		this.interventions = interventions;
	}

	public Service(List<Intervention> inter) {
		interventions = inter;
	}

	public Double getTotVolume(Integer year) {
		return interventions
				.stream()
				.filter(i -> year==null || i.getAnnee().equals(year))
				.collect(Collectors.summingDouble(i -> i.getVolume()));
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
