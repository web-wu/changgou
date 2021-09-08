package com.tabwu.changgou.file.controller;

import com.tabwu.changgou.entity.MsgConstant;
import com.tabwu.changgou.entity.Result;
import com.tabwu.changgou.entity.StatusCode;
import com.tabwu.changgou.file.utils.FdsFileInfo;
import com.tabwu.changgou.file.utils.FdsUtil;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;

@RestController
@CrossOrigin
public class FileController {

    @PostMapping("/fileUpload")
    public Result upload(@RequestParam("file") MultipartFile file) {
        try {
            String filename = file.getOriginalFilename();
            String extendFile = StringUtils.getFilenameExtension(filename);
            byte[] fileContent = file.getBytes();

            FdsFileInfo fileInfo = new FdsFileInfo(filename, extendFile, fileContent);

            String[] upload = FdsUtil.upload(fileInfo);

            String url = FdsUtil.getTrackerUrl() + "/" + upload[0] + "/" + upload[1];

            FdsFileInfo info = new FdsFileInfo(url,upload);
            return new Result(true, StatusCode.OK, MsgConstant.FILE_UPLOAD_SUCCESS,info);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, StatusCode.ERROR, MsgConstant.FILE_UPLOAD_FAIL);
        }
    }



    @DeleteMapping("/fileDelete")
    public Result delete(String[] group_remotePath) {
        try {
            FdsUtil.delete(group_remotePath[0],group_remotePath[1]);
            return new Result(true, StatusCode.OK, MsgConstant.FILE_DELETE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, StatusCode.ERROR, MsgConstant.FILE_DELETE_FAIL);
        }
    }

    @PostMapping("/fileDownload")
    public void download(String[] group_remotePath, HttpServletResponse response, ServletContext context) {
        try {
            String filename = group_remotePath[1].split("/")[3];
            InputStream inputStream = FdsUtil.dowmload(group_remotePath[0], group_remotePath[1]);

            String mimeType = context.getMimeType(filename);
            response.setHeader("content-type",mimeType);
            response.setHeader("content-disposition","attachment;filename="+filename);
            /*FileOutputStream outputStream = new FileOutputStream(new File(filename));*/
            OutputStream outputStream = response.getOutputStream();
            byte[] chs = new byte[1024 * 8];
            int len = 0;
            while ((len = inputStream.read(chs)) != -1) {
                outputStream.write(chs,0,len);
            }
            inputStream.close();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
