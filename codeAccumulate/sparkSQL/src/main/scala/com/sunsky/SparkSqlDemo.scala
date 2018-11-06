package com.sunsky

import java.io.File
import java.text.SimpleDateFormat
import java.util.{Date, Properties}

import com.sunsky.entity.Person
import com.sunsky.udf.GetTime
import org.apache.hadoop.hbase.HBaseConfiguration
import org.apache.hadoop.hbase.client.{Put, Result}
import org.apache.hadoop.hbase.io.ImmutableBytesWritable
import org.apache.hadoop.hbase.mapreduce.TableOutputFormat
import org.apache.hadoop.hbase.util.Bytes
import org.apache.hadoop.mapreduce.Job
import org.apache.log4j.{Level, Logger}
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.{Row, SaveMode, SparkSession}
import org.apache.spark.sql.types._
import org.apache.spark.streaming.{Minutes, Seconds, StreamingContext}

object SparkSqlDemo {

  Logger.getLogger("org").setLevel(Level.ERROR)

  def main(args: Array[String]): Unit = {

    val con = new SparkConf().setAppName("sql").setMaster("local[3]")
    val spark = SparkSession
                .builder()//.master("local[3]")
                //.appName("Spark SQL basic example")
                .config(con)
                .getOrCreate()

//    createDFrame(spark)
//    untypedDataFrameOperations(spark)
//    createProgrammatically(spark)
//    createTemporaryView(spark)
//    creatingDatasets(spark)
//    rDDstoDataFrames(spark)
//    specifyingSchema(spark)
//    untypedUserDefinedAggregate(spark)
//    typeSafeUserDefinedAggregate(spark)


//    loadSaveFunctions(spark)

    matchTest(spark)

//    runSqlOnFile(spark)
//    manuallySpecifyingOptions(spark)

//    savingPersistTable(spark)

//    hiveTables()  //not success
//    readJDBCDataBase(spark)
//    jDBCToOtherDatabases(spark)
//    runSqlOnFile(spark)
//    getSchema(spark)
//    insertHbase(spark)

//    readStream(spark)

  }

  /**
    * create sparksession
    * @param name
    * @return
    */
  def getSparkContext(name:String) = {
    val con = new SparkConf().setAppName(name).setMaster("local")
    val sc = new SparkContext(con)
    sc
  }

  /**
    * create dataframe
    * @param spark
    */
  def createDFrame(spark:SparkSession) = {
    val df = spark.read.json("D://file//user.json")
    df.show()
  }

  /**
    * Untyped Dataset Operations (aka DataFrame Operations)
    * use dateset untyped APIs
    * @param spark
    */
  def untypedDataFrameOperations(spark:SparkSession) = {
    val df = spark.read.json("d://file//user.json")
    import spark.implicits._

    // Print the schema in a tree format
    df.printSchema()

    // Select only the "name" column
    df.select("name").show()

    // Select everybody, but increment the age by 1
    df.select($"name",$"age"+1).show()

    // Select people older than 21
    df.filter($"age" > 21).show()

    // Count people by age
    df.groupBy($"age").count().show()
    df.groupBy($"create_time").avg("age").show()
  }

  /**
    * Running SQL Queries Programmatically
    * @param spark
    */
  def createProgrammatically(spark : SparkSession) = {
    val df = spark.read.json("d://file//user.json")

    // Register the DataFrame as a SQL temporary view
    df.createOrReplaceTempView("people")

    val sqlDF = spark.sql("SELECT name,age,sex,create_time FROM people")

    sqlDF.show()
  }

  def createTemporaryView(spark:SparkSession) = {
    val df = spark.read.json("d://file//user.json")
    df.createTempView("people")
    spark.sql("select * from people").show()
  }

  /**
    * Creating Datasets
    * @param spark
    */
  def creatingDatasets(spark:SparkSession) = {

    import spark.implicits._
    // Encoders are created for case classes
    val seq = Seq(Person("zhangsan",25,"M","20170108"),Person("lisi",26,"F","20170108"))
    val rdd = spark.sparkContext.parallelize(seq)
    // Encoders are created for case classes
    val caseClassDS = rdd.toDS()
    caseClassDS.show()

    val ds = seq.toDS()
    ds.show()

    // Encoders for most common types are automatically provided by importing spark.implicits._
    val numSeqDS = Seq(1,2,4,5,6).toDS()
    numSeqDS.map(_+1).show()

    // DataFrames can be converted to a Dataset by providing a class. Mapping will be done by name
    val path = "d://file//user.json"
    val peopleDS = spark.read.json(path).as[Person]
    peopleDS.show()
  }

