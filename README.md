# RDDs in Apache Spark
Resilient Distributed Datasets (RDD) is a fundamental data structure of Spark. It is an immutable distributed collection of objects. Each dataset in RDD is divided into logical partitions, which may be computed on different nodes of the cluster. RDDs can contain any type of Python, Java, or Scala objects, including user-defined classes.

Formally, an RDD is a read-only, partitioned collection of records. RDDs can be created through deterministic operations on either data on stable storage or other RDDs. RDD is a fault-tolerant collection of elements that can be operated on in parallel.

There are two ways to create RDDs − parallelizing an existing collection in your driver program, or referencing a dataset in an external storage system, such as a shared file system, HDFS, HBase, or any data source offering a Hadoop Input Format.

Spark makes use of the concept of RDD to achieve faster and efficient MapReduce operations. Let us first discuss how MapReduce operations take place and why they are not so efficient.

# Datasets and Dataframes
A Dataset is a distributed collection of data. Dataset is a new interface added in Spark 1.6 that provides the benefits of RDDs (strong typing, ability to use powerful lambda functions) with the benefits of Spark SQL’s optimized execution engine. A Dataset can be constructed from JVM objects and then manipulated using functional transformations (map, flatMap, filter, etc.). The Dataset API is available in Scala and Java. Python does not have the support for the Dataset API. But due to Python’s dynamic nature, many of the benefits of the Dataset API are already available (i.e. you can access the field of a row by name naturally row.columnName). The case for R is similar.

A DataFrame is a Dataset organized into named columns. It is conceptually equivalent to a table in a relational database or a data frame in R/Python, but with richer optimizations under the hood. DataFrames can be constructed from a wide array of sources such as: structured data files, tables in Hive, external databases, or existing RDDs. The DataFrame API is available in Scala, Java, Python, and R. In Scala and Java, a DataFrame is represented by a Dataset of Rows. In the Scala API, DataFrame is simply a type alias of Dataset[Row]. While, in Java API, users need to use Dataset<Row> to represent a DataFrame.

Throughout this document, we will often refer to Scala/Java Datasets of Rows as DataFrames.

# Spark Example

This project contains for running and testing applications locally using Apache Spark.

Apache Spark is fully testable locally using standard unit testing frameworks.

Unit testing is accepted engineering best practice, but is often not used.
Instead, a developer will either work from the Spark shell, copying code back
to their IDE when it works and piece it together, or develop locally and build 
a jar to deploy to the cluster for testing on the full dataset. These options are
time consuming, error prone, and not scalable beyond a single developer.

Writing unit tests allows you to test a variety of data, catch regressions, and 
greatly reduce the iteration cycle so it's possible to get more done with better quality.

## Project Overview

- Language: [Scala](https://www.scala-lang.org/)
- Framework: [Apache Spark](https://spark.apache.org/)
- Build tool: [SBT](https://www.scala-sbt.org/) 
- Testing Framework: [Scalatest](http://www.scalatest.org/)

## Code Overview

### Driver

`ExampleDriver` is a Spark `Driver` (or coordinator) that will run a Spark application

It defines: 
- a 'main' class that allows the Spark appliction
to be run using `spark-submit` 
- a function `readData` to load data from a datasource
- a function `process` to apply transformations to the data

Functions `readData' and `process` take as an argument a `Spark` object. This Spark object
will be different if the `ExampleDriver` is run on a real cluster or in the unit tests in the project.

### Test

`ExampleDriverTest` is a test for the Spark driver. It contains two tests,
one to assert we can read data and the other that we can apply a transformation
to the data.

## IDE Setup

- Download [Intellij IDEA Community Edition](https://www.jetbrains.com/idea/download/#section=mac)
- Install the `Scala` plugin in intellij ([plugin install instructions](https://www.jetbrains.com/help/idea/managing-plugins.html))
- From Intellij, open the `build.sbt` file in this directory with 'File' -> 'Open'. Opening the `build.sbt` file will ensure Intellij loads the project correctly
- When prompted, choose 'Open as Project'

## Running Tests

### From Intellij

Right click on `ExampleDriverTest` and choose `Run 'ExampleDriverTest'`

### From the command line

On Unix systems, test can be run:

```shell script
$ ./sbt test
```

or on Windows systems:

```shell script
C:\> ./sbt.bat test
```

## Configuring Logging

Spark uses log4j 1.2 for logging. Logging levels can be configured in the file `src/test/resources/log4j.properties`

Spark logging can be verbose, for example, it will tell you when each task starts and finishes as well
as resource cleanup messages. This isn't always useful or desired during regular development. To reduce the verbosity of logs,
change the line `log4j.logger.org.apache.spark=INFO` to `log4j.logger.org.apache.spark=WARN`

## Scala Worksheets

The worksheet `src/test/scala/com/spark/example/playground.sc` is a good place to try out Scala code. Add your code
to the left pane of the worksheet, click the 'play' button, and the result will display in the right pane.

Note: The worksheet will not work for Spark code.

## Documentation

* RDD: https://spark.apache.org/docs/latest/rdd-programming-guide.html
* Batch Structured APIs: https://spark.apache.org/docs/latest/sql-programming-guide.html
