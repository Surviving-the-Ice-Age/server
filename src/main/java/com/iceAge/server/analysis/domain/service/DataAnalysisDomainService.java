package com.iceAge.server.analysis.domain.service;

import com.iceAge.server.analysis.presentation.dto.request.CommentRequestDTO;
import com.iceAge.server.analysis.presentation.dto.response.AllAnalysisResponseDTO;
import com.iceAge.server.analysis.presentation.dto.response.DistrictAnalysisResponseDTO;
import com.iceAge.server.analysis.presentation.dto.response.LargeCommentResponseDTO;
import com.iceAge.server.analysis.presentation.dto.response.ScoreResponseDTO;
import com.iceAge.server.analysis.presentation.dto.response.SmallCommentResponseDTO;
import com.iceAge.server.analysis.presentation.dto.response.SummaryResponseDTO;

public interface DataAnalysisDomainService {
  DistrictAnalysisResponseDTO getAllDistrictData(int districtCode);
  AllAnalysisResponseDTO getAllData(int districtCode, String categoryCode);

  ScoreResponseDTO getScoreData(int districtCode, String categoryCode);

  LargeCommentResponseDTO getLargeCommentAnalysis(CommentRequestDTO commentRequestDTO);
  SmallCommentResponseDTO getSmallCommentAnalysis(CommentRequestDTO commentRequestDTO);
  SummaryResponseDTO getSummaryData(int districtCode);
}
