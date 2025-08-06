package com.iceAge.server.analysis.domain.service;

import com.iceAge.server.analysis.presentation.dto.response.AllAnalysisResponseDTO;
import com.iceAge.server.analysis.presentation.dto.response.DistrictAnalysisResponseDTO;

public interface DataAnalysisDomainService {
  DistrictAnalysisResponseDTO getAllDistrictData(int districtCode);
  AllAnalysisResponseDTO getAllData(int districtCode, String categoryCode);
}
