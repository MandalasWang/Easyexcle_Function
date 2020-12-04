package ink.boyuan.util;


import ink.boyuan.util.easyexcel.model.ComplexHeadDemo;
import ink.boyuan.util.easyexcel.model.DataDemo;
import ink.boyuan.util.easyexcel.util.ReportExcelUtil;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import java.io.*;
import java.util.ArrayList;
import java.util.List;


/**
 * @author wyy
 * @version 1.0
 * @Classname ReportTest
 * @date 2020/11/30 13:38
 * @description
 **/
@SpringBootTest
public class ReportTest {

    /**
     * 数据集合
     */
    static List<DataDemo> dataDemos = new ArrayList<>();

    static {
        DataDemo data = new DataDemo(1,"小明","12");
        DataDemo data1 = new DataDemo(2,"小红","13");

        dataDemos.add(data);
        dataDemos.add(data1);
    }


    /**
     * 简单的导出单sheet
     * @throws FileNotFoundException
     */
    @Test
    public void simpleWrite() throws FileNotFoundException {

        File file = new File("D:\\work\\excel\\report.xlsx");
        OutputStream outputStream = new FileOutputStream(file);
        try {
            ReportExcelUtil.writeExcelIn(outputStream,dataDemos,"1",DataDemo.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 复杂sheet写入 指定写入1,2两个sheet的数据并导出
     * @throws FileNotFoundException
     */
    @Test
    public void complexSheetWriteTest() throws FileNotFoundException {
        File file = new File("D:\\work\\excel\\reportComplexSheet.xlsx");
        OutputStream outputStream = new FileOutputStream(file);
        try {
            ReportExcelUtil.writeExcelComplexSheet(outputStream,dataDemos,dataDemos,"1","2",DataDemo.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 多sheet重复导出  由sheetNo 控制循环次数
     * @throws FileNotFoundException
     */
    @Test
    public void repeatWriteTest() throws FileNotFoundException {
        File file = new File("D:\\work\\excel\\repeatWrite.xlsx");
        OutputStream outputStream = new FileOutputStream(file);
        try {
            ReportExcelUtil.repeatedWrite(outputStream,dataDemos,"1",DataDemo.class,3);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 复杂表头导出
     * @throws FileNotFoundException
     */
    @Test
    public void complexHeadWriteTest() throws FileNotFoundException {
        ComplexHeadDemo data = new ComplexHeadDemo(1,"小明","12");
        ComplexHeadDemo data1 = new ComplexHeadDemo(2,"小红","13");
        List<ComplexHeadDemo> dataDemos = new ArrayList<>();
        dataDemos.add(data);
        dataDemos.add(data1);
        File file = new File("D:\\work\\excel\\ComplexHeadWrite.xlsx");
        OutputStream outputStream = new FileOutputStream(file);
        try {
            ReportExcelUtil.writeExcelIn(outputStream,dataDemos,"1",ComplexHeadDemo.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void WriteInTemplateTest() throws FileNotFoundException {
        File file = new File("D:\\work\\excel\\repeatWrite.xlsx");
        InputStream inputStream = new FileInputStream(file);
        File file1 = new File("D:\\work\\excel\\WriteInTemplate.xlsx");
        OutputStream outputStream = new FileOutputStream(file1);
        try {
            ReportExcelUtil.writeExcelInSheetNo(outputStream,dataDemos,inputStream,"4",DataDemo.class,4);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void WriteByData() throws FileNotFoundException {
        File file1 = new File("D:\\work\\excel\\WriteByData.xlsx");
        OutputStream outputStream = new FileOutputStream(file1);
        ReportExcelUtil.writeSheetByData(outputStream,DataDemo.class,dataDemos,dataDemos);
    }

}
