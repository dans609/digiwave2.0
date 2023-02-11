package com.dash.projects.android.digiwave.views.features.logate

import android.content.Context
import android.os.Bundle
import android.widget.AutoCompleteTextView
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import com.dash.projects.android.digiwave.R
import com.dash.projects.android.digiwave.`object`.DrawableDropdownGenerator
import com.dash.projects.android.digiwave.`object`.ToolbarPreferences
import com.dash.projects.android.digiwave.`object`.utils.Utils.and
import com.dash.projects.android.digiwave.`object`.utils.Utils.arrayRes
import com.dash.projects.android.digiwave.`object`.utils.Utils.callToast
import com.dash.projects.android.digiwave.`object`.utils.Utils.castToBoolean
import com.dash.projects.android.digiwave.`object`.utils.Utils.clickEvent
import com.dash.projects.android.digiwave.`object`.utils.Utils.drawableRes
import com.dash.projects.android.digiwave.`object`.utils.Utils.inverse
import com.dash.projects.android.digiwave.`object`.utils.Utils.nand
import com.dash.projects.android.digiwave.`object`.utils.Utils.nor
import com.dash.projects.android.digiwave.`object`.utils.Utils.observeTextView
import com.dash.projects.android.digiwave.`object`.utils.Utils.or
import com.dash.projects.android.digiwave.`object`.utils.Utils.str
import com.dash.projects.android.digiwave.`object`.utils.Utils.toi
import com.dash.projects.android.digiwave.`object`.utils.Utils.xnor
import com.dash.projects.android.digiwave.`object`.utils.Utils.xor
import com.dash.projects.android.digiwave.adapter.features.logate.DropdownAdapter
import com.dash.projects.android.digiwave.databinding.ActivityLogicGateBinding
import com.dash.projects.android.digiwave.databinding.LayoutLogicGateBinding
import com.dash.projects.android.digiwave.factory.viewmodel.ViewModelFactory
import com.dash.projects.android.digiwave.model.DrawableDropdownItem
import com.dash.projects.android.digiwave.sealed.BinaryState
import com.dash.projects.android.digiwave.views.features.logate.viewmodel.LogicGateViewModel
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable

class LogicGateActivity : AppCompatActivity() {
    private var gateDrawableRes = R.drawable.ic_and

    private val mGateList = ArrayList<DrawableDropdownItem>()
    private val binding by lazy(LazyThreadSafetyMode.NONE) {
        ActivityLogicGateBinding.inflate(layoutInflater)
    }

    private val incBinding by lazy(LazyThreadSafetyMode.NONE) {
        binding.incLayoutLogate
    }

    private val viewModel by lazy(LazyThreadSafetyMode.NONE) {
        val mFactory = ViewModelFactory.getInstance()
        ViewModelProvider(this, mFactory)[LogicGateViewModel::class.java]
    }

    private val disposables by lazy(LazyThreadSafetyMode.NONE) {
        CompositeDisposable()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        ToolbarPreferences(this)
            .hideStatusBar(window)

        val dropdownItemGenerator = DrawableDropdownGenerator.getInstance(this)
        mGateList.addAll(dropdownItemGenerator.generateDropdownItems())

        incBinding.apply {
            viewModel.also { vm ->
                observeInput(this@LogicGateActivity, vm.inputA(), vm.inputB())
                disposables.addAll(
                    tvGateInp1.clickEvent().subscribe { vm.inputA(true) },
                    tvGateInp2.clickEvent().subscribe { vm.inputB(true) },
                )

                imageSimulation.setOnClickListener {
                    vm.play(true)
                    it.callToast(if (vm.play().value == true) "Started" else "Stopped")
                }

                vm.play().observe(this@LogicGateActivity) { isPlay ->
                    imageSimulation.setDrawable(isPlay, R.drawable.ic_stop, R.drawable.ic_play)

                    val isOn = gateOutput(tvGateInp1.toi(), tvGateInp2.toi()).castToBoolean()
                    imageOutput.setDrawable(
                        isPlay && isOn,
                        R.drawable.ic_lamp_on,
                        R.drawable.ic_lamp_off
                    )
                }
            }

            disposables.add(Observable.combineLatest(
                tvGateInp1.observeTextView().map(String::toInt),
                tvGateInp2.observeTextView().map(String::toInt),
            ) { p1, p2 ->
                if (viewModel.play().value == true) gateOutput(p1, p2) else 0
            }.subscribe {
                imageOutput.setDrawable(
                    it.castToBoolean(),
                    R.drawable.ic_lamp_on,
                    R.drawable.ic_lamp_off
                )
            })
        }
    }

