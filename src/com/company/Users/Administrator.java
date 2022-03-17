package com.company.Users;

public class Administrator {
    //singleton (lazy initialization)
    public static Administrator administrator;

    private Administrator() {}
    public static Administrator getAdministrator() {

        if (administrator == null)
            administrator = new Administrator();
        return administrator;
    }

    public void addEvent() {} //TODO
    public void deleteEvent(){} //TODO
    public void modifyEvent(){} // TODO, ADD PARAMETERS
}
