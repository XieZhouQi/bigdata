package com.technology.flink.constants;

/**
 * @description: TODO
 * @author: xzq
 * @date: 2021/4/22
 * @version:
 */
public class SqlConstant {

    public static String testSql(String table) {
        return  "SELECT nob, nameb, ageb FROM  mqDataTable";
    }

    public static String testSql1() {
        return  "SELECT nob, nameb, mqDataTable.ageb, stage FROM  mqDataTable,LATERAL TABLE(flinkTableFunctionUDF(nob, nameb, ageb)) a as T(ageb, stage)";
    }
}
