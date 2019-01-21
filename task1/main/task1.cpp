#include <iostream>
#include <fstream>
#include <string>
#include <vector>

using namespace std;

class Vertex {
public:
    string value;
    int leftV;
    int rightV;
    string operation;
    bool used;

    Vertex(string v, int l, int r, string o) {
        value = v;
        leftV = l;
        rightV = r;
        operation = o;
        used = false;
    }
};

string s, tree_str;
size_t p = 0, num_suppose;
int incorrect_statement = -1, sup;
pair< int, int > mp;

vector< Vertex > listV;
vector< string >  suppose;
vector< vector< Vertex > > inList;
vector< pair< int, int > > types;

const string letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ", digits = "0123456789";

Vertex expression();
Vertex part();
Vertex complex2();
Vertex complex1();
Vertex statement();
bool isConnection1(char c);
bool isConnection2(char c);
bool isConnection3(char c);
bool isConnection4(char c);
bool isLetter(char c);
bool isDigit(char c);
bool isAxiom(int st);
bool isMP(int st);
bool isSuppose(int st);
void process();
void dfs(int l, int v);

void tokenize(const string& str, vector<string>& tokens, const string& delimiters) {
    string::size_type lastPos = str.find_first_not_of(delimiters, 0);
    string::size_type pos = str.find_first_of(delimiters, lastPos);
    while (string::npos != pos || string::npos != lastPos) {
        tokens.push_back(str.substr(lastPos, pos - lastPos));
        lastPos = str.find_first_not_of(delimiters, pos);
        pos = str.find_first_of(delimiters, lastPos);
    }
}

int main(int argv, char* argc[]) {
    string filename = argc[1];
    ifstream input_file(filename);
    
    getline(input_file, s);
    s = s.substr(0, s.find("|-"));
    tokenize(s, suppose, ",");
    num_suppose = suppose.size();
    for (size_t i = 0; i < num_suppose; i++) {
        p = 0;
        s = suppose[i];
        listV.clear();
        expression();
        inList.push_back(listV);
    }

    while (input_file.good()) {
        p = 0;
        getline(input_file, s);
        if (s.compare("") == 0) {
            break;
        }
        listV.clear();
        expression();
        inList.push_back(listV);
    }
    input_file.close();

    for (size_t i = num_suppose; i < inList.size(); i++) {
        if (isAxiom(i)) {
            types.push_back(make_pair(-1, -1));
        } else if (i > 0 && isMP(i))  {
            types.push_back(make_pair(mp.first, mp.second));
        }  else if (isSuppose(i)) {
            types.push_back(make_pair(-1, sup));
        } else {
            incorrect_statement = i - num_suppose + 2;
            break;
        }
    }
    
    string output_filename = filename.substr(0, filename.find_last_of(".")) + ".out";
    freopen(output_filename.c_str(), "w", stdout);
    if (incorrect_statement > -1) {
        cout << "[ERROR] Input statement (" << incorrect_statement << ") is not an axiom and it's not a MP of smth.";
    } else {
        if (suppose.size() > 1) {
            for (size_t i = 0; i < suppose.size() - 2; i++) {
                cout << suppose[i] << ",";
            }
            tree_str = "";
            dfs(inList.size() - 1, inList[inList.size() - 1].size() - 1);
            cout << suppose[suppose.size() - 2] << "|-(" << suppose[suppose.size() - 1] << ")->" << tree_str << endl;
        } else {
            cout << "|-(" << suppose[suppose.size() - 1] << ")->" << tree_str << endl;
        }
        process();
    }
    fclose(stdout);

    return 0;
}

