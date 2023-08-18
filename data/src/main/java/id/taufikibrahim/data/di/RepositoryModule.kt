package id.taufikibrahim.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.taufikibrahim.data.repository.discover.DiscoverRepositoryImpl
import id.taufikibrahim.data.repository.detail.MovieDetailRepositoryImpl
import id.taufikibrahim.data.repository.nowplaying.NowPlayingRepositoryImpl
import id.taufikibrahim.data.repository.popular.PopularRepositoryImpl
import id.taufikibrahim.data.repository.toprated.TopRatedRepositoryImpl
import id.taufikibrahim.data.repository.upcoming.UpcomingRepositoryImpl
import id.taufikibrahim.domain.moviedetail.MovieDetailRepository
import id.taufikibrahim.domain.moviediscover.DiscoverRepository
import id.taufikibrahim.domain.nowplaying.NowPlayingRepository
import id.taufikibrahim.domain.popular.PopularRepository
import id.taufikibrahim.domain.toprated.TopRatedRepository
import id.taufikibrahim.domain.upcoming.UpcomingRepository
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {
    
    @Singleton
    @Binds
    internal abstract fun bindRepositoryNowPlaying(nowPlayingRepositoryImpl: NowPlayingRepositoryImpl): NowPlayingRepository

    @Singleton
    @Binds
    internal abstract fun bindRepositoryMovieDetail(movieDetailRepositoryImpl: MovieDetailRepositoryImpl): MovieDetailRepository

    @Singleton
    @Binds
    internal abstract fun bindRepositoryDiscover(discoverRepositoryImpl: DiscoverRepositoryImpl): DiscoverRepository

    @Singleton
    @Binds
    internal abstract fun bindRepositoryPopular(popularRepositoryImpl: PopularRepositoryImpl): PopularRepository

    @Singleton
    @Binds
    internal abstract fun bindRepositoryTopRated(topRatedRepositoryImpl: TopRatedRepositoryImpl): TopRatedRepository

    @Singleton
    @Binds
    internal abstract fun bindRepositoryUpcoming(upcomingRepositoryImpl: UpcomingRepositoryImpl): UpcomingRepository
    
    
}