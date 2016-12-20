package genielogiciel.model;

import java.time.LocalDateTime;
import java.util.UUID;

import org.apache.spark.sql.Encoder;
import org.apache.spark.sql.Encoders;

public class Souhait {

	public static Encoder<Souhait> Encoder = Encoders.bean(Souhait.class);
	
	private Boolean publie;
	private String uuid;
	transient Enseignant ens ;
	

	public Souhait(String uuid, Boolean publie) {
		setUuid(uuid);
		setPublie(publie);
	}
	
	public Souhait(Boolean publie) {
		setUuid(UUID.randomUUID().toString());
		setPublie(publie);
	}
	
	public Souhait() {
		this(false);
	}
	
	public Intervention affecte(){
		Intervention res = new Intervention();
		res.setAnnee(LocalDateTime.now().getYear());
		return res;
	}
	
	public boolean equals(Object o){
		if(o==null)
			return false;
		
		if(o instanceof String)
			return ((String)o).equals(uuid);
		
		if(o instanceof Souhait)
			return ((Souhait)o).uuid.equals(uuid);
		return false;
	}
	
	public Double getVolume(){
		return 0.;
	}

	public Boolean getPublie() {
		return publie;
	}

	public void setPublie(Boolean publie) {
		this.publie = publie;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String string) {
		this.uuid = string;
	}

}
