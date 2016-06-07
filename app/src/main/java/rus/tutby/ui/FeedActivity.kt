package rus.tutby.ui

import android.content.res.Configuration
import android.graphics.Color
import android.graphics.Typeface
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*

import butterknife.Bind
import butterknife.ButterKnife
import com.mikepenz.materialdrawer.AccountHeaderBuilder
import com.mikepenz.materialdrawer.DrawerBuilder
import com.mikepenz.materialdrawer.model.DividerDrawerItem
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem
import com.mikepenz.materialdrawer.model.ProfileDrawerItem
import kotlinx.android.synthetic.main.activity_feed.*
import rus.tutby.App
import rus.tutby.R
import rus.tutby.ui.adapters.provideradapters.OnlinerPagerAdapter
import rus.tutby.ui.adapters.provideradapters.ProviderPagerAdapter
import rus.tutby.ui.adapters.provideradapters.TutPagerAdapter
import rus.tutby.entity.Provider

class FeedActivity : AppCompatActivity() {

    lateinit private var providerPagerAdapter: ProviderPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed)

        ButterKnife.bind(this)

        initToolbar()
        initTabLayout()
        initViewPager()
        initNavigationDrawer()
    }

    private fun initToolbar() {
        toolbar.title = activityTitle
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
    }

    private fun initTabLayout() {
        setTabs()

        tabLayout.setOnTabSelectedListener(
                object : TabLayout.ViewPagerOnTabSelectedListener(viewPager) {
                    override fun onTabSelected(tab: TabLayout.Tab) {
                        viewPager!!.currentItem = tab.position
                    }
                })
    }

    private fun initViewPager() {
        providerPagerAdapter = getProviderPagerAdapter()
        viewPager!!.adapter = providerPagerAdapter
        viewPager!!.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
    }

    private fun initNavigationDrawer() {

        val accountHeader = AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.header)
                .addProfiles(
                        ProfileDrawerItem().withName(getString(R.string.tut)).withIcon(R.drawable.tut),
                        ProfileDrawerItem().withName(getString(R.string.onliner)).withIcon(R.drawable.onliner)
                )
                .build()

        val drawer = DrawerBuilder()
                .withActivity(this)
                .withAccountHeader(accountHeader)
                .withToolbar(toolbar)
                .addDrawerItems(
                        PrimaryDrawerItem()
                                .withName(getString(R.string.tut))
                                .withIdentifier(1)
                                .withTextColor(Color.BLACK)
                                .withSelectedTextColorRes(R.color.colorPrimary),
                        PrimaryDrawerItem()
                                .withName(getString(R.string.onliner))
                                .withIdentifier(2)
                                .withTextColor(Color.BLACK)
                                .withSelectedTextColorRes(R.color.colorPrimary),
                        DividerDrawerItem(),
                        PrimaryDrawerItem()
                                .withName(getString(R.string.action_settings))
                                .withIdentifier(3)
                                .withTextColor(Color.BLACK)
                                .withSelectedTextColorRes(R.color.colorPrimary)
                )
                .withOnDrawerItemClickListener { view, position, drawerItem ->
                    when(position) {
                        1 -> App.setProvider(Provider.TUT)
                        2 -> App.setProvider(Provider.ONLINER)
                    }
                    providerPagerAdapter = getProviderPagerAdapter();
                    viewPager.setAdapter(providerPagerAdapter);
                    toolbar.title = activityTitle;
                    setTabs();
                    true
                }
                .withCloseOnClick(true)
                .build()
    }

    private fun setTabs() {
        val categories = providerCategories
        tabLayout.removeAllTabs()
        for (i in 0..categories.size - 1) {
            tabLayout.addTab(tabLayout.newTab().setText(categories[i]))
            val tv: TextView = ((tabLayout.getChildAt(0) as LinearLayout).getChildAt(i) as LinearLayout).getChildAt(1) as TextView
            tv.typeface = Typeface.createFromAsset(assets, "Roboto-Medium.ttf")
        }
    }

    private fun getProviderPagerAdapter(): ProviderPagerAdapter {
        when (App.getProvider()) {
            Provider.TUT -> return TutPagerAdapter(supportFragmentManager, providerCategories, providerUrls)
            Provider.ONLINER -> return OnlinerPagerAdapter(supportFragmentManager, providerCategories, providerUrls)
        }
    }

    private val activityTitle: String
        get() {
            when (App.getProvider()) {
                Provider.TUT -> return resources.getString(R.string.tut)
                Provider.ONLINER -> return resources.getString(R.string.onliner)
            }
        }

    private val providerCategories: Array<String>
        get() {
            when (App.getProvider()) {
                Provider.TUT -> return resources.getStringArray(R.array.tut_categories)
                Provider.ONLINER -> return resources.getStringArray(R.array.onliner_categories)
            }
        }

    private val providerUrls: Array<String>
        get() {
            when (App.getProvider()) {
                Provider.TUT -> return resources.getStringArray(R.array.tut_urls)
                Provider.ONLINER -> return resources.getStringArray(R.array.onliner_urls)
            }
        }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
    }
}
