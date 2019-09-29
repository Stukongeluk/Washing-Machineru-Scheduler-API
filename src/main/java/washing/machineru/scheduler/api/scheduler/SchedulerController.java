package washing.machineru.scheduler.api.scheduler;

import io.micronaut.core.version.annotation.Version;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import washing.machineru.scheduler.api.scheduler.pojo.ScheduleItemIdentifier;

import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@Controller("/scheduler")
public class SchedulerController {
    @Inject
    SchedulerService schedulerService;

    @Version("1")
    @Get("/schedules")
    HttpResponse getAllSchedulerItems() {
        return HttpResponse.ok(this.schedulerService.getAllScheduledItems());
    }

    @Version("1")
    @Get("/schedules/{userId}")
    HttpResponse getScheduledItemsForUser(long userId) {
        Optional<List<Schedule>> scheduleList = this.schedulerService.getScheduledItemsForUser(userId);
        if (scheduleList.isPresent()) {
            return HttpResponse.ok(scheduleList.get());
        }
        return HttpResponse.ok("No items found");
    }

    @Version("1")
    @Get("/schedule/{userId}/{scheduleId}")
    HttpResponse getSchedulerItem(long userId, long scheduleId) {
        Optional<Schedule> scheduleItem = this.schedulerService.getSchedulerItem(userId, scheduleId);
        if (scheduleItem.isPresent()) {
            return HttpResponse.ok(scheduleItem.get());
        }
        return HttpResponse.ok("No item found for given userId and schedule id");
    }

    @Version("1")
    @Post("/schedule")
    HttpResponse addScheduleItem(@NotNull @Body Schedule schedule) {
        this.schedulerService.addSchedulerItem(schedule);
        return HttpResponse.ok();
    }

    @Version("1")
    @Put("/schedule")
    HttpResponse updateScheduleItem(@NotNull @Body Schedule schedule) {
        this.schedulerService.updateSchedulerItem(schedule);
        return HttpResponse.ok();
    }

    @Version("1")
    @Delete("/schedule")
    HttpResponse deleteScheduleItem(@NotNull @Body ScheduleItemIdentifier scheduleItemIdentifier) {
        this.schedulerService.deleteSchedulerItem(scheduleItemIdentifier.getUserId(), scheduleItemIdentifier.getId());
        return HttpResponse.ok();
    }
}
