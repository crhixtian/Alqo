package com.alqo.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.alqo.R
import com.alqo.databinding.CardPostBinding
import com.alqo.databinding.FragmentHomeBinding
import com.alqo.entities.Post
import com.alqo.utils.BaseAdapter
import com.google.android.material.imageview.ShapeableImageView
import com.squareup.picasso.Picasso


class HomeFragment : Fragment(R.layout.fragment_home) {
    private lateinit var binding: FragmentHomeBinding

    private val adapter: BaseAdapter<Post> =
        object : BaseAdapter<Post>(emptyList()) {
            override fun getViewHolder(parent: ViewGroup): BaseViewHolder<Post> {
                val view =
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.card_post, parent, false)
                return object : BaseViewHolder<Post>(view) {
                    private val binding: CardPostBinding = CardPostBinding.bind(view)

                    override fun bind(entity: Post) =
                        with(binding) {
                            if (entity.profile.isNotEmpty()) {
                                Picasso.get().load(entity.profile).into(profilePhoto)
                            }
                            username.text = entity.names
                            time.text = entity.create
                            if (entity.content.isNotEmpty()) {
                                contentPost.text = entity.content
                            } else {
                                binding.contentPost.visibility = View.GONE
                            }
                            if (entity.image.isNotEmpty()) {
                                loadImage(entity.image, binding.imagePost)

                                binding.imagePost.setOnClickListener {
                                    val intent = Intent(it.context, FullImageActivity::class.java)
                                    intent.putExtra("IMAGE_URL", entity.image)
                                    it.context.startActivity(intent)
                                }

                            } else {
                                binding.imagePost.visibility = View.GONE
                            }
                        }
                }
            }
        }

    private val viewModel: ListPostHomeViewModel by lazy {
        ViewModelProvider(this)[ListPostHomeViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)
        action()
        setupAdapter()
        observeViewModel()
        refresh()
    }

    private fun action() {
    }

    private fun refresh() {
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.obtainData()
            binding.swipeRefresh.isRefreshing = false
        }
    }

    private fun setupAdapter() {
        binding.recyclePostHome.adapter = adapter
    }

    private fun observeViewModel() {
        viewModel.data.observe(viewLifecycleOwner) { posts ->
            adapter.updateList(posts)
        }
        viewModel.obtainData()
    }

    private fun loadImage(
        imageURL: String,
        imageView: ShapeableImageView,
    ) {
        Picasso.get().load(imageURL).into(imageView)
    }
}