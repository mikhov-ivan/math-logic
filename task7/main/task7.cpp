#include<iostream>
#include<vector>

#pragma comment(linker, "/STACK:256000000")

using namespace std;

struct Fake {
    static const long long num_args = 1000;
    static long long result(vector< long long > args) {
        return 0;
    }
};

struct Z {
    static const long long num_args = 1;
    static long long result(vector< long long > args) {
        return 0;
    }
};

struct N {
    static const long long num_args = 1;
    static long long result(vector< long long > args) {
        return args[0] + 1;
    }
};

template< long long n, long long k >
struct U {
    static const long long num_args = n;
    static long long result(vector< long long > args) {
        return args[k - 1];
    }
};


template< typename F, typename G1, typename G2, typename G3, typename G4 >
struct S {
    static const long long num_args = G1::num_args;
    static long long result(vector< long long > args) {
        vector< long long > v;
        if (G1::num_args < 500) {
            v.push_back(G1::result(args));
        }        
        if (G2::num_args < 500) {
            v.push_back(G2::result(args));
        }
        if (G3::num_args < 500) {
            v.push_back(G3::result(args));
        }
        if (G4::num_args < 500) {
            v.push_back(G4::result(args));
        }
        return F::result(v);
    }
};


template< typename F, typename G >
struct R {
    static const long long num_args = F::num_args + 1;
    static long long result(vector< long long > args) {
        vector< long long > v (args.begin(), --args.end());
        if (args.back() == 0) {
            return F::result(v);
        } else {
            v.push_back(args.back() - 1);
            long long res = R< F, G >::result(v);
            v.push_back(res);
            return G::result(v);
        }
    }
};

template< typename F >
struct M {
    static const long long num_args = F::num_args - 1;
    static long long result(vector< long long > args) {
        long long tmp = 0;
        vector< long long > v2 = args;
        v2.push_back(tmp);
        while (F::result(v2) != 0) {
            tmp++;
            v2[num_args] = tmp;
        }
        return tmp;
    }
};

struct Div {
    static const long long num_args = 2;
    static long long result(vector< long long > args) {
        return calc(args[0], args[1]);
    }
    static long long calc(long long a, long long b) {
        return a / b;
    }
};

struct Sub {
    static const long long num_args = 2;
    static long long result(vector< long long > args) {
        return calc(args[0], args[1]);
    }
    static long long calc(long long a, long long b) {
        if (a <= b) {
            return 0;
        } else {
            return a - b;
        }
    }
};

struct Mul {
    static const long long num_args = 2;
    static long long result(vector< long long > args) {
        return calc(args[0], args[1]);
    }
    static long long calc(long long a, long long b) {
        return a * b;
    }
};

struct Mod {
    static const long long num_args = 2;
    static long long result(vector< long long > args) {
        calc(args[0], args[1]);
    }

    static long long calc(long long a, long long b) {
        long long tmp = a % b;
        if (tmp > 0) {
            return tmp;
        } else {
            return 0;
        }
    }
};

struct One {
    static const long long num_args = 0;
    static long long result(vector< long long > args) {
        return 1;
    }
    static long long calc() {
        return 1;
    }
};


struct Two {
    static const long long num_args = 0;
    static long long result(vector< long long > args) {
        return 2;
    }
    static long long calc() {
        return 2;
    }
};

struct Pow {
    static const long long num_args = 2;
    static long long result(vector< long long > args) {
        return calc(args[0], args[1]);
    }
    static long long calc(long long a, long long b) {
        long long tmp = 1;
        for(int i = 0; i < b; i++) {
            tmp *= a;
        }
        return tmp;
    }
};

struct Not {
    static const long long num_args = 1;
    static long long result(vector< long long > args) {
        return calc(args[0]);
    }
    static long long calc(long long a) {
        if(a == 0) {
            return 1;
        } else {
            return 0;
        }
    }
};

struct Divbl {
    static const long long num_args = 2;
    static long long result(vector< long long > args) {
        return calc(args[0], args[1]);
    }
    static long long calc(long long a, long long b) {
        if (b == 0) {
            return 1;
        }
        long long tmp = Mod::calc(a, b);
        if(tmp == 0) {
            return 1;
        } else {
            return 0;
        }
    }
};

