package washing.machineru.scheduler.api.scheduler;

import io.micronaut.test.annotation.MicronautTest;
import io.micronaut.test.annotation.MockBean;
import io.reactivex.Scheduler;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.inject.Inject;
import javax.swing.text.html.Option;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

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
        verify(schedulerRepository).getAllScheduledItems();
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
        verify(schedulerRepository).getAllScheduledItems();
    }

    @Test
    void givenAValidUserIdWithoutSchedules_whenCallingGetScheduledItemsForUserWithId0_returnAnEmptyOptional() {
        final long userId = 0;

        when(schedulerRepository.getScheduledItemsForUser(userId)).thenReturn(Optional.empty());
        final Optional<List<Schedule>> result = schedulerService.getScheduledItemsForUser(userId);

        assertEquals(Optional.empty(), result);
        verify(schedulerRepository).getScheduledItemsForUser(userId);
    }

    @Test
    void givenAValidUserIdWithSingleSchedule_whenCallingGetScheduledItemsForUserWithId0_returnAnOptionalWithList() {
        final long userId = 0;
        final List<Schedule> scheduleList = new ArrayList<>();
        scheduleList.add(new Schedule());
        final Optional<List<Schedule>> optionalScheduleList = Optional.of(scheduleList);

        when(schedulerRepository.getScheduledItemsForUser(userId)).thenReturn(optionalScheduleList);
        final Optional<List<Schedule>> result = schedulerService.getScheduledItemsForUser(userId);

        assertEquals(optionalScheduleList, result);
        verify(schedulerRepository).getScheduledItemsForUser(userId);
    }

    @Test
    void givenAnInvalidScheduleAndUserId_whenCallingGetSchedulerItem_returnAnEmptyOptional() {
        final long userId = 0;
        final long scheduleId = 0;

        when(schedulerRepository.getSchedulerItem(userId, scheduleId)).thenReturn(Optional.empty());
        final Optional<Schedule> result = schedulerService.getSchedulerItem(userId, scheduleId);

        assertEquals(Optional.empty(), result);
        verify(schedulerRepository).getSchedulerItem(userId, scheduleId);
    }

    @Test
    void givenAValidScheduleAndUserId_whenCallingGetSchedulerItem_returnAScheduleOptional() {
        final long userId = 0;
        final long scheduleId = 0;
        final Optional<Schedule> scheduleOptional = Optional.of(new Schedule());

        when(schedulerRepository.getSchedulerItem(userId, scheduleId)).thenReturn(scheduleOptional);
        final Optional<Schedule> result = schedulerService.getSchedulerItem(userId, scheduleId);

        assertEquals(scheduleOptional, result);
        verify(schedulerRepository).getSchedulerItem(userId, scheduleId);
    }

    @Test
    void givenAValidScheduleObject_whenCallingAddScheduleItem_verifySchedulerRepoCalled() {
        when(schedulerRepository.addScheduleItem(Mockito.any())).thenReturn(new Schedule());
        schedulerService.addSchedulerItem(Mockito.any());

        verify(schedulerRepository).addScheduleItem(Mockito.any());
    }

    @Test
    void givenAValidSchedule_whenCallingUpdateScheduleItem_verifySchedulerRepoCalled() {
        doNothing().when(schedulerRepository).updateScheduleItemById(Mockito.any());
        schedulerService.updateSchedulerItem(Mockito.any());

        verify(schedulerRepository).updateScheduleItemById(Mockito.any());
    }

    @Test
    void givenAValidUserIdandScheduleId_whenCallingDeleteScheduleItem_verifySchedulerRepoCalled() {
        doNothing().when(schedulerRepository).removeScheduleItemById(Mockito.anyLong(), Mockito.anyLong());
        schedulerService.deleteSchedulerItem(Mockito.anyLong(), Mockito.anyLong());

        verify(schedulerRepository).removeScheduleItemById(Mockito.anyLong(), Mockito.anyLong());
    }
}
