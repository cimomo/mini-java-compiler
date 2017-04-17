class Z {
    public static void main(String[] a)
    {
        System.out.println(10);
    }
}

class A extends B {

    public int baz(int a) {
        return a;
    }
}

class B extends C {

    C c;
    E e;

    public int foo(int a) {
        int i;
        int j;

        i = i && true;
        j = j + false;

        f = new Z();

        if (i) {
            c = new B();
        } else {
            c = new E();
        }

        while (a) {
            System.out.println(new D());
        }

        i = this.foo(i);
        i = this.foo(i, j);
        i = this.foo(true);
        j = this.bar(j);
        j = this.bar(false);
        j = this.baz(i);

        return a;
    }
}

class C extends D {
    E e;

    public int bar(int a) {
        return a;
    }

}

class D {}
