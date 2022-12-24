package com.ilya.advicesapp.advices.data.cloud

import com.ilya.advicesapp.advices.domain.AdvicesDomain
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Response
import java.net.UnknownServiceException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by HP on 05.12.2022.
 **/
class CloudDataSourceTest {

    lateinit var service: AdviceServiceTest
    lateinit var cloudDataSource: CloudDataSource

    @Before
    fun setup(){
        service = AdviceServiceTest()
        cloudDataSource = CloudDataSource.Base(service,
            SlipRandom.Mapper.ToAdviceDomainMapper(),
            AdviceCloud.Mapper.ToDomainMapper(Slip.Mapper.Advice())
        )
    }

    @Test
    fun `test advice sucess`() = runBlocking {
         val expected =  AdvicesDomain(
             advices = "- aaa\n",
             date = SimpleDateFormat("dd.MM.yyyy").format(Calendar.getInstance().time),
             searchTerm = "a")
        service.setResultForFind(Response.success(AdviceCloud(
            query = "a",
            slips = listOf(Slip(advice = "aaa", date = "2", id = 0)),
            total_results = "1")))

         val actual = cloudDataSource.advices("a")
         assertEquals(expected, actual)
    }

    @Test
    fun `test find advice error`(): Unit = runBlocking{
        val expected = UnknownServiceException()
        service.setError(expected)
       runCatching {
           cloudDataSource.advices("")
       }.onFailure {actual->
          assertEquals(expected::class,actual::class)
      }
    }

    @Test
    fun `test random advice`() = runBlocking {
        val expected =  AdvicesDomain(
            advices = "- aaa aaa\n",
            date = SimpleDateFormat("dd.MM.yyyy").format(Calendar.getInstance().time),
            searchTerm = "aaa")
        service.setResultForRandom(Response.success(
            SlipRandom(slip = SlipX(advice = "aaa aaa\n", id = 0))))
        val actual = cloudDataSource.randomAdvice()
        assertEquals(expected, actual)
    }


    class AdviceServiceTest: AdviceService {
        private var error:Throwable? = null
       private var responseToFind: Response<AdviceCloud> = Response.success(null)
       private var responseForRandom: Response<SlipRandom> = Response.success(null)

        fun setResultForFind(r:Response<AdviceCloud>){
            responseToFind = r
        }

        fun setResultForRandom(r:Response<SlipRandom>){
            responseForRandom = r
        }

        fun setError(error: Throwable){
            this.error = error
        }

        override suspend fun findAdvices(name: String): Response<AdviceCloud> {
            return if(error==null)
                responseToFind
            else throw error?: Exception()
        }

        override suspend fun randomAdvice(): Response<SlipRandom> {
            return responseForRandom
        }

    }

    class TestCloudToDomainMapper(private val date: String): AdviceCloud.Mapper<AdvicesDomain>{
        override fun map(query: String, slips: List<Slip>, total_results: String): AdvicesDomain {
            var advices =""
            slips.forEach {advices+="- ${""/*it.advice*/}\n"} //todo
            return AdvicesDomain(
                advices = advices,
                date = date,
                searchTerm = query
            )
        }

    }
}