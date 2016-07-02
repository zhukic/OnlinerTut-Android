package rus.tutby

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.TabLayout
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.TextView
import com.mikepenz.materialdrawer.AccountHeaderBuilder
import com.mikepenz.materialdrawer.Drawer
import com.mikepenz.materialdrawer.DrawerBuilder
import com.mikepenz.materialdrawer.model.DividerDrawerItem
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem
import com.mikepenz.materialdrawer.model.ProfileDrawerItem
import kotlinx.android.synthetic.main.activity_feed.*
import rus.tutby.entity.Provider
import rus.tutby.ui.adapters.provideradapters.OnlinerPagerAdapter
import rus.tutby.ui.adapters.provideradapters.ProviderPagerAdapter
import rus.tutby.ui.adapters.provideradapters.TutPagerAdapter
import rus.tutby.utils.showToast
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var provider: Provider

    lateinit private var providerPagerAdapter: ProviderPagerAdapter
    lateinit private var drawer: Drawer

    private val mediumTypeface: Typeface by lazy { Typeface.createFromAsset(assets, "Roboto-Medium.ttf") }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed)

        App.objectGraph.inject(this)

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
                        viewPager.currentItem = tab.position
                    }
                })
    }

    private fun initViewPager() {
        providerPagerAdapter = getProviderPagerAdapter()
        viewPager.adapter = providerPagerAdapter
        viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
    }

    private fun initNavigationDrawer() {

        val accountHeader = AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.header)
                .addProfiles(
                        ProfileDrawerItem().withEmail(getString(R.string.tut)).withIcon(R.drawable.tut),
                        ProfileDrawerItem().withEmail(getString(R.string.onliner)).withIcon(R.drawable.onliner)
                )
                .withOnAccountHeaderListener { view, iProfile, b -> changeDrawerItems(b) }
                .build()

        drawer = DrawerBuilder()
                .withActivity(this)
                .withAccountHeader(accountHeader)
                .withToolbar(toolbar)
                .withOnDrawerItemClickListener { view, position, drawerItem ->
                    viewPager.currentItem = position - 1
                    Handler().postDelayed({ tabLayout.getTabAt(position - 1)?.select() }, 100)
                    drawer.closeDrawer()
                    true
                }
                .withCloseOnClick(true)
                .build()
        setDrawerItems()
    }

    private fun changeProvider() {
        when(provider) {
            Provider.TUT -> (applicationContext as App).provider = Provider.ONLINER
            Provider.ONLINER -> (applicationContext as App).provider = Provider.TUT
        }
        provider = (applicationContext as App).provider
    }

    private fun changeDrawerItems(current: Boolean): Boolean {
        if(!current) {
            drawer.removeAllItems()
            changeProvider()
            setDrawerItems()
            providerPagerAdapter = getProviderPagerAdapter();
            viewPager.adapter = providerPagerAdapter;
            toolbar.title = activityTitle;
            setTabs();
        }
        return true
    }

    private fun setDrawerItems() {
        for(i in 0..providerCategories.size - 1) {
            drawer.addItem(
                    PrimaryDrawerItem()
                            .withName(providerCategories[i])
                            .withIdentifier((i + 1).toLong())
                            .withTextColor(Color.BLACK)
                            .withSelectedTextColorRes(R.color.colorPrimary))
        }
        drawer.addItem(DividerDrawerItem())
        drawer.addItem(
                PrimaryDrawerItem()
                        .withName(getString(R.string.action_settings))
                        .withIdentifier(3)
                        .withTextColor(Color.BLACK)
                        .withSelectedTextColorRes(R.color.colorPrimary))
    }

    private fun setTabs() {
        val categories = providerCategories
        tabLayout.removeAllTabs()
        for (i in 0..categories.size - 1) {
            tabLayout.addTab(tabLayout.newTab().setText(categories[i]))
            val tv: TextView = ((tabLayout.getChildAt(0) as LinearLayout).getChildAt(i) as LinearLayout).getChildAt(1) as TextView
            tv.typeface = mediumTypeface
        }
    }

    private fun getProviderPagerAdapter(): ProviderPagerAdapter {
        when (provider) {
            Provider.TUT -> return TutPagerAdapter(supportFragmentManager, providerCategories, providerUrls)
            Provider.ONLINER -> return OnlinerPagerAdapter(supportFragmentManager, providerCategories, providerUrls)
        }
    }

    private val activityTitle: String
        get() {
            when (provider) {
                Provider.TUT -> return resources.getString(R.string.tut)
                Provider.ONLINER -> return resources.getString(R.string.onliner)
            }
        }

    private val providerCategories: Array<String>
        get() {
            when (provider) {
                Provider.TUT -> return resources.getStringArray(R.array.tut_categories)
                Provider.ONLINER -> return resources.getStringArray(R.array.onliner_categories)
            }
        }

    private val providerUrls: Array<String>
        get() {
            when (provider) {
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


