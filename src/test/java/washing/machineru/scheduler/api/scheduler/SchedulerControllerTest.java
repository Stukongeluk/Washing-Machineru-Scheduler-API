package washing.machineru.scheduler.api.scheduler;

import com.fasterxml.classmate.GenericType;
import io.micronaut.core.type.Argument;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MutableHttpRequest;
import io.micronaut.http.client.RxHttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.test.annotation.MicronautTest;
import io.micronaut.test.annotation.MockBean;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import washing.machineru.scheduler.api.Application;
import washing.machineru.scheduler.api.scheduler.pojo.ScheduleItemIdentifier;

import javax.inject.Inject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@MicronautTest(application= Application.class)
class SchedulerControllerTest {
    @Inject
    SchedulerService schedulerService;

    @Inject
    @Client("/scheduler")
    RxHttpClient client;

    @MockBean(SchedulerServiceImpl.class)
    SchedulerService schedulerService() {
        return mock(SchedulerService.class);
    }

    @Test
    void givenSchedulesTableIsEmpty_whenCallingGetAllScheduledItems_returnAnEmptyList() {
        final List<Schedule> scheduleList = new ArrayList<>();

        when(schedulerService.getAllScheduledItems()).thenReturn(scheduleList);
        final List result = client.toBlocking().retrieve(HttpRequest.GET("/schedules"), List.class);

        assertEquals(result.size(), 0);
        verify(schedulerService).getAllScheduledItems();
    }

    @Test
    void givenSchedulesTableHas5Items_whenCallingGetAllScheduledItems_returnListWith5Schedules() {
        final List<Schedule> scheduleList = new ArrayList<>();
        final int expectedSize = 5;
        for(int i = 0; i < expectedSize; i++) {
            scheduleList.add(new Schedule());
        }

        when(schedulerService.getAllScheduledItems()).thenReturn(scheduleList);
        final List<Schedule> result = client.toBlocking().retrieve(HttpRequest.GET("/schedules"), Argument.listOf(Schedule.class));

        assertEquals(expectedSize, result.size());
        verify(schedulerService).getAllScheduledItems();
    }

    @Test
    void givenAnEmptyScheduledListForUser_whenCallingGetScheduledItemsForUserWithId0_returnAHttpOkWithoutContent() {
        final int userId = 0;

        when(schedulerService.getScheduledItemsForUser(userId)).thenReturn(Optional.empty());
        final HttpResponse result = client.toBlocking().exchange(HttpRequest.GET("/schedules/" +  userId));

        assertEquals(HttpResponse.ok().getStatus(), result.getStatus());
        verify(schedulerService).getScheduledItemsForUser(userId);
    }

    @Test
    void givenAnEmptyScheduledListForUser_whenCallingGetScheduledItemsForUserWithId0_returnAStringMessage() {
        final long userId = 0;
        final String expected = "No items found";

        when(schedulerService.getScheduledItemsForUser(userId)).thenReturn(Optional.empty());
        final String result = client.toBlocking().retrieve(HttpRequest.GET("/schedules/" +  userId), String.class);

        assertEquals(expected, result);
        verify(schedulerService).getScheduledItemsForUser(userId);
    }

    @Test
    void givenAScheduleListWithSingleSchedule_whenCallingGetScheduledItemsForUserWithId1_returnTheScheduleObject() {
        final long userId = 1;
        final List<Schedule> schedules = new ArrayList<>();
        Schedule schedule = new Schedule();
        schedule.setUserId(userId);
        schedule.setId(1L);
        schedule.setEndDateTime(new Date());
        schedule.setStartDateTime(new Date());
        schedules.add(schedule);

        when(schedulerService.getScheduledItemsForUser(userId)).thenReturn(Optional.of(schedules));
        final List<Schedule> resultList = client.toBlocking().retrieve(HttpRequest.GET("/schedules/" +  userId),
                Argument.listOf(Schedule.class));
        final Schedule result = resultList.get(0);

        assertEquals(schedule.getUserId(), result.getUserId());
        verify(schedulerService).getScheduledItemsForUser(userId);
    }