struct Dec {
    static const long long num_args = 1;
    static long long result(vector< long long > args) {
        return calc(args[0]);
    }
    static long long calc(long long a) {
        if (a > 0) {
            return a - 1;
        } else {
            return 0;
        }
    }
};

struct Is_prime {
    static const long long num_args = 1;
    static long long result(vector< long long > args) {
       return calc(args[0]);
    }
    static long long calc(long long tmp) {
        for(int i = 2; i < tmp - 1; i++) {
            if (tmp % i == 0) {
                return 0;
            }
        }
        return 1;
    }
};

struct Nth_prime {
    static const long long num_args = 1;
    static long long result(vector< long long > args) {
        return calc(args[0]);
    }
    static long long calc(long long tmp) {
        for(int i = 2;; i++) {
            if(Is_prime::calc(i)) {
                tmp--;
            }
            if(tmp == 0) {
                return i;
            }
        }
        return 0;
    }
};

struct Plog {
    static const long long num_args = 2;
    static long long result(vector< long long > args) {
        return S< Dec, M< S < Divbl, U< 3, 2 >, S<Pow, U< 3, 1 >, U< 3, 3 >, Fake, Fake >, Fake, Fake > >, Fake, Fake, Fake >::result(args);
    }

    static long long calc(long long a, long long x)    {
        vector< long long > v;
        v.push_back(a);
        v.push_back(x);
        return result(v);
    }
};

typedef S< Dec, S< Plog, S < Nth_prime, S< S< Plog, Two, U< 1, 1 >, Fake, Fake >, U< 1, 1 >, Fake, Fake, Fake >, Fake, Fake, Fake >, U< 1, 1 >, Fake, Fake >, Fake, Fake, Fake > TOP;
typedef S< Dec, S< Plog, S < Nth_prime, S< Sub, S< S< Plog, Two, U< 1, 1 >, Fake, Fake >, U< 1, 1 >, Fake, Fake, Fake >, One, Fake, Fake >, Fake, Fake, Fake >, U< 1, 1 >, Fake, Fake >, Fake, Fake, Fake > TOP2;
typedef S< Div, U< 1, 1 >, S< Mul, S< Pow, S< Nth_prime, S< S< Plog, Two, U< 1, 1 >, Fake, Fake >, U< 1, 1 >, Fake, Fake, Fake >, Fake, Fake, Fake >, S < N, S< TOP, U< 1, 1 >, Fake, Fake, Fake >, Fake, Fake, Fake >, Fake, Fake >, Two, Fake, Fake >, Fake, Fake > POP;
typedef S< POP, POP, Fake, Fake, Fake > POP2;
typedef S< Mul, U< 2, 1 >, S< Mul, S< S< Pow, S< Nth_prime, S< N, S< S< Plog, Two, U< 1, 1 >, Fake, Fake >, U< 2, 1 >, Fake, Fake, Fake >, Fake, Fake, Fake >, Fake, Fake, Fake >, S< N, U< 2, 2 >, Fake, Fake, Fake >, Fake, Fake >, U< 2, 1 >, U< 2, 2 >, Fake, Fake >, Two, Fake, Fake >, Fake, Fake > PUSH;
typedef S< S< PUSH, S< PUSH, S< PUSH, U< 4, 1 >, U< 4, 2 >, Fake, Fake >, U< 4, 3 >, Fake, Fake >, U< 4, 4>, Fake, Fake >, U< 3, 1 >, S< Dec, U< 3, 2 >, Fake, Fake, Fake >, U< 3, 2 >, S< Dec, U< 3, 3 >, Fake, Fake, Fake > > CMN;
typedef R< S< S< PUSH, S< PUSH, U< 3, 1 >, U< 3, 2 >, Fake, Fake >, U< 3, 3 >, Fake, Fake >, U< 2, 1 >, S< Dec, U< 2, 2 >, Fake, Fake, Fake >, One, Fake >, S< CMN, U< 4, 1 >, U< 4, 2 >, S< N, U< 4, 3 >, Fake, Fake, Fake >, Fake > > CN;
typedef R< S< PUSH, U< 2, 1 >, S< N, U< 2, 2 >, Fake, Fake, Fake >, Fake, Fake >, S< CN, U< 4, 1 >, S< N, U< 4, 3 >, Fake, Fake, Fake >, U< 4, 2 >, Fake > > CM;
typedef S< CM, S< POP2, U< 1, 1 >, Fake, Fake, Fake >, S< TOP, U< 1, 1 >, Fake, Fake, Fake >, S< TOP2, U< 1, 1 >, Fake, Fake, Fake >, Fake > STEP;
typedef R< S< S< S< PUSH, S< PUSH, U< 3, 1 >, U< 3, 2 >, Fake, Fake >, U< 3, 3 >, Fake, Fake >, Two, U< 2, 1 >, U< 2, 2 >, Fake >, U< 3, 1 >, U< 3, 2 >, Fake, Fake >, S< STEP, U< 4, 4 >, U< 4, 1 >, U< 4, 2 >, Fake > > STEPS;
typedef M< S< Sub, S< S< S< Plog, Two, U< 1, 1 >, Fake, Fake >, S< STEPS, U< 3, 1 >, U< 3, 2 >, U< 3, 3 >, Fake >, Fake, Fake, Fake >, U< 3, 1 >, U< 3, 2 >, U< 3, 3 >, Fake >, Two, Fake, Fake > > K;
typedef S< TOP, S< STEPS, U< 3, 1 >, U< 3, 2 >, S< K, U< 3, 1 >, U< 3, 2 >, Fake, Fake >, Fake >, Fake, Fake, Fake > ACKERMAN;

