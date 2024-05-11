package com.voda.eip.repository;

import com.voda.eip.entity.MilkCollect;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;

@Repository
public interface IMilkCollectRepository extends JpaRepository<MilkCollect, Long> {

    List<MilkCollect> findByMqttStatus(String mqttStatus);

    List<MilkCollect> findMilkCollectBySerialWeigherAndCreatedAtBetween(String serialWeigher, ZonedDateTime from, ZonedDateTime to);

    List<MilkCollect> findAllByCreatedAtBetween(ZonedDateTime from, ZonedDateTime to);


}
