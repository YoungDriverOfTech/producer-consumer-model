# 多线程练习：实现一个多线程的Word Count

Word Count是一个著名的练手程序。一个文本文件包含若干行，每行包含若干个*只包含小写字母的单词*，单词之间以空格分割。

请编写一个程序，统计一个文件列表中，每个单词出现的次数。
例如，文件内容是：

```
i am a boy
i have a dog
```

你应该返回一个Map：{i->2, am->1, a->2, boy->1, have->1, dog->1}

请编写一个多线程的`count`方法完成挑战。我们鼓励你采用不同的方法尝试，例如：

- `Object.wait/notify`
- `Lock/Condition`
- `CountDownLatch`
- `Future`与线程池
- `ForkJoinPool`
- `parallelStream()`
- 等等等等