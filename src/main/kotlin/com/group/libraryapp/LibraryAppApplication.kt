package com.group.libraryapp

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class LibraryAppApplication

// Kotlin에서 top-level에 함수를 작성하면 static 함수가 된다.
fun main(args: Array<String>) {
    // 확장 함수 runApplication
    runApplication<LibraryAppApplication>(*args)  // 가변인자에는 spread 연산자(*args)를 넣는다
}
