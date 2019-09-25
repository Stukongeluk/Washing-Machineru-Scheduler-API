package washing.machineru.scheduler.api.scheduler;


import java.util.List;
import java.util.Optional;

public interface SchedulerService {
    Optional<List<Schedule>> getScheduledItemsForUser(long userId);

    List<Schedule> getAllScheduledItems();

    Optional<Schedule> getSchedulerItem(long userId, long scheduleId);

    void addSchedulerItem(Schedule schedule);

    void updateSchedulerItem(Schedule schedule);

    void deleteSchedulerItem(long userId, long scheduleId);




}
