package id.co.bubui.tiketsayaapp

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView

class TicketAdapter(private val myTicket : ArrayList<MyTicket>) : RecyclerView.Adapter<TicketAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_myticket, parent, false))
    }

    override fun getItemCount(): Int {
        return myTicket.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val tiket = myTicket[position]
        var order_id = tiket.id_tiket
        holder.xnama_wisata.text = tiket.nama_wisata
        holder.xlokasi.text = tiket.lokasi
        holder.xjumlah_tiket.text = tiket.jumlah_tiket + " Tiket"

        if(tiket.payment_status.toLowerCase() == "settlement" || tiket.payment_status.toLowerCase() == "success"){
            holder.xpayment_status.text = "Paid"
            holder.xpayment_status.setTextColor(Color.rgb(	51, 229, 99))
        } else {
            holder.xpayment_status.text = "Unpaid"
            holder.xpayment_status.setTextColor(Color.rgb(229, 52, 92))
        }

        holder.itemView.setOnClickListener {
            val context : Context = holder.itemView.context

            if(tiket.payment_status.toLowerCase() == "settlement" || tiket.payment_status.toLowerCase() == "success"){
                val gotomyticketdetailIntent = Intent(context, MyTicketDetailAct::class.java)
                gotomyticketdetailIntent.putExtra("order_id", order_id)
                context.startActivity(gotomyticketdetailIntent)
            } else {
                val intentPayment = Intent(context, PaymentWebViewActivity::class.java)
                intentPayment.putExtra(PaymentWebViewActivity.URL_KEY, tiket.url)
                context.startActivity(intentPayment)
            }

        }
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var xnama_wisata : TextView = itemView.findViewById(R.id.xnama_wisata)
        var xlokasi : TextView = itemView.findViewById(R.id.xlokasi)
        var xjumlah_tiket : TextView = itemView.findViewById(R.id.xjumlah_tiket)
        var xpayment_status: TextView = itemView.findViewById(R.id.tv_payment_status)

    }

}