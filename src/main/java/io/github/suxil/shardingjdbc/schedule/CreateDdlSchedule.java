package io.github.suxil.shardingjdbc.schedule;

import io.github.suxil.shardingjdbc.service.TableDdlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CreateDdlSchedule {

    @Autowired
    private TableDdlService tableDdlService;

    @Scheduled(cron = "0 */1 * * * ?")
    public void createAisMessageTableAndIndex() {
        System.out.println("create ais message table and index");

        tableDdlService.createAisMessageTableDdl();
    }

}
