package com.cudo.mediabusiness.mediasite.service;

import com.cudo.mediabusiness.mediasite.domain.BusinessRequest;
import com.cudo.mediabusiness.mediasite.dto.BusinessRequestDto;
import com.cudo.mediabusiness.mediasite.repository.BusinessRequestRepository;
import com.cudo.mediabusiness.mediasite.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BusinessRequestService {

    private final BusinessRequestRepository businessRequestRepository;
    private final FileRepository fileRepository;
    
    //제휴문의 등록
    @Transactional
    public Long registerBusinessRequest(BusinessRequestDto businessRequestDto){
        return businessRequestRepository.saveRequest(businessRequestDto.toEntity());
    }

    @Transactional
    public void updateBusiness(Long businessId, Long fileId){
        BusinessRequest businessRequest = businessRequestRepository.findById(businessId);
        businessRequest.updateFile(fileRepository.findFile(fileId));
    }

    //상세 조회
    public BusinessRequestDto getBusiness(Long id) {
        BusinessRequest businessRequest = businessRequestRepository.findById(id);
        BusinessRequestDto businessRequestDto = BusinessRequestDto.builder()
                .title(businessRequest.getTitle())
                .company_name(businessRequest.getCompany_name())
                .number(businessRequest.getNumber())
                .manager(businessRequest.getManager())
                .email(businessRequest.getEmail())
                .content(businessRequest.getContent())
                .file(businessRequest.getFile())
                .id(businessRequest.getId())
                .register_date(businessRequest.getRegister_date())
                .build();
        return businessRequestDto;
    }

    //제휴문의 전체 조회
    public List<BusinessRequestDto> findAllBusinessRequest(){
        List<BusinessRequest> businessRequestList = businessRequestRepository.findAllRequest();
        List<BusinessRequestDto> businessRequestDtoList = new ArrayList<>();
        for(BusinessRequest businessRequest : businessRequestList){
            BusinessRequestDto businessRequestDto = BusinessRequestDto.builder()
                    .title(businessRequest.getTitle())
                    .company_name(businessRequest.getCompany_name())
                    .number(businessRequest.getNumber())
                    .manager(businessRequest.getManager())
                    .email(businessRequest.getEmail())
                    .content(businessRequest.getContent())
                    .file(businessRequest.getFile())
                    .id(businessRequest.getId())
                    .register_date(businessRequest.getRegister_date())
                    .build();
            businessRequestDtoList.add(businessRequestDto);
        }
        return businessRequestDtoList;
    }
}