struct calculate {
    template <typename Function>
    static void calc(long long a, long long b) {
        vector< long long > args;
        args.push_back(a);
        args.push_back(b);
        cout << Function::result(args);
    }
};

int main() {
    cout << endl;    
    cout << endl << "ACKERMAN(0, 0) = ";
    calculate::calc<ACKERMAN>(0, 0);
    cout << endl << "ACKERMAN(0, 1) = ";
    calculate::calc<ACKERMAN>(0, 1);
    cout << endl << "ACKERMAN(0, 2) = ";
    calculate::calc<ACKERMAN>(0, 2);
    cout << endl << "ACKERMAN(0, 3) = ";
    calculate::calc<ACKERMAN>(0, 3);

    cout << endl << endl << "ACKERMAN(1, 0) = ";
    calculate::calc<ACKERMAN>(1, 0);
    cout << endl << "ACKERMAN(1, 1) = ";
    calculate::calc<ACKERMAN>(1, 1);
    cout << endl << "ACKERMAN(1, 2) = ";
    calculate::calc<ACKERMAN>(1, 2);
    cout << endl << "ACKERMAN(1, 3) = ";
    calculate::calc<ACKERMAN>(1, 3);
    
    cout << endl << endl << "ACKERMAN(2, 0) = ";
    calculate::calc<ACKERMAN>(2, 0);
    cout << endl << "ACKERMAN(2, 1) = ";
    calculate::calc<ACKERMAN>(2, 1);
    cout << endl << "ACKERMAN(2, 2) = ";
    calculate::calc<ACKERMAN>(2, 2);
    cout << endl << "ACKERMAN(2, 3) = ";
    calculate::calc<ACKERMAN>(2, 3);
    
    cout << endl << endl << "ACKERMAN(2, 4) = ";
    calculate::calc<ACKERMAN>(2, 4);
    cout << endl << "ACKERMAN(2, 5) = ";
    calculate::calc<ACKERMAN>(2, 5);
    cout << endl << "ACKERMAN(2, 6) = ";
    calculate::calc<ACKERMAN>(2, 6);

    cout << endl << endl << "ACKERMAN(3, 0) = ";
    calculate::calc<ACKERMAN>(3, 0);
    cout << endl << endl;
    return 0;
}