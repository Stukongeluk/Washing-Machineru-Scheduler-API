package washing.machineru.scheduler.api.scheduler;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;
import java.util.Optional;

@Singleton
public class SchedulerServiceImpl implements SchedulerService {
    private SchedulerRepository schedulerRepository;

    @Inject
    public SchedulerServiceImpl(SchedulerRepository schedulerRepository) {
        this.schedulerRepository = schedulerRepository;
    }

    @Override
    public Optional<List<Schedule>> getScheduledItemsForUser(long userId) {
        return this.schedulerRepository.getScheduledItemsForUser(userId);
    }

    @Override
    public List<Schedule> getAllScheduledItems() {
        return this.schedulerRepository.getAllScheduledItems();
    }

    @Override
    public Optional<Schedule> getSchedulerItem(long userId, long scheduleId) {
        return this.schedulerRepository.getSchedulerItem(userId, scheduleId);
    }

    @Override
    public void addSchedulerItem(Schedule schedule) {
        this.schedulerRepository.addScheduleItem(schedule);
    }

    @Override
    public void updateSchedulerItem(Schedule schedule) {
        this.schedulerRepository.updateScheduleItemById(schedule);
    }

    @Override
    public void deleteSchedulerItem(long userId, long scheduleId) {
        this.schedulerRepository.removeScheduleItemById(userId, scheduleId);
    }
}
