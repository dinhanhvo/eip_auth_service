package com.voda.eip.controller;

import com.voda.eip.entity.Weigh;
import com.voda.eip.service.WeighService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class WeighController {
    @Autowired
    private WeighService weighService;

    @GetMapping("/weighs")
    public ResponseEntity<List<Weigh>> getWeighs() {
        return ResponseEntity.ok(weighService.getAllWeigh());
    }

    @PostMapping("/weigh")
    public ResponseEntity<Weigh> addWeigh(@RequestBody Weigh weigh) {
        return ResponseEntity.ok(weighService.saveWeigh(weigh));
    }
    @PutMapping("/weigh")
    public ResponseEntity<Weigh> updateWeigh(@RequestBody Weigh weigh) {
        return ResponseEntity.ok(weighService.updateWeigh(weigh));
    }
    @DeleteMapping("/weigh")
    public ResponseEntity<Boolean> deleteWeigh(@RequestParam("id") long id) {
        weighService.deleteWeigh(id);
        return ResponseEntity.ok(true);
    }

}
