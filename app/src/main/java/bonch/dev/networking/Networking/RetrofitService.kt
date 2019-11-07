package bonch.dev.networking.Networking

import bonch.dev.Post
import bonch.dev.networking.Album
import bonch.dev.networking.Photos

import bonch.dev.networking.User
import retrofit2.Call
import retrofit2.Response
import okhttp3.ResponseBody
import retrofit2.http.*


interface RetrofitService{
    @GET("/posts")
    suspend fun getPosts():Response<List<Post>>

    @GET("/users")
    suspend fun getUsers():Response<List<User>>

    @GET("/albums")
    suspend fun getAlbum(@Query("userId") userId:Int=1):Response<List<Album>>

    @DELETE("/albums/{id}")
    suspend fun delAlbum(@Path("id") id: Int): Response<*>

    @GET("/photos")
    suspend fun TransferToPhotosActivity():Response<List<Photos>>

    @FormUrlEncoded
    @POST("/posts")
    suspend fun TransferToFragment(@Field ("title") title:String,
                                   @Field ("body") body:String

    ) : Response<Post>

}

