package washing.machineru.scheduler.api;

import io.micronaut.runtime.Micronaut;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "Washing machine scheduler",
                version = "1.0",
                description = "API to schedule your washing machine usage"
        )
)
public class Application {
    public static void main(String[] args) {
        Micronaut.run(Application.class);
    }
}