package OMDB.Liantis_Pinseel_Benjamin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class OmDbLiantisPinseelBenjaminApplication {

    public static void main(String[] args) {

        SpringApplication.run(OmDbLiantisPinseelBenjaminApplication.class, args);

    }

}
