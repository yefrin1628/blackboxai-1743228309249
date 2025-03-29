package com.example.paymentsimulator.di

import android.content.Context
import com.example.paymentsimulator.data.AppDatabase
import com.example.paymentsimulator.data.repository.PaymentRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getDatabase(context)
    }

    @Provides
    @Singleton
    fun provideRepository(database: AppDatabase): PaymentRepository {
        return PaymentRepository(database)
    }
}