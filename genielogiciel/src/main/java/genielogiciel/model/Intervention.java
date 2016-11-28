package genielogiciel.model;

import org.apache.spark.sql.Encoder;
import org.apache.spark.sql.Encoders;

public class Intervention {

	public static final Encoder<Intervention> Encoder = Encoders.bean(Intervention.class);
	
	

}
