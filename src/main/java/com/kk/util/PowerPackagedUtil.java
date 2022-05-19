package com.kk.util;

import org.apache.poi.hssf.usermodel.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PowerPackagedUtil {
    // 参与解析脚本的 xls配置
    public static String readFilePath = "D:\\buding\\Packaged\\test.xls";
    // 输出bat部署脚本位置
    public static final String outFilePath = "D:\\buding\\Packaged\\test.bat";
    // 输出bat部署文件位置
    public static final String outFilePath_1 = "D:\\buding\\Packaged\\file.txt";

    // SRC文件路径 (用于打包 .class 文件)
    public static final String replaceString_1 ="xcop: D:\\mykkProject\\project\\web\\WEB_INF\\class\\";
    // WebRoot文件路径 (用于打包 .jsp 及其 xml 等文件)
    public static final String replaceString_2 ="xcop: D:\\mykkProject\\project\\web\\";

    // 代码仓库地址
    public static final String fixString = "https://172.21.21.1/svn/project";
    // 代码仓库地址,拼接
    public static final String srcString = "/src/";
    // 文件乱序时重排
    public static  boolean isLX = true;

    public static void main(String[] args) {
        try {
            HSSFWorkbook workbook = new HSSFWorkbook (new FileInputStream (readFilePath));
            HSSFSheet sheet = workbook.getSheetAt (0);
            int totalRow = sheet.getLastRowNum ( );
            int totalCell = 0;
            int i =0;
            int j =0;
            String cellValue = "";
            List<String> sourceList = new ArrayList<String> ( );
            HSSFCellStyle style = workbook.createCellStyle ( );
            System.out.println ("-----------1.文件内容读取开始-----------" );
            for ( i = 0; i < totalRow; i++) {
                HSSFRow row = sheet.getRow (i);
                if (row != null) {
                    totalCell = row.getLastCellNum ( );
                    for ( j = 0; j < totalCell; j++) {
                        HSSFCell cell = row.getCell (i);
                        cellValue = cell.getStringCellValue ( );
                        // 先读取 excle 中的信息：版本号，修改记录等

                    }
                }


            }
        } catch (IOException e) {
            throw new RuntimeException (e);
        }
    }

}
