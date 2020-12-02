package ink.boyuan.easyexclefunction.util;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.fastjson.JSON;
import ink.boyuan.easyexclefunction.listen.ReadExcelListener;
import org.apache.poi.ss.formula.functions.T;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author wyy
 * @version 2.0
 * @Classname ImportExcelUtil
 * @date 2020/11/27 14:44
 * @description  主要用于读取Excel 可以指定从第几行开始读取
 **/
public class ImportExcelUtil {


    /**
     * 日志记录
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ImportExcelUtil.class);

    /**
     * 简单的读 只读单sheet默认第一个sheet
     * @author wyy
     * @param inputStream 文件流
     * @param clazz    实体类
     * @param headRowNumber 从第几行开始读(角标从1开始)
     * @return 数据源list
     */
    public static  <T> List<T> simpleRead(InputStream inputStream, Class<T> clazz, int headRowNumber) {
        // 有个很重要的点 DemoDataListener 不能被spring管理，要每次读取excel都要new,然后里面用到spring可以构造方法传进去
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
        ReadExcelListener<T> dataListener = new ReadExcelListener<>();
        EasyExcel.read(inputStream, clazz,dataListener).headRowNumber(headRowNumber).sheet().doRead();
        return dataListener.getList();
    }



    /**
     * 指定列的下标或者列名
     * @author wyy
     * <p>1. 创建excel对应的实体对象,并使用{@link ExcelProperty}注解. 参照{@link IndexOrNameData}
     * <p>2. 由于默认一行行的读取excel，所以需要创建excel一行一行的回调监听器，参照{@link ReadExcelListener}
     * <p>3. 直接读即可
     */
    public static <T> List<T> indexOrNameRead(InputStream inputStream, Class<T> clazz, int headRowNumber) {
        ReadExcelListener<T> dataListener = new ReadExcelListener<>();
        // 这里默认读取第一个sheet
        EasyExcel.read(inputStream, clazz, dataListener).sheet().headRowNumber(headRowNumber).doRead();
        return dataListener.getList();
    }



    /**
     * 读全部sheet,这里注意一个sheet不能读取多次，多次读取需要重新读取文件
     * @author wyy
     * <p>
     * 1. 创建excel对应的实体对象 参照{@link DemoData}
     * <p>
     * 2. 由于默认一行行的读取excel，所以需要创建excel一行一行的回调监听器，参照{@link DemoDataListener}
     * <p>
     * 3. 直接读即可
     */
    public static <T>List<T> repeatedReadToAllSheet(InputStream inputStream, Class clazz) {

        ReadExcelListener<T> dataListener = new ReadExcelListener<>();
        // 读取全部sheet
        // 这里需要注意 DemoDataListener的doAfterAllAnalysed 会在每个sheet读取完毕后调用一次。然后所有sheet都会往同一个DemoDataListener里面写
        EasyExcel.read(inputStream, clazz, dataListener).doReadAll();
        return dataListener.getList();

    }


    /**
     * 读全部sheet,这里注意一个sheet不能读取多次，多次读取需要重新读取文件
     * 指定sheet读取 传入0、1、2分别读取的sheet是Excel从左到右
     * @author wyy
     * <p>
     * 1. 创建excel对应的实体对象 参照{@link DemoData}
     * <p>
     * 2. 由于默认一行行的读取excel，所以需要创建excel一行一行的回调监听器，参照{@link DemoDataListener}
     * <p>
     * 3. 直接读即可
     * @param sheetNos  输入需要读取的sheet 想要读取那个就输入哪个
     */
    public static <T>List<T> repeatedReadBySheetNos(InputStream inputStream, Class<T> clazz, int headRowNumber, Integer ...sheetNos) {
        ExcelReader excelReader  = EasyExcel.read(inputStream).build();
        List<T> res = new ArrayList<>();
        for(Integer sheet:sheetNos){
            List<T> list = readSheet(excelReader,clazz, headRowNumber, sheet);
            res.addAll(list);
        }
        return res;

    }

