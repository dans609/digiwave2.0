package com.dash.projects.android.digiwave.views.features.logate

import android.content.Context
import android.os.Bundle
import android.view.View
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
    private var selectedGateRes = Pair(R.drawable.ic_and, R.drawable.ic_and_inp)

    private val mGateList = ArrayList<DrawableDropdownItem>()
    private val binding by lazy(LazyThreadSafetyMode.NONE) {
        ActivityLogicGateBinding.inflate(layoutInflater)
    }

    private val incBinding by lazy(LazyThreadSafetyMode.NONE) {
        binding.incLayoutLogate
    }

    private val firstInputTranslationY by lazy(LazyThreadSafetyMode.NONE) {
        incBinding.tvGateInp1.translationY
    }

    private val viewModel by lazy(LazyThreadSafetyMode.NONE) {
        val mFactory = ViewModelFactory.getInstance()
        ViewModelProvider(this, mFactory)[LogicGateViewModel::class.java]
    }

    private val disposables by lazy(LazyThreadSafetyMode.NONE) {
        CompositeDisposable()
    }

    private val gateNames by lazy(LazyThreadSafetyMode.NONE) {
        arrayRes(R.array.gate_list)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        ToolbarPreferences(this).hideStatusBar(window)

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
                    it.callToast(
                        if (vm.play().value == true) R.string.play_condition
                        else R.string.stop_condition
                    )
                }

                vm.play().observe(this@LogicGateActivity) { isPlay ->
                    imageSimulation.setDrawable(isPlay, R.drawable.ic_stop, R.drawable.ic_play)

                    val isOn = gateOutput(tvGateInp1.toi(), tvGateInp2.toi()).castToBoolean()
                    imageOutput.setDrawable(
                        isPlay && isOn, R.drawable.ic_lamp_on, R.drawable.ic_lamp_off
                    )
                }

                vm.getGatePairs().observe(this@LogicGateActivity) { gatePairs ->
                    if (gatePairs.toList().none { it == 0 }) selectedGateRes = gatePairs

                    /**
                     * When the statement above is false
                     * (in this case the value of [gatePairs] component is zero (0))
                     *
                     * Therefore, in the code below uses the initial or last changed value of [selectedGateRes]
                     * instead of [gatePairs] value for updating the gate UI. Otherwise, an exception will occur.
                     */
                    incBinding.apply {
                        tilDropdown.startIconDrawable = drawableRes(selectedGateRes.first)
                        imageGate.setImageResource(selectedGateRes.second)
                        when (selectedGateRes.first) {
                            R.drawable.ic_not -> {
                                tvGateInp2.visibility = View.GONE
                                tvGateInp1.translationY = firstInputTranslationY * 0.05f
                            }
                            else -> {
                                tvGateInp2.visibility = View.VISIBLE
                                tvGateInp1.translationY = firstInputTranslationY
                            }
                        }
                    }
                }
            }

            disposables.add(Observable.combineLatest(
                tvGateInp1.observeTextView().map(String::toInt),
                tvGateInp2.observeTextView().map(String::toInt),
            ) { p1, p2 ->
                if (viewModel.play().value == true) gateOutput(p1, p2) else 0
            }.subscribe {
                imageOutput.setDrawable(
                    it.castToBoolean(), R.drawable.ic_lamp_on, R.drawable.ic_lamp_off
                )
            })
        }
    }

    private fun gateOutput(inputA: Int, inputB: Int) = when (selectedGateRes.first) {
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
        cond: Boolean, @DrawableRes ifMeet: Int, @DrawableRes ifNot: Int
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
            when (itemName) {
                gateNames[0] -> Pair(R.drawable.ic_and, R.drawable.ic_and_inp)
                gateNames[1] -> Pair(R.drawable.ic_or, R.drawable.ic_or_inp)
                gateNames[2] -> Pair(R.drawable.ic_not, R.drawable.ic_not_inp)
                gateNames[3] -> Pair(R.drawable.ic_nand, R.drawable.ic_nand_inp)
                gateNames[4] -> Pair(R.drawable.ic_nor, R.drawable.ic_nor_inp)
                gateNames[5] -> Pair(R.drawable.ic_xor, R.drawable.ic_xor_inp)
                gateNames[6] -> Pair(R.drawable.ic_xnor, R.drawable.ic_xnor_inp)
                else -> Pair(R.drawable.ic_and, R.drawable.ic_and_inp)
            }.apply { viewModel.setSelectedGateDrawables(first, second) }

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
