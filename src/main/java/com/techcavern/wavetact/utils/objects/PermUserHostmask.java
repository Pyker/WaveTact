package com.techcavern.wavetact.utils.objects;

/**
 * Created by jztech101 on 7/5/14.
 */
public class PermUserHostmask {
    private final String hostmask;
    private int PermLevel;
    private final String realname;


    public PermUserHostmask(String hostmask, String realname, int permlevel){
        this.PermLevel = permlevel;
        this.hostmask = hostmask;
        this.realname = realname;

    }

    public int getPermLevel(){
        return this.PermLevel;
    }
    public String getHostmask(){
        return this.hostmask;
    }
    public String getRealname(){
        return this.realname;
    }

    public void setPermLevel(int newpermlevel){
        this.PermLevel=newpermlevel;
    }
}
