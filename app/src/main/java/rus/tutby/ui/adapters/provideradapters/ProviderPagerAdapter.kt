package rus.tutby.ui.adapters.provideradapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import rus.tutby.ui.FeedFragment;

/**
 * Created by RUS on 21.02.2016.
 */

abstract class ProviderPagerAdapter(fm: FragmentManager, val categories: Array<String>,
                                    val urls: Array<String> ) : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        val feedFragment = FeedFragment()
        val args = Bundle()
        args.putString("URL", urls[position])
        args.putString("CATEGORY", categories[position])
        feedFragment.arguments = args

        return feedFragment
    }

    override fun getPageTitle(position: Int): CharSequence = categories[position]

}
