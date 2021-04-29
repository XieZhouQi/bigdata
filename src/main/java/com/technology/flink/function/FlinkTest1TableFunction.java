package com.technology.flink.function;

import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.table.functions.TableFunction;
import org.apache.flink.types.Row;

/**
 * @description: TODO
 * @author: xzq
 * @date: 2021/4/27
 * @version:
 */
public class FlinkTest1TableFunction extends TableFunction<Row> {

    @Override
    public TypeInformation<Row> getResultType() {
        return Types.ROW(Types.INT, Types.STRING);
    }

    public void eval(String nob, String nameb , int ageb) throws Exception {
        for(int i=0;i<=1;i ++){
            Row row = new Row(7);
            row.setField(0, ageb);
            if(ageb <= 2){
                row.setField(1, "少年");
            }else{
                row.setField(1, "青年");
            }
            collect(row);
        }
        
    }
}
