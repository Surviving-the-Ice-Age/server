package com.iceAge.server.analysis.application.service;

import com.iceAge.server.analysis.domain.service.DataAnalysisDomainService;
import com.iceAge.server.analysis.infrastructure.external.DistrictApiClient;
import com.iceAge.server.analysis.presentation.dto.request.CommentRequestDTO;
import com.iceAge.server.analysis.presentation.dto.response.AllAnalysisResponseDTO;
import com.iceAge.server.analysis.presentation.dto.response.DistrictAnalysisResponseDTO;
import com.iceAge.server.analysis.presentation.dto.response.LargeCommentResponseDTO;
import com.iceAge.server.analysis.presentation.dto.response.ScoreResponseDTO;
import com.iceAge.server.analysis.presentation.dto.response.SmallCommentResponseDTO;
import com.iceAge.server.analysis.presentation.dto.response.SummaryResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DistrictAnalysisService implements DataAnalysisDomainService {

  private final DistrictApiClient districtApiClient;

  @Override
  public DistrictAnalysisResponseDTO getAllDistrictData(int districtCode){
    String genderResidence_path = "/api/resident/gender-age"; //가장 많은 주거 인구(성별-나이)
    String genderWorker_path = "/api/workplace/gender-age"; //가장 많은 노동 인구(성별-나이)
    String floatingTime_path = "/api/floating/time"; //유동 인구가 많은 피크 타임
    String floatingAge_path = "/api/floating/age"; //가장 많은 유동 인구(나이)
    String areaAverage_path = "/api/sales/area-average"; //해당 상권 평균 매출

    String genderResidence = districtApiClient.getDistrictData(districtCode, genderResidence_path).block(); //가장 많은 주거 인구(성별-나이)
    String genderWorker = districtApiClient.getDistrictData(districtCode, genderWorker_path).block(); //가장 많은 노동 인구(성별-나이)
    String floatingTime = districtApiClient.getDistrictData(districtCode, floatingTime_path).block(); //유동 인구가 많은 피크 타임
    String floatingAge = districtApiClient.getDistrictData(districtCode, floatingAge_path).block(); //가장 많은 유동 인구(나이)
    String areaAverage = districtApiClient.getDistrictData(districtCode, areaAverage_path).block(); //해당 상권 평균 매출

    return new DistrictAnalysisResponseDTO(genderResidence, genderWorker, floatingTime, floatingAge, areaAverage);
  }

  @Override
  public AllAnalysisResponseDTO getAllData(int districtCode, String categoryCode){
    String salesGender_path = "/api/sales/gender"; // 해당 상권/업종에서 비중이 높은 성별
    String salesTime_path = "/api/sales/time"; // 해당 상권/업종에서 매출이 가장 높은 시간대
    String salesAge_path = "/api/sales/age"; // 해당 상권/업종에서 매출 비중이 높은 연령
    String salesMonthly_path = "/api/sales/monthly"; // 해당 상권/업종 월 평균 매출 & 증감 수치 (전분기, 전년 동분기 대비)

    String storeCount_path = "/api/store/count"; // 해당 상권/업종 최근 분기 점포 수 & 증감 수치 (전분기, 전년 동분기 대비)
    String storeOpen_path = "/api/store/open"; // 해당 상권/업종 최근 점포 개업 현황 (해당 상권, 해당 업종)
    String storeClose_path = "/api/store/close"; // 해당 상권/업종 최근 점포 폐업 현황 (해당 상권, 해당 업종)
    String storeFranchise_path = "/api/store/franchise"; // 해당 상권/업종 프랜차이즈 비율 (해당 상권, 해당 업종)

    String salesGender = districtApiClient.getAllData(districtCode, categoryCode, salesGender_path).block();
    String salesTime = districtApiClient.getAllData(districtCode,categoryCode,salesTime_path).block();
    String salesAge = districtApiClient.getAllData(districtCode,categoryCode,salesAge_path).block();
    String salesMonthly = districtApiClient.getAllData(districtCode,categoryCode,salesMonthly_path).block();

    String storeCount = districtApiClient.getAllData(districtCode,categoryCode,storeCount_path).block();
    String storeOpen = districtApiClient.getAllData(districtCode,categoryCode,storeOpen_path).block();
    String storeClose = districtApiClient.getAllData(districtCode,categoryCode,storeClose_path).block();
    String storeFranchise = districtApiClient.getAllData(districtCode,categoryCode,storeFranchise_path).block();

    return new AllAnalysisResponseDTO(salesGender, salesTime, salesAge, salesMonthly, storeCount, storeOpen, storeClose, storeFranchise);
  }

  @Override
  public ScoreResponseDTO getScoreData(int districtCode, String categoryCode){
    String score_path = "/api/score";

    return districtApiClient.getScoreData(districtCode, categoryCode, score_path).block();
  }

  @Override
  public LargeCommentResponseDTO getLargeCommentAnalysis(CommentRequestDTO commentRequestDTO){
    String large_path = "/sentiment/predict";
    return districtApiClient.getLargeCommentData(commentRequestDTO, large_path).block();
  }

  @Override
  public SmallCommentResponseDTO getSmallCommentAnalysis(CommentRequestDTO commentRequestDTO){
    String small_path = "/sentiment/predict";
    return districtApiClient.getSmallCommentData(commentRequestDTO, small_path).block();
  }

  @Override
  public SummaryResponseDTO getSummaryData(int districtCode){
    String summary_path = "/api/area/summary";
    return districtApiClient.getSummaryData(districtCode, summary_path).block();
  }

}
