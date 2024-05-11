package com.voda.eip.controller;

import com.voda.eip.entity.MilkCollect;
import com.voda.eip.service.MilkCollectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/api")
public class CheckWeigherController {

    @Autowired
    MilkCollectService milkCollectService;

    @GetMapping("/milk")
    public ResponseEntity<List<MilkCollect>> getMilkCollect() {
        List<MilkCollect> response = milkCollectService.getMilkCollects();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/milks")
    public ResponseEntity<List<MilkCollect>> getMilkCollects(@RequestParam("serialWeigh") String serialWeigh, @RequestParam("from") String from, @RequestParam("to") String to) {

        ZonedDateTime start = ZonedDateTime.parse(from);
        ZonedDateTime end = ZonedDateTime.parse(to);

        List<MilkCollect> response = milkCollectService.getMilkCollectsBySerialWeigh(serialWeigh, start, end);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/milks/dates")
    public ResponseEntity<List<MilkCollect>> getMilkCollectsByDate(@RequestParam("from")
                                               String from,
                                                                   String to) {
        ZonedDateTime start = ZonedDateTime.parse(from);
        ZonedDateTime end = ZonedDateTime.parse(to);
        List<MilkCollect> response = milkCollectService.getMilkCollectsByDates(start, end);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/milk/id/{id}")
    public ResponseEntity<MilkCollect> getMilkCollectById(@PathVariable("id") Long id) {
        MilkCollect response = milkCollectService.getMilkCollectById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/milk_status")
    public ResponseEntity<List<MilkCollect>> getMilkCollectByStatus(@RequestParam("status") String status) {
        List<MilkCollect> response = milkCollectService.getMilkCollectsByStatus(status);
        return ResponseEntity.ok(response);
    }
}
