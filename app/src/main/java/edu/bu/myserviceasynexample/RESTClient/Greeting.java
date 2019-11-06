package edu.bu.myserviceasynexample.RESTClient;

/**
 * Created by danazh on 11/7/17.
 */

public class Greeting {
    public int id;
    public String greetingname;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGreetingname() {
        return greetingname;
    }

    public void setGreetingname(String greetingname) {
        this.greetingname = greetingname;
    }

    @Override
    public String toString() {
        return new String ("id:" +getId() + "   greeting: " + getGreetingname());
    }
}
