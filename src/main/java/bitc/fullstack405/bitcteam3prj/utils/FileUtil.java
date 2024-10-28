package bitc.fullstack405.bitcteam3prj.utils;

import bitc.fullstack405.bitcteam3prj.database.entity.ImgFileEntity;
import bitc.fullstack405.bitcteam3prj.database.entity.UserEntity;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import org.springframework.stereotype.Component;

import java.io.*;


@Component
public class FileUtil {

  public ImgFileEntity uploadFile(HttpServletRequest req, String imgFileName) throws ServletException, IOException {

    Part part = req.getPart("uploadFile");

    String savePath = getSaveFilePath();
    String savedFileName = imgFileName +".jpg";


    File f = new File(savePath);

    if (!imgFileName.isEmpty()) {
      part.write(savePath + File.separator + savedFileName );


      ImgFileEntity imgFileEntity = new ImgFileEntity();
      return imgFileEntity;
    }
    else {
      return null;
    }
  }

  public void deleteFile(String fileName) {

    File file = new File(getSaveFilePath() + File.separator + fileName + ".jpg");

    if (file.exists()) {
      file.delete();
    }
  }

  public String getSaveFilePath(){
    File rootPath = new File("");
    String savePath = rootPath.getAbsolutePath();
    int subStdIdx = savePath.lastIndexOf('\\');
    savePath = savePath.substring(0, subStdIdx) + "\\Images\\";

    return savePath;
  }

}