  /**
    * Interoperating with RDDs
    * Inferring the Schema Using Reflection
    * @param spark
    */
  def rDDstoDataFrames(spark:SparkSession) = {
    // For implicit conversions from RDDs to DataFrames
    import spark.implicits._
    // Create an RDD of Person objects from a text file, convert it to a Dataframe
    val rdd = spark.sparkContext.textFile("d://file//people.txt")
    val peopleDF = rdd.map(line => line.split("\\|"))
      .map(record => Person(record(0),record(1).trim.toInt,record(2),record(3))).toDF()

    peopleDF.createOrReplaceTempView("people")

    val teenagersDF = spark.sql("select name,age,sex,create_time from people where age between 3 and 19")

    teenagersDF.show()

    // The columns of a row in the result can be accessed by field index
    teenagersDF.map(teenager => "name: " + teenager(0)).show()
    // or by field name
    teenagersDF.map(teenager => "age: " + teenager.getAs[Int]("age")).show()

    val array = teenagersDF.schema.toArray
    println(array.length)
    val rowkeyIndex = array.zipWithIndex.filter(f => f._1.name == "age").head._2
    val otherFields = array.zipWithIndex.filter(f => f._1.name != "age")
    println(rowkeyIndex)
    println(otherFields)

    otherFields.foreach(
      field => {
        val st = field._1
        println(st)
        println(st.name+"==="+st.dataType+"==="+st.metadata)
      }
    )
  }


  /**
    * Programmatically Specifying the Schema
    * Create an RDD of Rows from the original RDD;
    * Create the schema represented by a StructType matching the structure of Rows in the RDD created in Step 1.
    * Apply the schema to the RDD of Rows via createDataFrame method provided by SparkSession.
    * @param spark
    */
  def specifyingSchema(spark : SparkSession) = {
    import spark.implicits._
    import org.apache.spark.sql.types._
    //create rdd from local file
    val rdd = spark.sparkContext.textFile("d://file//people.txt")
    //schema string
    val schemaString = "name age sex create_time"
    //create schema fileds
    val fields = schemaString.split(" ").map(fieldName => StructField(fieldName, StringType , true))
    //create schema struct
    val schema = StructType(fields)

    // Convert records of the RDD (people) to Rows
    val rowRDD = rdd
      .map(_.split("\\|"))
      .map(attributes => Row(attributes(0), attributes(1).trim , attributes(2) , attributes(3)))

    val peopleDF = spark.createDataFrame(rowRDD,schema)
    peopleDF.createOrReplaceTempView("people")

    val result = spark.sql("select * from people")

    spark.sql("select SUM(CAST(age AS INT)) as sum_age from people").show()

    result.map(attribute => attribute.getAs[String]("name")).show()
  }

  /**
    * Untyped User-Defined Aggregate Functions
    * @param spark
    */
  def untypedUserDefinedAggregate(spark:SparkSession) = {
    spark.udf.register("myavg",MyAverage)
    val df = spark.read.json("d://file//user.json")
    df.createOrReplaceTempView("people")
    df.show()
    spark.sql("select myavg(age) as avg_age ,create_time from people group by create_time").show()
  }

  /**
    * Type-Safe User-Defined Aggregate Functions
    * @param spark
    */
  def typeSafeUserDefinedAggregate(spark:SparkSession) = {
    import spark.implicits._
    val ds = spark.read.json("d://file//user.json").as[Person]
    ds.show()

    // Convert the function to a `TypedColumn` and give it a name
    val averageSalary = MyAggregateAverage.toColumn.name("average_salary")
    val result = ds.select(averageSalary)
    result.show()
    // +------------
  }

  /**
    * 该操作保存的是一个视图文件（只能用sparkSession去加载）
    * 用默认的写入方式
    * @param spark
    */
  def loadSaveFunctions(spark : SparkSession) = {
    val usersDF = spark.read.load("d://file//useAndSex.parquet") //加载保存的视图
//    val usersDF = spark.read.json("D://file//user.json")
    usersDF.show()
//    usersDF.select("name","age").write.save("d://outuser.parquet")  //保存视图
  }

