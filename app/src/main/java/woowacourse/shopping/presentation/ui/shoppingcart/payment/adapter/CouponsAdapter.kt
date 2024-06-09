package woowacourse.shopping.presentation.ui.shoppingcart.payment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import woowacourse.shopping.databinding.HolderCouponBinding
import woowacourse.shopping.domain.model.coupons.Coupon

class CouponsAdapter : ListAdapter<Coupon, CouponsAdapter.CouponViewHolder>(CouponDiffCallback) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): CouponViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = HolderCouponBinding.inflate(inflater, parent, false)
        return CouponViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: CouponViewHolder,
        postion: Int,
    ) {
        holder.bind(getItem(postion))
    }

    class CouponViewHolder(
        private val binding: HolderCouponBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(coupon: Coupon) {
            binding.coupon = coupon
        }
    }

    object CouponDiffCallback : DiffUtil.ItemCallback<Coupon>() {
        override fun areItemsTheSame(
            oldItem: Coupon,
            newItem: Coupon,
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: Coupon,
            newItem: Coupon,
        ): Boolean {
            return oldItem == newItem
        }
    }
}
