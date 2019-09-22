package com.example.kariyer.view.main

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.kariyer.R
import com.example.kariyer.model.order.Order
import kotterknife.bindView

private val orderList = mutableListOf<Order>()

enum class Months{
    Ocak, Şubat, Mart, Nisan, Mayıs, Haziran, Temmuz, Ağustos, Eylül, Ekim, Kasım,Aralık
}
enum class Status{
    H, O, Y
}


class MainActivityAdapter(private val itemOnClickListener: (Order) -> Unit): RecyclerView.Adapter<MainActivityAdapter.MyCoinDataViewHolder>() {

    class MyCoinDataViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val textViewDate by bindView<TextView>(R.id.textViewDate)

        private val expandableCardView by bindView<CardView>(R.id.expandable_cardView)
        private val textViewMonth by bindView<TextView>(R.id.textViewMonth)
        private val textViewMarket by bindView<TextView>(R.id.textViewMarket)
        private val textViewDetail by bindView<TextView>(R.id.textViewOrderDetail)
        private val textViewState by bindView<TextView>(R.id.textViewState)
        private val imageViewState by bindView<ImageView>(R.id.imageViewState)
        private val textViewPrice by bindView<TextView>(R.id.textView_price)
        private val expandedRelativeView by bindView<RelativeLayout>(R.id.expandedRelativeLayout)
        private val textViewInnerDetail by bindView<TextView>(R.id.orderVG)
        private val textViewInnerPrice by bindView<TextView>(R.id.priceVG)


        fun bind(
            order: Order,
            itemViewClickLister: (Order) -> Unit
        ) {


            when(order.productState[0].toString()){
                Status.Y.name-> {
                    imageViewState.setImageDrawable(ContextCompat.getDrawable(itemView.context,R.drawable.square_green))
                    textViewState.setTextColor(ContextCompat.getColor(itemView.context, R.color.green))
                }
                Status.H.name->{
                    imageViewState.setImageDrawable(ContextCompat.getDrawable(itemView.context,R.drawable.square_orange))
                    textViewState.setTextColor(ContextCompat.getColor(itemView.context, R.color.Orange))
                }
                Status.O.name-> {
                    imageViewState.setImageDrawable(ContextCompat.getDrawable(itemView.context,R.drawable.square_red))
                    textViewState.setTextColor(ContextCompat.getColor(itemView.context, R.color.red))
                }
                else -> textViewState.setTextColor(ContextCompat.getColor(itemView.context, R.color.black))
            }
            when(order.month){
                "01" -> textViewMonth.text = Months.Ocak.name
                "02" -> textViewMonth.text = Months.Şubat.name
                "03" -> textViewMonth.text = Months.Mart.name
                "04" -> textViewMonth.text = Months.Nisan.name
                "05" -> textViewMonth.text = Months.Mayıs.name
                "06" -> textViewMonth.text = Months.Haziran.name
                "07" -> textViewMonth.text = Months.Temmuz.name
                "08" -> textViewMonth.text = Months.Ağustos.name
                "09" -> textViewMonth.text = Months.Eylül.name
                "10" -> textViewMonth.text = Months.Ekim.name
                "11" -> textViewMonth.text = Months.Kasım.name
                "12" -> textViewMonth.text = Months.Aralık.name
            }
                //textViewInnerDetail.text = order.productDetail.orderDetail
               // textViewInnerPrice.text = order.productDetail.summaryPrice.toString() + "TL"
                textViewDate.text = order.date
                textViewMarket.text = order.marketName
                textViewDetail.text = order.productDetail.orderDetail
            textViewState.text = order.productState
                textViewPrice.text = order.productDetail.summaryPrice.toString() + "TL"

            textViewInnerDetail.text = order.productDetail.orderDetail
            textViewInnerPrice.text = order.productDetail.summaryPrice.toString() + "TL"


            itemView.setOnClickListener {
                if (!textViewInnerDetail.isVisible){
                    expandedRelativeView.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.LightSlateGray))
                    textViewInnerDetail.visibility = View.VISIBLE
                    textViewInnerPrice.visibility = View.VISIBLE
                }
                else{
                    expandedRelativeView.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.White))
                    textViewInnerDetail.visibility = View.GONE
                    textViewInnerPrice.visibility = View.GONE
                }

            }


        }


    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyCoinDataViewHolder {


            val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.card_view_main,parent,false)
        return MyCoinDataViewHolder(itemView)


    }


    override fun onBindViewHolder(holder: MyCoinDataViewHolder, position: Int) =
        holder.bind(orderList[position],itemOnClickListener)

    fun updateList(newList:List<Order>){
        orderList.clear()
        orderList.addAll(newList)
        notifyDataSetChanged()
    }
    override fun getItemCount(): Int {
        return orderList.size
    }
}