  /**
    * Manually Specifying Options
    *
    * @param spark
    */
  def manuallySpecifyingOptions(spark:SparkSession) = {
    val userDF = spark.read.format("json").load("D://file//user.json")
    userDF.select("name","sex").write.format("parquet").mode("append").save("d://file//useAndSex.parquet")
  }

  /**
    * Run SQL on files directly
    * @param spark
    * @return
    */
  def runSqlOnFile(spark:SparkSession) = {
    val sqlDF = spark.sql("select * from parquet.`d://file//useAndSex.parquet`")
    sqlDF.show()
  }

  /**
    * Bucketing, Sorting and Partitioningde 的操作
    * @param sparkSession
    */
  def savingPersistTable(sparkSession: SparkSession) = {
//    val userDF = sparkSession.read.json("d://file//user.json")  //read json
//    userDF.write.bucketBy(2,"name").sortBy("age").option("path","d://file//persist").saveAsTable("user")  //save table
    val readUserDF = sparkSession.read.load("d://file//persist")  //read table

    readUserDF.printSchema()
    readUserDF.show()
  }

  /**
    * not success
    */
  def hiveTables() = {
    val warehouseLocation = new File("D:\\file\\spark-warehouse").getAbsolutePath
    val spark = SparkSession
      .builder()
      .appName("Spark Hive Example")
      .config("spark.sql.warehouse.dir", warehouseLocation)
      .enableHiveSupport()
      .getOrCreate()

    import spark.implicits._
    import spark.sql

    sql("CREATE TABLE IF NOT EXISTS src (name STRING,age INT,sex STRING, create_time STRING) ROW FORMAT DELIMITED FIELDS TERMINATED BY '\\|' USING hive")
    sql("LOAD DATA LOCAL INPATH 'D:\\file\\spark-warehouse\\people.txt' INTO TABLE src")

    sql("SELECT * FROM src").show()

  }

  def readJDBCDataBase(sparkSession: SparkSession) = {
    val jdbcDF = sparkSession.read.format("jdbc")
      .option("url","jdbc:mysql://localhost:3306/bigdata")
      .option("dbtable","bigdata.user")
      .option("user","root")
      .option("password","sunsky667")
      .load()

    jdbcDF.show()

    /****************another method*******************/
    val connectionProperties = new Properties()
    connectionProperties.put("user", "root")
    connectionProperties.put("password", "sunsky667")
    val jdbcDF1 = sparkSession.read.jdbc("jdbc:mysql://localhost:3306/bigdata","user",connectionProperties)
    jdbcDF1.show()
  }

  /**
    * 通过jdbc的方式写入的mysql或者oracle
    * @param spark
    */
  def jDBCToOtherDatabases(spark:SparkSession) = {
    val userDF = spark.read.format("json").load("D://file//user.json")
    userDF.createOrReplaceTempView("user")
    val selectUser = spark.sql("select name,age from user")


    val connectionProperties = new Properties()
    connectionProperties.put("user", "root")
    connectionProperties.put("password", "sunsky667")
    selectUser.write.mode(SaveMode.Append).jdbc("jdbc:mysql://localhost:3306/bigdata","user",connectionProperties)


    /****************another method*******************/
//    selectUser.write   //fuck this , not success
//      .format("jdbc")
//      .option("url", "jdbc:mysql://localhost:3306/bigdata")
//      .option("dbtable", "bigdata.user")
//      .option("user", "root")
//      .option("password", "sunsky667")
//      .save()
  }

  //测试用
  def getSchema(spark:SparkSession) = {
    val userDF = spark.read.format("json").load("D://file//user.json")
    val fileds = userDF.schema.toArray
    val name = fileds.zipWithIndex.filter(n => n._1.name == "name").head._2
    val other = fileds.zipWithIndex.filter(n => n._1.name != "name")
    println("======="+name)
    println("========"+other)

    userDF.createOrReplaceTempView("user")
    val selectUser = spark.sql("select name,age,sex from user")

    val sQLContext = spark.sqlContext

    sQLContext.udf.register("getDate",(input:String) => getDate(input))

//    spark.udf.register("bbb",getDate(_))

    def getDate(t1: String): String = {
      val sdf = new SimpleDateFormat(t1)
      val date = new Date()
      sdf.format(date)
    }

    selectUser.createOrReplaceTempView("selectUser")
    spark.sql("select name as rowkey, concat(age,'|',sex) as coloum , getDate(\"yyyyMMdd\") as time from selectUser").show()

  }


