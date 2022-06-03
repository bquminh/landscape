package com.example.landscapedemo.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Call
import retrofit2.http.*

const val BASE_URL: String = "https://dev-task-api.eztek.io/api/"
const val bearToken: String = "eyJhbGciOiJSUzI1NiIsImtpZCI6IkNENkExNDdCNzk3MjQ0MkE4OTIzRkQ1QTkwNTM3RDEyIiwidHlwIjoiYXQrand0In0.eyJuYmYiOjE2NTQyNDgyMjMsImV4cCI6MTY1NDI1MTgyMywiaXNzIjoiaHR0cHM6Ly9kZXYtc3NvLmV6dGVrLmlvIiwiYXVkIjoiVGFza01hbmFnZW1lbnQiLCJjbGllbnRfaWQiOiJlenRla190YXNrX21hbmFnZV9hcGkiLCJzdWIiOiJiYWU5OWVlOS01ODZhLTQ3NDgtYWQ0Zi0wYmFlMzhhN2Q2MTYiLCJhdXRoX3RpbWUiOjE2NTQxMzM2MDQsImlkcCI6ImxvY2FsIiwic2lkIjoiREI3ODk3RjAwMEEyMzE2NkFFRUFFNzBERTQ4MjM5NDUiLCJpYXQiOjE2NTQyNDgyMjMsInNjb3BlIjpbIlRhc2tNYW5hZ2VtZW50Il0sImFtciI6WyJwd2QiXX0.mEwdTfXLAyF_Sy6l-Y-2UTIyQXvhT6XpXSCVRx_qQbUr3km_dWgUyHhEBEjPqowQyRzaI792e6LZDvnP5Zd7qdDEl9sS7oLR_69bJuYvF9QwDUFRMCXjpgMszgNSMl9GK3W0wr2DyY9N2bWRfpsY5T6R3_3xyRWr99Z7IN3laPakJ7HY8XbXprNNXm-VXIb6d5Dbhok4_ExNv0C9Hl-yoZxKRn7GPJtJWirVuom3B77WemDVPLL7E5yw5-FfxLBjM6Edzxobg9ctU528oKD_ReC9v2qh13h0QJIr46bA6nnpdhUlHueY3MATBMev6xVZrprCUg0eGxCFfibF0G33Iw"

interface ApiService {

//    @POST("account/login")
//    fun login(@Body requestBody: RequestBody): Call<ResponseUpdateProject>

    @GET("Project/GetDashboardProjectAsync")
    @Headers("Authorization: Bearer $bearToken")
    fun getDashboardProjectAsync(): Call<ResponseProjectList>

    @POST("Project/AddProject")
    @Headers("Authorization: Bearer $bearToken")
    fun addProject(@Body requestProject: RequestUpdateProject): Call<ResponseProjectDetail>

    @GET("Project/GetProjectView")
    @Headers("Authorization: Bearer $bearToken")
    fun getProjectDetail(@Query("proId") proId: Int): Call<ResponseProjectDetail>

    @PUT("Project/UpdateProject")
    @Headers("Authorization: Bearer $bearToken")
    fun updateProject(@Body requestBody: RequestUpdateProject) : Call<ResponseUpdateProject>

    @GET("Stage/GetAllStages")
    @Headers("Authorization: Bearer $bearToken")
    fun getAllStages(@Query("page") page: Int,
                     @Query("pageSize") pageSize: Int,
                     @Query("proId") proId: Int): Call<ResponseStateList>

    @POST("Stage/AddStage")
    @Headers("Authorization: Bearer $bearToken")
    fun addState(@Query("proId") proId: Int, @Body requestAdd: RequestAddState): Call<ResponseAdd>

    @PUT("Stage/UpdateStage")
    @Headers("Authorization: Bearer $bearToken")
    fun updateState(@Body requestUpdate: State): Call<ResponseUpdateState>

    @GET("Grouptask/GetAllGrouptasks")
    @Headers("Authorization: Bearer $bearToken")
    fun getAllGrouptasks(@Query("page") page: Int,
                         @Query("pageSize") pageSize : Int,
                         @Query("staId") staId: Int): Call<ResponseGrouptaskList>

    @POST("Grouptask/AddGrouptask")
    @Headers("Authorization: Bearer $bearToken")
    fun addGrouptask(@Query("staId") staId: Int,
                     @Body requestAdd: RequestAddGrouptask): Call<ResponseAdd>

    @PUT("Grouptask/UpdateGrouptask")
    @Headers("Authorization: Bearer $bearToken")
    fun updateGrouptask(@Body requestUpdate: Grouptask): Call<ResponseUpdateGrouptask>

    @GET("TaskInfor/GetAllTaskinfors")
    @Headers("Authorization: Bearer $bearToken")
    fun getAllTasks(@Query("groId") groId:Int): Call<ResponseTaskList>

    @POST("TaskInfor/AddTaskInfor")
    @Headers("Authorization: Bearer $bearToken")
    fun addTask(@Body requestAdd: RequestAddTask): Call<ResponseAdd>

    @PUT("TaskInfor/UpdateTaskInfor")
    @Headers("Authorization: Bearer $bearToken")
    fun updateTask(@Body requestUpdate: Task): Call<ResponseUpdateTask>

    @GET("Note/GetAllNotes")
    @Headers("Authorization: Bearer $bearToken")
    fun getAllNote(@Query("proId") proId: Int): Call<ResponseNoteList>

    @POST("Note/AddNote")
    @Headers("Authorization: Bearer $bearToken")
    fun addNote(@Body requestAdd: RequestAddNote) : Call<ResponseAdd>

    @PUT("Note/UpdateNote")
    @Headers("Authorization: Bearer $bearToken")
    fun updateNote(@Body requestUpdate : RequestUpdateNote): Call<ResponseUpdateNote>

    @GET("TaskInfor/TimelineStage")
    @Headers("Authorization: Bearer $bearToken")
    fun getTimeline(@Query("staId") staId: Int): Call<ResponseTimeline>

    @GET("UserInfor/GetAllUsersByProjectId")
    @Headers("Authorization: Bearer $bearToken")
    fun getAllMember(@Query("proId") proId: Int): Call<ResponseMemberList>

    companion object{
        fun create(): ApiService{
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(ApiService::class.java)
        }
    }

}