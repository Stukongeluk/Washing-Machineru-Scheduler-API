package washing.machineru.scheduler.api.scheduler;

import io.micronaut.http.annotation.Controller;

import javax.inject.Inject;

@Controller
public class SchedulerController {
    @Inject
    SchedulerService schedulerService;

}
