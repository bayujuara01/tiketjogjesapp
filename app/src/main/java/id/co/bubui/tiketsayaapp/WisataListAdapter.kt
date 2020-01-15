package id.co.bubui.tiketsayaapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView

class WisataListAdapter(private val WisataList : ArrayList<WisataList>) : RecyclerView.Adapter<WisataListAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_wisata, parent, false))
    }

    override fun getItemCount(): Int {
        return WisataList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val tiket = WisataList[position]
        holder.xnama_wisata.text = tiket.nama_wisata
        holder.xlokasi.text = tiket.lokasi
        holder.xharga.text = tiket.harga_tiget.toString()

        holder.itemView.setOnClickListener(View.OnClickListener {
            val context : Context = holder.itemView.context
            val gotomyticketcheckoutIntent = Intent(context, TicketDetailActivity::class.java)
            gotomyticketcheckoutIntent.putExtra("nama_wisata", holder.xnama_wisata.text.toString())
            gotomyticketcheckoutIntent.putExtra(TicketDetailActivity.JENIS_WISATA, WisataListAct.nama_wisata)
            context.startActivity(gotomyticketcheckoutIntent)
        })
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var xnama_wisata : TextView = itemView.findViewById(R.id.xnama_wisata)
        var xlokasi : TextView = itemView.findViewById(R.id.xlokasi)
        var xharga : TextView = itemView.findViewById(R.id.xharga)

    }

}