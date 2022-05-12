package com.hmekhatib.currencyexchange
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.androidplot.xy.*
import com.hmekhatib.currencyexchange.api.ExchangeService
import com.hmekhatib.currencyexchange.api.model.Graph
import com.hmekhatib.currencyexchange.api.model.Stat
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.FieldPosition
import java.text.Format
import java.text.ParsePosition
import java.util.*

class InsightsFragment : Fragment() {

    private var stat1: TextView? = null
    private var stat2: TextView? = null
    private var stat3: TextView? = null
    private var stat4: TextView? = null
    private var stat5: TextView? = null
    private var stat6: TextView? = null
    private var plot: XYPlot? = null
    private var viewR: View? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(
            R.layout.fragment_stats,
            container, false
        )
        stat1 = view.findViewById(R.id.txtOneHChange)
        stat2 = view.findViewById(R.id.txtTwelveHChange)
        stat3 = view.findViewById(R.id.txtTwentyFourHChange)
        stat4 = view.findViewById(R.id.txtSevenDayChange)
        stat5 = view.findViewById(R.id.txtSellRate)
        stat6 = view.findViewById(R.id.txtBuyRate)
        plot = view.findViewById(R.id.plot)
        getStatsS(view)
        return view;


    }

    fun getStatsS(vw: View) {
        ExchangeService.exchangeApi().getStatsSell().enqueue(object :
            Callback<Stat> {
            override fun onResponse(call: Call<Stat>, response:
            Response<Stat>) {
                val responseBody: Stat? = response.body();
                var oneH = responseBody?.OneHChange.toString();
                var tH = responseBody?.TwelveHChange.toString();
                var thH = responseBody?.TwentyFourHChange.toString();
                var sD = responseBody?.SevenDChange.toString();
                var minR = responseBody?.minRate.toString();
                var maxR = responseBody?.maxRate.toString();
                stat1?.text = oneH+"%";
                stat2?.text = tH+"%";
                stat3?.text = thH+"%";
                stat4?.text = sD+"%";
                stat5?.text = minR+" / "+maxR;
                getStatsB(vw)
            }
            override fun onFailure(call: Call<Stat>, t: Throwable) {
                return;
                TODO("Not yet implemented")
            }
        })

    }

    fun getStatsB(vw: View) {
        ExchangeService.exchangeApi().getStatsBuy().enqueue(object :
            Callback<Stat> {
            override fun onResponse(call: Call<Stat>, response:
            Response<Stat>) {
                val responseBody: Stat? = response.body();
                var oneHS = responseBody?.OneHChange.toString();
                var tHS = responseBody?.TwelveHChange.toString();
                var thHS = responseBody?.TwentyFourHChange.toString();
                var sD = responseBody?.SevenDChange.toString();
                var minR = responseBody?.minRate.toString();
                var maxR = responseBody?.maxRate.toString();
                stat1?.text = stat1?.text.toString()+" / "+oneHS+"%";
                stat2?.text = stat2?.text.toString()+" / "+tHS+"%";
                stat3?.text = stat3?.text.toString()+" / "+thHS+"%";
                stat4?.text = stat4?.text.toString()+" / "+sD+"%";
                stat6?.text = minR+" / "+maxR;
                getPlotS(vw);
            }
            override fun onFailure(call: Call<Stat>, t: Throwable) {
                return;
                TODO("Not yet implemented")
            }
        })

    }

    private fun getPlotS(vw: View){
        ExchangeService.exchangeApi().getGraph().enqueue(object : Callback<Graph> {
            override fun onResponse(call: Call<Graph>, response: Response<Graph>) {
                val responseBody: Graph? = response.body();
                var xAxis = responseBody?.xaxis;
                var yAxis1 = responseBody?.yaxisB;
                var yAxis2 = responseBody?.yaxisS;
                //GRAPH SHIT STARTS
                val domainLabels : Array<String> = xAxis!!.toTypedArray();
                val series1Number : Array<Number> = yAxis1!!.toTypedArray();
                val series2Number : Array<Number> = yAxis2!!.toTypedArray();
                val series1 : XYSeries = SimpleXYSeries(Arrays.asList(*series1Number),SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "Buy Rate");
                val series2 : XYSeries = SimpleXYSeries(Arrays.asList(*series2Number),SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "Sell Rate");
                val series1Format = LineAndPointFormatter(Color.BLUE,Color.BLACK,null,null);
                val series2Format = LineAndPointFormatter(Color.RED,Color.BLACK,null,null);
                series1Format.setInterpolationParams(CatmullRomInterpolator.Params(10, CatmullRomInterpolator.Type.Uniform))
                series2Format.setInterpolationParams(CatmullRomInterpolator.Params(10, CatmullRomInterpolator.Type.Uniform))
                val max: Float? = Math.max(yAxis1.maxOrNull()!!,yAxis2.maxOrNull()!!)
                val min: Float? = Math.min(yAxis1.minOrNull()!!,yAxis2.minOrNull()!!)
                plot?.setRangeBoundaries(min!!-0.5,max!!+0.5,BoundaryMode.FIXED);
                plot?.addSeries(series1,series1Format)
                plot?.addSeries(series2,series2Format)
                plot?.graph?.getLineLabelStyle(XYGraphWidget.Edge.BOTTOM)?.format = object : Format() {
                    override fun format(p0: Any?, p1: StringBuffer?, p2: FieldPosition?): StringBuffer? {
                        val i = Math.round((p0 as Number).toFloat())
                        if (p1 != null) {
                            return p1.append(domainLabels[i])
                        }
                        return p1;
                    }
                    override fun parseObject(p0: String?, p1: ParsePosition?): Any? {
                        return null;
                    }
                }


            }

            override fun onFailure(call: Call<Graph>, t: Throwable) {
                return;
            }
        })

        viewR = vw;
    }

    }

