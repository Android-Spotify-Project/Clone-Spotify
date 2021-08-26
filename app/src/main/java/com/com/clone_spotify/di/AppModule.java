package com.com.clone_spotify.di;

import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ActivityComponent;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
@InstallIn(ActivityComponent.class)
public class AppModule {

    //hilt 인스턴스 삽입
//    @Provides
//    public static AnalyticsService provideAnalyticsService(
//            // Potential dependencies of this type
//    ) {
//        return new Retrofit.Builder()
//                .baseUrl("https://example.com") //서버를 가지고 시작해야하나...?
//                .build()
//                .create(AnalyticsService.class);

    //retrofit 사용 userService 에서 -> 참고하시오
//        Retrofit retrofit = new Retrofit.Builder()
//                .addConverterFactory(GsonConverterFactory.create())
//                .baseUrl("http://192.168.0.9:8080")
//                .build();
//
//        UserService service = retrofit.create(UserService.class);

//    }

}
