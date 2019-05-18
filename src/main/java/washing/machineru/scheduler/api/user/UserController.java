package washing.machineru.scheduler.api.user;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;
import washing.machineru.scheduler.api.user.domain.User;

import java.util.ArrayList;
import java.util.List;

@Controller("/user")
public class UserController {
    @Get()
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> getUser() {
        List<User> userList = new ArrayList<>();
        userList.add(new User("friend", "friend@friendly.com"));
        return userList;
    }
}
