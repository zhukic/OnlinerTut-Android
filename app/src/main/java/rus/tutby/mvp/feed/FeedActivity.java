package rus.tutby.mvp.feed;

import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support. v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import butterknife.Bind;
import butterknife.ButterKnife;
import rus.tutby.MyApplication;
import rus.tutby.R;
import rus.tutby.adapters.provideradapters.OnlinerPagerAdapter;
import rus.tutby.adapters.provideradapters.ProviderPagerAdapter;
import rus.tutby.adapters.provideradapters.TutPagerAdapter;
import rus.tutby.database.DatabaseManager;
import rus.tutby.provider.Provider;

public class FeedActivity extends AppCompatActivity {

    private static final String TAG = "TAG";

    @Bind(R.id.tab_layout) TabLayout tabLayout;
    @Bind(R.id.my_toolbar) Toolbar toolbar;
    @Bind(R.id.pager) ViewPager viewPager;
    @Bind(R.id.drawer_layout) DrawerLayout drawerLayout;

    private ProviderPagerAdapter providerPagerAdapter;
    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        ButterKnife.bind(this);

        initToolbar();
        initTabLayout();
        initViewPager();
        initNavigationDrawer();
    }

    private void initToolbar() {
        toolbar.setTitle(getActivityTitle());
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    private void initTabLayout() {
        setTabs();

        tabLayout.setOnTabSelectedListener(
                new TabLayout.ViewPagerOnTabSelectedListener(viewPager) {
                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        viewPager.setCurrentItem(tab.getPosition());
                    }
                });
    }

    private void initViewPager() {
        providerPagerAdapter = getProviderPagerAdapter();
        viewPager.setAdapter(providerPagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }

    private void initNavigationDrawer() {
        final String[] providers = getResources().getStringArray(R.array.providers);
        final ListView drawerList;

        drawerList = (ListView) findViewById(R.id.left_drawer);
        drawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        MyApplication.setProvider(Provider.TUT);
                        break;
                    case 1:
                        MyApplication.setProvider(Provider.ONLINER);
                        break;
                }

                providerPagerAdapter = getProviderPagerAdapter();
                viewPager.setAdapter(providerPagerAdapter);

                toolbar.setTitle(getActivityTitle());

                setTabs();

                drawerLayout.closeDrawers();
            }
        });
        drawerList.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, providers));

        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open,
                R.string.drawer_close) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };

        drawerLayout.setDrawerListener(drawerToggle);

    }

    private void setTabs() {
        String categories[] = getProviderCategories();
        tabLayout.removeAllTabs();
        for (String category : categories) {
            tabLayout.addTab(tabLayout.newTab().setText(category));
        }
    }

    private ProviderPagerAdapter getProviderPagerAdapter() {
        switch (MyApplication.getProvider()) {
            case TUT:
                return new TutPagerAdapter(getSupportFragmentManager(), getProviderCategories(), getProviderUrls());
            case ONLINER:
                return new OnlinerPagerAdapter(getSupportFragmentManager(), getProviderCategories(), getProviderUrls());
            default:
                return null;
        }
    }

    private String getActivityTitle() {
        switch (MyApplication.getProvider()) {
            case TUT:
                return getResources().getString(R.string.tut);
            case ONLINER:
                return getResources().getString(R.string.onliner);
            default:
                return null;
        }
    }

    private String[] getProviderCategories() {
        switch (MyApplication.getProvider()) {
            case TUT:
                return getResources().getStringArray(R.array.tut_categories);
            case ONLINER:
                return getResources().getStringArray(R.array.onliner_categories);
        }
        return null;
    }

    private String[] getProviderUrls() {
        switch (MyApplication.getProvider()) {
            case TUT:
                return getResources().getStringArray(R.array.tut_urls);
            case ONLINER:
                return getResources().getStringArray(R.array.onliner_urls);
        }
        return null;
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
