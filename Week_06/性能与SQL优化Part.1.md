# 性能与SQL优化Part.1

## SQL

- DQL：数据查询语言，e.g. SELECT, WHERE, ORDER BY, HAVING
- DML：数据操作语言，e.g. INSERT, UPDATE, DELETE
- TCL：事务控制语言，e.g. COMMIT, SAVEPOINT, ROLLBACK
- DCL：数据控制语言，e.g. GRANT, REVOKE
- DDL：数据定义语言，e.g. CREATE TABLE, DROP TABLE
- CCL：指针控制语言，e.g. DECLARE CURSOR, FETCH INTO

## Mysql

__架构图__

![](/Users/zhaxu/Mine/GeekUniversity/JAVA-000/Week_06/images/mysql.png)

__存储__

- 独占模式
  - 日志组文件：ib_logfile0、ib_logfile1，默认5M
  - 表结构文件：*.frm
  - 独占表空间文件：*.ibd
  - 字符集和排序规则文件：db.opt
  - binlog二进制日志文件：记录主数据库服务器的DDL和DML操作
  - 二进制日志索引文件：master-bin.index
- 共享模式 innodb_file_per_table=1
  - ibdata1，数据都在里面

__流程__

<img src="/Users/zhaxu/Mine/GeekUniversity/JAVA-000/Week_06/images/mysql-flow.png" style="zoom:50%;" />

![](/Users/zhaxu/Mine/GeekUniversity/JAVA-000/Week_06/images/mysql-flow-detail.png)

__引擎__

![](/Users/zhaxu/Mine/GeekUniversity/JAVA-000/Week_06/images/mysql-engine.png)

__引擎状态__

![](/Users/zhaxu/Mine/GeekUniversity/JAVA-000/Week_06/images/mysql-engine-status.png)

__执行顺序__

<img src="/Users/zhaxu/Mine/GeekUniversity/JAVA-000/Week_06/images/mysql-execution-order.png" style="zoom:50%;" />

__索引原理__

Innodb：B+树实现

B+树层数最好不超过3层（否则会增加查询IO次数），由此估算单表最大数据条数

*tips：* 一般单表数据不建议超过2000万

6byte(索引) + 8byte(主键bigint) = 14byte

mysql一页16kb / 14byte = 1170(最多主键个数)

高度为2的B+树，16个块，则最多可存储 16 * 1170 = 18720

高度为3的B+树，1170 * 1170 * 16 = 2190w（三层B+树最多容纳主键个数）

__配置优化__

 [mysqld] server端配置

 [mysql] client配置

- 连接请求的变量
  - max_connections
  - back_log
  - wait_timout和interative_timeout
- 缓冲区变量
  - key_buffer_size
  - query_cache_size（*查询缓存）
  - max_connect_errors
  - sort_buffer_size
  - max_allowed_packet=32M
  - join_buffer_size=2M
  - thread_cache_size=300
- 配置innodb变量
  - innodb_buffer_pool_size
  - innodb_flush_log_at_trx_commit
  - innodb_thread_concurrency=0
  - innodb_log_buffer_size
  - innodb_log_file_size=50M
  - innodb_log_files_in_group=3
  - read_buffer_size=1M
  - read_rnd_buffer_size=16M
  - bulk_insert_buffer_size=64M
  - binary log

__最佳实践__

- 数据类型：明确、尽量小
  - 慎用text/blob/clob
  - 文件、图片不建议存入db
  - 时间日期，建议存储时间戳
  - 数值精度，数据库存整数，在代码里处理精度问题
- 尽量不要使用外键、触发器
- 游标、变量、视图、自定义函数、存储过程，一般不建议使用
- 重要数据，建议逻辑删除
- 建议加时间戳，create_time、update_time
- 数据库碎片（数据库压缩）