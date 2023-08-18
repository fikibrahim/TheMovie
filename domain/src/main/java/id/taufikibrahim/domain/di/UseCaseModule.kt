package id.taufikibrahim.domain.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.taufikibrahim.domain.moviedetail.MovieDetailUseCase
import id.taufikibrahim.domain.moviedetail.MovieDetailUseCaseImpl
import id.taufikibrahim.domain.moviediscover.DiscoverUseCase
import id.taufikibrahim.domain.moviediscover.DiscoverUseCaseImpl
import id.taufikibrahim.domain.nowplaying.NowPlayingUseCase
import id.taufikibrahim.domain.nowplaying.NowPlayingUseCaseImpl
import id.taufikibrahim.domain.popular.PopularUseCase
import id.taufikibrahim.domain.popular.PopularUseCaseImpl
import id.taufikibrahim.domain.toprated.TopRatedUseCaseImpl
import id.taufikibrahim.domain.toprated.TopRatedUseCase
import id.taufikibrahim.domain.upcoming.UpcomingUseCase
import id.taufikibrahim.domain.upcoming.UpcomingUseCaseImpl
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class UseCaseModule {

    @Singleton
    @Binds
    internal abstract fun bindUseCaseNowPlaying(nowPlayingUseCaseImpl: NowPlayingUseCaseImpl): NowPlayingUseCase

    @Singleton
    @Binds
    internal abstract fun bindUseCaseMovieDetail(movieDetailUseCaseImpl: MovieDetailUseCaseImpl): MovieDetailUseCase

    @Singleton
    @Binds
    internal abstract fun bindUseCaseDiscover(discoverUseCaseImpl: DiscoverUseCaseImpl): DiscoverUseCase

    @Singleton
    @Binds
    internal abstract fun bindUseCasePopular(popularUseCaseImpl: PopularUseCaseImpl): PopularUseCase

    @Singleton
    @Binds
    internal abstract fun bindUseCaseTopRated(topRatedUseCaseImpl: TopRatedUseCaseImpl): TopRatedUseCase

    @Singleton
    @Binds
    internal abstract fun bindUseCaseUpcoming(upcomingUseCaseImpl: UpcomingUseCaseImpl): UpcomingUseCase
    
}