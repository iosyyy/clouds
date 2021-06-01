package com.fegin.demo.service.Imp;

import com.fegin.demo.service.FeginService;
import org.springframework.stereotype.Service;

/**
 * @author 靖鸿宣
 * @since 2021/4/3
 */
@Service
public class FeginServiceImp implements FeginService {
    @Override
    public String getTimeOut(Integer id) {
        return "Failed to start it";
    }
}
