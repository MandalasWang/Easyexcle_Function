package ink.boyuan.easyexclefunction;

import ink.boyuan.easyexclefunction.model.DataDemo;
import ink.boyuan.easyexclefunction.util.ImportExcelUtil;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

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
@SpringBootTest
public class ReadTest {

    private ImportExcelUtil importExcelUtil = new ImportExcelUtil();

    /**
     * 简单的读取所有
     * @throws FileNotFoundException
     */
    @Test
    public void simpleReadTest() throws FileNotFoundException {
        InputStream inputStream = new FileInputStream("D:\\work\\excel\\report.xlsx");
        List<DataDemo> dataDemos = importExcelUtil.simpleRead(inputStream, DataDemo.class, 1);
        dataDemos.forEach(System.out::println);
    }


    /**
     * 重复的读取sheetNo  读取所有的sheet
     * @throws FileNotFoundException
     */
    @Test
    public void repeatedReadTest() throws FileNotFoundException {
        InputStream inputStream = new FileInputStream("D:\\work\\excel\\repeatWrite.xlsx");
        List<DataDemo> dataDemos = importExcelUtil.repeatedReadToAll(inputStream, DataDemo.class);
        dataDemos.forEach(System.out::println);
    }

    /**
     * 读取指定的sheetNo  输入sheet不定参 想读哪个就输入哪个
     * @throws FileNotFoundException
     */
    @Test
    public void repeatedReadBySheetTest() throws FileNotFoundException {
        InputStream inputStream = new FileInputStream("D:\\work\\excel\\repeatWrite.xlsx");
        List<DataDemo> dataDemos = importExcelUtil.repeatedReadBySheetNo(inputStream, DataDemo.class, 2,1);
        dataDemos.forEach(System.out::println);
    }


    /**
     * 读取表头数据并保存到list
     * @throws FileNotFoundException
     */
    @Test
    public void headerRead() throws FileNotFoundException {
        InputStream inputStream = new FileInputStream("D:\\work\\excel\\repeatWrite.xlsx");
        List<Map<Integer, String>> maps = importExcelUtil.headerRead(inputStream, DataDemo.class);
        maps.forEach(System.out::println);
    }
}
