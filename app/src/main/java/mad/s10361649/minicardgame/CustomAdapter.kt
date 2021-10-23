package mad.s10361649.minicardgame

// Important : [This activity is not for assignment 1]

import android.view.*
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

// Related View(s) : item.xml
// Related Class(es) : LogList.kt
// Related drawable(s) : N/A
// Interactive Item(s) : N/A

// Adapter for RecyclerView
internal class CustomAdapter(private var itemsList: List<String>?) :
    RecyclerView.Adapter<CustomAdapter.MyViewHolder>() {
    internal inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var itemTextView: TextView = view.findViewById(R.id.itemTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = itemsList?.get(position) //? to accept Null variable
        holder.itemTextView.text = item
    }

    override fun getItemCount(): Int {
        return itemsList!!.size //!! Null safety

    }

}

