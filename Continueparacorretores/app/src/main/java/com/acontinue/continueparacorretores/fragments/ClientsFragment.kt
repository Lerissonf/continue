package com.acontinue.continueparacorretores.fragments

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.acontinue.continueparacorretores.R
import com.acontinue.continueparacorretores.adapters.Clients
import com.acontinue.continueparacorretores.models.Client
import com.acontinue.continueparacorretores.models.Corretor
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_clients.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [ClientsFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [ClientsFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class ClientsFragment : Fragment(), Clients.ClientsListener {

    val database = FirebaseDatabase.getInstance()
    val myRef = database.getReference("users").child("cliente")
    lateinit var adapter: Clients

    val valueEventListener = object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            val array = arrayListOf<Client?>()
            dataSnapshot.children.forEach { child ->
                array.add(child.getValue(Client::class.java))
            }
            adapter.items = ArrayList(array.filterNotNull())
            adapter.notifyDataSetChanged()
        }

        override fun onCancelled(error: DatabaseError) {}
    }

    override fun onItemClick(model: Client) {

    }

    // TODO: Rename and change types of parameters
    private var param1: FirebaseUser? = null
    private var param2: Corretor? = null
    private var listener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getParcelable(ARG_PARAM1)
            param2 = it.getParcelable(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_clients, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = Clients(arrayListOf())
        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(context)
        adapter.listener = this
        adapter.items = arrayListOf()

        myRef.addValueEventListener(valueEventListener)
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListenerRequests")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ClientsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: FirebaseUser?, param2: Corretor?) =
            ClientsFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_PARAM1, param1)
                    putParcelable(ARG_PARAM2, param2)
                }
            }
    }
}
