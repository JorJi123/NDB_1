package com.example.db1;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("Warehouse")
public class Warehouse {
    @Id
    private String id;
    private String adresas;
    private int plotasM2;

    public String getId(){
        return id;
    }
    public String getAdresas(){
        return adresas;
    }
    public int getplotasM2(){
        return plotasM2;
    }

    public void setAdresas(String adresas) {
        this.adresas = adresas;
    }

    public void setPlotasM2(int plotasM2) {
        this.plotasM2 = plotasM2;
    }

    public void setId(String id) {
        this.id = id;
    }
}
