package com.voda.eip.service;

import com.voda.eip.entity.MilkCollect;
import com.voda.eip.repository.IMilkCollectRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.List;

@Slf4j
@Service
public class MilkCollectService {

    @Autowired
    IMilkCollectRepository milkCollectRepository;

    public List<MilkCollect> getMilkCollects() {
        return  milkCollectRepository.findAll();
    }

    public List<MilkCollect> getMilkCollectsBySerialWeigh(String serialWeigh, ZonedDateTime from, ZonedDateTime to) {
        return  milkCollectRepository.findMilkCollectBySerialWeigherAndCreatedAtBetween(serialWeigh, from, to);
    }

    public List<MilkCollect> getMilkCollectsByDates(ZonedDateTime from, ZonedDateTime to) {
        return  milkCollectRepository.findAllByCreatedAtBetween(from, to);
    }

    public MilkCollect getMilkCollectById(Long id) {
        return  milkCollectRepository.findById(id).orElse(null);
    }

    public List<MilkCollect> getMilkCollectsByStatus(String status) {
        List<MilkCollect> mls = milkCollectRepository.findByMqttStatus(status);
        return  milkCollectRepository.findAll();
    }

    public MilkCollect storeMilkCollect(MilkCollect milkCollect) {
//        MilkCollect milkCollect = milkCollectRepository.findById(id).orElse(null);
        if (milkCollect != null) {
            milkCollectRepository.save(milkCollect);
        }
        return  milkCollect;
    }

    public MilkCollect updateStatusMilkCollectById(Long id, String status) {
        MilkCollect milkCollect = milkCollectRepository.findById(id).orElse(null);
        if (milkCollect != null) {
            milkCollect.setMqttStatus(status);
            milkCollectRepository.save(milkCollect);
        }
        return  milkCollect;
    }
}
