编写Apache Hive用户自定义函数（UDF）有两个不同的接口，一个非常简单，另一个...就相对复杂点。
	如果你的函数读和返回都是基础数据类型（Hadoop&Hive 基本writable类型，如Text,IntWritable,LongWriable,DoubleWritable等等），
	那么简单的API（org.apache.hadoop.hive.ql.exec.UDF）可以胜任
	但是，如果你想写一个UDF用来操作内嵌数据结构，如Map，List和Set，
	那么你要去熟悉org.apache.hadoop.hive.ql.udf.generic.GenericUDF这个API

简单API： org.apache.hadoop.hive.ql.exec.UDF
复杂API：  org.apache.hadoop.hive.ql.udf.generic.GenericUDF

接下来我将通过一个示例为上述两个API建立UDF，我将为接下来的示例提供代码与测试
如果你想浏览代码：fork it on Github：https://github.com/rathboma/hive-extension-examples

简单API
用简单UDF API来构建一个UDF只涉及到编写一个类继承实现一个方法（evaluate），以下是示例：