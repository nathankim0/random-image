package com.jinyeob.nathanks

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.jinyeob.nathanks.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        /*
        lifecycleScope.launch {
            mainViewModel.getMyData().onSuccess {
                binding.textView.text = it.toString()
            }.onFailure {
                binding.textView.text = it.toString()
            }
        }
        */

        lifecycleScope.launch {
            mainViewModel.getMyDataFlow().collect {
                binding.textView.text = it.toString()
            }
        }

        setContentView(binding.root)
    }
}
