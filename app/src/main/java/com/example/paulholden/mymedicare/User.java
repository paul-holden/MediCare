package com.example.paulholden.mymedicare;

/**
 * Created by paul on 23/04/2017.
 */

public class User {
    String Name ,UName, Email, PWord, NurseNum;

    public void setName(String Name)
    {
        this.Name = Name;
    }

    public String getName()
    {
        return this.Name;
    }

    public void setUName(String UName)
    {
        this.UName = UName;
    }
    public String getUName()
    {
        return this.UName;
    }

    public void setEmail(String Email)
    {
        this.Email = Email;
    }

    public String getEmail()
    {
        return this.Email;
    }

    public void setPWord (String PWord)
    {
        this.PWord = PWord;
    }

    public String getPWord()
    {
        return this.PWord;
    }

    public void setNurseNum (String NurseNum)
    {
        this.NurseNum = NurseNum;
    }

    public String getNurseNum()
    {
        return this.NurseNum;
    }


}
