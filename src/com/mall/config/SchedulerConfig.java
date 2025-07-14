package com.mall.config;

import com.mall.scheduler.InventoryAlertScheduler;
import com.mall.scheduler.InventoryExpiryChecker;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

public class SchedulerConfig {
    public static void startScheduler() {
        try {
            // 创建 scheduler
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

            // 定义 job detail
            JobDetail jobDetail = JobBuilder.newJob(InventoryAlertScheduler.class)
                    .withIdentity("inventoryAlertJob", "inventoryGroup")
                    .build();

            // 定义 trigger，每分钟执行一次
            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity("inventoryAlertTrigger", "inventoryGroup")
                    .withSchedule(CronScheduleBuilder.cronSchedule("0 * * * * ?")) // 每分钟执行
                    .build();

            // 注册 job 和 trigger
            scheduler.scheduleJob(jobDetail, trigger);

            // 启动 scheduler
            scheduler.start();
            System.out.println("库存预警定时任务已启动，每分钟执行一次");
        } catch (SchedulerException e) {
            e.printStackTrace();
        }




        try {
            // 创建 scheduler
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

            // 定义 job detail
            JobDetail jobDetail = JobBuilder.newJob(InventoryExpiryChecker.class)
                    .withIdentity("inventoryExpiryJob", "inventoryGroup")
                    .build();

            // 定义 trigger，每天凌晨1点执行
            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity("inventoryExpiryTrigger", "inventoryGroup")
                    .withSchedule(CronScheduleBuilder.cronSchedule("0 0 1 * * ?"))
                    .build();

            // 注册 job 和 trigger
            scheduler.scheduleJob(jobDetail, trigger);

            // 启动 scheduler
            scheduler.start();
            System.out.println("库存过期检查定时任务已启动");
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}