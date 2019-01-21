#include<iostream>
#include<vector>

using namespace std;

struct Fake {
    static const size_t num_args = 1000;
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
    static long long result(vector< long long >  args) {
        return args[0] + 1;
    }
};

template< long long n, long long k >
struct U {
    static const long long num_args = n;
    static long long result(vector< long long >  args) {
        return args[k - 1];
    }
};


template <typename T, typename T1, typename T2, typename T3, typename T4>
struct S {
    static const long long num_args = T1::num_args;
    static long long result(vector< long long >  args) {
        vector< long long > v;
        if (T1::num_args < 500) {
            v.push_back(T1::result(args));
        }        
        if (T2::num_args < 500) {
            v.push_back(T2::result(args));
        }
        if (T3::num_args < 500) {
            v.push_back(T3::result(args));
        }
        if (T4::num_args < 500) {
            v.push_back(T4::result(args));
        }
        return T::result(v);
    }
};


template<typename T, typename T1>
struct R {
    static const long long num_args = T::num_args + 1;
    static long long result(vector< long long >  args) {
        vector< long long > v (args.begin(), --args.end());
        if(args.back() == 0) {
            return T::result(v);
        } else {
            v.push_back(args.back() - 1);
            long long res = R<T, T1>::result(v);
            v.push_back(res);
            return T1::result(v);
        }
    }
};


template<typename T>
struct M {
    static const long long num_args = T::num_args - 1;
    static long long result(vector< long long >  args) {
        long long y = 0;
        vector< long long > v2 = args;
        v2.push_back(y);
        while(T::result(v2) != 0) {
            y++;
            v2[num_args] = y;
        }
        return y;
    }
};

typedef S< N, Z, Fake, Fake, Fake > ONE;
typedef R< U< 1, 1 >, S< N, U< 3, 3 >, Fake, Fake, Fake > > SUM;
typedef S< R< Z, U< 3, 2 > >, U< 1, 1 >, U< 1, 1 >, Fake, Fake > DEC;
typedef R< U< 1, 1 >, S<DEC, U< 3, 3 >, Fake, Fake, Fake > > SUB;
typedef R< Z, S< SUM, U< 3, 1 >, U< 3, 3 >, Fake, Fake > > MUL;
typedef R< ONE, S< MUL, U< 3, 1 >, U< 3, 3 >, Fake, Fake > > POW;
typedef S< DEC, S< M< S< SUB, U< 3, 1 >, S< MUL, U< 3, 2 >, U< 3, 3 >, Fake, Fake >, Fake, Fake > >, S< N, U< 2, 1 >, Fake, Fake, Fake >, U< 2, 2 >, Fake, Fake >, Fake, Fake, Fake > DIV;
typedef S< SUB, U< 2, 1 >, S< MUL, U< 2, 2 >, S< DIV, U< 2, 1 >, U< 2, 2 >, Fake, Fake >, Fake, Fake >, Fake, Fake > MOD;
typedef S< R< N, S< Z, U< 3, 3 >, Fake, Fake, Fake > >, U< 1, 1 >, U< 1, 1 >, Fake, Fake > NOT;
typedef S< NOT, MOD, Fake, Fake, Fake> DIVBL;
typedef S< DEC, M< S< DIVBL, U< 3, 2 >, S< POW, U< 3, 1 >, U< 3, 3 >, Fake, Fake >, Fake, Fake > >, Fake, Fake, Fake > PLOG;
typedef NOT IS_ZERO;
typedef S< NOT, IS_ZERO, Fake, Fake, Fake> IS_NOT_ZERO;
typedef S< IS_NOT_ZERO, MUL, Fake, Fake, Fake> AND;
typedef S< N, N, Fake, Fake, Fake> PLUS2;
typedef S< IS_NOT_ZERO, SUB, Fake, Fake, Fake> MORE;
typedef S< N, ONE, Fake, Fake, Fake > TWO;
typedef S< R< S< MORE, U< 1, 1 >, ONE, Fake, Fake>, S< S< AND, S< MOD, U< 3, 1 >, U< 3, 2 >, Fake, Fake >, U< 3, 3 >, Fake, Fake >, U< 3, 1 >, S< PLUS2, U< 3, 2 >, Fake, Fake, Fake >, U< 3, 3 >, Fake > >, U< 1, 1 >, S< DEC, S< DIV, U< 1, 1 >, TWO, Fake, Fake >, Fake, Fake, Fake >, Fake, Fake > IS_PRIME;
typedef S< R< Z, S< SUM, S< IS_PRIME, U< 3, 2 >, Fake, Fake, Fake >, U< 3, 3 >, Fake, Fake > >, U< 1, 1 >, S< N, U< 1, 1 >, Fake, Fake, Fake >, Fake, Fake > PREV_PRIME;
typedef S < M< S< MORE, U< 2, 1 >, S< PREV_PRIME, U< 2, 2 >, Fake, Fake, Fake >, Fake, Fake > >, N, Fake, Fake, Fake > NTH_PRIME;

