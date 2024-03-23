package ru.filimonov.hpa.config

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.auth.FirebaseAuth
import jakarta.validation.constraints.NotBlank
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.context.properties.bind.ConstructorBinding
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.io.ByteArrayInputStream
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi


private const val FIREBASE_PROPS = "app.firebase"

@Configuration
@ConfigurationPropertiesScan
class FirebaseConfiguration {

    @ConfigurationProperties(prefix = FIREBASE_PROPS)
    class FirebaseProperties @ConstructorBinding constructor(
        @NotBlank
        val privateKey: String
    )

    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    @OptIn(ExperimentalEncodingApi::class)
    @Bean
    fun firebaseApp(firebaseProperties: FirebaseProperties): FirebaseApp {
        val decodedPrivateKeyStream = ByteArrayInputStream(Base64.decode(firebaseProperties.privateKey))
        val options = FirebaseOptions.builder()
            .setCredentials(GoogleCredentials.fromStream(decodedPrivateKeyStream))
            .build()
        if (FirebaseApp.getApps().isEmpty()) {
            FirebaseApp.initializeApp(options)
        }
        return FirebaseApp.getInstance()
    }

    @Bean
    fun firebaseAuth(firebaseApp: FirebaseApp): FirebaseAuth {
        return FirebaseAuth.getInstance(firebaseApp)
    }
}
