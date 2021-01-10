package io.github.suxil.shardingjdbc.service;

import io.github.suxil.shardingjdbc.ShardingJdbcApplication;
import io.github.suxil.shardingjdbc.schedule.CreateDdlSchedule;
import io.github.suxil.shardingjdbc.util.ShardingAlgorithmUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.*;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ShardingJdbcApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CreateDdlServiceTest {

    @Autowired
    private CreateDdlService createDdlService;

    @Test
    public void createTableDdlTest() {
        createDdlService.createAisMessageTableDdl();
    }

}
