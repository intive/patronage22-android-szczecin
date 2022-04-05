package com.intive.patronage.retro.about.presentation.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.intive.patronage.retro.R
import com.intive.patronage.retro.about.presentation.entity.Dev
import com.intive.patronage.retro.about.presentation.entity.Devs
import com.intive.patronage.retro.databinding.AboutUsFragmentBinding
import com.intive.patronage.retro.databinding.AboutUsRecyclerItemLayoutBinding

class AboutUsRecyclerAdapter(private val devs: Devs, private val aboutUsBinding: AboutUsFragmentBinding) :
    RecyclerView.Adapter<AboutUsRecyclerAdapter.AboutUsViewHolder>() {

    inner class AboutUsViewHolder(private val binding: AboutUsRecyclerItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindItem(dev: Dev) {
            with(binding) {
                developerNameText.text = dev.displayName
                developerRoleText.text = dev.role
                developerEmailText.text = dev.email
                developerGithubText.text = dev.githubUrl
                Glide.with(avatarImage.context)
                    .load(dev.avatarUrl)
                    .centerCrop()
                    .error(R.drawable.ic_avatar_default)
                    .into(avatarImage)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AboutUsViewHolder {
        return AboutUsViewHolder(AboutUsRecyclerItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: AboutUsViewHolder, position: Int) {
        val dev = devs.devs[position]
        holder.bindItem(dev)
        if (position == devs.devs.size - 1) {
            aboutUsBinding.devsProgressBarCircular.visibility = View.GONE
            aboutUsBinding.devsAppBar.visibility = View.VISIBLE
        }
    }

    override fun getItemCount() = devs.devs.size
}
