package ser321.serialize;

/**
 * Copyright (c) 2015 Tim Lindquist,
 * Software Engineering,
 * Arizona State University at the Polytechnic campus
 * <p/>
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation version 2
 * of the License.
 * <p/>
 * This program is distributed in the hope that it will be useful,
 * but without any warranty or fitness for a particular purpose.
 * <p/>
 * Please review the GNU General Public License at:
 * http://www.gnu.org/licenses/gpl-2.0.html
 * see also: https://www.gnu.org/licenses/gpl-faq.html
 * so you are aware of the terms and your rights with regard to this software.
 * Or, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301,USA
 * <p/>
 * Purpose:
 * An class defining xml serializable user objects as java beans.
 * For a Java class to be XML serializable, it must have a parameterless
 * constructor and it must have get and set methods for each instance
 * variable in the class.
 * <p/>
 * Ser321 Principles of Distributed Software Systems
 * @see <a href="http://pooh.poly.asu.edu/Ser321">Ser321 Home Page</a>
 * @author Tim Lindquist (Tim.Lindquist@asu.edu) CIDSE - Software Engineering
 *                       Ira Fulton Schools of Engineering, ASU Polytechnic
 * @file    User.java
 * @date    August, 2015
 * @license See above
 */
public class User {

    private String userId, userPwd, first, last;

    public User(){
    }

    public User(String id, String pwd, String first, String last) {
        this.userId = id;
        this.userPwd = pwd;
        this.first = first;
        this.last = last;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String id) {
        userId = id;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String id) {
        userPwd = id;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String id) {
        first = id;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String id) {
        last = id;
    }

    public boolean check(String id, String pwd) {
        return (userId.equals(id) && userPwd.equals(pwd));
    }
}
