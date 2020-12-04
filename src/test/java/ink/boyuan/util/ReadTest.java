package ink.boyuan.util;


import ink.boyuan.util.easyexcel.model.DataDemo;
import ink.boyuan.util.easyexcel.util.ImportExcelUtil;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * @author wyy
 * @version 1.0
 * @Classname ReadTest
 * @date 2020/12/1 8:56
 * @description
 **/
public class ReadTest {


    /**
     * 简单的读取所有
     * @throws Exception
     */
    @Test
    public void simpleReadTest() throws Exception {
        InputStream inputStream = new FileInputStream("D:\\work\\excel\\repeatWrite.xlsx");
        List<DataDemo> dataDemos = ImportExcelUtil.simpleReadFirstSheet(inputStream, DataDemo.class);
        dataDemos.forEach(System.out::println);
    }


    @Test
    public void ReadAllTest() throws FileNotFoundException {
        InputStream inputStream = new FileInputStream("D:\\work\\excel\\repeatWrite.xlsx");
        List<Object> objects1 = ImportExcelUtil.repeatedReadToAllSheet(inputStream, DataDemo.class);
        objects1.forEach(System.out::println);
    }

    /**
     * 重复的读取sheetNo  读取所有的sheet
     * @throws FileNotFoundException
     */
    @Test
    public void repeatedReadTest() throws FileNotFoundException {
        InputStream inputStream = new FileInputStream("D:\\work\\excel\\repeatWrite.xlsx");
        List<DataDemo> dataDemos = ImportExcelUtil.repeatedReadToAllSheet(inputStream, DataDemo.class);
        dataDemos.forEach(System.out::println);
    }

    /**
     * 读取指定的sheetNo  输入sheet不定参 想读哪个就输入哪个
     * @throws Exception
     */
    @Test
    public void repeatedReadBySheetTest() throws Exception {
        InputStream inputStream = new FileInputStream("D:\\work\\excel\\repeatWrite.xlsx");
        List<DataDemo> dataDemos = ImportExcelUtil.repeatedReadBySheetNos(inputStream, DataDemo.class, 1,0,1);
        dataDemos.forEach(System.out::println);
    }


    /**
     * 读取表头数据并保存到list
     * @throws FileNotFoundException
     */
    @Test
    public void headerRead() throws FileNotFoundException {
        InputStream inputStream = new FileInputStream("D:\\work\\excel\\repeatWrite.xlsx");
        List<Map<Integer, String>> maps = ImportExcelUtil.headerRead(inputStream, DataDemo.class);
        maps.forEach(System.out::println);
    }
}
