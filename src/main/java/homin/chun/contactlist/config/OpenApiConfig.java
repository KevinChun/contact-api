package homin.chun.contactlist.config;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Contact List API",
                version = "v1",
                description = "Contact List API v1"
        ),
        servers = @Server(url = "http://localhost:8080")
)
public class OpenApiConfig {
}
