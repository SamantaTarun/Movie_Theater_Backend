package com.demo.book.utils

import com.nimbusds.jwt.JWTClaimsSet
import io.micronaut.context.annotation.Primary
import io.micronaut.context.annotation.Replaces
import io.micronaut.http.HttpRequest
import io.micronaut.security.authentication.Authentication
import io.micronaut.security.filters.AuthenticationFetcher
import io.micronaut.security.token.TokenAuthenticationFetcher
import io.micronaut.security.token.jwt.validator.AuthenticationJWTClaimsSetAdapter
import org.reactivestreams.Publisher
import reactor.core.publisher.Mono
import javax.inject.Singleton

@Singleton
@Primary
@Replaces(bean = TokenAuthenticationFetcher::class)
class TestAuthenticationFetcher : AuthenticationFetcher {

    override fun fetchAuthentication(request: HttpRequest<*>?): Publisher<Authentication> {
        val claims = mapOf(
            "adminRights" to listOf("read", "update", "write"),
            "sub" to "operator@bms.com",
            "firstName" to "operator",
            "lastName" to "bms"
        )
        val jwtClaimSet = JWTClaimsSet.parse(claims)
        return Mono.just(AuthenticationJWTClaimsSetAdapter(jwtClaimSet))
    }
}
