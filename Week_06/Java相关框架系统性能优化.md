# Java相关框架/系统性能优化

## Lambda

__常见表达式__

- 不需要参数，有返回值，e.g. `()->5`
- 接受一个参数（数字类型），e.g. `x->2*x`
- 接受2个参数（数字），e.g. `(x,y)->x-y`
- 接受参数明确类型，e.g. `(int x, int y)->x+y`
- 接受参数，没有返回值，e.g. `(String s)->System.out.print(s)`

函数式

__@FunctionalInterface__

- Predicate\<T\> 有参数、条件判断
- Function<T,R> 有参数、有返回值
- Consumer\<T\> 无返回值
- Supplier\<T\> 无参数、有返回值

## Stream

一个来自数据源的元素队列并支持聚合操作

- 元素
- 数据源
- 聚合操作
- Pipelining
- 内部迭代

__Stream操作__

- 中间操作
  - 选择与过滤，`filter` `distinct` `limit` `skip`
  - 映射，`map` `mapToXXX` `flatMap`
  - 排序，`sorted`
- 终止操作
  - 查找与匹配，`allMatch` `anyMatch` `nonMatch` `findFirst` `findAny` `count` `max` `min`
  - 归约，`reduce`
  - 收集，`collect`，`toMap` `toList` `toSet` `toCollection` `count` `summaryStatistics`
  - 迭代，`forEach`

```java
// list中小于4的元素，去重，求和
// reduce中第一个参数，用于计算首个元素相加
list.stream().filter(i -> i<4).distinct().reduce(0, (a,b)->a+b);
// list to map，key=a value=a+1
// 重复元素保留第一个，创建LinkedHashMap
Map<Integer, Integer> map = list.parallelStream().collect(Collectors.toMap(a->a, a->(a+1), (a,b) -> a, LinkedHashMap::new));
```

## Lombok

简化编程，编译期增强

- @Setter @Getter
- @Data
- @XXXConstructor
- @Builder
- @ToString
- @Sl4j

## Guava

开源的Java库，Google开发托管

- 集合工具
- 缓存
- 并发，`ListenableFuture`
- 字符串处理，`Strings`
- 事件总线，发布订阅模式，`EeventBus`
- 反射，`Reflection`

## 面向对象设计原则S.O.L.I.D

- SRP: The Simple Responsibility Principle 单一责任原则
- OCP: The Open Closed Principle 开放封版原则
- LSP: The Liskov Substitution Principle 里氏替换原则
- ISP: The Interface Segregation Principle 接口分离原则
- DIP: The Dependency Inversion Principle 依赖倒置原则

## 编码规范、checkstyple

__常见编码规范：__

- Google编码规范: https://google.github.io/styleguide/javaguide.html

- Alibab 编码规范: https://github.com/alibaba/p3c
- VIP规范: https://vipshop.github.io/vjtools/#/standard/

