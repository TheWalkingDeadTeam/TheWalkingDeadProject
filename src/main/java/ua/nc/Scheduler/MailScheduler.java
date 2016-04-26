package ua.nc.Scheduler;

import ua.nc.entity.Mail;
import ua.nc.service.MailService;
import ua.nc.service.MailServiceImpl;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Created by Alexander on 25.04.2016.
 */
public class MailScheduler {

//    private int POOL_SIZE = 10;
//    private ScheduledExecutorService executorService = Executors.newScheduledThreadPool(10);
//    MailService mailServiceInstance;
//
//    public MailScheduler(MailService mailService){
//        this.mailServiceInstance = mailService;
//    }
//
//    public void startExecutionAt(int targetHour, int targetMin, int targetSec, final String address, final Mail mail)
//    {
//        Runnable taskWrapper = new Runnable(){
//
//            @Override
//            public void run()
//            {
//                mailServiceInstance.sendMail(address,mail);
//                startExecutionAt(targetHour, targetMin, targetSec);
//            }
//
//        };
//        long delay = computeNextDelay(targetHour, targetMin, targetSec);
//        executorService.schedule(taskWrapper, delay, TimeUnit.SECONDS);
//    }


}
