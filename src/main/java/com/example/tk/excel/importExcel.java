package com.example.tk.excel;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFPicture;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFShape;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.PictureData;
import org.apache.poi.xssf.usermodel.*;

/**
 *execl表格多张图片导入
 */

public class importExcel {


    public static void main(String[] args) {
        // 1.一个单元格一个图片
      //  importExcelOne();
        // 2.一个单元格多个图片
        importExcelTwo();

    }

    /**
     * 1.一个单元格一个图片
     */
    public static void importExcelOne() {
        try {
            FileInputStream fileInputStream = new FileInputStream("D://1603169080802.xls");

            HSSFWorkbook hssfWorkbook = new HSSFWorkbook(fileInputStream);// 工作簿

            HSSFSheet sheet = hssfWorkbook.getSheetAt(0);// 工作表

            int lastRowNum = sheet.getLastRowNum();// 获取最后一行序号,从零开始

            Map<String, PictureData> picMap = new HashMap<>();// 存储图片信息和坐标

            List<HSSFShape> list = sheet.getDrawingPatriarch().getChildren();

            if (list != null && list.size() > 0) {// 处理获取图片信息和坐标
                list = list.stream().filter(item -> item instanceof HSSFPicture).collect(Collectors.toList());// 过滤出图片的数据
                for (HSSFShape hssfShape : list) {
                    HSSFPicture hSSFPicture = (HSSFPicture) hssfShape;
                    HSSFClientAnchor hSSFClientAnchor = (HSSFClientAnchor) hSSFPicture.getAnchor();
                    PictureData pictureData = hSSFPicture.getPictureData();
                    picMap.put(hSSFClientAnchor.getRow1() + "," + hSSFClientAnchor.getCol1(), pictureData);
                }
            }

            for (int i = 1; i <= lastRowNum; i++) {
                HSSFRow row = sheet.getRow(i);
                String name = row.getCell(0) == null ? "" : row.getCell(0).getStringCellValue();
                String sex = row.getCell(1) == null ? "" : row.getCell(1).getStringCellValue();

                // 获取图片数据
                PictureData pictureData = picMap.get(i + "," + 2);
                String path = "";
                if (pictureData != null) {
                    String suggestFileExtension = pictureData.suggestFileExtension();// 图片格式
                    path = "D://" + i + "-2." + suggestFileExtension;// 存储路径
                    FileOutputStream out = new FileOutputStream(path);// 流写入
                    out.write(pictureData.getData());
                    out.close();
                }

                System.out.println("名称:" + name + ",    性别:" + sex + ",    图片存储路径:" + path);

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 1.一个单元格多图片
     */
    public static void importExcelTwo() {
        try {
            FileInputStream fileInputStream = new FileInputStream("F://理货报告导入模板.xlsx");

            XSSFWorkbook xssfWorkbook = new XSSFWorkbook (fileInputStream);// 工作簿

            XSSFSheet sheet = xssfWorkbook.getSheetAt(0);// 工作表

            int lastRowNum = sheet.getLastRowNum();// 获取最后一行序号,从零开始

            Map<String, List<PictureData>> picMap = new HashMap<>();// 存储图片信息和坐标

            List<XSSFShape> list = sheet.getDrawingPatriarch().getShapes();


            if (list.size() > 0) {// 处理获取图片信息和坐标
                list = list.stream().filter(item -> item instanceof XSSFPicture).collect(Collectors.toList());// 过滤出图片的数据
                for (XSSFShape xssfShape : list) {
                    XSSFPicture xSSFPicture = (XSSFPicture) xssfShape;
                    XSSFClientAnchor xSSFClientAnchor = (XSSFClientAnchor) xSSFPicture.getAnchor();
                    PictureData pictureData = xSSFPicture.getPictureData();
                    String point = xSSFClientAnchor.getRow1() + "," + xSSFClientAnchor.getCol1();
                    // 如果存在这个坐标KEY表示相同单元格中的图片,直接集合添加该图片,不存在该坐标key直接创建添加
                    if (picMap.containsKey(point)) {
                        picMap.get(point).add(pictureData);
                    } else {
                        List<PictureData> arrayList = new ArrayList<>();
                        arrayList.add(pictureData);
                        picMap.put(point, arrayList);
                    }
                }
            }

            for (int i = 1; i <= lastRowNum; i++) {
                XSSFRow row = sheet.getRow(i);
                String goodsCode = row.getCell(1) == null ? "" : row.getCell(1).getStringCellValue();
                String goodsName = row.getCell(2) == null ? "" : row.getCell(2).getStringCellValue();

                // 获取图片数据
                List<PictureData> pictureDataList = picMap.get(i + "," + 7);
                String paths = "";
                if (pictureDataList != null)
                    for (PictureData pictureData : pictureDataList) {
                        String suggestFileExtension = pictureData.suggestFileExtension();// 图片格式
                        String path = "D://" + i + "-2--" + pictureDataList.indexOf(pictureData) + "."
                                + suggestFileExtension;// 存储路径
                        paths = paths + path;
                        FileOutputStream out = new FileOutputStream(path);// 流写入
                        out.write(pictureData.getData());
                        out.close();
                    }

                System.out.println("商品条码:" + goodsCode + ",    商品名称:" + goodsName + ",    图片存储路径:" + paths);

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
