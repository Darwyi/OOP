package org.Darwyi.courseProject;

import org.Darwyi.courseProject.observer.EventManager;
import org.Darwyi.courseProject.observer.NotificationService;
import org.Darwyi.courseProject.observer.StatisticsCollector;
import org.Darwyi.courseProject.service.LearningService;
import org.Darwyi.courseProject.storage.DataStore;
import org.Darwyi.courseProject.ui.ConsoleUI;

public class Main {
    public static void main(String[] args){
        if (args.length > 0 && args[0].equals("--test")){
            SelfTest.run();
            return;
        }

        DataStore store = DataStore.getInstance();
        EventManager events = new EventManager();

        StatisticsCollector stats = new StatisticsCollector();
        NotificationService notifications = new NotificationService(store);
        events.subscribe(stats);
        events.subscribe(notifications);

        LearningService service = new LearningService(store, events);

        if (args.length > 0 && args[0].equals("--demo")){
            Demo.run(service, stats, notifications);
            return;
        }
        new ConsoleUI(service, stats, notifications).run();
    }
}