void process() {
    string alpha = "(" + suppose[suppose.size() - 1] + ")";
    for (size_t i = 0; i < inList.size() - num_suppose; i++) {
        if (types[i].first == -1) {
            if (types[i].second == -1) {
                //cout << "Axiom" << endl;
                tree_str = "";
                dfs(i + num_suppose, inList[i + num_suppose].size() - 1);
                cout << tree_str << endl;
                cout << tree_str << "->(" << alpha << "->" << tree_str << ")" << endl;
                cout << alpha << "->" << tree_str << endl;
            }
            
            if (types[i].second > -1) {
                //cout << "Suppose " << types[i].second << "(" << types[i].second + 1 << ")" << endl;
                if (types[i].second == num_suppose - 1) {
                    tree_str = "";
                    dfs(i + num_suppose, inList[i + num_suppose].size() - 1);
                    cout << alpha << "->(" << alpha << "->" << alpha << ")" << endl;
                    cout << "(" << alpha << "->(" << alpha << "->" << alpha << "))->(" << alpha << "->((" << alpha << "->" << alpha << ")->" << alpha << "))->(" << alpha << "->" << alpha << ")" << endl;
                    cout << "(" << alpha << "->((" << alpha << "->" << alpha << ")->" << alpha << "))->(" << alpha << "->" << alpha << ")" << endl;
                    cout << "(" << alpha << "->((" << alpha << "->" << alpha << ")->" << alpha << "))" << endl;
                    cout << alpha << "->" << tree_str << endl;
                } else {
                    tree_str = "";
                    dfs(i + num_suppose, inList[i + num_suppose].size() - 1);
                    cout << tree_str << endl;
                    cout << "(" << tree_str << ")->(" << alpha << "->" << tree_str << ")" << endl;
                    cout << alpha << "->" << tree_str << endl;
                }
            }
        } else {
            //cout << "MP of " << types[i].first << "(" << types[i].first - num_suppose + 1 <<  ") and " << types[i].second << "(" << types[i].second - num_suppose + 1 << ")" << endl;
            tree_str = "";
            dfs(types[i].first, inList[types[i].first].size() - 1);
            string sj = tree_str;
            tree_str = "";
            dfs(types[i].second, inList[types[i].second].size() - 1);
            string sk = tree_str;
            tree_str = "";
            dfs(i + num_suppose, inList[i + num_suppose].size() - 1);
            cout << "(" << alpha << "->" << sj << ")->((" << alpha << "->(" << sj << "->" << tree_str << "))->(" << alpha << "->" << tree_str << "))" << endl;
            cout << "(" << alpha << "->(" << sj << "->" << tree_str << "))->(" << alpha << "->" << tree_str << ")" << endl;
            cout << alpha << "->" << tree_str << endl;
        }
    }
}

void dfs(int l, int v) {
    if (inList[l][v].leftV > -1 && inList[l][v].rightV > -1) {
        tree_str += "(";
        dfs(l, inList[l][v].leftV);
        tree_str += inList[l][v].operation;
        dfs(l, inList[l][v].rightV);
        tree_str += ")";
    } else if (inList[l][v].rightV > -1) {
        tree_str += "!(";
        dfs(l, inList[l][v].rightV);
        tree_str += ")";
    } else {
        tree_str += "(";
        tree_str += inList[l][v].value;
        tree_str += ")";
    }
}

bool equal_subtrees(int first, int first_subtree, int second, int second_subtree) {
    tree_str = "";
    dfs(first, first_subtree);
    string dfs1 = tree_str;
    tree_str = "";
    dfs(second, second_subtree);
    string dfs2 = tree_str;
    return dfs1.compare(dfs2) == 0;
}

bool isMP(int st) {
    for (int i = st - 1; i > -1; i--) {
        if (inList[i][inList[i].size() - 1].operation.compare("->") == 0 &&
            equal_subtrees(i, inList[i][inList[i].size() - 1].rightV, st, inList[st].size() - 1)) {
            for (int j = st - 1; j > -1; j--) {
                if (j != i &&
                    equal_subtrees(j, inList[j].size() - 1, i, inList[i][inList[i].size() - 1].leftV)) {
                        mp.first = j;
                        mp.second = i;
                        return true;
                }
            }
        }        
    }
    return false;
}

bool isSuppose(int st) {
    for (size_t i = 0; i < num_suppose; i++) {
        if (equal_subtrees(i, inList[i].size() - 1, st, inList[st].size() - 1)) {
            sup = i;
            return true;
        }        
    }
    return false;
}

