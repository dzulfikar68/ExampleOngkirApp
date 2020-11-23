package io.github.dzulfikar68.exampleongkirapp

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.title = "Cek Ongkir Kuy!"

        spProvOrigin?.setSpinner(listOf(
                ProvinceItem(province = "jogja")
        )) { item ->

        }

        spCityOrigin?.setSpinner(listOf(
                ProvinceItem(province = "xxx")
        )) { item ->

        }

        spProvDestination?.setSpinner(listOf(
                CityItem(city_name = "zzz")
        )) { item ->

        }

        spCityDestination?.setSpinner(listOf(
                CityItem(city_name = "yyy")
        )) { item ->

        }

        rvResult?.setListItem(
                "Yayaya",
                listOf(
                        CostsItem(
                                service = "Selamanya",
                                description = "mantab jiwa bro",
                                cost = listOf(
                                        CostItem(
                                                value = 9000,
                                                etd = "2-4"
                                        )
                                )
                        )
                )
        )


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.ic_sync) {

            true
        } else super.onOptionsItemSelected(item)
    }

    private fun RecyclerView.setListItem(name: String, items: List<CostsItem>) {
        layoutManager = LinearLayoutManager(context)
        hasFixedSize()
        adapter = ItemAdapter(name, items)
    }

    @Suppress("UNCHECKED_CAST")
    private fun <T> MaterialAutoCompleteTextView.setSpinner(
            list: List<T>,
            event: (T?) -> Unit
    ) {
        val adapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, list)
        this.setAdapter(adapter)
        this.setOnItemClickListener { parent, view, position, id ->
            val item = (parent?.adapter?.getItem(position)) as T?
            event(item)
        }
    }
}