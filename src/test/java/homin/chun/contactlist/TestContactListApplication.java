package homin.chun.contactlist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

//@TestConfiguration(proxyBeanMethods = false)
@Testcontainers
public class TestContactListApplication {

    /*@Bean
    @ServiceConnection
    PostgreSQLContainer<?> postgresContainer() {
        String DATABASE_NAME = "clo";
        String USERNAME = "test_user";
        String PASSWORD = "test_password";
        return new PostgreSQLContainer<>(DockerImageName.parse("postgres:latest"))
                .withDatabaseName(DATABASE_NAME)
                .withUsername(USERNAME)
                .withPassword(PASSWORD)
                .withInitScript("init.sql");
    }

    public static void main(String[] args) {
        SpringApplication.from(ContactListApplication::main).with(TestContactListApplication.class).run(args);
    }*/

    @Container
    public static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:latest")
            .withDatabaseName("clo")
            .withUsername("sa")
            .withPassword("sa");


    @DynamicPropertySource
    static void registerPgProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", () -> "postgres");
        registry.add("spring.datasource.password", () -> "pass");
    }


}
