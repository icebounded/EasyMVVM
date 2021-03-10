package pers.icebounded.mvvm.demo.paging

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import pers.icebounded.mvvm.base.BaseMvvmActivity
import pers.icebounded.mvvm.demo.R
import pers.icebounded.mvvm.demo.databinding.ActivityPagingTestBinding

class PagingTestActivity : BaseMvvmActivity<ActivityPagingTestBinding, PagingTestViewModel>() {

    companion object {
        fun start(context: Context) {
            val starter = Intent(context, PagingTestActivity::class.java)
            context.startActivity(starter)
        }
    }

    override fun getVariableId(): Int = 2

    override fun getLayoutId(): Int = R.layout.activity_paging_test

    override fun bindView(view: View) {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        val pagingTestAdapter = PagingTestAdapter()
        binding.recyclerView.adapter = pagingTestAdapter
        viewModel.mDataList.observe(this){
            pagingTestAdapter.submitList(it)
        }
    }

}