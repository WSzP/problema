package com.example.tgmuresproblema.ui.intro

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.example.tgmuresproblema.R
import com.example.tgmuresproblema.data.AvailableLanguage
import com.example.tgmuresproblema.data.translations.TranslationKey
import com.example.tgmuresproblema.data.translations.TranslationManager
import com.example.tgmuresproblema.ui.base.BaseFragment
import com.example.tgmuresproblema.ui.intro.adapter.LanguageListAdapter
import com.example.tgmuresproblema.ui.util.WebOpenerUtil
import kotlinx.android.synthetic.main.fragment_intro.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf


/**
 * Fragment which is shown on the first run, contains the language selection, the impressum and the
 * data protection and privacy links.
 */
class IntroFragment : BaseFragment<IntroContract.Presenter>(), IntroContract.View {

    override val presenter: IntroContract.Presenter by inject { parametersOf(this) }
    val translationManager: TranslationManager by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_intro, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    fun initView() {
        introDataProtection.text =
            translationManager.getTranslation(TranslationKey.KEY_DATA_PROTECTION)
        introDataProtection.setOnClickListener {
            presenter.showDataProtection();
        }
        introDataImpressum.text = translationManager.getTranslation(TranslationKey.KEY_IMPRESSUM)
        introDataImpressum.setOnClickListener {
            presenter.showImpressum()
        }

        listOfLanguages.apply {
            layoutManager = LinearLayoutManager(
                activity,
                VERTICAL,
                false
            )
            adapter = LanguageListAdapter(AvailableLanguage.values().toList()) {
                presenter.handleLanguageSelection(it)
            }
        }
    }

    override fun showNextFragment() {
        try {
            findNavController().navigate(R.id.action_introFragment_to_issueSenderFragment)
        } catch (ex: IllegalArgumentException) {

        }
    }

}