    private fun gateOutput(inputA: Int, inputB: Int) = when (gateDrawableRes) {
        R.drawable.ic_and -> and(inputA, inputB)
        R.drawable.ic_or -> or(inputA, inputB)
        R.drawable.ic_not -> inverse(inputA)
        R.drawable.ic_nand -> nand(inputA, inputB)
        R.drawable.ic_nor -> nor(inputA, inputB)
        R.drawable.ic_xor -> xor(inputA, inputB)
        R.drawable.ic_xnor -> xnor(inputA, inputB)
        else -> 0
    }

    private fun ImageView.setDrawable(
        cond: Boolean,
        @DrawableRes ifMeet: Int,
        @DrawableRes ifNot: Int
    ) = setImageResource(if (cond) ifMeet else ifNot)

    override fun onResume() {
        super.onResume()
        incBinding.autoTvGates.showDropdown(this, 0, mGateList)
    }

    private fun AutoCompleteTextView.showDropdown(
        context: Context, @LayoutRes resource: Int, objects: Collection<DrawableDropdownItem>
    ) = DropdownAdapter(context, resource, objects.toTypedArray()).let { adapter ->
        setAdapter(adapter)
        setOnItemClickListener { av, v, i, _ ->
            // this code's used for terminate the simulation when dropdown menu got clicked
            if (viewModel.play().value == true) {
                viewModel.play(true)
            }

            val itemName = str(av.getItemAtPosition(i))
            val arrayItem = arrayRes(R.array.gate_list)
            val drawablePairs: Pair<Int, Int> = when (itemName) {
                arrayItem[0] -> Pair(R.drawable.ic_and, R.drawable.ic_and_inp)
                arrayItem[1] -> Pair(R.drawable.ic_or, R.drawable.ic_or_inp)
                arrayItem[2] -> Pair(R.drawable.ic_not, R.drawable.ic_not_inp)
                arrayItem[3] -> Pair(R.drawable.ic_nand, R.drawable.ic_nand_inp)
                arrayItem[4] -> Pair(R.drawable.ic_nor, R.drawable.ic_nor_inp)
                arrayItem[5] -> Pair(R.drawable.ic_xor, R.drawable.ic_xor_inp)
                arrayItem[6] -> Pair(R.drawable.ic_xnor, R.drawable.ic_xnor_inp)
                else -> Pair(R.drawable.ic_and, R.drawable.ic_and_inp)
            }

            incBinding.apply {
                tilDropdown.startIconDrawable = drawableRes(mGateList[i].itemImage)
                imageGate.setImageResource(drawablePairs.second)
                gateDrawableRes = drawablePairs.first
            }

            v.callToast(itemName)
        }
    }

    private fun LayoutLogicGateBinding.observeInput(
        lo: LifecycleOwner,
        inputA: LiveData<BinaryState>,
        inputB: LiveData<BinaryState>,
    ) {
        inputA.setTextState(lo, tvGateInp1)
        inputB.setTextState(lo, tvGateInp2)
    }

    private fun LiveData<BinaryState>.setTextState(lo: LifecycleOwner, tv: TextView) = observe(lo) {
        tv.text = when (it) {
            is BinaryState.StateOff -> str(it.value)
            is BinaryState.StateOn -> str(it.value)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mGateList.clear()
    }
}
