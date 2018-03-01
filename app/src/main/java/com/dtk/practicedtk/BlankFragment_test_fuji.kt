package com.dtk.practicedtk

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_blank_fragment_test_fuji.*
import android.widget.Toast
import android.widget.AdapterView




/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [BlankFragment_test_fuji.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [BlankFragment_test_fuji.newInstance] factory method to
 * create an instance of this fragment.
 */
class BlankFragment_test_fuji : Fragment() {

    // TODO: Rename and change types of parameters
    private var mParam1: String? = null
    private var mParam2: String? = null

    private var mListener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.fragment_blank_fragment_test_fuji)


        if (arguments != null) {
            mParam1 = arguments.getString(ARG_PARAM1)
            mParam2 = arguments.getString(ARG_PARAM2)
        }

    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        return inflater!!.inflate(R.layout.fragment_blank_fragment_test_fuji, container, false)
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        if (mListener != null) {
            mListener!!.onFragmentInteraction(uri)
        }
    }

    override fun onStart() {
        super.onStart()
        val dataArray = arrayOf("現在地付近","最寄り駅から選ぶ","住所を手動で選択")
        val adapter = ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, dataArray)
        listView.adapter = adapter
        listView.onItemClickListener=AdapterView.OnItemClickListener { adapterView, view, i, l ->
            when{
                i==0 ->{
                    Log.d("case i==0",i.toString())
                    //val transaction = fragmentManager.beginTransaction()
                    //transaction.replace(R.id.container, BlankFragment_test_fuji1.newInstance())
                    //transaction.addToBackStack(null)
                    //transaction.commit()
                }
                i==1->{
                    Log.d("case i==1",i.toString())
                }
                i==2->{
                    Log.d("case i==2",i.toString())
                }
            }
        //Log.d("test",i.toString())
        }

    }
    override fun onAttach(context: Context?) {
        super.onAttach(context)

    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html) for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private val ARG_PARAM1 = "param1"
        private val ARG_PARAM2 = "param2"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment BlankFragment_test_fuji.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(param1: String, param2: String): BlankFragment_test_fuji {
            val fragment = BlankFragment_test_fuji()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }
}// Required empty public constructor
