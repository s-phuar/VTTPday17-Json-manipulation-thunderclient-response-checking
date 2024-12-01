package VTTPday17.workshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WorkshopApplication {

	public static void main(String[] args) {
		SpringApplication.run(WorkshopApplication.class, args);
	}

}
// after testing that our SpringApp can connect to local redis, and we want to try deploying

//1. Test with redis cloud
	//go on railway, create database, new redis data (pseudo redis cloud for testing)
	//go into variables afted deployment success
	//grab REDIS_PUBLIC_URL string	*****************************************	
		//export SPRING_DATA_REDIS_USERNAME="default"
		//export SPRING_DATA_REDIS_PASSWORD="****"
		//export SPRING_DATA_REDIS_HOST="autotrack.proxy.rlwy.net"
		//export SPRING_DATA_REDIS_PORT="3099"
	
	//on ubuntu, open redis cloud. try running springboot on railway's redis
		//redis-cli -u {redis_public_url string}
		//mvn clean spring-boot:run
		//test program while connected to railway's redis. can use redis commands to check

	//windows uses a different command from above I think, test it
	//****creat text file and write out commands first, copy paste to terminal in the future***

//2. Dockerize
	//run mvn package once to check jar file name
	//add Dcokerfile to source directory (where pom file is)
		//set the environment variables
		//set host localhost
		//set 6379 port (default redis port)
		//never set username and password inside
	//turn into image
	//run image with -e to add on arguements, also include container name like in workshop 14

//3. railway
	//crate empty service on railway
	//copy over the credentials environment variables(not the values) to the empty service's setting's variables
	//then fill in the actuall password and stuff
	//deploy
	//generate domain
	//domain should work after doing railway link

