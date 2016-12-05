package genielogiciel.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.spark.sql.Dataset;

public class Service {

	private static final long serialVersionUID = -2763937560141368234L;

	private List<Intervention> interventions;

	public Service() {

	}

	public Service(List<Intervention> inter) {
		interventions = inter;
	}

	public void showService(Integer year) {
		String s = interventions
				.stream()
				.filter(i -> year == null ? true : year.equals(i.annee))
				.map(i -> i.toString())
				.collect(Collectors.joining("\n"));
		System.out.println("Services : \n" + s);
	}

	public Double getTotVolume() {
		return interventions.stream().collect(Collectors.summingDouble(i -> i.getVolume()));
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
