package sunsky.config;

import org.apache.commons.dbcp.BasicDataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@PropertySource(value = "classpath:jdbc.properties")
@EnableTransactionManagement
@MapperScan(basePackages = "sunsky.dao.slave",sqlSessionFactoryRef = "sqlSlaveSessionFactoryBean")
public class DataSourceSlaveConfig {
    //property for local database
    @Value("${ubuntu.jdbc.url}")
    private String jdbcUrl;
    @Value("${ubuntu.jdbc.driverClassName}")
    private String jdbcDriverClassName;
    @Value("${ubuntu.jdbc.username}")
    private String jdbcUserName;
    @Value("${ubuntu.jdbc.password}")
    private String jdbcPassword;


    @Bean(name = "slaveDataSource",destroyMethod = "close")
    public DataSource getDataSouce(){
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(jdbcDriverClassName);
        dataSource.setUrl(jdbcUrl);
        dataSource.setUsername(jdbcUserName);
        dataSource.setPassword(jdbcPassword);
        return dataSource;
    }

    //不能用@ConditionalOnMissingBean，否则会报错
    @Bean(name = "sqlSlaveSessionFactoryBean")
    public SqlSessionFactoryBean getSqlSessionFactoryBean(@Qualifier("slaveDataSource") DataSource dataSource){
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
        Resource mybatisConfigXml = resourcePatternResolver.getResource("classpath:mybatis/slave/mybaits-config-slave.xml");
        sqlSessionFactoryBean.setConfigLocation(mybatisConfigXml);
        return sqlSessionFactoryBean;
    }
}
