package io.github.suxil.shardingjdbc.service;

import io.github.suxil.shardingjdbc.ShardingJdbcApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ShardingJdbcApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TableDdlServiceTest {

    @Autowired
    private TableDdlService tableDdlService;

    @Test
    public void createAisMessageTableDdlTest() {
        tableDdlService.createAisMessageTableDdl();
    }

    @Test
    public void dropAisMessageTableDdlTest() {
        tableDdlService.dropAisMessageTableDdl();
    }

}
