package com.yanis.front_end_mobile;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface UserService {

    @Multipart
    @POST("events/{eventId}/medias")
    Call<ResponseBody> setProfilePicture(@Header("Authorization") String token, @Path("eventId") String id, @Part MultipartBody.Part image);
}
