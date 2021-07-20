package com.github.fashionbrot;

import org.apache.commons.math3.stat.regression.OLSMultipleLinearRegression;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * java
 * 多元线性回归分析 demo
 */
public class OLSMultipleLinearRegressionTest {

    public static void main(String[] args) {
        List<Double[]> x2List=new ArrayList<>(4);
        x2List.add(new Double[]{2D,1D});
        x2List.add(new Double[]{6D,0D});
        x2List.add(new Double[]{8D,1D});
        x2List.add(new Double[]{3D,0D});

        List<Double> yList =new ArrayList<>(4);
        yList.add(2.9D);
        yList.add(3.0D);
        yList.add(4.8D);
        yList.add(1.8D);



        double[][] xx = parseList2(x2List);
        double[] y = parseList(yList);

        OLSMultipleLinearRegression omlr=new OLSMultipleLinearRegression();
        omlr.newSampleData(y,xx);
        //得到系数
        //按顺序依次代表回归方程中的常量、x1系数、x2系数
        //y=0.8999999999999999+0.33333333333333326x1+1.2833333333333339x2
        double[] result = omlr.estimateRegressionParameters();
        for (int j=0;j<result.length;j++){
            System.out.println(result[j]);
        }
        //该方程的标准差S：
        double s = omlr.estimateRegressionStandardError();
        //判定系数R2:
        double rSquared = omlr.calculateRSquared();
    }

    private static double[] parseList(List<Double> list){
        if (!CollectionUtils.isEmpty(list)){
            double[] d=new double[list.size()];
            for(int i=0;i<list.size();i++){
                d[i]= list.get(i);
            }
            return d;
        }
        return null;
    }

    private static double[][] parseList2(List<Double[]> list){
        if (!CollectionUtils.isEmpty(list)){
            double[][] d=new double[list.size()][2];
            for(int i=0;i<list.size();i++){
                Double[] x= list.get(i);
                double[] xx=new double[]{x[0],x[1]};
                d[i]=xx;
            }
            return d;
        }
        return null;
    }

}
