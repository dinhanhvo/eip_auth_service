package com.voda.eip.service;

import com.voda.eip.entity.Weigh;
import com.voda.eip.repository.WeighRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WeighService {

    @Autowired
    private WeighRepository weighRepository;

    public List<Weigh> getAllWeigh() {
        return weighRepository.findAll();
    }

    public Weigh getWeighById(long id) {
        return weighRepository.findById(id).get();
    }

    public Weigh saveWeigh(Weigh weigh) {
        return weighRepository.save(weigh);
    }
    public Weigh updateWeigh(Weigh weigh) {
        return weighRepository.save(weigh);
    }
    public void deleteWeigh(long id) {
        weighRepository.deleteById(id);
    }
}
