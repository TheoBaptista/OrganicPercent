package br.edu.ifrs.poa.organicpercent.ui.about

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import br.edu.ifrs.poa.organicpercent.R

class AboutFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_about, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val introTitle = view.findViewById<TextView>(R.id.intro_title_text)
        val introText = view.findViewById<TextView>(R.id.intro_text)
        val organicProductTitle = view.findViewById<TextView>(R.id.organic_product_title_text)
        val organicProductText = view.findViewById<TextView>(R.id.organic_product_text)
        val reallyOrganicTitle = view.findViewById<TextView>(R.id.really_organic_title_text)
        val reallyOrganicText = view.findViewById<TextView>(R.id.really_organic_text)
        val okOrganicTitle = view.findViewById<TextView>(R.id.ok_organic_title_text)
        val okOrganicText = view.findViewById<TextView>(R.id.ok_organic_text)
        val organicMarketTitle = view.findViewById<TextView>(R.id.organic_market_title_text)
        val organicMarketText = view.findViewById<TextView>(R.id.organic_market_text)

        introTitle.setText(R.string.intro_title_text)
        introText.setText(R.string.intro_text)
        organicProductTitle.setText(R.string.organic_product_title_text)
        organicProductText.setText(R.string.organic_product_text)
        reallyOrganicTitle.setText(R.string.really_organic_title_text)
        reallyOrganicText.setText(R.string.really_organic_text)
        okOrganicTitle.setText(R.string.ok_organic_title_text)
        okOrganicText.setText(R.string.ok_organic_text)
        organicMarketTitle.setText(R.string.organic_market_title_text)
        organicMarketText.setText(R.string.organic_market_text)
    }
}