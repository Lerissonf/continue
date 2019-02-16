package com.acontinue.continueparacorretores.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.acontinue.continueparacorretores.R
import com.acontinue.continueparacorretores.models.Request
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.client_item.view.*
import kotlinx.android.synthetic.main.request_item.view.*
import java.util.*

class Requests(var items: ArrayList<Request>) : RecyclerView.Adapter<Requests.RequestsViewHolder>() {
    var listener: RequestsListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RequestsViewHolder {
        return RequestsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.request_item, parent, false))
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: RequestsViewHolder, position: Int) {
        listener?.let { holder.bind(it, items[position]) }
    }

    interface RequestsListener {
        fun OnItemClick(model: Request)
    }

    class RequestsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(listener: RequestsListener, model: Request) {
            itemView.age_client_request.text = model.idade_titular.toString()
            itemView.tipoSeguro.text = model.tipo_seguro
            itemView.requests.text = model.status_requisicao.toString()
            itemView.description.text = model.descricao
            itemView.risco.text = model.risco.toString()
            itemView.setOnClickListener {
                listener.OnItemClick(model)
            }
        }
    }
}