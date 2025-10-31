package ru.yandex.practicum.gym;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

public class TimetableTest {


    @Test
    void testAddNewTrainingSessionForMONDAY() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        TrainingSession singleTrainingSession = new TrainingSession(group, coach, DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        timetable.addNewTrainingSession(singleTrainingSession);

        Assertions.assertTrue(timetable.getTimetable().containsKey(DayOfWeek.MONDAY));

    }

    @Test
    void testAddNewTrainingSessionForWeak() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        TrainingSession singleTrainingSession = new TrainingSession(group, coach, DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession singleTrainingSession2 = new TrainingSession(group, coach, DayOfWeek.TUESDAY, new TimeOfDay(13, 0));
        TrainingSession singleTrainingSession3 = new TrainingSession(group, coach, DayOfWeek.WEDNESDAY, new TimeOfDay(13, 0));
        TrainingSession singleTrainingSession4 = new TrainingSession(group, coach, DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        TrainingSession singleTrainingSession5 = new TrainingSession(group, coach, DayOfWeek.FRIDAY, new TimeOfDay(13, 0));
        TrainingSession singleTrainingSession6 = new TrainingSession(group, coach, DayOfWeek.SATURDAY, new TimeOfDay(13, 0));
        TrainingSession singleTrainingSession7 = new TrainingSession(group, coach, DayOfWeek.SUNDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);
        timetable.addNewTrainingSession(singleTrainingSession2);
        timetable.addNewTrainingSession(singleTrainingSession3);
        timetable.addNewTrainingSession(singleTrainingSession4);
        timetable.addNewTrainingSession(singleTrainingSession5);
        timetable.addNewTrainingSession(singleTrainingSession6);
        timetable.addNewTrainingSession(singleTrainingSession7);

        Assertions.assertEquals(7, timetable.getTimetable().size());

    }

    @Test
    void testAddNewTrainingSessionIfNoTrainingSession() {
        Timetable timetable = new Timetable();

        Assertions.assertEquals(0, timetable.getTimetable().size());
    }

    @Test
    void testGetTrainingSessionsForDaySingleSession() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach, DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        Assertions.assertEquals(1, timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY).size());
        Assertions.assertEquals(0, timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY).size());
    }

    @Test
    void testGetTrainingSessionsForDayMultipleSessions() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coach, DayOfWeek.THURSDAY, new TimeOfDay(20, 0));

        timetable.addNewTrainingSession(thursdayAdultTrainingSession);

        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, coach, DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupChild, coach, DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        TrainingSession saturdayChildTrainingSession = new TrainingSession(groupChild, coach, DayOfWeek.SATURDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(mondayChildTrainingSession);
        timetable.addNewTrainingSession(thursdayChildTrainingSession);
        timetable.addNewTrainingSession(saturdayChildTrainingSession);

        // Проверить, что за понедельник вернулось одно занятие
        // Проверить, что за четверг вернулось два занятия в правильном порядке: сначала в 13:00, потом в 20:00
        // Проверить, что за вторник не вернулось занятий
        Assertions.assertEquals(1, timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY).size());
        Assertions.assertEquals(0, timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY).size());
        Assertions.assertEquals(thursdayChildTrainingSession.getTimeOfDay(), timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY).getFirst().getFirst().getTimeOfDay());
        Assertions.assertEquals(thursdayAdultTrainingSession.getTimeOfDay(), timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY).getLast().getFirst().getTimeOfDay());
    }

    @Test
    void testGetTrainingSessionsForDayAndTime() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach, DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        //Проверить, что за понедельник в 13:00 вернулось одно занятие
        //Проверить, что за понедельник в 14:00 не вернулось занятий
        Assertions.assertEquals(1, timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, singleTrainingSession.getTimeOfDay()).size());
        Assertions.assertNull(timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY, new TimeOfDay(14, 0)));
    }

    @Test
    void testGetCountByCoaches() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        Coach coach2 = new Coach("Сергеев", "Николай", "Васильевич");

        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coach, DayOfWeek.THURSDAY, new TimeOfDay(10, 0));
        TrainingSession thursdayAdultTrainingSession2 = new TrainingSession(groupAdult, coach, DayOfWeek.THURSDAY, new TimeOfDay(12, 0));
        TrainingSession thursdayAdultTrainingSession3 = new TrainingSession(groupAdult, coach, DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        TrainingSession thursdayAdultTrainingSession4 = new TrainingSession(groupAdult, coach2, DayOfWeek.THURSDAY, new TimeOfDay(14, 0));

        timetable.addNewTrainingSession(thursdayAdultTrainingSession);
        timetable.addNewTrainingSession(thursdayAdultTrainingSession2);
        timetable.addNewTrainingSession(thursdayAdultTrainingSession3);
        timetable.addNewTrainingSession(thursdayAdultTrainingSession4);

        Assertions.assertEquals(2, timetable.getCountByCoaches().size());
        Assertions.assertEquals(3, timetable.getCountByCoaches().get(coach));
        Assertions.assertEquals(1, timetable.getCountByCoaches().get(coach2));

    }

    @Test
    void testGetCountByCoachesIfNoTrainingSession() {
        Timetable timetable = new Timetable();

        Assertions.assertTrue(timetable.getCountByCoaches().isEmpty());
    }

    @Test
    void testGetCountByCoachesSorted() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        Coach coach2 = new Coach("Сергеев", "Николай", "Васильевич");

        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);

        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coach, DayOfWeek.THURSDAY, new TimeOfDay(10, 0));
        TrainingSession thursdayAdultTrainingSession2 = new TrainingSession(groupAdult, coach, DayOfWeek.THURSDAY, new TimeOfDay(12, 0));
        TrainingSession thursdayAdultTrainingSession3 = new TrainingSession(groupAdult, coach, DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        TrainingSession thursdayAdultTrainingSession4 = new TrainingSession(groupAdult, coach2, DayOfWeek.THURSDAY, new TimeOfDay(14, 0));

        timetable.addNewTrainingSession(thursdayAdultTrainingSession);
        timetable.addNewTrainingSession(thursdayAdultTrainingSession2);
        timetable.addNewTrainingSession(thursdayAdultTrainingSession3);
        timetable.addNewTrainingSession(thursdayAdultTrainingSession4);

        int i = 0;
        for (Map.Entry<Coach, Integer> map : timetable.getCountByCoaches().entrySet()) {
            if (i == 0) {
                Assertions.assertEquals(coach, map.getKey());
                Assertions.assertEquals(3, map.getValue());
                i++;
            } else if (i == 1) {
                Assertions.assertEquals(coach2, map.getKey());
                Assertions.assertEquals(1, map.getValue());
            }


        }
    }
}
