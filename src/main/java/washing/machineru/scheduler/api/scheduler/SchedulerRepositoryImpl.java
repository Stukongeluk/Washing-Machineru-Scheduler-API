package washing.machineru.scheduler.api.scheduler;

import io.micronaut.configuration.hibernate.jpa.scope.CurrentSession;
import io.micronaut.spring.tx.annotation.Transactional;

import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Singleton
public class SchedulerRepositoryImpl implements SchedulerRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public SchedulerRepositoryImpl(@CurrentSession EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public Schedule addScheduleItem(Schedule schedule) {
        entityManager.persist(schedule);
        return schedule;
    }

    @Override
    @Transactional
    public List<Schedule> getAllScheduledItems() {
        String queryString = "SELECT scheduleList from Schedule as scheduleList";
        TypedQuery<Schedule> query = entityManager.createQuery(queryString, Schedule.class);
        return query.getResultList();
    }

    @Override
    @Transactional
    public Optional<List<Schedule>> getScheduledItemsForUser(long userId) {
        List<Schedule> scheduleList = entityManager.createQuery("SELECT scheduleItem from Schedule as scheduleItem " +
                "WHERE userId = :userId", Schedule.class)
                .setParameter("userId", userId)
                .getResultList();
        return scheduleList.size() > 0 ? Optional.of(scheduleList) : Optional.empty();
    }

    @Override
    @Transactional
    public Optional<Schedule> getSchedulerItem(long userId, long scheduleId) {
        List<Schedule> scheduleList = entityManager.createQuery("SELECT scheduleItem from Schedule as scheduleItem " +
                "WHERE userId = :userId AND scheduleId = :scheduleId", Schedule.class)
                .setParameter("userId", userId)
                .setParameter("scheduleId", scheduleId)
                .getResultList();
        return scheduleList.size() > 0 ? Optional.of(scheduleList.get(0)) : Optional.empty();
    }

    @Override
    @Transactional
    public void removeScheduleItemById(long userId, long scheduleId) {
        String queryString = "SELECT schedule from Schedule as schedule " +
                "WHERE userid = :userId AND scheduleId = :scheduleId";
        Schedule schedule = entityManager.createQuery(queryString, Schedule.class)
                .setParameter("userId", userId)
                .setParameter("scheduleId", scheduleId)
                .getSingleResult();
        entityManager.remove(schedule);
    }

    @Override
    public void updateScheduleItemById(Schedule schedule) {
        entityManager.createQuery("UPDATE Schedule schedule " +
                "SET startDateTime = :startDateTime," +
                "endDateTime = :endDateTime " +
                "WHERE userId = :userId AND scheduleId = :scheduleId")
        .setParameter("startDateTime", schedule.getStartDateTime())
        .setParameter("endDateTime", schedule.getEndDateTime())
        .setParameter("userId", schedule.getUserId())
        .setParameter("scheduleId", schedule.getId())
        .executeUpdate();
    }
}
