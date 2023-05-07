we get bike crime ratio by copying the crimecount from crimesortbylatitude.cvs and past it into bikesortbylatitude.cvs, then rename it as bikecrimratio.cvs


following are the trendline for crimebikeratio:


Hdfs dfs -put bus_crime_ratio.csv

spark-shell --deploy-mode client

val buscrimeRDD = sc.textFile("buscrimeratio.csv")

val NoHeader = buscrimeRDD.map{x => x.split(',')}.map{x => (x(0).toDouble, x(1).toDouble)}

val df = NoHeader.toDF("crime", "bus")

import org.apache.spark.ml.Pipeline
import org.apache.spark.ml.feature.VectorAssembler
import org.apache.spark.ml.regression.{LinearRegression, LinearRegressionModel}

val features = new VectorAssembler().setInputCols(Array("crime")).setOutputCol("features")

val lr = new LinearRegression().setLabelCol("bus")

val pipeline = new Pipeline().setStages(Array(features, lr))

val model = pipeline.fit(df)

val linRegModel = model.stages(1).asInstanceOf[LinearRegressionModel]

println(s"RMSE:  ${linRegModel.summary.rootMeanSquaredError}")

println(s"r2:    ${linRegModel.summary.r2}")

println(s"Model: Y = ${linRegModel.coefficients(0)} * X + ${linRegModel.intercept}")


below are code for trenline for bikecrimeratio.cvs trendline:

BIKE_CRIME_RATIO

Hdfs dfs -put bus_crime_ratio.csv

spark-shell --deploy-mode client

val bikecrimeRDD = sc.textFile("bike_crime_ratio.csv")

val header = bikecrimeRDD.first()

val bikecrime = bikecrimeRDD.filter(line => !line.equals(header))

val NoHeader = bikecrime.map{x => x.split(',')}.map{x => (x(0).toDouble, x(1).toDouble)}

val df = NoHeader.toDF("crime", "bike")

import org.apache.spark.ml.Pipeline
import org.apache.spark.ml.feature.VectorAssembler
import org.apache.spark.ml.regression.{LinearRegression, LinearRegressionModel}

val features = new VectorAssembler().setInputCols(Array("crime")).setOutputCol("features")

val lr = new LinearRegression().setLabelCol("bike")

val pipeline = new Pipeline().setStages(Array(features, lr))

val model = pipeline.fit(df)

val linRegModel = model.stages(1).asInstanceOf[LinearRegressionModel]

println(s"RMSE:  ${linRegModel.summary.rootMeanSquaredError}")

println(s"r2:    ${linRegModel.summary.r2}")

println(s"Model: Y = ${linRegModel.coefficients(0)} * X + ${linRegModel.intercept}")













