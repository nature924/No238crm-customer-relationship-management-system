package com.wuhunyu.task;

import com.wuhunyu.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 客户流失数据转移任务
 *
 * @author wuhunyu
 * @version 1.0
 * @date 2020-12-17 21:45
 */
@Component
public class DataBaseTask {

    @Autowired
    private CustomerService customerService;

    private Logger logger = LoggerFactory.getLogger(DataBaseTask.class);

    /**
     * 每个月的1号凌晨2点执行
     */
    @Scheduled(cron = "0 0 0 1 * ?")
    public void updateLoss() {
        logger.info("当前时间为：{}.开始执行客户流失数据转移",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        // 执行操作
        customerService.updateCustomerList();
    }

}
