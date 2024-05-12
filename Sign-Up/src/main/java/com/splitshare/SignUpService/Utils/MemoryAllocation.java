package com.splitshare.SignUpService.Utils;

import com.splitshare.SignUpService.Model.MemoryReturn;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MemoryAllocation {

    private MemoryReturn returnMemory() {
        Runtime runtime = Runtime.getRuntime();
        long maxMemory = runtime.maxMemory();
        long allocatedMemory = runtime.totalMemory();
        long freeMemory = runtime.freeMemory();
        long usedMemory = allocatedMemory - freeMemory;

        return MemoryReturn.builder()
                .allocatedMemory(allocatedMemory / (1024 * 1024))
                .maxMemory(maxMemory / (1024 * 1024))
                .freeMemory(freeMemory / (1024 * 1024))
                .usedMemoey(usedMemory / (1024 * 1024))
                .build();
    }

    @Scheduled(fixedRate = 600000) // This will run the method every 10 minutes
    public void scheduledTask() {
        MemoryReturn memory = returnMemory();
        log.info("\nThe meory usage is as below: \nallocated memory: {} megabytes \nmax memory: {} megabytes \nfree memory: {} megabytes  \nused memory: {} megabytes ",
                memory.getAllocatedMemory(),memory.getMaxMemory(),memory.getFreeMemory(),memory.getUsedMemoey());
    }
}
