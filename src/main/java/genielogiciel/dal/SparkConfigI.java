package genielogiciel.dal;

import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.SparkSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

public interface SparkConfigI {

	public SparkSession sparkSession() ;
	
	public SQLContext sqlContext();
	
}
