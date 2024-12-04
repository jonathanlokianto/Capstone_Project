import com.dicoding.capstone.response.ArticleResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("articles")
    suspend fun getArticles(): Response<ArticleResponse>
}
