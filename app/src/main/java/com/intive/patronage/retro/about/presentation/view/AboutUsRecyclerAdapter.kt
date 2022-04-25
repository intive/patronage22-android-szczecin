package com.intive.patronage.retro.about.presentation.view

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.intive.patronage.retro.R
import com.intive.patronage.retro.about.presentation.entity.Dev
import com.intive.patronage.retro.about.presentation.entity.Devs
import com.intive.patronage.retro.databinding.AboutUsFragmentBinding
import com.intive.patronage.retro.databinding.AboutUsRecyclerItemLayoutBinding

class AboutUsRecyclerAdapter(private val devs: Devs, private val aboutUsBinding: AboutUsFragmentBinding) :
    RecyclerView.Adapter<AboutUsRecyclerAdapter.AboutUsViewHolder>() {

    private val intentGithub = Intent(Intent.ACTION_VIEW)
    private val intentEmail = Intent(Intent.ACTION_SEND)

    inner class AboutUsViewHolder(private val binding: AboutUsRecyclerItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindItem(dev: Dev) {
            binding.dev = dev
            binding.developerGithubText.setOnClickListener { githubListener(binding, dev) }
            binding.developerGithubIcon.setOnClickListener { githubListener(binding, dev) }
            binding.developerEmailText.setOnClickListener { emailListener(binding, dev) }
            binding.developerEmailIcon.setOnClickListener { emailListener(binding, dev) }
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

    private fun githubListener(binding: AboutUsRecyclerItemLayoutBinding, dev: Dev) {
        intentGithub.data = Uri.parse(dev.githubUrl)
        binding.root.context.startActivity(intentGithub)
    }

    private fun emailListener(binding: AboutUsRecyclerItemLayoutBinding, dev: Dev) {
        intentEmail.putExtra(Intent.EXTRA_EMAIL, listOf(dev.email).toTypedArray())
        intentEmail.putExtra(
            Intent.EXTRA_SUBJECT,
            binding.root.context.getString(R.string.send_email_subject) +
                dev.displayName
        )
        intentEmail.putExtra(
            Intent.EXTRA_TEXT,
            binding.root.context.getString(R.string.greetings_to_developer) +
                dev.displayName +
                binding.root.context.getString(R.string.email_text_to_developer)
        )
        intentEmail.type = binding.root.context.getString(R.string.mail_message_type)
        binding.root.context.startActivity(
            Intent.createChooser(
                intentEmail,
                binding.root.context.getString(R.string.question_to_user_before_choose_email_app)
            )
        )
    }
}
