package com.splitshare.SignUpService.Model;

import lombok.*;


@Builder
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MemoryReturn {
    private long maxMemory;
    private long allocatedMemory;
    private long freeMemory;
    private long usedMemoey;


}
