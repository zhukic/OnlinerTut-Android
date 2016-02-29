package rus.tutby.adapters.provideradapters;

import android.support.v4.app.FragmentManager;


public class TutPagerAdapter extends ProviderPagerAdapter {

    public TutPagerAdapter(FragmentManager fm, String[] categories, String[] urls) {
        super(fm, categories, urls);
    }

    @Override
    public int getCount() {
        return 10;
    }
}
