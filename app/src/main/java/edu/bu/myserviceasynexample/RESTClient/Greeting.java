package edu.bu.myserviceasynexample.RESTClient;


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
        return "id:" +getId() + "   greeting: " + getGreetingname();
    }
}
