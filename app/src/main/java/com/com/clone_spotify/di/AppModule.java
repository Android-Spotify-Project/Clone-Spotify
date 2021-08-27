package com.com.clone_spotify.di;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.com.clone_spotify.R;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
@InstallIn(SingletonComponent.class) //dagger hilt에게 어떤 field가 필요한지 알려줘야한다 어노테이션으로 ( 어플리케이션범위)
public class AppModule {

    @Singleton
    @Provides
    public static RequestOptions provideRequestOptions() {
        return RequestOptions
                .placeholderOf(R.drawable.ic_image)
                .error(R.drawable.ic_image)
                .diskCacheStrategy(DiskCacheStrategy.DATA);
    }

    @Singleton
    @Provides
    public static RequestManager provideGlideInstance(@ApplicationContext Context context, RequestOptions requestOptions) {
        return Glide.with(context)
                .setDefaultRequestOptions(requestOptions);
    }

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
