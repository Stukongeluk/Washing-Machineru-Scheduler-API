package washing.machineru.scheduler.api.scheduler;

import io.micronaut.test.annotation.MicronautTest;
import io.micronaut.test.annotation.MockBean;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static org.mockito.Mockito.mock;

@MicronautTest
public class SchedulerServiceTest {
    @Inject
    SchedulerRepository schedulerRepository;

    @MockBean(SchedulerRepositoryImpl.class)
    SchedulerRepository schedulerRepository() {
        return mock(SchedulerRepository.class);
    }
}
