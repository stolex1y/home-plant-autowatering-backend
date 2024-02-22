package ru.filimonov.hpa.config

import org.apache.commons.logging.LogFactory
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.sql.DataSource

@Configuration(proxyBeanMethods = false)
class DataSourceConfiguration {

    private val logger = LogFactory.getLog(javaClass)

    @Bean
    @ConfigurationProperties(prefix = "app.datasource")
    fun dataSource(): DataSource {
        logger.debug("Get data source config")
        return DataSourceBuilder.create().build()
    }

}
