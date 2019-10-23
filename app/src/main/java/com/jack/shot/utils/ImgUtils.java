package com.jack.shot.utils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ImgUtils {
    /**
     * 创建文件和文件夹
     *
     * @param filename
     * @param isDir
     * @return
     * @throws IOException
     * @author： WX
     */
    public static File buildFile(String filename, boolean isDir)
            throws IOException {
        File file = new File(filename);
        if (!file.exists()) {
            if (isDir) {
                file.mkdirs();
            } else {
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                    file = new File(file.getAbsolutePath());
                }
            }
        }
        return file;
    }

    /**
     * 获取文件名
     * @param wsa
     * @return
     */
    public static String getTimeStampFileName(int wsa){
        SimpleDateFormat timesdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String timeStamp = timesdf.format(new Date()).toString();
        String fileName = "";
        if (wsa == 0){
            fileName = timeStamp + ".png";
        }else if (wsa == 1){
            fileName = timeStamp + ".xls";
        } else if (wsa == 2) {
            fileName = timeStamp + ".jpg";
        } else if (wsa == 3) {
            fileName = timeStamp + ".txt";
        }
        return fileName;
    }
}
