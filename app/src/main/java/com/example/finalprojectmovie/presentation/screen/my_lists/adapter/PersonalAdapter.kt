package com.example.finalprojectmovie.presentation.my_lists.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.finalprojectmovie.databinding.ItemPersonalListBinding
import com.example.finalprojectmovie.presentation.adapter_common.OnMovieClickListener
import com.example.finalprojectmovie.domain.model.PersonalListItem

class PersonalAdapter :
    ListAdapter<PersonalListItem, PersonalAdapter.ViewHolder>(PersonalDiffUtils) {

    var onClick: OnMovieClickListener? = null

    class ViewHolder(
        private val binding: ItemPersonalListBinding,
        private val onClick: OnMovieClickListener?
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(listItem: PersonalListItem) = with(binding) {
            title.text = listItem.title
            details.text = listItem.details

            moreButton.setOnClickListener {

            }

            root.setOnClickListener {
                onClick?.click(listItem.id)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemPersonalListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            onClick
        )
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    private object PersonalDiffUtils : DiffUtil.ItemCallback<PersonalListItem>() {
        override fun areItemsTheSame(
            oldItem: PersonalListItem,
            newItem: PersonalListItem
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: PersonalListItem,
            newItem: PersonalListItem
        ): Boolean {
            return oldItem == newItem
        }
    }

}

