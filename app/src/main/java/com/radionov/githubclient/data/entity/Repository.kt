package com.radionov.githubclient.data.entity

import com.google.gson.annotations.SerializedName

/**
 * @author Andrey Radionov
 */
data class Repository(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("full_name") val fullName: String,
    @SerializedName("description") val description: String,
    @SerializedName("forks_count") val forksCount: Int,
    @SerializedName("stargazers_count") val starsCount: Int,
    @SerializedName("watchers_count") val watchersCount: Int,
    @SerializedName("owner") val owner: Owner,
    @SerializedName("license") val license: License)

data class Owner(
    @SerializedName("login") val login: String,
    @SerializedName("avatar_url") val avatarUrl: String)

data class License(
    @SerializedName("key") val key: String,
    @SerializedName("name") val name: String)
