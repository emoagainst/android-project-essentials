package com.quickstart.mvp.repos

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import com.quickstart.R
import com.quickstart.models.Repo
import com.quickstart.mvp.BaseFragment
import com.quickstart.utils.addIfNotExists
import com.quickstart.utils.bindView
import java.util.*

/**
 * Created at 29.11.16 10:34
 * @author Alexey_Ivanov
 */
class ReposFragment() : BaseFragment<ReposContract.Presenter>(), ReposContract.View{

    override lateinit var mPresenter: ReposContract.Presenter
    val mRecyclerView : RecyclerView by bindView(R.id.recycler_view)
    val mProgressBar : ProgressBar by bindView(R.id.progress)
    val mEmptyTextView : TextView by bindView(R.id.tv_empty_text)
    lateinit var adapter : ReposAdapter

    companion object {
        @JvmStatic
        fun newInstance():ReposFragment{
            return ReposFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_repos, container, false)
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = ReposAdapter(ArrayList())
        mRecyclerView.adapter = adapter
        mRecyclerView.layoutManager = LinearLayoutManager(context)
        mPresenter.loadRepos()
    }

    override fun showRepos(repos: List<Repo>) {
        check(!repos.isEmpty(), {"empty repos list recieved"})
        if (adapter.repos.isEmpty()){
            mRecyclerView.visibility = View.VISIBLE
            mProgressBar.visibility = View.GONE
            mEmptyTextView.visibility = View.GONE
        }
        repos.forEach {
            adapter.repos.addIfNotExists(it) }

        adapter.notifyDataSetChanged()
    }

    override fun showEmptyRepos() {
        mRecyclerView.visibility = View.GONE
        mProgressBar.visibility = View.GONE
        mEmptyTextView.visibility = View.VISIBLE
        mEmptyTextView.text = "Repos are empty"

    }

    override fun setLoadingIndicator(active: Boolean) {
        val visibility = if (active) View.VISIBLE else View.GONE
        mProgressBar.visibility = visibility
    }
}
