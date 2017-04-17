/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package typesystem;

public class MJClassType extends MJType
{
    protected String id;
    protected MJClassType superClassType;
    
    public MJClassType(String id, MJClassType superClassType)
    {
        this.id = id;
        this.superClassType = superClassType;
    }
    
    public MJClassType(String id)
    {
        this(id, null);
    }
    
    
    public MJClassType getSuper()
    {
        return superClassType;
    }
    
    public void setSuper(MJClassType superClassType)
    {
        if (this.superClassType != null) {
            MJTypeSystem.error("Error: Class " + id + " already have super class " + superClassType.id);
        } 
        
        this.superClassType = superClassType;
    }
    
    public boolean compatibleWith(MJType that)
    {
        if (!(that instanceof MJClassType)) {
            return false;
        }
        
        for (MJClassType type = this; 
             type != null; 
             type = type.superClassType) {
            
            if (type == that) {
                return true;
            }
        }
        
        return false;
    }
    
    
    public String toString()
    {
        return id;
    }
}
