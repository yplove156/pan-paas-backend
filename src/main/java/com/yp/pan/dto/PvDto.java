package com.yp.pan.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * PvDto class
 *
 * @author Administrator
 * @date 2019/02/15
 */
@Data
@NoArgsConstructor
public class PvDto {

    private String name;
    private List<String> accessModes;
    private String persistentVolumeReclaimPolicy;
    private String nfsServer;
    private String nfsPath;
}
