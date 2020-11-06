void print_lines(const set<string>& s) {
    int c = 0;
    set<string>::const_iterator i
         = s.begin();
    for (; i != s.end(); ++i) {
        cout << c << ", "
            << *i << endl;
        ++c;
    }
}
void print_table(const map<string, string>& m) {
    int c = 0;
    map<string, string>::const_iterator i
        = m.begin();
    for (; i != m.end(); ++i){
        cout << c << ", "
            << i->first << " "
            << i->second << endl;
        ++c;
    }
}