bool isAxiom(int st) {
    vector< int > c;
    for (size_t i = 0; i < 13; i++) {
        c.push_back(-1);
    }

    // Axiom 1
    c[0] = inList[st].size() - 1;
    if (inList[st][c[0]].operation.compare("->") == 0 && 
        inList[st][c[0]].leftV >= 0 && 
        inList[st][c[0]].rightV >= 0) {
        c[1] = inList[st][c[0]].leftV;
        c[2] = inList[st][c[0]].rightV;
        if (inList[st][c[2]].operation.compare("->") == 0 && 
            inList[st][c[2]].leftV >= 0 && 
            inList[st][c[2]].rightV >= 0) {
            c[3] = inList[st][c[2]].rightV;
            if (equal_subtrees(st, c[1], st, c[3])) {
                return true;
            }
        }
    }
    
    // Axiom 2
    c[0] = inList[st].size() - 1;
    if (inList[st][c[0]].operation.compare("->") == 0 && 
        inList[st][c[0]].leftV >= 0 && 
        inList[st][c[0]].rightV >= 0) {
        c[1] = inList[st][c[0]].leftV;
        c[4] = inList[st][c[0]].rightV;
        if (inList[st][c[1]].operation.compare("->") == 0 && 
            inList[st][c[1]].leftV >= 0 && 
            inList[st][c[1]].rightV >= 0) {
            c[2] = inList[st][c[1]].leftV;
            c[3] = inList[st][c[1]].rightV;
            if (inList[st][c[4]].operation.compare("->") == 0 && 
                inList[st][c[4]].leftV >= 0 && 
                inList[st][c[4]].rightV >= 0) {
                c[5] = inList[st][c[4]].leftV;
                c[10] = inList[st][c[4]].rightV;
                if (inList[st][c[5]].operation.compare("->") == 0 && 
                    inList[st][c[5]].leftV >= 0 && 
                    inList[st][c[5]].rightV >= 0) {
                    c[6] = inList[st][c[5]].leftV;
                    c[7] = inList[st][c[5]].rightV;
                    if (inList[st][c[7]].operation.compare("->") == 0 && 
                        inList[st][c[7]].leftV >= 0 && 
                        inList[st][c[7]].rightV >= 0) {
                        c[8] = inList[st][c[7]].leftV;
                        c[9] = inList[st][c[7]].rightV;
                        if (inList[st][c[10]].operation.compare("->") == 0 && 
                            inList[st][c[10]].leftV >= 0 && 
                            inList[st][c[10]].rightV >= 0) {
                            c[11] = inList[st][c[10]].leftV;
                            c[12] = inList[st][c[10]].rightV;
                            if (equal_subtrees(st, c[2], st, c[6]) &&
                                equal_subtrees(st, c[2], st, c[11]) &&
                                equal_subtrees(st, c[3], st, c[8]) &&
                                equal_subtrees(st, c[9], st, c[12])) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
    }
    
    // Axiom 3
    c[0] = inList[st].size() - 1;
    if (inList[st][c[0]].operation.compare("->") == 0 && 
        inList[st][c[0]].leftV >= 0 && 
        inList[st][c[0]].rightV >= 0) {
        c[1] = inList[st][c[0]].leftV;
        c[2] = inList[st][c[0]].rightV;
        if (inList[st][c[2]].operation.compare("->") == 0 && 
            inList[st][c[2]].leftV >= 0 && 
            inList[st][c[2]].rightV >= 0) {
            c[3] = inList[st][c[2]].leftV;
            c[4] = inList[st][c[2]].rightV;
            if (inList[st][c[4]].operation.compare("&") == 0 && 
                inList[st][c[4]].leftV >= 0 && 
                inList[st][c[4]].rightV >= 0) {
                c[5] = inList[st][c[4]].leftV;
                c[6] = inList[st][c[4]].rightV;
                if (equal_subtrees(st, c[1], st, c[5]) &&
                    equal_subtrees(st, c[3], st, c[6])) {
                    return true;
                }
            }
        }
    }
    
    // Axiom 4
    c[0] = inList[st].size() - 1;
    if (inList[st][c[0]].operation.compare("->") == 0 && 
        inList[st][c[0]].leftV >= 0 && 
        inList[st][c[0]].rightV >= 0) {
        c[1] = inList[st][c[0]].leftV;
        c[4] = inList[st][c[0]].rightV;
        if (inList[st][c[1]].operation.compare("&") == 0 && 
            inList[st][c[1]].leftV >= 0 && 
            inList[st][c[1]].rightV >= 0) {
            c[2] = inList[st][c[1]].leftV;
            c[3] = inList[st][c[1]].rightV;
            if (equal_subtrees(st, c[2], st, c[4])) {
                return true;
            }
        }
    }
    
    // Axiom 5
    c[0] = inList[st].size() - 1;
    if (inList[st][c[0]].operation.compare("->") == 0 && 
        inList[st][c[0]].leftV >= 0 && 
        inList[st][c[0]].rightV >= 0) {
        c[1] = inList[st][c[0]].leftV;
        c[4] = inList[st][c[0]].rightV;
        if (inList[st][c[1]].operation.compare("&") == 0 && 
            inList[st][c[1]].leftV >= 0 && 
            inList[st][c[1]].rightV >= 0) {
            c[2] = inList[st][c[1]].leftV;
            c[3] = inList[st][c[1]].rightV;
            if (equal_subtrees(st, c[3], st, c[4])) {
                return true;
            }
        }
    }
    
    // Axiom 6
    c[0] = inList[st].size() - 1;
    if (inList[st][c[0]].operation.compare("->") == 0 && 
        inList[st][c[0]].leftV >= 0 && 
        inList[st][c[0]].rightV >= 0) {
        c[1] = inList[st][c[0]].leftV;
        c[2] = inList[st][c[0]].rightV;
        if (inList[st][c[2]].operation.compare("|") == 0 && 
            inList[st][c[2]].leftV >= 0 && 
            inList[st][c[2]].rightV >= 0) {
            c[3] = inList[st][c[2]].leftV;
            c[4] = inList[st][c[2]].rightV;
            if (equal_subtrees(st, c[1], st, c[3])) {
                return true;
            }
        }
    }
    
    // Axiom 7
    c[0] = inList[st].size() - 1;
    if (inList[st][c[0]].operation.compare("->") == 0 && 
        inList[st][c[0]].leftV >= 0 && 
        inList[st][c[0]].rightV >= 0) {
        c[1] = inList[st][c[0]].leftV;
        c[2] = inList[st][c[0]].rightV;
        if (inList[st][c[2]].operation.compare("|") == 0 && 
            inList[st][c[2]].leftV >= 0 && 
            inList[st][c[2]].rightV >= 0) {
            c[3] = inList[st][c[2]].leftV;
            c[4] = inList[st][c[2]].rightV;
            if (equal_subtrees(st, c[1], st, c[4])) {
                return true;
            }
        }
    }
    
    // Axiom 8
    c[0] = inList[st].size() - 1;
    if (inList[st][c[0]].operation.compare("->") == 0 && 
        inList[st][c[0]].leftV >= 0 && 
        inList[st][c[0]].rightV >= 0) {
        c[1] = inList[st][c[0]].leftV;
        c[4] = inList[st][c[0]].rightV;
        if (inList[st][c[1]].operation.compare("->") == 0 && 
            inList[st][c[1]].leftV >= 0 && inList[st][c[1]].rightV >= 0) {
            c[2] = inList[st][c[1]].leftV;
            c[3] = inList[st][c[1]].rightV;
            if (inList[st][c[4]].operation.compare("->") == 0 && 
                inList[st][c[4]].leftV >= 0 && 
                inList[st][c[4]].rightV >= 0) {
                c[5] = inList[st][c[4]].leftV;
                c[8] = inList[st][c[4]].rightV;
                if (inList[st][c[5]].operation.compare("->") == 0 && 
                    inList[st][c[5]].leftV >= 0 && 
                    inList[st][c[5]].rightV >= 0) {
                    c[6] = inList[st][c[5]].leftV;
                    c[7] = inList[st][c[5]].rightV;
                    if (inList[st][c[8]].operation.compare("->") == 0 && 
                        inList[st][c[8]].leftV >= 0 && 
                        inList[st][c[8]].rightV >= 0) {
                        c[9] = inList[st][c[8]].leftV;
                        c[12] = inList[st][c[8]].rightV;
                        if (inList[st][c[9]].operation.compare("|") == 0 && 
                            inList[st][c[9]].leftV >= 0 && 
                            inList[st][c[9]].rightV >= 0) {
                            c[10] = inList[st][c[9]].leftV;
                            c[11] = inList[st][c[9]].rightV;
                            if (equal_subtrees(st, c[2], st, c[10]) &&
                                equal_subtrees(st, c[3], st, c[7]) &&
                                equal_subtrees(st, c[3], st, c[12]) &&
                                equal_subtrees(st, c[6], st, c[11])) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
    }
    
    // Axiom 9
    c[0] = inList[st].size() - 1;
    if (inList[st][c[0]].operation.compare("->") == 0 && 
        inList[st][c[0]].leftV >= 0 && 
        inList[st][c[0]].rightV >= 0) {
        c[1] = inList[st][c[0]].leftV;
        c[4] = inList[st][c[0]].rightV;
        if (inList[st][c[1]].operation.compare("->") == 0 && 
            inList[st][c[1]].leftV >= 0 && 
            inList[st][c[1]].rightV >= 0) {
            c[2] = inList[st][c[1]].leftV;
            c[3] = inList[st][c[1]].rightV;
            if (inList[st][c[4]].operation.compare("->") == 0 && 
                inList[st][c[4]].leftV >= 0 && 
                inList[st][c[4]].rightV >= 0) {
                c[5] = inList[st][c[4]].leftV;
                c[9] = inList[st][c[4]].rightV;
                if (inList[st][c[5]].operation.compare("->") == 0 && 
                    inList[st][c[5]].leftV >= 0 && 
                    inList[st][c[5]].rightV >= 0) {
                    c[6] = inList[st][c[5]].leftV;
                    c[7] = inList[st][c[5]].rightV;
                    if (inList[st][c[7]].operation.compare("!") == 0 && 
                        inList[st][c[0]].rightV >= 0) {
                        c[8] = inList[st][c[7]].rightV;
                        if (inList[st][c[9]].operation.compare("!") == 0 && 
                            inList[st][c[9]].rightV >= 0) {
                            c[10] = inList[st][c[9]].rightV;
                            if (equal_subtrees(st, c[2], st, c[6]) &&
                                equal_subtrees(st, c[2], st, c[10]) &&
                                equal_subtrees(st, c[3], st, c[8])) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
    }
    
    // Axiom 10
    c[0] = inList[st].size() - 1;
    if (inList[st][c[0]].operation.compare("->") == 0 && 
        inList[st][c[0]].leftV >= 0 && 
        inList[st][c[0]].rightV >= 0) {
        c[1] = inList[st][c[0]].leftV;
        c[4] = inList[st][c[0]].rightV;
        if (inList[st][c[1]].operation.compare("!") == 0 && 
            inList[st][c[1]].rightV >= 0) {
            c[2] = inList[st][c[1]].rightV;
            if (inList[st][c[2]].operation.compare("!") == 0 && 
                inList[st][c[2]].rightV >= 0) {
                c[3] = inList[st][c[2]].rightV;
                if (equal_subtrees(st, c[3], st, c[4])) {
                    return true;
                }
            }
        }
    }

    return false;
}

Vertex expression() {
    Vertex expressionV = Vertex("", -1, -1, "");
    Vertex v3 = complex2();
    expressionV.leftV = listV.size() - 1;
    while (p < s.length() && isConnection1(s[p])) {
        expressionV.operation = "->";
        p += 2;
        expressionV.leftV = listV.size() - 1;
        Vertex u3 = expression();
        expressionV.rightV = listV.size() - 1;
        listV.push_back(expressionV);
    }
    return expressionV;
}

Vertex complex2() {
    Vertex complex2V = Vertex("", -1, -1, "");
    Vertex v2 = complex1();
    complex2V.leftV = listV.size() - 1;
    while (p < s.length() && isConnection2(s[p])) {
        complex2V.operation = "|";
        p++;
        complex2V.leftV = listV.size() - 1;
        Vertex u2 = complex1();
        complex2V.rightV = listV.size() - 1;
        listV.push_back(complex2V);
    }
    return complex2V;
}

Vertex complex1() {
    Vertex complex1V = Vertex("", -1, -1, "");
    Vertex v1 = part();
    complex1V.leftV = listV.size() - 1;
    while (p < s.length() && isConnection3(s[p])) {
        complex1V.operation = "&";
        p++;
        complex1V.leftV = listV.size() - 1;
        Vertex u1 = part();
        complex1V.rightV = listV.size() - 1;
        listV.push_back(complex1V);
    }
    return complex1V;
}

Vertex part() {
    Vertex partV = Vertex("", -1, -1, "");
    string result = "";
    if (s[p] == '!') {
        p++;
        Vertex v = part();
        partV.rightV = listV.size() - 1;
        partV.operation = "!";
        listV.push_back(partV);
    }
    else if (s[p] == '(') {
        p++;
        partV = expression();
        p++;
    }
    else if (isLetter(s[p])) {
        partV = statement();
        listV.push_back(partV);
    }
    return partV;
}

Vertex statement() {
    string result = "";
    while (p != s.length()) {
        if (!isLetter(s[p])) {
            break;
        }
        result += s[p];
        p++;
    }
    while (p != s.length()) {
        if (!isDigit(s[p])) {
            break;
        }
        result += s[p];
        p++;
    }
    Vertex statementV = Vertex(result, -1, -1, "0");
    return statementV;
}

bool isConnection1(char c) {
    return (c == '-');
}

bool isConnection2(char c) {
    return (c == '|');
}

bool isConnection3(char c) {
    return (c == '&');
}

bool isConnection4(char c) {
    return (c == '!');
}

bool isLetter(char c) {
    for (size_t i = 0; i < letters.length(); i++) {
        if (letters[i] == c) {
            return true;
        }
    }
    return false;
}

bool isDigit(char c) {
    for (size_t i = 0; i < digits.length(); i++) {
        if (digits[i] == c) {
            return true;
        }
    }
    return false;
}