    /**
     * 简单的读取第一个sheet表格内容
     * @author wyy
     * @param inputStream
     * @param clazz
     * @param headRowNumber
     * @param <T>
     * @return
     */
    public static <T>List<T> simpleReadFirstSheet(InputStream inputStream, Class<T> clazz, int headRowNumber) {
        return repeatedReadBySheetNos(inputStream,clazz,headRowNumber,0);

    }
    /**
     * @author wyy
     * @param excelReader  excel读取reader
     * @param clazz      读取模板
     * @param headRowNumber  读取行数
     * @param sheet    读取的sheetNo
     * @param <T>
     * @return
     */
    private static <T> List<T> readSheet( ExcelReader excelReader,Class<T> clazz, int headRowNumber, Integer sheet) {
        ReadExcelListener<T> dataListener = new ReadExcelListener<>();
        // 这里为了简单 所以注册了 同样的head 和Listener 自己使用功能必须不同的Listener
        ReadSheet build = EasyExcel.readSheet(sheet).head(clazz).registerReadListener(dataListener).headRowNumber(headRowNumber).build();
        excelReader.read(build);
        return dataListener.getList();
    }

    /**
     * 多行头
     * @author wyy
     * <p>1. 创建excel对应的实体对象 参照{@link DemoData}
     * <p>2. 由于默认一行行的读取excel，所以需要创建excel一行一行的回调监听器，参照{@link DemoDataListener}
     * <p>3. 设置headRowNumber参数，然后读。 这里要注意headRowNumber如果不指定， 会根据你传入的class的{@link ExcelProperty#value()}里面的表头的数量来决定行数，
     * 如果不传入class则默认为1.当然你指定了headRowNumber不管是否传入class都是以你传入的为准。
     */
    public static <T>List<T> complexHeaderRead(InputStream inputStream, Class<T> clazz, int headRowNumber) {
        ReadExcelListener<T> dataListener = new ReadExcelListener<>();
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet
        EasyExcel.read(inputStream, clazz,dataListener).sheet()
                // 这里可以设置1，因为头就是一行。如果多行头，可以设置其他值。不传入也可以，因为默认会根据DemoData 来解析，他没有指定头，也就是默认1行
                .headRowNumber(headRowNumber).doRead();
        return dataListener.getList();
    }



    /**
     * 同步的返回，不推荐使用，如果数据量大会把数据放到内存里面
     * @author wyy
     */
    @Deprecated
    public static void synchronousRead(InputStream inputStream, Class<T> clazz, int headRowNumber) {
        ReadExcelListener<T> dataListener = new ReadExcelListener<>();
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 同步读取会自动finish
        List<T> list = EasyExcel.read(inputStream,dataListener).head(clazz).headRowNumber(headRowNumber).sheet().doReadSync();
        for (T data : list) {
            LOGGER.info("读取到数据:{}", JSON.toJSONString(data));
        }


        // 这里 也可以不指定class，返回一个list，然后读取第一个sheet 同步读取会自动finish
        List<Map<T, T>> listMap = EasyExcel.read(inputStream,dataListener).sheet().doReadSync();
        for (Map<T, T> data : listMap) {
            // 返回每条数据的键值对 表示所在的列 和所在列的值
            LOGGER.info("读取到数据:{}", JSON.toJSONString(data));
        }
        return;
    }


    /**
     * 读取表头数据
     * @author wyy
     * <p>
     * 1. 创建excel对应的实体对象 参照{@link DemoData}
     * <p>
     * 2. 由于默认一行行的读取excel，所以需要创建excel一行一行的回调监听器，参照{@link DemoHeadDataListener}
     * <p>
     * 3. 直接读即可
     */
    public static <T>List<Map<Integer,String>> headerRead(InputStream inputStream, Class<T> clazz) {
        ReadExcelListener<T> dataListener = new ReadExcelListener<>();
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet
        EasyExcel.read(inputStream, clazz, dataListener).sheet().doRead();
        return dataListener.getHeadMapList();
    }




}
