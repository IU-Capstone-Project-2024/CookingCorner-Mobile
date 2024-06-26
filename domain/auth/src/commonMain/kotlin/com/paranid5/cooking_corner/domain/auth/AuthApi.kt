package com.paranid5.cooking_corner.domain.auth

import com.paranid5.cooking_corner.core.common.ApiResultWithCode
import com.paranid5.cooking_corner.domain.auth.dto.LoginResponse

interface AuthApi {
    suspend fun register(
        username: String,
        password: String,
    ): ApiResultWithCode<Unit>

    suspend fun login(
        username: String,
        password: String,
    ): ApiResultWithCode<LoginResponse>

    suspend fun verifyToken(accessToken: String): ApiResultWithCode<Unit>
}