package example.micronaut;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.validation.Validated;

@Controller("/")
@Validated
public class HelloController {

    @Get(uri = "/hello",produces = MediaType.TEXT_PLAIN)
    public String hello() {
        return "Hello World";
    }
}
