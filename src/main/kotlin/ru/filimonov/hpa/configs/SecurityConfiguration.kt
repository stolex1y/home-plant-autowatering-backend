package ru.filimonov.hpa.configs

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseToken
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthenticationToken
import org.springframework.security.web.SecurityFilterChain
import org.springframework.stereotype.Component
import org.springframework.util.Assert
import ru.filimonov.hpa.domain.model.User

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true, prePostEnabled = true)
class SecurityConfiguration {
    @Bean
    fun filterChain(
        http: HttpSecurity,
        authManager: AuthenticationManager
    ): SecurityFilterChain {
        http.invoke {
            cors {
                disable()
            }
            csrf {
                disable()
            }
            logout {
                disable()
            }
            sessionManagement {
                sessionCreationPolicy = SessionCreationPolicy.STATELESS
            }
            authorizeHttpRequests {
                authorize("/reauth/**", permitAll)
                authorize("/error/**", permitAll)
                authorize(anyRequest, authenticated)
            }
            oauth2ResourceServer {
                jwt {
                    authenticationManager = authManager
                }
            }
        }
        return http.build()
    }

    class FirebaseTokenAdapter(
        private val firebaseToken: FirebaseToken
    ) : AbstractAuthenticationToken(emptyList()) {
        private val user = User(firebaseToken.uid)

        override fun getCredentials(): Any {
            return firebaseToken
        }

        override fun getPrincipal(): Any {
            return user
        }

        override fun isAuthenticated(): Boolean {
            return true
        }
    }

    @Component
    class FirebaseAuthenticationManager @Autowired constructor(
        private val firebaseAuth: FirebaseAuth
    ) : AuthenticationManager {
        override fun authenticate(authentication: Authentication): Authentication {
            Assert.isInstanceOf(
                BearerTokenAuthenticationToken::class.java,
                authentication,
                "Authentication must be of type BearerTokenAuthenticationToken"
            )
            authentication as BearerTokenAuthenticationToken
            try {
                return FirebaseTokenAdapter(firebaseAuth.verifyIdToken(authentication.token))
            } catch (ex: FirebaseAuthException) {
                throw InvalidBearerTokenException("Invalid bearer token")
            }
        }
    }
}
