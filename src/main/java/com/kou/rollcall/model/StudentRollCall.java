package com.kou.rollcall.model;

public class StudentRollCall
{
    private Student ogrenci;

    private RollCallInfo devamsizlik;

    public Student getOgrenci()
    {
        return ogrenci;
    }

    public void setOgrenci(Student ogrenci)
    {
        this.ogrenci = ogrenci;
    }

    public RollCallInfo getDevamsizlik()
    {
        return devamsizlik;
    }

    public void setDevamsizlik(RollCallInfo devamsizlik)
    {
        this.devamsizlik = devamsizlik;
    }
}
