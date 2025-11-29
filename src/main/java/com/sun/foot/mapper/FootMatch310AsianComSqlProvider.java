package com.sun.foot.mapper;

import org.apache.ibatis.jdbc.SQL;

import java.util.Map;

public class FootMatch310AsianComSqlProvider {


    public String findByCompetitionId(Map<String, Object> params) {
        return new SQL() {{
            SELECT("*");
            FROM("FOOT_MATCH_310_ASIAN_COM");
            if (params.get("competitionId") != null && !"".equals(params.get("competitionId"))) {
                WHERE("competition_id = #{competitionId}");
            }
            if (params.get("initCalculationValue1") != null && !"".equals(params.get("initCalculationValue1"))) {
                // 将用户输入转换为一个范围
                int input = Integer.parseInt((String) params.get("initCalculationValue1"));
                params.put("lowerBound1", input);
                params.put("upperBound1", input + 1);
                WHERE("init_calculation_value1 BETWEEN #{lowerBound1} AND #{upperBound1}");
            }
            if (params.get("initCalculationValue2") != null && !"".equals(params.get("initCalculationValue2"))) {
                // 将用户输入转换为一个范围
                int input = Integer.parseInt((String) params.get("initCalculationValue2"));
                params.put("lowerBound2", input);
                params.put("upperBound2", input + 1);
                WHERE("init_calculation_value2 BETWEEN #{lowerBound2} AND #{upperBound2}");
            }
            if (params.get("initCalculationValue3") != null && !"".equals(params.get("initCalculationValue3"))) {
                // 将用户输入转换为一个范围
                int input = Integer.parseInt((String) params.get("initCalculationValue3"));
                params.put("lowerBound3", input);
                params.put("upperBound3", input + 1);
                WHERE("init_calculation_value3 BETWEEN #{lowerBound3} AND #{upperBound3}");
            }
            if (params.get("finalCalculationValue1") != null && !"".equals(params.get("finalCalculationValue1"))) {
                // 将用户输入转换为一个范围
                int input = Integer.parseInt((String) params.get("finalCalculationValue1"));
                params.put("lowerBound4", input);
                params.put("upperBound4", input + 1);
                WHERE("final_calculation_value1 BETWEEN #{lowerBound4} AND #{upperBound4}");
            }
            if (params.get("finalCalculationValue2") != null && !"".equals(params.get("finalCalculationValue2"))) {
                // 将用户输入转换为一个范围
                int input = Integer.parseInt((String) params.get("finalCalculationValue2"));
                params.put("lowerBound5", input);
                params.put("upperBound5", input + 1);
                WHERE("final_calculation_value2 BETWEEN #{lowerBound5} AND #{upperBound5}");
            }
            if (params.get("finalCalculationValue3") != null && !"".equals(params.get("finalCalculationValue3"))) {
                // 将用户输入转换为一个范围
                int input = Integer.parseInt((String) params.get("finalCalculationValue3"));
                params.put("lowerBound6", input);
                params.put("upperBound6", input + 1);
                WHERE("final_calculation_value3 BETWEEN #{lowerBound6} AND #{upperBound6}");
            }
            if (params.get("upDownValue") != null && !"".equals(params.get("upDownValue"))) {
                WHERE("up_down_value = #{upDownValue}");
            }
            if (params.get("companyName") != null && !"".equals(params.get("companyName"))) {
                WHERE("company_name = #{companyName}");
            }
        }}.toString();
    }
}
