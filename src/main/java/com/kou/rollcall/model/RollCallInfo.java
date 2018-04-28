package com.kou.rollcall.model;


public class RollCallInfo
{
    private String dersAdi;
    private int devamBilgisi;
    private int devamsizlikBilgisi;
    private long dersId;

    public long getDersId()
    {
        return dersId;
    }

    public void setDersId(long dersId)
    {
        this.dersId = dersId;
    }

    public String getDersAdi()
    {
        return dersAdi;
    }

    public void setDersAdi(String dersAdi)
    {
        this.dersAdi = dersAdi;
    }

    public int getDevamBilgisi()
    {
        return devamBilgisi;
    }

    public void setDevamBilgisi(int devamBilgisi)
    {
        this.devamBilgisi = devamBilgisi;
    }

    public int getDevamsizlikBilgisi()
    {
        return devamsizlikBilgisi;
    }

    public void setDevamsizlikBilgisi(int devamsizlikBilgisi)
    {
        this.devamsizlikBilgisi = devamsizlikBilgisi;
    }
}