    @Test
    void givenNonExistingUserIdandScheduleId_whenCallingGetScheduledItem_returnAHttpOk() {
        final long userId = 1;
        final long scheduleId = 1;

        when(schedulerService.getSchedulerItem(userId, scheduleId)).thenReturn(Optional.empty());
        final MutableHttpRequest<Object> getSchedulerItemRequest = HttpRequest.GET(String.format("/schedule/%s/%s", userId, scheduleId));
        final HttpResponse result = client.toBlocking().exchange(getSchedulerItemRequest);

        assertEquals(HttpResponse.ok().getStatus(), result.getStatus());
        verify(schedulerService).getSchedulerItem(userId, scheduleId);
    }

    @Test
    void givenNonExistingUserIdandScheduleId_whenCallingGetScheduledItem_returnAStringMessage() {
        final long userId = 1;
        final long scheduleId = 1;
        final String expected = "No item found for given userId and schedule id";

        when(schedulerService.getSchedulerItem(userId, scheduleId)).thenReturn(Optional.empty());
        final MutableHttpRequest<Object> getSchedulerItemRequest = HttpRequest.GET(String.format("/schedule/%s/%s", userId, scheduleId));
        final String result = client.toBlocking().retrieve(getSchedulerItemRequest, String.class);

        assertEquals(expected, result);
        verify(schedulerService).getSchedulerItem(userId, scheduleId);
    }

    @Test
    void givenAnExistingScheduleItem_whenCallingGetScheduledItem_returnTheScheduleObject() {
        final long userId = 1;
        final long scheduleId = 1;
        Schedule expectedScheduleObject = new Schedule();
        expectedScheduleObject.setId(scheduleId);
        expectedScheduleObject.setUserId(userId);

        when(schedulerService.getSchedulerItem(userId, scheduleId)).thenReturn(Optional.of(expectedScheduleObject));
        final MutableHttpRequest<Object> getSchedulerItemRequest = HttpRequest.GET(String.format("/schedule/%s/%s", userId, scheduleId));
        final Schedule result = client.toBlocking().retrieve(getSchedulerItemRequest, Schedule.class);

        assertEquals(expectedScheduleObject.getUserId(), result.getUserId());
        assertEquals(expectedScheduleObject.getId(), result.getId());
        verify(schedulerService).getSchedulerItem(userId, scheduleId);
    }

    @Test
    void givenAScheduleItem_whenCallingAddScheduleItem_returnAHttpOk() {
        Schedule schedule = new Schedule();

        doNothing().when(schedulerService).addSchedulerItem(Mockito.any(Schedule.class));
        final HttpResponse result = client.toBlocking().exchange(HttpRequest.POST("/schedule", schedule));

        assertEquals(HttpResponse.ok().getStatus(), result.getStatus());
        verify(schedulerService).addSchedulerItem(Mockito.any(Schedule.class));
    }

    @Test
    void givenAScheduleItem_whenCallingUpdateScheduleItem_returnAHttpOk() {
        Schedule schedule = new Schedule();

        doNothing().when(schedulerService).updateSchedulerItem(Mockito.any(Schedule.class));
        final HttpResponse result = client.toBlocking().exchange(HttpRequest.PUT("/schedule", schedule));

        assertEquals(HttpResponse.ok().getStatus(), result.getStatus());
        verify(schedulerService).updateSchedulerItem(Mockito.any(Schedule.class));
    }

    @Test
    void givenAScheduleItemIdentiefier_whenCallingDeleteScheduleItem_returnAHttpOk() {
        ScheduleItemIdentifier scheduleItemIdentifier = new ScheduleItemIdentifier();
        scheduleItemIdentifier.setId(1L);
        scheduleItemIdentifier.setUserId(1L);

        doNothing().when(schedulerService).deleteSchedulerItem(Mockito.anyLong(), Mockito.anyLong());
        final HttpResponse result = client.toBlocking().exchange(HttpRequest.DELETE("/schedule", scheduleItemIdentifier));

        assertEquals(HttpResponse.ok().getStatus(), result.getStatus());
        verify(schedulerService).deleteSchedulerItem(Mockito.anyLong(), Mockito.anyLong());
    }
}
