package com.acontinue.continueparacorretores.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.acontinue.continueparacorretores.R
import com.acontinue.continueparacorretores.models.Client
import kotlinx.android.synthetic.main.client_item.view.*
import java.util.*


class Clients(var items: ArrayList<Client>) : RecyclerView.Adapter<Clients.ClientsViewHolder>() {
    var listener: ClientsListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClientsViewHolder {
        return ClientsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.client_item, parent, false))
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ClientsViewHolder, position: Int) {
        listener?.let { holder.bind(it, items[position]) }
    }

    interface ClientsListener {
        fun onItemClick(model: Client)
    }

    class ClientsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(listener: ClientsListener, model: Client) {
            itemView.age_client_item.text = model.idade.toString()
            itemView.name_client_item.text = model.nome
            itemView.age_client_item.text = model.idade.toString()
            itemView.email_client_item.text = model.email
            itemView.pagar.text = model.dataPagamento
            itemView.apolice.text = model.apolice
            itemView.tipoSeguro.text = model.tipoSeguro
            itemView.cpf.text = model.cpf

            itemView.stepsView.apply {
                labels = arrayOf("Mês 1", "Mês 3", "Mês 9", "Mês 12")
                barColorIndicator = ContextCompat.getColor(context, R.color.gray)
                if (model.statusPagamento == true) {
                    progressColorIndicator = ContextCompat.getColor(context, R.color.green)
                    labelColorIndicator = ContextCompat.getColor(context, R.color.green)
                } else  {
                    progressColorIndicator = ContextCompat.getColor(context, R.color.orange)
                    labelColorIndicator = ContextCompat.getColor(context, R.color.orange)
                }
                completedPosition = model.vigencia ?: 0
                drawView()
            }

            itemView.setOnClickListener {
                listener.onItemClick(model)
            }
        }
    }
}