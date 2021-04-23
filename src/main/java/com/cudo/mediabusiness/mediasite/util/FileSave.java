package com.cudo.mediabusiness.mediasite.util;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Getter @Setter
public class FileSave {
    private String originFilename;
    //변환한 파일 이름
    private String conversionFilename;
    private String filePath;
    private String savePath;
    private Long fileSize;

    public void fileInfo(MultipartFile file) {
        try{
            fileSize = file.getSize();
            originFilename = file.getOriginalFilename();

            //파일 저장 위치
            savePath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\upload";
            if(!new File(savePath).exists()){
                try {
                    new File(savePath).mkdir();
                }catch (Exception e){
                    e.getStackTrace();
                }
            }

            //파일 확장자 구해서 이미지 관련 파일은 암호화 안하기
            int index = originFilename.lastIndexOf(".");
            String fileExt = originFilename.substring(index + 1);
            if(!fileExt.equals("jpg") && !fileExt.equals("png") && !fileExt.equals("gif") && !fileExt.equals("bmp")){
                conversionFilename = new MD5Generator(originFilename).toString();
                filePath = savePath + "\\" + conversionFilename;
                file.transferTo(new File(filePath));
            }
            else {
                conversionFilename = originFilename;
                filePath = savePath + "\\" + conversionFilename;
                file.transferTo(new File(filePath));
            }
        }catch (Exception  e) {
            e.printStackTrace();
        }
    }
}
