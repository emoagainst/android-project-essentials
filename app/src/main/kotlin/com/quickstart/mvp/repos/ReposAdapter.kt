package com.quickstart.mvp.repos

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.quickstart.databinding.ListItemRepoBinding
import com.quickstart.models.Repo
import com.quickstart.utils.layoutInflater

/**
 * Created at 08.12.16 14:48
 * @author Alexey_Ivanov
 */
class ReposAdapter(val repos : MutableList<Repo>) : RecyclerView.Adapter<RepoViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        val binding = ListItemRepoBinding.inflate(parent.context.layoutInflater())
        return RepoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        val repo = repos[position]
        holder.binding.repo = repo
    }

    override fun getItemCount(): Int = repos.size
}

class RepoViewHolder(val binding : ListItemRepoBinding) : RecyclerView.ViewHolder(binding.root){}
