package genielogiciel.model;

import org.apache.spark.sql.Encoder;
import org.apache.spark.sql.Encoders;

public abstract class Intervention {
	
	Integer annee;

	public static final Encoder<Intervention> Encoder = Encoders.bean(Intervention.class);
	
	public abstract Double getVolume();

}
