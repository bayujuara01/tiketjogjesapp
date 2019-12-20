package id.co.bubui.tiketsayaapp

import android.content.Context
import android.content.Intent
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
        holder.xnama_wisata.text = tiket.nama_wisata
        holder.xlokasi.text = tiket.lokasi
        holder.xjumlah_tiket.text = tiket.jumlah_tiket + " Tiket"

        holder.itemView.setOnClickListener(View.OnClickListener {
            val context : Context = holder.itemView.context
            val gotomyticketdetailIntent = Intent(context, MyTicketDetailAct::class.java)
            gotomyticketdetailIntent.putExtra("nama_wisata", holder.xnama_wisata.text.toString())
            context.startActivity(gotomyticketdetailIntent)
        })
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var xnama_wisata : TextView = itemView.findViewById(R.id.xnama_wisata)
        var xlokasi : TextView = itemView.findViewById(R.id.xlokasi)
        var xjumlah_tiket : TextView = itemView.findViewById(R.id.xjumlah_tiket)

    }

}