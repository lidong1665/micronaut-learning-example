package example.micronaut;

import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.reactive.RedisReactiveCommands;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.validation.Validated;
import reactor.core.publisher.Mono;

import javax.inject.Inject;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller("/")
@Validated
public class HelloController {

    @Inject
    StatefulRedisConnection<String, String> connection;

    /**
     * 获取数据
     * @param name
     * @return
     */
    @Get(uri = "/getStringByMoible/{name}", produces = MediaType.TEXT_PLAIN)
    public Mono<String> getStringByMoible(@NotBlank String name) {
        System.out.println("name="+name);
        RedisReactiveCommands<String, String> redisReactiveCommands = connection.reactive();
        Mono<String> mono = redisReactiveCommands.get(name);
        return mono;
    }

    /**
     * 保存数据
     * @param name
     * @param mobile
     * @return
     */
    @Get(uri = "/saveString/{name}/{mobile}", produces = MediaType.TEXT_PLAIN)
    public Mono<String> saveString(@NotBlank String name,@NotBlank String mobile) {
        System.out.println("name="+name);
        System.out.println("mobile="+mobile);
        RedisReactiveCommands<String, String> redisReactiveCommands = connection.reactive();
        Mono<String> result = redisReactiveCommands.set(name, mobile);
        return result;
    }

    /**
     * 保存一个List数据
     * @param name
     * @return
     */
    @Get(uri = "/saveList/{name}", produces = MediaType.TEXT_PLAIN)
    public Mono<List<Long>> saveList(@NotBlank String name) {
        System.out.println("name="+name);
        String[] names = new String[]{"name1","mame2","name3"};
        RedisReactiveCommands<String, String> redisReactiveCommands = connection.reactive();
        List <Long> ls = new ArrayList<>();
        Arrays.stream(names).forEach(s -> {
            Mono<Long> lpush = redisReactiveCommands.lpush(name, s);
            ls.add(lpush.block());
        });
        return Mono.just(ls);
    }

}
