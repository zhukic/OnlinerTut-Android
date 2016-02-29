package rus.tutby.adapters.provideradapters;

import android.support.v4.app.FragmentManager;

public class OnlinerPagerAdapter extends ProviderPagerAdapter {


    public OnlinerPagerAdapter(FragmentManager fm, String[] categories, String[] urls) {
        super(fm, categories, urls);
    }

    @Override
    public int getCount() {
        return 5;
    }
}