struct calculate {
    template <typename Function>
    static void calc(long long a) {
        vector< long long > args;
        args.push_back(a);
        cout << Function::result(args);
    }
    template <typename Function>
    static void calc(long long a, long long b) {
        vector< long long > args;
        args.push_back(a);
        args.push_back(b);
        cout << Function::result(args);
    }
    template <typename Function>
    static void calc(long long a, long long b, long long c) {
        vector< long long > args;
        args.push_back(a);
        args.push_back(b);
        args.push_back(c);
        cout << Function::result(args);
    }
};

int main() {
    cout << endl << endl;
    cout << "3 + 2 = ";
    calculate::calc<SUM>(3, 2);
    cout << endl << endl << "11 - 6 = ";
    calculate::calc<SUB>(11, 6);
    cout << endl << endl << "11 div 2 = ";
    calculate::calc<DIV>(11, 2);
    cout << endl << endl << "11 mod 6 = ";
    calculate::calc<MOD>(11, 6);
    
    cout << endl << endl<< "isPrime(1) = ";
    calculate::calc<IS_PRIME>(1);
    cout << endl << endl<< "isPrime(3) = ";
    calculate::calc<IS_PRIME>(3);
    cout << endl << endl << "isPrime(2) = ";
    calculate::calc<IS_PRIME>(2);
    cout << endl << endl << "isPrime(5) = ";
    calculate::calc<IS_PRIME>(5);
    cout << endl << endl << "isPrime(6) = ";
    calculate::calc<IS_PRIME>(6);
    cout << endl << endl << "isPrime(12) = ";
    calculate::calc<IS_PRIME>(12);
    cout << endl << endl << "isPrime(17) = ";
    calculate::calc<IS_PRIME>(17);
    
    cout << endl << endl << "Prime numbers: ";
    calculate::calc<NTH_PRIME>(1);
    cout << ", ";
    calculate::calc<NTH_PRIME>(2);
    cout << ", ";
    calculate::calc<NTH_PRIME>(3);
    cout << ", ";
    calculate::calc<NTH_PRIME>(4);
    cout << ", ";
    calculate::calc<NTH_PRIME>(5);
    cout << ", ";
    calculate::calc<NTH_PRIME>(6);
    cout << ", ";
    calculate::calc<NTH_PRIME>(7);
    cout << ", ";
    calculate::calc<NTH_PRIME>(8);
    cout << ", ";
    calculate::calc<NTH_PRIME>(9);
    cout << ", ";
    calculate::calc<NTH_PRIME>(10);
    cout << ", ..." << endl << endl;
    
    cout << endl << endl << "plog(3, 12) = ";
    calculate::calc<PLOG>(3, 12);
    cout << endl << endl << "plog(5, 7) = ";
    calculate::calc<PLOG>(5, 7);
    cout << endl << endl << "plog(2, 32) = ";
    calculate::calc<PLOG>(2, 32);
    cout << endl;

    return 0;
}