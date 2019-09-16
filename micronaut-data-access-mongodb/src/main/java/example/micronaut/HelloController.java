package example.micronaut;

import com.mongodb.reactivestreams.client.*;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.validation.Validated;
import io.reactivex.Single;
import org.bson.Document;

import javax.inject.Inject;
import javax.validation.constraints.NotBlank;


@Controller("/")
@Validated
public class HelloController {

    @Inject
    MongoClient mongoClient;

    @Get(uri = "/saveUser", produces = MediaType.TEXT_PLAIN)
    public Single<User> saveUser(@NotBlank String name,@NotBlank Integer age) {
        System.out.println("name="+name);
        User user = new User(name, age);
        Document doc = new Document();
        doc.append("name",name);
        doc.append("age",age);
        return Single
                .fromPublisher(getCollection().insertOne(doc))
                .map(success -> {
                    String id = doc.get("_id").toString();
                    System.out.println(id);
                    user.set_id(id);
                    return  user;
                });
    }



    @Get(uri = "/getUser/{name}",produces = MediaType.TEXT_PLAIN)
    public Single<User> getUserByName(String name) {
        User user = new User();
        return Single
                .fromPublisher(getCollection().find(new Document("name",name)).first())
                .map(document -> {
                    String _id = document.get("_id").toString();
                    Integer age =(Integer) document.get("age");
                    String namex = (String) document.get("name");
                    System.out.println(_id);
                    user.setAge(age);
                    user.setName(namex);
                    user.set_id(_id);
                    return user;
                });
    }


    private MongoCollection<Document> getCollection() {
        return mongoClient
                .getDatabase("dbConsultMessage")
                .getCollection("tp_user");
    }
}
