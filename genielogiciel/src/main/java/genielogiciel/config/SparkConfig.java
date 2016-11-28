package genielogiciel.config;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.SparkSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;


@Configuration
public class SparkConfig {
	
	@Value(value = "${appName:Gestion}")
	private String APP_NAME;
	
	@Value(value = "${master:local[*]}")
	private String master;
	
	
	@Lazy
	@Bean
	public JavaSparkContext sparkContext() {
		return new JavaSparkContext(
			new SparkConf()
				.setAppName(APP_NAME)
				.setMaster(master));
	}
	
	@Lazy
	@Bean 
	public SparkSession sparkSession(){
		LogManager.getLogger("org").setLevel(Level.OFF);

		SparkSession sess = SparkSession.builder()
			     .master(master)
			     .appName(APP_NAME)
			     //.config("spark.some.config.option", "some-value")
			     .getOrCreate();

		return sess;
	};
	
	@Lazy	
	@Bean
	public SQLContext sqlContext(){
		return sparkSession().sqlContext();
	}
}
