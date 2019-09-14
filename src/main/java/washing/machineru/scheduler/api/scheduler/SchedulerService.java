package washing.machineru.scheduler.api.scheduler;


import java.util.List;

public interface SchedulerService {

    void submitSchedulerItem();

    void updateSchedulerItem();

    void deleteSchedulerItem();

    Schedule getSchedulerItem();

    List<Schedule> getSchedulerItems();
}
