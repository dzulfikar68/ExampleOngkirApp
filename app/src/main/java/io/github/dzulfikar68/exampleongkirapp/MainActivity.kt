package io.github.dzulfikar68.exampleongkirapp

import android.app.ProgressDialog
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.core.KoinComponent
import org.koin.core.get
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity(), KoinComponent {

    // init retrofit tanpa dependency injection
//    private var retrofitServices: RajaOngkirServices? = null
    // init retrofit dengan dependency injection
    private var retrofitServices = get<RajaOngkirServices?>()
    private var dialog: ProgressDialog? = null
    private var cityOrigin: CityItem? = null
    private var cityDestination: CityItem? = null
    private var beratBarang: String? = null
    private var tipeEkspedisi: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.title = "Cek Ongkir Kuy!"

        // assign retrofit tanpa dependency injection
//        retrofitServices = RetrofitTanpaDI().getService()

        // assign retrofit dengan dependency injection
//

        // init data / get city
        dialog = ProgressDialog.show(this, "",
                "Loading. Please wait...", true)
        dialog?.show()
        retrofitServices?.getCities()?.enqueue(object : Callback<RajaOngkirResponse<ResultList<CityItem>?>?> {
            override fun onFailure(call: Call<RajaOngkirResponse<ResultList<CityItem>?>?>, t: Throwable) {
                dialog?.dismiss()
                Toast.makeText(this@MainActivity, t.localizedMessage, Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<RajaOngkirResponse<ResultList<CityItem>?>?>, response: Response<RajaOngkirResponse<ResultList<CityItem>?>?>) {
                val result = response.body()?.rajaongkir?.results ?: emptyList()
                spCityOrigin?.setSpinner(result) { item ->
                    cityOrigin = item
                }
                spCityDestination?.setSpinner(result) { item ->
                    cityDestination = item
                }
                dialog?.dismiss()
            }
        })

        // init listener
        etBeratBarang?.addTextChangedListener {
            beratBarang = it.toString().trim()
        }
        rgEkspedisi?.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.rbPos -> {
                    tipeEkspedisi = "pos"
                }
                R.id.rbTiki -> {
                    tipeEkspedisi = "tiki"
                }
                R.id.rbJne -> {
                    tipeEkspedisi = "jne"
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.ic_sync) {
            if (cityOrigin == null
                    || cityDestination == null
                    || beratBarang.isNullOrEmpty()
                    || tipeEkspedisi.isNullOrEmpty()) {
                Toast.makeText(this@MainActivity, "Harap form diisi dengan lengkap", Toast.LENGTH_LONG).show()
                return false
            }
            pbLoading?.visibility = View.VISIBLE
            retrofitServices?.postCosts(
                    cityOrigin = cityOrigin?.city_id?.toInt(),
                    cityDestination = cityDestination?.city_id?.toInt(),
                    beratBarang = beratBarang,
                    tipeEkspedisi = tipeEkspedisi
            )?.enqueue(object : Callback<RajaOngkirResponse<ResultList<OngkirItem>?>?> {
                override fun onFailure(call: Call<RajaOngkirResponse<ResultList<OngkirItem>?>?>, t: Throwable) {
                    finishingRequestCost(null)
                    Toast.makeText(this@MainActivity, t.localizedMessage, Toast.LENGTH_LONG).show()
                }

                override fun onResponse(call: Call<RajaOngkirResponse<ResultList<OngkirItem>?>?>, response: Response<RajaOngkirResponse<ResultList<OngkirItem>?>?>) {
                    val result = response.body()?.rajaongkir?.results?.get(0)
                    rvResult?.setListItem(
                            result?.name,
                            result?.costs
                    )
                    finishingRequestCost(result?.costs)
                }
            })
            true
        } else super.onOptionsItemSelected(item)
    }

    private fun finishingRequestCost(list: List<CostsItem>?) {
        if (list?.isNullOrEmpty() == true)
            tvEmpty?.visibility = View.VISIBLE
        else
            tvEmpty?.visibility = View.GONE
        pbLoading?.visibility = View.GONE
    }

    private fun RecyclerView.setListItem(name: String?, items: List<CostsItem>?) {
        layoutManager = LinearLayoutManager(context)
        hasFixedSize()
        adapter = ItemAdapter(name, items ?: emptyList())
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