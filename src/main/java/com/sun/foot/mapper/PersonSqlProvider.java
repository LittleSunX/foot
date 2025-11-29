package com.sun.foot.mapper;

import org.apache.ibatis.jdbc.SQL;

public class PersonSqlProvider {

    public String findAll() {
        return new SQL() {{
            SELECT("*");
            FROM("T_B_PERSON");
        }}.toString();
    }

    public String count() {
        return new SQL() {{
            SELECT("COUNT(*)");
            FROM("T_B_PERSON");
        }}.toString();
    }
}
