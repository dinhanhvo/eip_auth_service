package com.voda.eip.repository;

import com.voda.eip.entity.Weigh;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeighRepository extends JpaRepository<Weigh, Long> {

}
