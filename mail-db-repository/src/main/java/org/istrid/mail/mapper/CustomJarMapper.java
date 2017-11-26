package org.istrid.mail.mapper;


import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.istrid.mail.domain.CustomJar;

import java.util.List;

@Mapper
public interface CustomJarMapper {

    @Select("select * from users")
    List<CustomJar> findAll();

    @Insert("insert into users(name,salary) values(#{name},#{salary})")
    @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id",
            before = false, resultType = Integer.class)
    void insert(CustomJar users);
}