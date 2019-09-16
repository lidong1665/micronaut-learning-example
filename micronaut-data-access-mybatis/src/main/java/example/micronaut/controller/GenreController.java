package example.micronaut.controller;

import example.micronaut.domain.ListingArguments;
import example.micronaut.domain.Genre;
import example.micronaut.service.GenreRepository;
import example.micronaut.domain.GenreSaveCommand;
import example.micronaut.domain.GenreUpdateCommand;
import io.micronaut.http.HttpHeaders;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Put;
import io.micronaut.validation.Validated;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@Validated // <1>
@Controller("/genres") // <2>
public class GenreController {

    protected final GenreRepository genreRepository;

    public GenreController(GenreRepository genreRepository) { // <3>
        this.genreRepository = genreRepository;
    }

    @Get("/show/{id}") // <4>
    public Genre show(Long id) {
        return genreRepository
                .findById(id)
                .orElse(null); // <5>
    }

    @Put("/update") // <6>
    public HttpResponse update(@Body @Valid GenreUpdateCommand command) { // <7>
        int numberOfEntitiesUpdated = genreRepository.update(command.getId(), command.getName());

        return HttpResponse
                .noContent()
                .header(HttpHeaders.LOCATION, location(command.getId()).getPath()); // <8>
    }

    @Get("/list{?args*}") // <9>
    public List<Genre> list(@Valid ListingArguments args) {
        return genreRepository.findAll(args);
    }

    @Post("/save") // <10>
    public HttpResponse<Genre> save(@Body @Valid GenreSaveCommand cmd) {
        Genre genre = genreRepository.save(cmd.getName());

        return HttpResponse
                .created(genre)
                .headers(headers -> headers.location(location(genre.getId())));
    }

    @Delete("/delete/{id}") // <11>
    public HttpResponse delete(Long id) {
        genreRepository.deleteById(id);
        return HttpResponse.noContent();
    }

    protected URI location(Long id) {
        return URI.create("/genres/" + id);
    }

    protected URI location(Genre genre) {
        return location(genre.getId());
    }
}
