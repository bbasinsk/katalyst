package io.github.bbasinsk.http.gradle

import io.github.bbasinsk.http.openapi.Contact
import io.github.bbasinsk.http.openapi.Info
import io.github.bbasinsk.http.openapi.License
import java.io.Serializable

data class InfoConfig(
    val title: String,
    val version: String,
    val description: String? = null,
    val termsOfService: String? = null,
    val contact: ContactConfig? = null,
    val license: LicenseConfig? = null
) : Serializable

data class ContactConfig(
    val name: String? = null,
    val url: String? = null,
    val email: String? = null
) : Serializable

data class LicenseConfig(
    val name: String,
    val url: String? = null
) : Serializable

class InfoConfigBuilder {
    var title: String = ""
    var version: String = ""
    var description: String? = null
    var termsOfService: String? = null
    private var contactConfig: ContactConfig? = null
    private var licenseConfig: LicenseConfig? = null

    fun contact(configure: ContactConfigBuilder.() -> Unit) {
        val builder = ContactConfigBuilder()
        builder.configure()
        contactConfig = builder.build()
    }

    fun license(configure: LicenseConfigBuilder.() -> Unit) {
        val builder = LicenseConfigBuilder()
        builder.configure()
        licenseConfig = builder.build()
    }

    internal fun build(): InfoConfig {
        require(title.isNotEmpty()) { "Info title is required" }
        require(version.isNotEmpty()) { "Info version is required" }
        return InfoConfig(
            title = title,
            version = version,
            description = description,
            termsOfService = termsOfService,
            contact = contactConfig,
            license = licenseConfig
        )
    }
}

class ContactConfigBuilder {
    var name: String? = null
    var url: String? = null
    var email: String? = null

    internal fun build() = ContactConfig(
        name = name,
        url = url,
        email = email
    )
}

class LicenseConfigBuilder {
    var name: String = ""
    var url: String? = null

    internal fun build(): LicenseConfig {
        require(name.isNotEmpty()) { "License name is required" }
        return LicenseConfig(name = name, url = url)
    }
}

internal fun InfoConfig.toLibraryInfo() = Info(
    title = title,
    version = version,
    description = description,
    termsOfService = termsOfService,
    contact = contact?.let {
        Contact(
            name = it.name,
            url = it.url,
            email = it.email
        )
    },
    license = license?.let {
        License(
            name = it.name,
            url = it.url
        )
    }
)
