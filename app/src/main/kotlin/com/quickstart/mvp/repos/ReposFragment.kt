package com.quickstart.mvp.repos

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import butterknife.bindView
import com.quickstart.R
import com.quickstart.models.Repo
import java.util.*

/**
 * Created at 29.11.16 10:34
 * @author Alexey_Ivanov
 */
class ReposFragment() : Fragment(), ReposContract.View{

    lateinit var mPresenter: ReposContract.Presenter
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
        mPresenter.loadRepos()
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = ReposAdapter(ArrayList())
        mRecyclerView.adapter = adapter
        mRecyclerView.layoutManager = LinearLayoutManager(context)

    }

    override fun setPresenter(presenter: ReposContract.Presenter) {
        mPresenter = checkNotNull(presenter)
    }

    override fun showRepos(repos: List<Repo>) {
        check(!repos.isEmpty(), {"empty repos list recieved"})
        if (adapter.repos.isEmpty()){
            mRecyclerView.visibility = View.VISIBLE
            mProgressBar.visibility = View.GONE
            mEmptyTextView.visibility = View.GONE
        }

        adapter.repos.addAll(repos)
        adapter.notifyDataSetChanged()
    }

    override fun showEmptyRepos() {
        mRecyclerView.visibility = View.GONE
        mProgressBar.visibility = View.GONE
        mEmptyTextView.visibility = View.VISIBLE
        mEmptyTextView.text = "Repos are empty"

    }

    override fun setLoadingIndicator(active: Boolean) {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
