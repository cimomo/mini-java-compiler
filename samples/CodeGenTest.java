/**
 * Adding "super" support.
 */

class Factorial{
    public static void main(String[] a){
        {
            System.out.println(20);
            System.out.println(new A().method1());
            System.out.println(new A().method2());
            System.out.println(new A().method(3));
            System.out.println(new B().method(13));
            System.out.println(new B().method3(11));
            System.out.println(new B().method4());
            System.out.println(new B().method6(20));
        }
    }
}

class A
{
    int m_x;
    int m_y;

    public int method1()
    {
        return 1;
    }

    public int method2()
    {
        return 2;
    }

    public int method(int a)
    {
        int x;
        int y;
        boolean b;
        int i;

        x = a;
        System.out.println(x);

        y = x;
        System.out.println(y+1);

        if (0 < x + y) {
            System.out.println(x+y-1);
        } else {
            System.out.println(x-y);
        }

        if (true) {
            System.out.println(x*y-3);
        } else {
            System.out.println(x-y);
        }

        if (false) {
            System.out.println(0);
        } else {
            System.out.println(7);
        }

        b = !(0 < (x-y));

        if (b && true) {
            System.out.println(8);
        } else {
            System.out.println(0);
        }

        if (b && false) {
            System.out.println(0);
        } else {
            System.out.println(9);
        }

        i = 0;

        while (i < 2) {
            System.out.println(i+10);
            i = i + 1;
        }

        m_x = a + 9;

        return m_x;
    }

    public int method5(int x)
    {
        m_x = x;
        return m_x;
    }

}

class B extends A
{
    int m_x;

    public int method(int x)
    {
        return x;
    }

    public int method3(int x)
    {
        int result;
        A a;
        B b;
        a = new B();
        b = new B();

        result = 0;
        if (b instanceof B) {
            result = result + a.method(x);
        } else {
            result = 0;
        }

        if (b instanceof A) {
            result = result + b.method1();
        } else {
            result = 0;
        }

        if (a instanceof A) {
            result = result + this.method1();
        } else {
            result = 0;
        }

        if (a instanceof B) {
            result = result + method1();
        } else {
            result = ((0));
        }

        result = (<B>a).method6(result);

        return result;
    }

    public int method4()
    {
        int[] a;
        A[] oa;

        a = new int[15];
        oa = new A[18];

        m_x = a.length;
        System.out.println(m_x);

        a[0] = 16;
        a[14] = 17;

        m_y = a[0];
        System.out.println(m_y);

        System.out.println(a[14]);

        System.out.println(oa.length);

        oa[0] = new B();

        return (<B>oa[0]).method6(19);
    }

    public int method5(int x)
    {
        return 0;
    }

    public int method6(int x)
    {
        return super.method5(x);
    }
}
