package com.quickstart.dagger.modules

import com.quickstart.ApiComponent
import com.quickstart.api.GitHubService
import com.quickstart.api.repos.ReposAPIService
import com.quickstart.api.repos.ReposLocalService
import com.quickstart.api.repos.ReposRequestManager
import com.quickstart.dagger.scopes.DomainScope
import dagger.Component
import dagger.Module
import dagger.Provides

/**
 * Created at 12.12.16 13:22
 * @author Alexey_Ivanov
 */

@Module
class RequestManagerModules {

    @Provides
    @DomainScope
    fun provideReposRequestManager(reposRemote: ReposAPIService, reposLocal: ReposLocalService): ReposRequestManager {
        return ReposRequestManager(reposRemote, reposLocal)
    }

    @Provides
    @DomainScope
    fun provideReposAPIService(githubService: GitHubService): ReposAPIService {
        return ReposAPIService(githubService)
    }

    @Provides
    @DomainScope
    fun provideReposLocalService(): ReposLocalService {
        return ReposLocalService()
    }
}

@DomainScope
@Component(dependencies = arrayOf(ApiComponent::class), modules = arrayOf(RequestManagerModules::class))
interface RequestManagerComponent {
    fun getReposRequestManager(): ReposRequestManager
}

