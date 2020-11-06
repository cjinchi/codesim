package com.chenjinchi.codesim;

import org.antlr.v4.runtime.Token;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CodeSim {
    public static void main(String[] args) {
        Config config = UserInterface.processArgs(args);
        if (config.isError()) {
            System.err.println(config.getErrorInfo());
            System.exit(-1);
        }
        if (config.isHelp()) {
            UserInterface.printUsage();
            return;
        }


        String codeA = null, codeB = null;
        try {
            codeA = new String(Files.readAllBytes(Paths.get(config.getFiles().get(0))), StandardCharsets.UTF_8);
            codeB = new String(Files.readAllBytes(Paths.get(config.getFiles().get(1))), StandardCharsets.UTF_8);
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.exit(-2);
        }

        System.out.println(CppCompare.compare(codeA, codeB));



//                String codeA = "ListNode *mergeKLists(vector<ListNode *> &lists) {\n" +
//                "    if(lists.empty()){\n" +
//                "        return nullptr;\n" +
//                "    }\n" +
//                "    while(lists.size() > 1){\n" +
//                "        lists.push_back(mergeTwoLists(lists[0], lists[1]));\n" +
//                "        lists.erase(lists.begin());\n" +
//                "        lists.erase(lists.begin());\n" +
//                "    }\n" +
//                "    return lists.front();\n" +
//                "}\n" +
//                "ListNode *mergeTwoLists(ListNode *l1, ListNode *l2) {\n" +
//                "    if(l1 == nullptr){\n" +
//                "        return l2;\n" +
//                "    }\n" +
//                "    if(l2 == nullptr){\n" +
//                "        return l1;\n" +
//                "    }\n" +
//                "    if(l1->val <= l2->val){\n" +
//                "        l1->next = mergeTwoLists(l1->next, l2);\n" +
//                "        return l1;\n" +
//                "    }\n" +
//                "    else{\n" +
//                "        l2->next = mergeTwoLists(l1, l2->next);\n" +
//                "        return l2;\n" +
//                "    }\n" +
//                "}";
//        String codeB = "class Solution {\n" +
//                "public:\n" +
//                "    ListNode *mergeTwoLists(ListNode* l1, ListNode* l2) {\n" +
//                "        if (NULL == l1) return l2;\n" +
//                "        else if (NULL == l2) return l1;\n" +
//                "        if (l1->val <= l2->val) {\n" +
//                "            l1->next = mergeTwoLists(l1->next, l2);\n" +
//                "            return l1;\n" +
//                "        }\n" +
//                "        else {\n" +
//                "            l2->next = mergeTwoLists(l1, l2->next);\n" +
//                "            return l2;\n" +
//                "        }\n" +
//                "    }\n" +
//                "    ListNode *mergeKLists(vector<ListNode *> &lists) {\n" +
//                "        if (lists.empty()) return NULL;\n" +
//                "        int len = lists.size();\n" +
//                "        while (len > 1) {\n" +
//                "            for (int i = 0; i < len / 2; ++i) {\n" +
//                "                lists[i] = mergeTwoLists(lists[i], lists[len - 1 - i]);\n" +
//                "            }\n" +
//                "            len = (len + 1) / 2;\n" +
//                "        }\n" +
//                "        \n" +
//                "        return lists.front();\n" +
//                "    }\n" +
//                "};";



//        Set<Integer> delimiterTypes = new HashSet<>(Arrays.asList(83,124,84,3));
//        List<Token> tokens = CppCompare.tokenize(codeA);
//        for(Token token:tokens){
//            System.out.print(token.getText());
//            System.out.print(" ");
//            if (delimiterTypes.contains(token.getType()) ){
//                System.out.println();
//            }
//        }
//        double score = CppCompare.compare(codeA,codeB);

//       something related to user interface here
    }
}
