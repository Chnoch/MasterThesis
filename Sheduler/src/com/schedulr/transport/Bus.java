package com.schedulr.transport;

public class Bus implements Comparable<Bus>
{
    private String busNum;
    private String destination;
    private String time;
    private String min1;
    private String min2;
    private long departDate;
    private String name;
    private String category;

    public Bus(String busNum, String destination, String category, String time, String min1, String min2, long departDate, String name)
    {
        this.busNum = busNum;
        this.destination = destination;
        this.time = time;
        this.min1 = min1;
        this.min2 = min2;
        this.departDate = departDate;
        this.name = name;
        this.category = category;
    }

    public boolean isNumberNum()
    {
        try
        {
            Integer.parseInt(this.busNum);
        } catch (NumberFormatException e)
        {
            return false;
        }
        return true;
    }

    public String getBusNum()
    {
        return busNum;
    }

    public void setBusNum(String busNum)
    {
        this.busNum = busNum;
    }

    public String getDestination()
    {
        return destination;
    }

    public void setDestination(String destination)
    {
        this.destination = destination;
    }

    public String getTime()
    {
        return time;
    }

    public void setTime(String time)
    {
        this.time = time;
    }

    public String getMin1()
    {
        return min1;
    }

    public void setMin1(String min1)
    {
        this.min1 = min1;
    }

    public String getMin2()
    {
        return min2;
    }

    public void setMin2(String min2)
    {
        this.min2 = min2;
    }

    public Long getDepartDate()
    {
        return departDate;
    }

    public void setDepartDate(Long departDate)
    {
        this.departDate = departDate;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getCategory()
    {
        return category;
    }

    public void setCategory(String category)
    {
        this.category = category;
    }

    public int compareTo(Bus another)
    {
        return getDepartDate().compareTo(another.getDepartDate());
    }

    @Override
    public int hashCode()
    {
        return 17 * busNum.hashCode() + 23 * destination.hashCode() + Long.valueOf(departDate).hashCode();
    }

    @Override
    public boolean equals(Object o)
    {
        if (!(o instanceof Bus))
            return false;
        Bus bus = (Bus) o;
        return busNum.equals(bus.getBusNum())
                && destination.equals(bus.getDestination())
                && departDate == bus.getDepartDate();
    }
}
