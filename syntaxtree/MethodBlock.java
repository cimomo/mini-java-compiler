/**
 *
 * Kai Chen (kchen@cs.washington.edu)
 *
 * Method block.
 *
 */

package syntaxtree;
import java.util.Vector;
import typesystem.*;

public class MethodBlock
{
    private Vector<MethodBlockItem> list;

    public MethodBlock()
    {
        list = new Vector<MethodBlockItem>();
    }


    public void addElement(MethodBlockItem mbi)
    {
        list.addElement(mbi);
    }


    public MethodBlockItem elementAt(int i)
    {
        return list.elementAt(i);
    }


    public int size()
    {
        return list.size();
    }
}
