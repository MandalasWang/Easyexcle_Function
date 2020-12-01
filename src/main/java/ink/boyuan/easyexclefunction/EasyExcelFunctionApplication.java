package ink.boyuan.easyexclefunction;

import ink.boyuan.easyexclefunction.util.ReportExcelUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * @author wyy
 * @date 2020/11/20
 * @version 2.0.0
 */
@SpringBootApplication
public class EasyExcelFunctionApplication {

    public static void main(String[] args) {
        SpringApplication.run(EasyExcelFunctionApplication.class, args);
    }


    @Bean
    public ReportExcelUtil getReportExcelUtil(){
        return new ReportExcelUtil();
    }
}
