package com.ilya.advicesapp.advices.data.cloud

import com.ilya.advicesapp.advices.data.FetchLanguage
import com.ilya.advicesapp.advices.domain.AdvicesDomain
import java.net.UnknownServiceException
import javax.inject.Inject

interface CloudDataSource: FetchLanguage<AdvicesDomain> {

    suspend fun randomAdvice(): AdvicesDomain

    class Base @Inject constructor(
        private val languageService: AdviceService,
        private val slipToDomainMapper: SlipRandom.Mapper<AdvicesDomain>,
        private val adviceCloudToDomainMapper: AdviceCloud.Mapper<AdvicesDomain>
    ) : CloudDataSource {

        override suspend fun randomAdvice(): AdvicesDomain {
            val result = languageService.randomAdvice()
            return if (result.isSuccessful) {
                if (result.body() != null) {
                    result.body()?.map(slipToDomainMapper)
                        ?: AdvicesDomain(advices = "", date = "", searchTerm = "")
                } else AdvicesDomain(advices = "", date = "", searchTerm = "")
            } else throw UnknownServiceException()
        }

        override suspend fun advices(name: String): AdvicesDomain {
            val result = languageService.findAdvices(name)
            return if (result.isSuccessful) {
                if (result.body() != null) {
                    if (result.body()?.total_results == null)
                        throw NoSuchElementException()
                    else {
                        result.body()?.map(adviceCloudToDomainMapper)
                            ?: AdvicesDomain(advices = "", date = "", searchTerm = "")
                    }
                } else AdvicesDomain(advices = "", date = "", searchTerm = "")
            } else throw UnknownServiceException()
        }
    }
}
