package washing.machineru.scheduler.api.scheduler;

import java.util.List;
import java.util.Optional;

public interface SchedulerRepository {
    List<Schedule> getAllScheduledItems();

    Optional<List<Schedule>> getScheduledItemsForUser(long userId);

    Optional<Schedule> getSchedulerItem(long userId, long scheduleId);

    Schedule addScheduleItem(Schedule schedule);

    void removeScheduleItemById(long userId, long id);

    void updateScheduleItemById(Schedule schedule);
}
