## 阿里面试给的小Proj，限期1个星期完成，具体要求如下
### 写一段程序实现一个数据导出的功能，程序运行后能把指定数据库中指定条件数据导出生成文件到指定目录。
- ### 要求 支持一次性导出1000W条。
- ### 要求 支持多种格式导出excle,csv。
- ### 要求 数据准确有序，越快越好。
- ### 要求 完整代码功能，代码运行后可以直接生成文件，数据库可以用文件等NOSQL等集成代替。
- ### 支持分布式系统加分点。

## 整个实现过程只要分为两个部分实现。
### 第一是如何对数据库进行查询，查询该怎么优化。
### 第二是将数据库查询的数据生成目标文件，这个过程可能会涉及到数据的处理包括外链接，外排序等。因为面对的是海量数据，所以内存肯定不能满足需求一次性将数据全部导入。