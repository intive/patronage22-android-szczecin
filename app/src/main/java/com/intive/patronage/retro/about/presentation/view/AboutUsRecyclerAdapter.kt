package com.intive.patronage.retro.about.presentation.view

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.intive.patronage.retro.R
import com.intive.patronage.retro.about.presentation.entity.Dev
import com.intive.patronage.retro.about.presentation.entity.Devs
import com.intive.patronage.retro.databinding.AboutUsRecyclerItemLayoutBinding

class AboutUsRecyclerAdapter(private val devs: Devs, private val progressBar: ProgressBar) :
    RecyclerView.Adapter<AboutUsRecyclerAdapter.AboutUsViewHolder>() {

    inner class AboutUsViewHolder(private val binding: AboutUsRecyclerItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindItem(dev: Dev) {
            binding.developerNameText.text = dev.displayName
            binding.developerRoleText.text = dev.role
            binding.developerEmailText.text = dev.email
            binding.developerGithubText.text = dev.githubUrl
            Glide.with(binding.avatarImage.context)
                .load(dev.avatarUrl)
                .centerCrop()
                .error(R.drawable.ic_avatar_default)
                .into(binding.avatarImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AboutUsViewHolder {
        return AboutUsViewHolder(AboutUsRecyclerItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: AboutUsViewHolder, position: Int) {
        val dev = devs.devs[position]
        holder.bindItem(dev)
        if (position == devs.devs.size - 1) {
            progressBar.visibility = ProgressBar.GONE
        }
    }

    override fun getItemCount() = devs.devs.size
}
