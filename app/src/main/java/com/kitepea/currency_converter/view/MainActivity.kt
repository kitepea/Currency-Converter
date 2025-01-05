package com.kitepea.currency_converter.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.kitepea.currency_converter.R
import com.kitepea.currency_converter.databinding.ActivityMainBinding
import com.kitepea.currency_converter.helper.EndPoints
import com.kitepea.currency_converter.helper.Resource
import com.kitepea.currency_converter.helper.Utility
import com.kitepea.currency_converter.model.Rates
import com.kitepea.currency_converter.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.Currency
import java.util.Locale

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    // Default values for the two spinners
    private var selectedItem1: String? = "AFN"
    private var selectedItem2: String? = "AFN"

    //ViewModel
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme_NoActionBar)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // Initialize spinner
        initSpinner()
        convertButtonClickListener()
    }

    private fun initSpinner() {
        val spinner1 = binding.spnFirstCountry

        // Set item with countries
        spinner1.setItems(getAllCountries())

        spinner1.setOnClickListener {
            Utility.hideKeyboard(this)
        }

        // Handle selected item
        spinner1.setOnItemSelectedListener { _, _, _, item ->
            //Set the currency code for each country as hint
            val countryCode = getCountryCode(item.toString())
            val currencySymbol = getSymbol(countryCode)
            selectedItem1 = currencySymbol
            binding.txtFirstCurrencyName.text = selectedItem1
        }

        //get second spinner country reference in view
        val spinner2 = binding.spnSecondCountry

        //hide key board when spinner shows
        spinner1.setOnClickListener {
            Utility.hideKeyboard(this)
        }

        // Set item with countries
        spinner2.setItems(getAllCountries())

        //Handle selected item
        spinner2.setOnItemSelectedListener { _, _, _, item ->
            //Set the currency code for each country as hint
            val countryCode = getCountryCode(item.toString())
            val currencySymbol = getSymbol(countryCode)
            selectedItem2 = currencySymbol
            binding.txtSecondCurrencyName.text = selectedItem2
        }
    }

    /**
     * A method for getting a country's currency symbol from the country's code
     * e.g USA - USD
     */

    private fun getSymbol(countryCode: String?): String? {
        val availableLocales: Array<Locale> = Locale.getAvailableLocales()
        for (i in availableLocales.indices) {
            if (availableLocales[i].country == countryCode) return Currency.getInstance(
                availableLocales[i]
            ).currencyCode
        }
        return ""
    }

    /**
     * A method for getting a country's code from the country name
     * e.g Nigeria - NG
     */

    private fun getCountryCode(countryName: String) =
        Locale.getISOCountries().find { Locale("", it).displayCountry == countryName }


    private fun getAllCountries(): ArrayList<String> {
        val locales = Locale.getAvailableLocales()
        val countries = ArrayList<String>()
        for (locale in locales) {
            val country = locale.displayCountry
            if (country.trim { it <= ' ' }.isNotEmpty() && !countries.contains(country)) {
                countries.add("$country")
            }
        }
        countries.sort()
        return countries
    }

    /**
     * A method for handling click events in the UI
     */

    private fun convertButtonClickListener() {

        binding.btnConvert.setOnClickListener {


            val numberToConvert = binding.etFirstCurrency.text.toString()

            // Internet Connection
            if (!Utility.isNetworkAvailable(this)) {
                Snackbar.make(
                    binding.main, "You are not connected to the internet", Snackbar.LENGTH_LONG
                ).setBackgroundTint(ContextCompat.getColor(this, R.color.dark_red))
                    .setTextColor(ContextCompat.getColor(this, R.color.white)).show()
            }
            // Empty input
            else if (numberToConvert.isEmpty() || numberToConvert == "0") {
                Snackbar.make(
                    binding.main,
                    "Input a value in the first text field, result will be shown in the second text field",
                    Snackbar.LENGTH_LONG
                ).setBackgroundTint(ContextCompat.getColor(this, R.color.dark_red))
                    .setTextColor(ContextCompat.getColor(this, R.color.white)).show()
            }
            // Continue with conversion
            else {
                doConversion()
            }
        }
    }

    /**
     * A method that does the conversion by communicating with the API - fixer.io based on the data inputted
     * Uses viewModel and flows
     */

    private fun doConversion() {

        //hide keyboard
        Utility.hideKeyboard(this)

        // Hide button and show progress bar
        binding.prgLoading.visibility = View.VISIBLE
        binding.btnConvert.visibility = View.GONE

        //Get the data inputted
        val apiKey = EndPoints.API_KEY
        val from = selectedItem1.toString()
        val to = selectedItem2.toString()
        val amount = binding.etFirstCurrency.text.toString().toDouble()

        //do the conversion
        mainViewModel.getConvertedData(apiKey, from, to, amount)

        //observe for changes in UI
        observeUi()

    }

    /**
     * Using coroutines flow, changes are observed and responses gotten from the API
     *
     */

    @SuppressLint("SetTextI18n")
    private fun observeUi() {
        mainViewModel.data.observe(this) { result ->
            when (result.status) {
                Resource.Status.SUCCESS -> {
                    if (result.data?.status == "success") {
                        val map: Map<String, Rates>
                        map = result.data.rates
                        map.keys.forEach {

                            val rateForAmount = map[it]?.rate_for_amount

                            mainViewModel.convertedRate.value = rateForAmount

                            //format the result obtained e.g 1000 = 1,000
                            val formattedString =
                                String.format(Locale.US, "%,.2f", mainViewModel.convertedRate.value)

                            //set the value in the second edit text field
                            binding.etSecondCurrency.setText(formattedString)
                        }

                        //stop progress bar
                        binding.prgLoading.visibility = View.GONE
                        //show button
                        binding.btnConvert.visibility = View.VISIBLE
                    } else if (result.data?.status == "fail") {
                        val layout = binding.main
                        Snackbar.make(
                            layout, "Oops! something went wrong, Try again", Snackbar.LENGTH_LONG
                        ).setBackgroundTint(ContextCompat.getColor(this, R.color.dark_red))
                            .setTextColor(ContextCompat.getColor(this, R.color.white)).show()

                        //stop progress bar
                        binding.prgLoading.visibility = View.GONE
                        //show button
                        binding.btnConvert.visibility = View.VISIBLE
                    }
                }

                Resource.Status.ERROR -> {

                    val layout = binding.main
                    Snackbar.make(
                        layout, "Oops! Something went wrong, Try again", Snackbar.LENGTH_LONG
                    ).setBackgroundTint(ContextCompat.getColor(this, R.color.dark_red))
                        .setTextColor(ContextCompat.getColor(this, R.color.white)).show()

                    //stop progress bar
                    binding.prgLoading.visibility = View.GONE
                    //show button
                    binding.btnConvert.visibility = View.VISIBLE
                }

                Resource.Status.LOADING -> {
                    //stop progress bar
                    binding.prgLoading.visibility = View.VISIBLE
                    //show button
                    binding.btnConvert.visibility = View.GONE
                }
            }
        }
    }
}
