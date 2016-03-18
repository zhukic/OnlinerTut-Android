package rus.tutby.adapters.provideradapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import rus.tutby.mvp.feed.view.FeedFragment;

/**
 * Created by RUS on 21.02.2016.
 */
public abstract class ProviderPagerAdapter extends FragmentStatePagerAdapter {

    private String categories[];
    private String urls[];

    public ProviderPagerAdapter(FragmentManager fm, String[] categories, String[] urls) {
        super(fm);
        this.categories = categories;
        this.urls = urls;
    }

    @Override
    public Fragment getItem(int position) {
        FeedFragment feedFragment = new FeedFragment();
        Bundle args = new Bundle();
        args.putString("URL", urls[position]);
        args.putString("CATEGORY", categories[position]);
        feedFragment.setArguments(args);

        return feedFragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return categories[position];
    }
}
