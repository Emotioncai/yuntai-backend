package com.atguigu.scheduler.bean;

import org.quartz.Scheduler;
import org.quartz.impl.StdSchedulerFactory;

public class QuartzScheduler {
    private static Scheduler scheduler = null;

    public static Scheduler getInstance() throws Exception {
        if (scheduler == null) {
            StdSchedulerFactory factory = new StdSchedulerFactory();
            scheduler = factory.getScheduler();
        }

        return scheduler;
    }
}
