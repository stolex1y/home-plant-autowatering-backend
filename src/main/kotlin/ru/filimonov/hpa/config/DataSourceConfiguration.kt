package ru.filimonov.hpa.config

import org.apache.commons.logging.LogFactory
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import javax.sql.DataSource

@Configuration(proxyBeanMethods = false)
@EnableJpaAuditing
class DataSourceConfiguration {

    private val logger = LogFactory.getLog(javaClass)

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    fun dataSource(): DataSource {
        logger.debug("Get data source config")
        return DataSourceBuilder.create().build()
    }

}
