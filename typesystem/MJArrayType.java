/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package typesystem;

/**
 *
 * @author kaichen
 */
public class MJArrayType extends MJType 
{
    private MJType type;
    
    public MJArrayType(MJType type)
    {
        this.type = type;
    }
    
    public MJType getType()
    {
        return type;
    }
    
    public boolean compatibleWith(MJType that)
    {
        if (!(that instanceof MJArrayType)) {
            return false;
        }
        
        return (this.getType() == ((MJArrayType)that).getType());
    }
    
    public String toString()
    {   
        return type + "[]";
    }
}
