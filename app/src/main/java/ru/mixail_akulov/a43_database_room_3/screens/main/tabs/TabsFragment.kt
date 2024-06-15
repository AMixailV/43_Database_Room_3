package ru.mixail_akulov.a43_database_room_3.screens.main.tabs

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import ru.mixail_akulov.a43_database_room_3.R
import ru.mixail_akulov.a43_database_room_3.Repositories
import ru.mixail_akulov.a43_database_room_3.databinding.FragmentTabsBinding
import ru.mixail_akulov.a43_database_room_3.utils.viewModelCreator

class TabsFragment : Fragment(R.layout.fragment_tabs) {

    private lateinit var binding: FragmentTabsBinding

    private val viewModel by viewModelCreator { TabsViewModel(Repositories.accountsRepository) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentTabsBinding.bind(view)

        val navHost = childFragmentManager.findFragmentById(R.id.tabsContainer) as NavHostFragment
        val navController = navHost.navController
        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController)

        observeAdminTab()
    }

    private fun observeAdminTab() {
        viewModel.showAdminTab.observe(viewLifecycleOwner) { showAdminTab ->
            binding.bottomNavigationView.menu.findItem(R.id.admin).isVisible = showAdminTab
        }
    }

}