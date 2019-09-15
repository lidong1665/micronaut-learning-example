package example.micronaut;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.validation.Validated;
import io.reactivex.Single;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import javax.validation.constraints.NotBlank;

@Controller("/")
@Validated
public class HelloController {



    @Get(uri = "/hello/{name}", produces = MediaType.TEXT_PLAIN)
    @Operation(summary = "name",
            description = "A friendly greeting is returned"
    )
    @ApiResponse(
            content = @Content(mediaType = "text/plain",
                    schema = @Schema(type="string"))
    )
    @ApiResponse(responseCode = "400", description = "Invalid Name Supplied")
    @ApiResponse(responseCode = "404", description = "Person not found")
    @Tag(name = "greeting")
    public Single<String> hello(@Parameter(description="The name of the person")  @NotBlank String name) {
        System.out.println("name="+name);
        return Single.just("Hello " + name + "!");
    }



    @Get(uri = "/index",produces = MediaType.TEXT_PLAIN)
    public String index1() {
        return "Hello World";
    }
}
