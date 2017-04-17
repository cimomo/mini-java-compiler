/**
 *
 * Kai Chen (kchen@cs.washington.edu)
 *
 * Statement list.
 *
 */

package syntaxtree;
import java.util.Vector;

public class StatementList
{
    private Vector<Statement> list;

    public StatementList()
    {
        list = new Vector<Statement>();
    }


    public void addElement(Statement n)
    {
        list.addElement(n);
    }


    public Statement elementAt(int i)
    {
        return list.elementAt(i);
    }


    public int size()
    {
        return list.size();
    }
}