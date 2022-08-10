package com.group.libraryapp.domain.book

import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface BookRepository : JpaRepository<Book, Long> {

    // Optional 제외하고 Book?도 가능 -- 하지만 여기서는 sevice 계층과의 호환을 위해 Optional로 남겨둔다.
    fun findByName(bookName: String): Optional<Book>

}
