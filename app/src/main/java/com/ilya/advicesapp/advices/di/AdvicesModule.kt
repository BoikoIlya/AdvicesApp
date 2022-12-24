package com.ilya.advicesapp.advices.di

import android.content.Context
import androidx.room.Room
import com.ilya.advicesapp.advicedetails.data.AdviceDetails

import com.ilya.advicesapp.advices.data.AdviceRepository
import com.ilya.advicesapp.advices.data.HandleDataRequest
import com.ilya.advicesapp.advices.data.HandleDomainError
import com.ilya.advicesapp.advices.data.cache.*
import com.ilya.advicesapp.advices.data.cloud.*
import com.ilya.advicesapp.advices.domain.*
import com.ilya.advicesapp.advices.presentation.*
import com.ilya.advicesapp.main.presentation.NavigationCommunication
import dagger.Binds
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Created by HP on 05.12.2022.
 **/
@Module(includes = [DataModule::class])
class AdvicesModule(private val context: Context) {

    @Provides
    fun provideLanguageService(): AdviceService{
        return Retrofit.Builder()
            .baseUrl("https://api.adviceslip.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AdviceService::class.java)
    }

    @Provides
    fun provideLanguageDB(): AdviceDataBase{
        return Room.databaseBuilder(
            context,
            AdviceDataBase::class.java,
            "language_db"
        ).build()
    }

    @Provides
    fun provideDao(db: AdviceDataBase): AdviceDao{
        return db.getDao()
    }

    @Provides
    fun provideManagerResource(): ManagerResource{
        return ManagerResource.Base(context)
    }

    @Singleton
    @Provides
    fun provideAdviceDetails(): AdviceDetails.Mutable<AdvicesUi>{
        return AdviceDetails.Base(AdvicesUi(advices = "", date = "", searchTerm = ""))
    }

}

@Module
interface DataModule{

    @Singleton
    @Binds
    fun bindAdviceNavigationCommunicationMutable(
        obj: NavigationCommunication.Base):NavigationCommunication.Mutable

    @Binds
    fun bindSlipToStringMapper(obj: Slip.Mapper.Advice): Slip.Mapper<String>

    @Binds
    fun bindSlipToDomainMapper(obj: SlipRandom.Mapper.ToAdviceDomainMapper): SlipRandom.Mapper<AdvicesDomain>

    @Binds
    fun bindAdviceResultMapper(obj: AdviceResultMapper):AdviceResult.Mapper<Unit>

    @Binds
    fun bindCloudToDomainMapper(obj: AdviceCloud.Mapper.ToDomainMapper): AdviceCloud.Mapper<AdvicesDomain>

    @Binds
    fun bindHandleErrorException(obj: HandleDomainError): HandleError<Exception>

    @Binds
    fun bindHandleDataRequest(obj: HandleDataRequest.Base): HandleDataRequest

    @Binds
    fun bindHandleLanguageResult(obj: HandleAdviceResult.Base): HandleAdviceResult

    @Binds
    fun bindAdviceNavigationCommunicationUpdate(
        obj: NavigationCommunication.Mutable): NavigationCommunication.Update

    @Binds
    fun bindAdviceNavigationCommunicationCollect(
        obj: NavigationCommunication.Mutable): NavigationCommunication.Collect

    @Binds
    fun bindDomainToUiMapper(obj: AdvicesDomain.Mapper.ToAdviceUi): AdvicesDomain.Mapper<AdvicesUi>

    @Binds
    fun bindDispatchersList(obj: DispatchersList.Base): DispatchersList

    @Singleton
    @Binds
    fun bindAdviceListCommunication(obj: AdviceListCommunication.Base): AdviceListCommunication

    @Singleton
    @Binds
    fun bindAdviceStateCommunication(obj: AdviceStateCommunication.Base): AdviceStateCommunication

    @Singleton
    @Binds
    fun bindProgressCommunication(obj: ProgressCommunication.Base): ProgressCommunication

    @Singleton
    @Binds
    fun bindAdviceCommunication(obj: AdvicesCommunication.Base): AdvicesCommunication

    @Binds
    fun bindAdviceInteractor(obj: AdviceInteractor.Base): AdviceInteractor

    @Binds
    fun bindAdviceDetailsRead(obj: AdviceDetails.Mutable<AdvicesUi>): AdviceDetails.Read<AdvicesUi>

    @Binds
    fun bindAdviceDetailsSave(obj: AdviceDetails.Mutable<AdvicesUi>): AdviceDetails.Save<AdvicesUi>

    @Binds
    fun bindHandleRequest(obj: HandleRequest.Base): HandleRequest

    @Binds
    fun bindHandleErrorString(obj: HandleError.Base): HandleError<String>

    @Binds
    fun bindDomainToCacheMapper(
        obj: AdvicesDomain.Mapper.ToCacheMapper
    ): AdvicesDomain.Mapper<AdvicesCache>

    @Binds
    fun bindCloudDataSource(obj: CloudDataSource.Base): CloudDataSource

    @Binds
    fun bindCacheDataSource(obj: CacheDataSource.Base): CacheDataSource

    @Binds
    fun bindAdviceRepository(obj: AdviceRepository.Base): AdviceRepository
}