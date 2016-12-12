package com.quickstart.dagger.modules

import com.quickstart.api.GitHubService
import com.quickstart.api.repos.ReposAPIService
import com.quickstart.api.repos.ReposRequestManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created at 12.12.16 13:22
 * @author Alexey_Ivanov
 */

@Module
class RequestManagerModules {

    @Provides
    @Singleton
    fun provideReposRequestManager(reposService : ReposAPIService) : ReposRequestManager {
        return ReposRequestManager(reposService)
    }

    @Provides
    @Singleton
    fun provideReposAPIService (githubService : GitHubService) : ReposAPIService {
        return ReposAPIService (githubService)
    }
}
