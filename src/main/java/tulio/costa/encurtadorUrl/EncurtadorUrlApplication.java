package tulio.costa.encurtadorUrl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tulio.costa.encurtadorUrl.service.UrlsService;

@SpringBootApplication
public class EncurtadorUrlApplication {


	public static void main(String[] args) {


		SpringApplication.run(EncurtadorUrlApplication.class, args);
	}

}
