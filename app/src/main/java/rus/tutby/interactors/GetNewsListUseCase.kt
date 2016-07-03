package rus.tutby.interactors

import rus.tutby.entity.News
import rus.tutby.repository.NewsRepository
import rx.Subscriber
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import rx.subscriptions.Subscriptions

/**
 * Created by RUS on 15.06.2016.
 */
class GetNewsListUseCase {

    private var subscription: Subscription
    private var newsRepository: NewsRepository

    init {
        subscription = Subscriptions.empty()
        newsRepository = NewsRepository()
    }

    fun downloadNews(url: String, subscriber: Subscriber<List<News>>) {
        this.subscription = this.newsRepository.getAllNews(url)
                .toList()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber)

    }

    fun unsubscribe() {
        this.subscription.unsubscribe()
    }
}
