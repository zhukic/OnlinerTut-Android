package rus.tutby.ui.adapters.provideradapters

import android.support.v4.app.FragmentManager

class OnlinerPagerAdapter(fm: FragmentManager, categories: Array<String>,
                          urls: Array<String>) : ProviderPagerAdapter(fm, categories, urls) {

    override fun getCount(): Int = 5
}
