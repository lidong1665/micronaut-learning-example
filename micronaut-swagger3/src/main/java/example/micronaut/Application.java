package example.micronaut;

import io.micronaut.context.ApplicationContext;
import io.micronaut.runtime.Micronaut;
import io.micronaut.runtime.context.scope.refresh.RefreshEvent;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.servers.ServerVariable;
import io.swagger.v3.oas.annotations.tags.Tag;

@OpenAPIDefinition(
        info = @Info(
                title = "hello world",
                version = "1.0",
                description = "My API",
                license = @License(name = "Apache 2.0", url = "http://foo.bar"),
                contact = @Contact(url = "http://gigantic-server.com", name = "lidong", email = "lidong1665@163.com")
        ),
        tags = {
                @Tag(name = "Tag 1", description = "desc 1", externalDocs = @ExternalDocumentation(description = "docs desc")),
                @Tag(name = "Tag 2", description = "desc 2", externalDocs = @ExternalDocumentation(description = "docs desc 2")),
                @Tag(name = "Tag 3")
        },
        externalDocs = @ExternalDocumentation(description = "definition docs desc"),
        security = {
                @SecurityRequirement(name = "req 1", scopes = {"a", "b"}),
                @SecurityRequirement(name = "req 2", scopes = {"b", "c"})
        },
        servers = {
                @Server(
                        description = "server 1",
                        url = "http://127.0.0.1:8089",
                        variables = {
                                @ServerVariable(name = "var1", description = "var 1", defaultValue = "1", allowableValues = {"1", "2"}),
                                @ServerVariable(name = "var2", description = "var 2", defaultValue = "1", allowableValues = {"1", "2"})
                        }),
                @Server(
                        description = "server 2",
                        url = "http://127.0.0.1:8089",
                        variables = {
                                @ServerVariable(name = "var1", description = "var 1", defaultValue = "1", allowableValues = {"1", "2"}),
                                @ServerVariable(name = "var2", description = "var 2", defaultValue = "1", allowableValues = {"1", "2"})
                        })
        }
)
public class Application {

    public static void main(String[] args) {
        Micronaut.run(Application.class);
//        try (ApplicationContext context = ApplicationContext.run()) {
//            HelloController myBean = context.getBean(HelloController.class);
//            // do something with your bean
//            myBean.hello("222222");
//        }
//        Vehicle vehicle = BeanContext.run().getBean(Vehicle.class);
//        System.out.println(vehicle.start());

//        TestController bean = BeanContext.run().getBean(TestController.class);
//        System.out.println(bean.index());

        try (ApplicationContext applicationContext = ApplicationContext.run()) {
            applicationContext.publishEvent(new RefreshEvent());
            WeatherService weatherService = applicationContext.getBean(WeatherService.class);
//            // do something with your bean
           System.out.println(weatherService.latestForecast());
        }

    }
}