  //测试用
  def matchTest(spark:SparkSession) = {
    val userDF = spark.read.format("json").load("D://file//user.json")
    val fileds = userDF.schema.toArray

    userDF.createOrReplaceTempView("user")
    val selectUser = spark.sql("select name,age,sex from user")

    val sQLContext = spark.sqlContext

//    sQLContext.udf.register("getDate",(input:String) => getDate(input))

    spark.udf.register("getDate",getDate(_:String))

      def getDate(t1: String): String = {
//        t1 match {
//          case "00" => "fuck"
//          case "05" => "dd"
//          case _ => "other"
//        }

        if(t1.equals("00")){
          "fuck"
        }else if(t1.equals("05")){
          "dd"
        }else{
          "other"
        }
      }

    selectUser.createOrReplaceTempView("selectUser")
    spark.sql("select name as rowkey, concat(age,'|',sex) as coloum , getDate(\"yyyyMMdd\") as time from selectUser").show()

  }

  //写入到hbase
  def insertHbase(spark:SparkSession) = {
    val userDF = spark.read.format("json").load("D://file//user.json")

    val hc = HBaseConfiguration.create()
    hc.set("hbase.zookeeper.quorum","192.168.80.128:2181")
    hc.set("zookeeper.znode.parent","/hbase")
    hc.set(TableOutputFormat.OUTPUT_TABLE,"userinfo")
    val job = Job.getInstance(hc)
    job.setOutputKeyClass(classOf[ImmutableBytesWritable])
    job.setOutputValueClass(classOf[Result])
    job.setOutputFormatClass(classOf[TableOutputFormat[ImmutableBytesWritable]])

    userDF.createOrReplaceTempView("user")
    val selectUser = spark.sql("select name as rowkey ,concat(age,'|',sex) as coloum from user")

    val fileds = selectUser.schema.toArray
    val rowkeyIndex = fileds.zipWithIndex.filter(n => n._1.name == "rowkey").head._2
    val otherFields = fileds.zipWithIndex.filter(n => n._1.name != "rowkey")

    val rdd = selectUser.rdd

    def convertToPut(row: Row) = {

      val put = new Put(Bytes.toBytes(row.getString(rowkeyIndex)))
      otherFields.foreach { field =>
        if(row.get(field._2) != null) {
          val st = field._1
          val datatype = if (true) {
            //StringType
            val name = "StringType"
            name match {
              case "StringType" => DataTypes.StringType
              case "BinaryType" => DataTypes.BinaryType
              case _ => DataTypes.StringType
            }
          } else st.dataType
          val c = st.dataType match {
            case StringType => Bytes.toBytes(row.getString(field._2))
            case FloatType => Bytes.toBytes(row.getFloat(field._2))
            case DoubleType => Bytes.toBytes(row.getDouble(field._2))
            case LongType => Bytes.toBytes(row.getLong(field._2))
            case IntegerType => Bytes.toBytes(row.getInt(field._2))
            case BooleanType => Bytes.toBytes(row.getBoolean(field._2))
           case BinaryType => row.getAs[Array[Byte]](field._2)
            //            case ArrayType => Bytes.toBytes(row.getList(field._2).mkString(","))
            //            case DecimalType.BigIntDecimal => Bytes.toBytes(row.getDecimal(field._2))
            case _ => Bytes.toBytes(row.getString(field._2))
          }
          put.addColumn(Bytes.toBytes("info"), Bytes.toBytes(field._1.name), c)
        }
      }
      (new ImmutableBytesWritable, put)
    }

    rdd.map(convertToPut).saveAsNewAPIHadoopDataset(job.getConfiguration)

  }


  /**
    * struct streaming
    * 可参照com.sunsky.structedstreaming.App
    * @param sparkSession
    */
  def readStream(sparkSession: SparkSession) = {

    val map = Map("a"->1,"b"->2,"c"->3,"d"->4)
    (map - "a" - "b" - "c").map(f => (f._1,f._2)).foreach(
      r => println(r._1+r._2)
    )

    import sparkSession.implicits._

    val lines = sparkSession.readStream.format("socket").option("host","192.168.80.128").option("port",9999).load()

    // Split the lines into words
    val words = lines.as[String].flatMap(_.split(" "))

    // Generate running word count
    val wordCounts = words.groupBy("value").count()

    // Start running the query that prints the running counts to the console
    val query = wordCounts.writeStream
      .outputMode("complete")
      .format("console")
      .start()

    query.awaitTermination()
  }
}
