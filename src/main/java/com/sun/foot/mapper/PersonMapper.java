package com.sun.foot.mapper;

import com.github.pagehelper.PageInfo;
import com.sun.foot.entity.Person;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

@Mapper
public interface PersonMapper {

    @SelectProvider(type = PersonSqlProvider.class, method = "findAll")
    List<Person> findAll();

    @SelectProvider(type = PersonSqlProvider.class, method = "count")
    long getCount();
}
