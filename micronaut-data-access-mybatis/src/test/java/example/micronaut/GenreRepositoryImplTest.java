package example.micronaut;

import example.micronaut.domain.Genre;
import example.micronaut.service.GenreRepository;
import io.micronaut.test.annotation.MicronautTest;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.validation.ConstraintViolationException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

@MicronautTest
public class GenreRepositoryImplTest {

    @Inject
    public GenreRepository genreRepository;

    @Test
    public void constraintsAreValidatedForFindById() {
        assertThrows(ConstraintViolationException.class, () -> {
            genreRepository.findById(null);
        });
    }

    @Test
    public void constraintsAreValidatedForDeleteById() {
        assertThrows(ConstraintViolationException.class, () -> {
            genreRepository.deleteById(null);
        });
    }

    @Test
    public void constraintsAreValidatedForFindAll() {
        assertThrows(ConstraintViolationException.class, () -> {
            List<Genre> all = genreRepository.findAll(null);
            all.stream().forEach(genre -> {
                System.out.println(genre.getName());
            });
        });
    }

    @Test
    public void constraintsAreValidatedForSave() {
        assertThrows(ConstraintViolationException.class, () -> {
            genreRepository.save(null);
        });
    }

    @Test
    public void constraintsAreValidatedForUpdateNameIsNull() {
        assertThrows(ConstraintViolationException.class, () -> {
            genreRepository.update(12L, null);
        });
    }

    @Test
    public void constraintsAreValidatedForUpdateIdIsNull() {
        assertThrows(ConstraintViolationException.class, () -> {
            genreRepository.update(null, "foo");
        });
    }


    @Test
    public void constraintsAreValidatedForUpdateNameIsBlank() {
        assertThrows(ConstraintViolationException.class, () -> {
            genreRepository.update(4L, "");
        });
    }
}
