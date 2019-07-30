package com.koraextra.app.ui.mainActivity.players.playersDetails

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView

import com.koraextra.app.R
import com.koraextra.app.data.models.NewsModel
import kotlinx.android.synthetic.main.players_details_fragment.*

class PlayersDetailsFragment : Fragment() {

    companion object {
        fun newInstance() = PlayersDetailsFragment()
    }

    private lateinit var viewModel: PlayersDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.players_details_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(PlayersDetailsViewModel::class.java)
        // TODO: Use the ViewModel

        val mainNews = ArrayList<NewsModel>()
        mainNews.add(
            NewsModel(
                "https://fos.cmb.ac.lk/blog/wp-content/uploads/2019/04/Football.jpg",
                "صفقة انتقال بيبي  "
            )
        )
        mainNews.add(
            NewsModel(
                "https://www.raf.mod.uk/aircadets/raf-aircadets/assets/Image/Football.jpg",
                "كوتروني يعلن انضمامه "
            )
        )
        mainNews.add(
            NewsModel(
                "https://images.performgroup.com/di/library/GOAL/f3/95/football-rule-changes_1sq9naux6zqbf13gxeov5833fa.jpg?t=-2038154983&quality=60&w=1408",
                "آخر أخبار صفقات وسوق"
            )
        )
        mainNews.add(
            NewsModel(
                "https://images.performgroup.com/di/library/GOAL/7f/ea/willian-chelsea-manchester-united-free-kick_1sx72o7v2m2im1cf1kqvboht0r.jpg?t=-2034903303&quality=60&w=1600",
                "خاميس ينضم لتدريبات "
            )
        )

        val spinnerAdapter = ChampionSpinnerAdapter(context!!, mainNews)
        spinnerChampion.adapter = spinnerAdapter

        spinnerChampion?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            }

        }

    }

}
