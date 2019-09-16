package example.micronaut;

import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.validation.Validated;
import io.reactivex.Single;
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
    public Single<String> getStringByMoible(@NotBlank String name) {
        System.out.println("name="+name);
        RedisCommands<String, String> redisCommands = connection.sync();
        final String mobile = redisCommands.get(name);
        return Single.just(mobile);
    }

    /**
     * 保存数据
     * @param name
     * @param mobile
     * @return
     */
    @Get(uri = "/saveString/{name}/{mobile}", produces = MediaType.TEXT_PLAIN)
    public Single<String> saveString(@NotBlank String name,@NotBlank String mobile) {
        System.out.println("name="+name);
        System.out.println("mobile="+mobile);
        RedisCommands<String, String> redisCommands = connection.sync();
        String result = redisCommands.set(name, mobile);
        return Single.just(result);
    }

    /**
     * 保存一个List数据
     * @param name
     * @return
     */
    @Get(uri = "/saveList/{name}", produces = MediaType.TEXT_PLAIN)
    public Single<List<Long>> saveList(@NotBlank String name) {
        System.out.println("name="+name);
        String[] names = new String[]{"name1","mame2","name3"};
        RedisCommands<String, String> redisCommands = connection.sync();
        List<Long> aL = new ArrayList<>();
        Arrays.stream(names).forEach(s -> {
            Long lpush = redisCommands.lpush(name, s);
            aL.add(lpush);
        });
        return Single.just(aL);
    }

}
