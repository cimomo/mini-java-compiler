/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package symboltable;

import typesystem.*;

/**
 *
 * @author kaichen
 */
public class MJVariable 
{
    protected String id;
    protected MJType type;
    protected String address;
    
    public MJVariable(String id, MJType type)
    {
        this.id = id;
        this.type = type;
    }
    
    
    public void setAddress(String address)
    {
        this.address = address;
    }
    
    
    public String getAddress()
    {
        return address;
    }
    
    
    public MJType getType()
    {
        return type;
    }
    
    
    public String toString()
    {
        return "(" + type + " " + id + ")";
    }
}
