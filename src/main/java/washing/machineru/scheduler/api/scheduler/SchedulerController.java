package washing.machineru.scheduler.api.scheduler;

import io.micronaut.context.annotation.Parameter;
import io.micronaut.core.version.annotation.Version;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;

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
    HttpResponse getSchedulerItemsForUser(long userId) {
        Optional<List<Schedule>> scheduleList = this.schedulerService.getScheduledItemsForUser(userId);
        scheduleList.ifPresent(HttpResponse::ok);
        return HttpResponse.notFound();
    }

    @Version("1")
    @Get("/schedule/{userId}/{id}")
    HttpResponse getSchedulerItem(long userId, long id) {
        Optional<Schedule> scheduleItem = this.schedulerService.getSchedulerItem(userId, id);
        scheduleItem.ifPresent(HttpResponse::ok);
        return HttpResponse.notFound();
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
    HttpResponse deleteScheduleItem(@NotNull @Body long userId, long id) {
        this.schedulerService.deleteSchedulerItem(userId, id);
        return HttpResponse.ok();
    }
}
