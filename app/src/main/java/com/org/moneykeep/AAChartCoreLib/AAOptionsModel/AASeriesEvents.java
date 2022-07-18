package com.org.moneykeep.AAChartCoreLib.AAOptionsModel;

import com.org.moneykeep.AAChartCoreLib.AATools.AAJSStringPurer;

public class AASeriesEvents {
    public String legendItemClick;

    public AASeriesEvents legendItemClick(String prop) {
        String pureJSFunctionStr = "(" + prop + ")";
        pureJSFunctionStr = AAJSStringPurer.pureJavaScriptFunctionString(pureJSFunctionStr);
        legendItemClick = pureJSFunctionStr;
        return this;
    }

}
