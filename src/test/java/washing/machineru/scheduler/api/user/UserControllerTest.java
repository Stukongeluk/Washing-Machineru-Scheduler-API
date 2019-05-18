package washing.machineru.scheduler.api.user;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.RxHttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.annotation.MicronautTest;

import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@MicronautTest
public class UserControllerTest {

    @Inject
    @Client("/")
    RxHttpClient client;

    @Test
    public void whenCallingUserEndpoint_itShouldReturnAListWithUsers() throws Exception { ;
        HttpRequest request = HttpRequest.GET("/user");
        String body = client.toBlocking().retrieve(request);

        assertNotNull(body);
        assertEquals(body, "[{\"name\":\"friend\",\"email\":\"friend@friendly.com\"}]");
    }

}
