package com.udacity.asteroidradar.main

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.FragmentMainBinding
import com.udacity.asteroidradar.databinding.FragmentMainItemBinding

class MainFragment : Fragment() {

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentMainBinding.inflate(LayoutInflater.from(container?.context), container, false)
        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        // Sets the adapter of the photosGrid RecyclerView with clickHandler lambda that
        // tells the viewModel when our property is clicked
        binding.recyclerView.adapter = RecyclerViewAdapter(RecyclerViewAdapter.OnClickListener {
            viewModel.displayDetails(it)
        })

        /**
         * Observe the [navigateToSelectedAsteroid] LiveData and Navigate when it isn't null
         * After navigating, call [displayAsteroidDetailsComplete] so that the ViewModel is ready
         * for another navigation event.
         */
        viewModel.navigateToDetails.observe(viewLifecycleOwner, Observer {
            if ( null != it ) {
                // Must find the NavController from the Fragment
                this.findNavController().navigate(MainFragmentDirections.actionShowDetail(it))
                // Tell the ViewModel we've made the navigate call to prevent multiple navigation
                viewModel.displayDetailsComplete()
            }
        })
        binding.recyclerView.adapter = RecyclerViewAdapter(RecyclerViewAdapter.OnClickListener {
            viewModel.displayDetails(it)
        })
        setHasOptionsMenu(true)
        return binding.root
    }
    /**
     * Inflates the overflow menu that contains filtering options.
     */
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    /**
     * Updates the filter in the [MainViewModel] when the menu items are selected from the
     * overflow menu.
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return true
    }
}
