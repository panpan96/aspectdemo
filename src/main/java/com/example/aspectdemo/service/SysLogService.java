package com.example.aspectdemo.service;

import com.example.aspectdemo.entity.SysLogBo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @Author zhoutf
 * @Date 2019/3/20 9:23
 * @Description
 */
@Service
@Slf4j
public class SysLogService {

    public boolean save(SysLogBo sysLogBo) {

        log.info(sysLogBo.getParams());
        return true;
    }
}
