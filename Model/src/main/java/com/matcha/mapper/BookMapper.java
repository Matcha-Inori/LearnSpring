package com.matcha.mapper;

import com.matcha.model.Book;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

public interface BookMapper
{
    @Select("SELECT * FROM T_BOOK WHERE ID = #{id}")
    Book selectBookById(int bookId);

    @Insert("INSERT INTO T_BOOK(ID, NAME, NUMBER, PRICE) VALUE (#{id}, #{name}, #{number}, #{price});")
    void insertBook(Book book);
}
