package com.util;

public class Page {
    private int start;
    private int count;
    private int total;
    private String param;
    private static final int defaultCount=5;
    public Page(){
        count=defaultCount;
    }
    public Page(int start,int count){
        this();
        this.start=start;
        this.count=count;
    }

    public boolean isHasPreviouse(){
        if (start==0)
            return false;
        return true;
    }
    public boolean isHasNext(){
        if (start==getLast())
            return false;
        return true;
    }
    public int getLast(){
        int last;
        if (total%count==0)
            last=total-count;
        else
            last=total-total%count;
        last=last<0?0:last;
        return last;
    }
    public int getTotalPage(){
        int totalPage;
        if (total%count==0)
            totalPage=total/count;
        else
            totalPage=total/count+1;
        if (totalPage<1)
            totalPage=1;
        return totalPage;
    }
    public int getStart() {
        return start;
    }

    public void setStart(int start) {
       if (start<0)
           this.start=0;
//       else if (start>getLast())
//           this.start=getLast();
       else
           this.start=start;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }
}
