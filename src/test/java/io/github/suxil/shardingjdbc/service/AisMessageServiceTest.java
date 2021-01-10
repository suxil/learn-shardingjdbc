package io.github.suxil.shardingjdbc.service;

import io.github.suxil.shardingjdbc.ShardingJdbcApplication;
import io.github.suxil.shardingjdbc.domain.AisMessage;
import io.github.suxil.shardingjdbc.mapper.AisMessageRowMapper;
import io.github.suxil.shardingjdbc.repository.AisMessageRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ShardingJdbcApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AisMessageServiceTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private AisMessageRepository aisMessageRepository;

    /**
     * 测试查询单船轨迹
     */
    @Test
    public void searchMmsiTest() {
        long start = System.currentTimeMillis();

        Date date = new Date(1610264424231L);

        int mmsi = 0;

        // 查询表 ais_message_202101_0
        String sql = "select id, mmsi, utc, lat, lon, ais_type from ais_message where mmsi = ? and utc = ?";

        List<AisMessage> list = jdbcTemplate.query(sql, new AisMessageRowMapper(), mmsi, date.getTime());

        for (AisMessage aisMessage : list) {
            Assert.assertEquals(mmsi, aisMessage.getMmsi());
        }



        int mmsi1 = 1;
        // 查询表 ais_message_202101_1
        String sql1 = "select id, mmsi, utc, lat, lon, ais_type from ais_message where mmsi = ? and utc = ?";

        List<AisMessage> list1 = jdbcTemplate.query(sql1, new AisMessageRowMapper(), mmsi1, date.getTime());

        for (AisMessage aisMessage : list1) {
            Assert.assertEquals(mmsi1, aisMessage.getMmsi());
        }

        long end = System.currentTimeMillis();

        log.info("search mmsi utc equal search millis: {}", end - start);
    }

    /**
     * 测试查询utc区间
     */
    @Test
    public void searchMmsiAllTest() {
        long start = System.currentTimeMillis();

        Calendar calendar = Calendar.getInstance();
        calendar.set(2020, 11, 3);

        Date date0 = calendar.getTime();

        Date date = new Date();

        // 查询表 ais_message_202101_${0..10}
        String sql = "select id, mmsi, utc, lat, lon, ais_type from ais_message where utc > ? and utc < ?";

        List<AisMessage> list = jdbcTemplate.query(sql, new AisMessageRowMapper(), date0.getTime(), date.getTime());

        for (AisMessage aisMessage : list) {
            Date utcDate = new Date(aisMessage.getUtc());
            Assert.assertTrue(date0.before(utcDate));
            Assert.assertTrue(date.after(utcDate));
        }

        long end = System.currentTimeMillis();

        log.info("search range utc millis: {}", end - start);
    }

    /**
     * 测试单船日期区间查询
     */
    @Test
    public void searchMmsiRangeUtcTest() {
        long start = System.currentTimeMillis();

        Calendar calendar = Calendar.getInstance();
        calendar.set(2020, 11, 3);

        Date date0 = calendar.getTime();

        Date date = new Date();

        int mmsi = 0;

        // 查询表 ais_message_202101_0
        String sql = "select id, mmsi, utc, lat, lon, ais_type from ais_message where mmsi = ? and utc > ? and utc < ?";

        List<AisMessage> list = jdbcTemplate.query(sql, new AisMessageRowMapper(), mmsi, date0.getTime(), date.getTime());

        for (AisMessage aisMessage : list) {
            Assert.assertEquals(mmsi, aisMessage.getMmsi());

            Date utcDate = new Date(aisMessage.getUtc());
            Assert.assertTrue(date0.before(utcDate));
            Assert.assertTrue(date.after(utcDate));
        }

        long end = System.currentTimeMillis();

        log.info("search mmsi range utc millis: {}", end - start);
    }

    /**
     * 测试批量保存 jpa
     */
    @Test
    public void batchSaveJpaTest() {
        long start = System.currentTimeMillis();

        List<AisMessage> aisMessageList = new ArrayList<>();

        Random random = new Random();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYYMM");

        for (int i = 0; i < 10000; i++) {
            Date date = new Date();

            AisMessage aisMessage = new AisMessage();
            aisMessage.setMmsi(random.nextInt(2));
            aisMessage.setUtc(date.getTime());
            aisMessage.setAisType(String.format("%s_%s", simpleDateFormat.format(date), aisMessage.getMmsi()));

            aisMessageList.add(aisMessage);
        }

        aisMessageRepository.saveAll(aisMessageList);

        long end = System.currentTimeMillis();

        log.info("batch save jpa time millis: {}", end - start);
    }

    /**
     * 测试批量保存 jdbc
     */
    @Test
    public void batchSaveJdbcTest() {
        long start = System.currentTimeMillis();

        List<AisMessage> aisMessageList = new ArrayList<>();

        Random random = new Random();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYYMM");

        for (int i = 0; i < 10000; i++) {
            Date date = new Date();

            AisMessage aisMessage = new AisMessage();
            aisMessage.setMmsi(random.nextInt(2));
            aisMessage.setUtc(date.getTime());
            aisMessage.setAisType(String.format("%s_%s", simpleDateFormat.format(date), aisMessage.getMmsi()));

            aisMessageList.add(aisMessage);
        }

        aisMessageRepository.saveAll(aisMessageList);

        long end = System.currentTimeMillis();

        log.info("batch save jdbc time millis: {}", end - start);
    }

    /**
     * 测试保存一张不存在的表是否报错；注：会报表不存在
     */
    @Test
    public void saveNotExistTableTest() {
        long start = System.currentTimeMillis();

        Calendar calendar = Calendar.getInstance();
        calendar.set(2020, 5, 3);

        Date date = calendar.getTime();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYYMM");

        AisMessage aisMessage = new AisMessage();
        aisMessage.setMmsi(3);
        aisMessage.setUtc(date.getTime());
        aisMessage.setAisType(String.format("%s_%s", simpleDateFormat.format(date), aisMessage.getMmsi()));

        aisMessageRepository.save(aisMessage);

        long end = System.currentTimeMillis();

        log.info("batch save jpa time millis: {}", end - start);
    }

}
