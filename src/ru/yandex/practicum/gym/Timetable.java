package ru.yandex.practicum.gym;

import java.util.*;

public class Timetable {

    private HashMap<DayOfWeek, TreeMap<TimeOfDay, List<TrainingSession>>> timetable = new HashMap<>();

    public HashMap<DayOfWeek, TreeMap<TimeOfDay, List<TrainingSession>>> getTimetable() {
        return timetable;
    }


    public void addNewTrainingSession(TrainingSession trainingSession) {
        List<TrainingSession> trainingSessionList = new ArrayList<>();
        trainingSessionList.add(trainingSession);

        if (timetable.containsKey(trainingSession.getDayOfWeek())) {
            if (timetable.get(trainingSession.getDayOfWeek()).containsKey(trainingSession.getTimeOfDay())) {
                timetable.get(trainingSession.getDayOfWeek()).get(trainingSession.getTimeOfDay()).add(trainingSession);
            } else {
                timetable.get(trainingSession.getDayOfWeek()).put(trainingSession.getTimeOfDay(), trainingSessionList);
            }
        } else {
            Map<TimeOfDay, List<TrainingSession>> trainingSessionMap = new TreeMap<>(Comparator.comparing(timeOfDay
                    -> timeOfDay));
            trainingSessionMap.put(trainingSession.getTimeOfDay(), trainingSessionList);
            timetable.put(trainingSession.getDayOfWeek(), (TreeMap<TimeOfDay, List<TrainingSession>>) trainingSessionMap);
        }
    }

    public List<List<TrainingSession>> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        //как реализовать, тоже непонятно, но сложность должна быть О(1)
        List<List<TrainingSession>> sessions = new ArrayList<>();
        if (timetable.containsKey(dayOfWeek)) {
            sessions.addAll(timetable.get(dayOfWeek).values());
        }
        return sessions;
    }

    public List<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        //как реализовать, тоже непонятно, но сложность должна быть О(1)
        if (timetable.containsKey(dayOfWeek) && timetable.get(dayOfWeek).containsKey(timeOfDay)) {
            return timetable.get(dayOfWeek).get(timeOfDay);
        } else {
            return new ArrayList<>();
        }
    }

    public LinkedHashMap<Coach, Integer> getCountByCoaches() {
        HashMap<Coach, Integer> countByCoaches = new HashMap<>();

        for (TreeMap<TimeOfDay, List<TrainingSession>> map : timetable.values()) {
            for (List<TrainingSession> listSessions : map.values()) {
                for (TrainingSession trainingSession : listSessions) {
                    if (countByCoaches.containsKey(trainingSession.getCoach())) {
                        int count = 0;
                        count = countByCoaches.get(trainingSession.getCoach());
                        countByCoaches.put(trainingSession.getCoach(), count + 1);

                    } else {
                        countByCoaches.put(trainingSession.getCoach(), 1);
                    }
                }
            }
        }

        List<Map.Entry<Coach, Integer>> list = new ArrayList<>(countByCoaches.entrySet());
        list.sort(Map.Entry.<Coach, Integer>comparingByValue().reversed());
        LinkedHashMap<Coach, Integer> countByCoachesSorted = new LinkedHashMap<>();
        for (Map.Entry<Coach, Integer> entry : list) {
            countByCoachesSorted.put(entry.getKey(), entry.getValue());
        }
        return countByCoachesSorted;
    }
}
