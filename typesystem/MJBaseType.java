/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package typesystem;

/**
 *
 * @author kaichen
 */
public class MJBaseType extends MJType 
{
    public boolean compatibleWith(MJType that)
    {
        if (!(that instanceof MJBaseType)) {
            return false;
        }
        
        return (this == that);
    }
    
    public String toString()
    {
        String typeName;
        
        if (this == MJTypeSystem.INT) {
            typeName = "int";
        
        } else if (this == MJTypeSystem.BOOL) {
            typeName = "boolean";
            
        } else if (this == MJTypeSystem.INTARRAY) {
            typeName = "int[]";
        
        } else {
            typeName = "unknown";
        }
        
        return typeName;
    }
}
