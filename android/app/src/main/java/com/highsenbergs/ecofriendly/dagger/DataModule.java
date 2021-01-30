package com.highsenbergs.ecofriendly.dagger;

import android.app.Application;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.highsenbergs.ecofriendly.utils.Constants.mBaseUrl;

@Module
public class DataModule {

    private final Application application;

    public DataModule(Application application){this.application = application;}


    @Singleton
    @Provides
    Application provideApplication(){
        return this.application;
    }

    @Provides
    @Singleton
    Cache provideHttpCache(Application application) {
        int cacheSize = 10 * 1024 * 1024;
        Cache cache = new Cache(application.getCacheDir(), cacheSize);
        return cache;
    }

    @Provides
    @Singleton
    Gson provideGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setFieldNamingPolicy( FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        return gsonBuilder.create();
    }

    @Provides
    @Singleton
    OkHttpClient provideOkhttpClient(Cache cache) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel( HttpLoggingInterceptor.Level.BODY );

        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.addInterceptor( interceptor );
        client.cache(cache);
        client.readTimeout( 60, TimeUnit.SECONDS );
        client.connectTimeout( 60, TimeUnit.SECONDS );
        return client.build();
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(Gson gson, OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .addConverterFactory( GsonConverterFactory.create(gson))
                .baseUrl(mBaseUrl)
                .client(okHttpClient)
                .build();
    }
}
