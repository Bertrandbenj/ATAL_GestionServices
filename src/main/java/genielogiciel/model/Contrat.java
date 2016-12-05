package genielogiciel.model;

import org.apache.spark.sql.Encoder;
import org.apache.spark.sql.Encoders;

public class Contrat {
	
	private Integer min;
	private Integer max;
	
	public static Encoder<Contrat> Encoder = Encoders.bean(Contrat.class);
	
	public Contrat(Integer min, Integer max){
		this.setMin(min);
		this.setMax(max);
	}
	
	public Contrat(){
		this.setMin(0);
		this.setMax(0);
	}
	
	public Contrat(Integer min){
		this.setMin(min);
		this.setMax(0);
	}

	public Integer getMin() {
		return min;
	}

	public void setMin(Integer min) {
		this.min = min;
	}

	public Integer getMax() {
		return max;
	}

	public void setMax(Integer max) {
		this.max = max;
	}

}
