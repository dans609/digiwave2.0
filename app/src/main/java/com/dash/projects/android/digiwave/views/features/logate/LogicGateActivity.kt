package com.dash.projects.android.digiwave.views.features.logate

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AutoCompleteTextView
import androidx.annotation.LayoutRes
import com.dash.projects.android.digiwave.`object`.*
import com.dash.projects.android.digiwave.`object`.utils.Utils.callToast
import com.dash.projects.android.digiwave.`object`.utils.Utils.drawableRes
import com.dash.projects.android.digiwave.adapter.features.logate.DropdownAdapter
import com.dash.projects.android.digiwave.databinding.ActivityLogicGateBinding
import com.dash.projects.android.digiwave.model.DrawableDropdownItem

class LogicGateActivity : AppCompatActivity() {
    private val mGateList = ArrayList<DrawableDropdownItem>()
    private val binding by lazy(LazyThreadSafetyMode.NONE) {
        ActivityLogicGateBinding.inflate(layoutInflater)
    }

    private val incBinding by lazy(LazyThreadSafetyMode.NONE) {
        binding.incLayoutLogate
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        ToolbarPreferences(this)
            .hideStatusBar(window)

        mGateList.addAll(DrawableDropdownGenerator(this).generateDropdownItems())
    }

    override fun onResume() {
        super.onResume()
        incBinding.autoTvGates.showDropdown(this, 0, mGateList)
    }

    private fun AutoCompleteTextView.showDropdown(
        context: Context, @LayoutRes resource: Int, objects: Collection<DrawableDropdownItem>
    ) = DropdownAdapter(context, resource, objects.toTypedArray()).let { adapter ->
        setAdapter(adapter)
        setOnItemClickListener { av, v, i, _ ->
            incBinding.tilDropdown.startIconDrawable = drawableRes(mGateList[i].itemImage)
            v.callToast(av.getItemAtPosition(i).toString())
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mGateList.clear()
    }
}
