package washing.machineru.scheduler.api.scheduler;

import io.micronaut.test.annotation.MicronautTest;
import io.micronaut.test.annotation.MockBean;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@MicronautTest
class SchedulerServiceTest {
    @Inject
    SchedulerRepository schedulerRepository;

    @Inject
    SchedulerService schedulerService;

    @MockBean(SchedulerRepositoryImpl.class)
    SchedulerRepository schedulerRepository() {
        return mock(SchedulerRepository.class);
    }

    @Test
    void givenNoScheduledItemsAreAvailable_whenCallingTheGetAllScheduledItems_itShouldReturnAnEmptyList() {
        final List<Schedule> scheduleList = new ArrayList<>();

        when(schedulerRepository.getAllScheduledItems()).thenReturn(scheduleList);
        final List<Schedule> result = schedulerService.getAllScheduledItems();

        assertEquals(0, result.size());
    }

    @Test
    void givenAListOf3ScheduleItems_whenCallingGetAllScheduledItems_itShouldReturnAListOf3ScheduledItems() {
        final List<Schedule> scheduleList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Schedule schedule = new Schedule();
            scheduleList.add(schedule);
        }

        when(schedulerRepository.getAllScheduledItems()).thenReturn(scheduleList);
        final List<Schedule> result = schedulerService.getAllScheduledItems();

        assertEquals(3, result.size());
    }

}
