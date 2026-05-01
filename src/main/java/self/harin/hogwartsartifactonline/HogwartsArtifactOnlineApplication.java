package self.harin.hogwartsartifactonline;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import self.harin.hogwartsartifactonline.artifact.utils.IdWorker;

@SpringBootApplication
public class HogwartsArtifactOnlineApplication {

    public static void main(String[] args) {
        SpringApplication.run(HogwartsArtifactOnlineApplication.class, args);
    }

    @Bean
    public IdWorker idWorker() {
        return new IdWorker(1, 1);
    }

}
