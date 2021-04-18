package cn.com.springcloud.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(value = "cn.com.springcloud.dao")
public class MybatisConfig {
}
