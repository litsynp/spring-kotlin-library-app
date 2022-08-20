# Spring Kotlin Library App

코틀린과 스프링 부트, JUnit 5, SQL, JPA, Querydsl을 이용해 웹 애플리케이션을 개발하고 리팩토링한다.

## How to

서버 실행 후 `http://localhost:8080/v1/index.html` 로 접속한다.

## About Spring and Kotlin

Kotlin을 이용하여 Spring 애플리케이션을 개발하기 위해 세팅이 필요하다.

`build.gradle`에 다음과 같은 내용을 추가한다.

```groovy
plugins {
    id 'org.jetbrains.kotlin.plugin.jpa' version '1.6.21'  // no-arg
    id 'org.jetbrains.kotlin.plugin.spring' version '1.6.21'  // all-open
}
// ...
allOpen {
    annotation("javax.persistence.Entity")
    annotation("javax.persistence.MappedSuperclass")
    annotation("javax.persistence.Embeddable")
}
// ...
dependencies {
    // ...
    implementation 'org.jetbrains.kotlin:kotlin-reflect:1.6.21'
    implementation 'com.fasterxml.jackson.module:jackson-module-kotlin:2.13.3'
    // ...
}
```

1. `kotlin-jpa`: JPA에서 모든 entity는 `public` 또는 `protected`로 된 기본 생성자가 필요하다. 기본 생성자를 자동으로 추가하기 위해 no-arg 설정을 한다.
2. `kotlin-spring`: Kotlin에서 모든 클래스나 메소드는 상속했을 때 오버라이드 할 수 없는 `final`로 지정된다. Spring에서는 런타임에 프록시 객체를 만들어 lazy loading을 수행하게 되는데, 그러기 위해선 원본 클래스를 오버라이드할 수 있도록 `open`을 붙여줘야 한다. all-open 설정으로 기본을 `open`으로 바꾼다. 또한, [`plugin.spring` 만으로는 모든 클래스를 `open`하게 만들어주지 않으므로](https://kotlinlang.org/docs/all-open-plugin.html#spring-support), `@ManyToOne`에 대해서도 Proxy를 기반으로 원활히 lazy fetching을 수행할 수 있도록 `Entity`, `MappedSuperclass`, `Embeddable`에 대해서 `allOpen`을 명시적으로 지정함으로써 Entity 클래스에 대해서 decompile을 했을 때도 class가 열려있을 수 있도록 한다. 
3. `kotlin-reflect`: Spring Framework 5부터 리플렉션을 추가해야 한다. Kotlin에서는 런타임 라이브러리 용량을 줄이기 위해 기본적으로 reflection을 제공하지 않는다. 직접 추가해준다. 
4. `jackson-module-kotlin`: Kotlin에서 HTTP 요청을 보냈을 때 Spring에 내장된 Jackson 모듈이 Kotlin을 지원할 수 있도록 모듈을 추가한다.

그 외 각종 설정이나, 설정되지 않은 부분은 생략한다. 

## About Querydsl

Querydsl을 이용하여 쿼리를 작성할 수 있도록 했다. 다음 코드로 추가하였다.

Querydsl 버전은 5.0.0을 사용하며, `kapt`를 통해 추가하였다.

```groovy
plugins {
    id 'org.jetbrains.kotlin.kapt' version '1.6.21'
}

// ...

dependencies {
    // ...
    implementation 'com.querydsl:querydsl-jpa:5.0.0'
    kapt('com.querydsl:querydsl-apt:5.0.0:jpa')
    kapt('org.springframework.boot:spring-boot-configuration-processor')
    // ...
}
```

`gradle build`를 하게 되면 결과물로 QClass를 생성하게 된다.

## REF

[Inflearn - 실전! 코틀린과 스프링 부트로 도서관리 애플리케이션 개발하기 (Java 프로젝트 리팩토링)](https://www.inflearn.com/course/java-to-kotlin-2)
