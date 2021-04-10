package com.jimmy.weather.ui.list

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.jimmy.weather.R
import com.jimmy.weather.base.BaseActivity
import com.jimmy.weather.data.persistence.ImageEntity
import com.jimmy.weather.ui.detail.WeatherDetailActivity
import com.jimmy.weather.util.gone
import kotlinx.android.synthetic.main.activity_weather_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class WeatherListActivity : BaseActivity() {

    private val viewModel: WeatherListViewModel by viewModel()

    /**
     * Subscribe for images and populate the list
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather_list)
        viewModel.images.observe(this) {
            if (it.isNotEmpty()) {
                imagesRecycler.adapter = ImagesAdapter(it) { openDetails(it) }
                noItems.gone()
            }
        }
    }

    /**
     * Send the image entity to be rendered in the details page
     */
    private fun openDetails(image: ImageEntity) {
        val extra = Bundle()
        extra.putParcelable(WeatherDetailActivity.IMAGE_EXTRA, image)
        start(WeatherDetailActivity::class.java, extra)
    }

    /**
     * Render menu add button
     */
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.add_menu, menu)
        return true
    }

    /**
     * Handle add button action to open details activity
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.add)
            start(WeatherDetailActivity::class.java)
        return super.onOptionsItemSelected(item)
    }

    /**
     * Sync the images list each time the screen is displayed
     */
    override fun onResume() {
        super.onResume()
        viewModel.getImages()
    }

}