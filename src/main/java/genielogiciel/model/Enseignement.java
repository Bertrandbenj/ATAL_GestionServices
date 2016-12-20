package genielogiciel.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;

import org.apache.spark.sql.Encoder;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;

public class Enseignement {
	
	public static final Encoder<Enseignement> Encoder = Encoders.bean(Enseignement.class);

	private String type ;

	private Double volume; 
	
	private String uuid;
	
	transient Module mod;

	public Enseignement(){
		setUuid(UUID.randomUUID().toString());
	}
	
	public Enseignement(String t, Double vol){
		this();
		volume=vol;
		type=t;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Double eqTD(){
		switch(type){
		case "CM":
			return 1.5 ;
		case "TP":
			return 1/1.5 ;
		default:
			return 1.0;
		}
	}
	
	public static Double eqTD(String type){
		switch(type){
		case "CM":
			return 1.5 ;
		case "TP":
			return 1/1.5 ;
		default:
			return 1.0;
		}
	}
	
	public Row withEqTDRow(){
		return RowFactory.create( new Object[]{volume,type,eqTD()});
	}
	
	public Double getVolume() {
		return new BigDecimal(volume).setScale(2,RoundingMode.HALF_UP).doubleValue();
	}

	public void setVolume(Double volume) {
		this.volume = volume;
	}
	
	public String toString(){
		return (mod==null?"":mod.getName()+":")+type+":" + getVolume();
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

}
