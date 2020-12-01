package ink.boyuan.easyexclefunction.model;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.converters.string.StringImageConverter;
import lombok.Data;

import java.io.File;
import java.io.InputStream;
import java.net.URL;

/**
 * @author wyy
 * @version 1.0
 * @Classname ImageDemo
 * @date 2020/11/27 16:58
 * @description
 **/
@Data
@ContentRowHeight(100)
@ColumnWidth(100 / 8)
public class ImageDemo {


    /**
     * 文件
     */
    private File file;
    /**
     * 文件输入流
     */
    private InputStream inputStream;
    /**
     * 如果string类型 必须指定转换器，string默认转换成string
     */
    @ExcelProperty(converter = StringImageConverter.class)
    private String string;
    private byte[] byteArray;
    /**
     * 根据url导出
     *
     * @since 2.1.1
     */
    private URL url;

}
