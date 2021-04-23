package com.cudo.mediabusiness.mediasite.service;

import com.cudo.mediabusiness.mediasite.domain.File;
import com.cudo.mediabusiness.mediasite.dto.FileDto;
import com.cudo.mediabusiness.mediasite.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FileService {
    private final FileRepository fileRepository;

    @Transactional
    public Long saveFile(FileDto fileDto) {
        return fileRepository.save(fileDto.toEntity());
    }

    @Transactional void deleteFile(FileDto fileDto) {
        fileRepository.delete(fileDto.toEntity());
    }

    //파일 찾기
    public FileDto getFile(Long id) {
        File file = fileRepository.findFile(id);
        FileDto fileDto = FileDto.builder()
                .id(id)
                .origin_file_name(file.getOrigin_file_name())
                .filename(file.getFilename())
                .filepath(file.getFilepath())
                .filesize(file.getFilesize())
                .build();
        return fileDto;
    }
}
