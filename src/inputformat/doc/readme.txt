
//https://www.coder4.com/archives/4031
Hive中，默认使用的是TextInputFormat，一行表示一条记录。在每条记录(一行中)，默认使用^A分割各个字段。
在有些时候，我们往往面对多行，结构化的文档，并需要将其导入Hive处理，
此时，就需要自定义InputFormat、OutputFormat，以及SerDe了。

首先来理清这三者之间的关系，我们直接引用Hive官方说法：
	SerDe is a short name for “Serializer and Deserializer.”
	Hive uses SerDe (and !FileFormat) to read and write table rows.
	HDFS files –> InputFileFormat –> <key, value> –> Deserializer –> Row object
	Row object –> Serializer –> <key, value> –> OutputFileFormat –> HDFS files
总结一下，当面临一个HDFS上的文件时，Hive将如下处理（以读为例）：
	(1) 调用InputFormat，将文件切成不同的文档。每篇文档即一行(Row)。
	(2) 调用SerDe的Deserializer，将一行(Row)，切分为各个字段。

当HIVE执行INSERT操作，将Row写入文件时，主要调用OutputFormat、SerDe的Seriliazer，顺序与读取相反。
本文将对InputFormat、OutputFormat、SerDe自定义，使Hive能够与自定义的文档格式进行交互：
file/doc.txt
如上所示，每篇文档用<DOC>和</DOC>分割。文档之中的每行，为key=value的格式。