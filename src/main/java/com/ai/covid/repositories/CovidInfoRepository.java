package com.ai.covid.repositories;

import com.ai.covid.models.CovidInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CovidInfoRepository extends JpaRepository<CovidInfo, Long> {

    List<CovidInfo> getAllBySent(Boolean status);

}
