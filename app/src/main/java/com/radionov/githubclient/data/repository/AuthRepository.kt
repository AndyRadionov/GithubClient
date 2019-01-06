package com.radionov.githubclient.data.repository

import com.radionov.githubclient.BuildConfig.CLIENT_ID
import com.radionov.githubclient.BuildConfig.CLIENT_SECRET
import com.radionov.githubclient.data.datasource.local.Prefs
import com.radionov.githubclient.data.datasource.network.GithubAuthApi
import io.reactivex.Single

/**
 * @author Andrey Radionov
 */
class AuthRepository(
    private val authApi: GithubAuthApi,
    private val prefs: Prefs
) {

    fun getLocalToken() = prefs.getToken()

    fun removeLocalToken() {
        prefs.removeToken()
    }

    fun getRemoteToken(accessCode: String): Single<String> =
        authApi.getToken(CLIENT_ID, CLIENT_SECRET, accessCode)
            .doOnSuccess { token -> prefs.setToken(token) }
}
