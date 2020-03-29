package Model.Indexer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;

public class UnPackingInvertedIndex {


    public static void UnPackFile(String path,String str){
            try {
                File file = new File(path + "/InvertedIndex"+str+".txt");
                BufferedReader br = new BufferedReader(new FileReader(file));
                PrintWriter E = new PrintWriter(path+"/E"+str+".txt", "UTF-8");
                PrintWriter A = new PrintWriter(path+"/A"+str+".txt", "UTF-8");
                PrintWriter R = new PrintWriter(path+"/R"+str+".txt", "UTF-8");
                PrintWriter I = new PrintWriter(path+"/I"+str+".txt", "UTF-8");
                PrintWriter O = new PrintWriter(path+"/O"+str+".txt", "UTF-8");
                PrintWriter T = new PrintWriter(path+"/T"+str+".txt", "UTF-8");
                PrintWriter N = new PrintWriter(path+"/N"+str+".txt", "UTF-8");
                PrintWriter S = new PrintWriter(path+"/S"+str+".txt", "UTF-8");
                PrintWriter L = new PrintWriter(path+"/L"+str+".txt", "UTF-8");
                PrintWriter C = new PrintWriter(path+"/C"+str+".txt", "UTF-8");
                PrintWriter U = new PrintWriter(path+"/U"+str+".txt", "UTF-8");
                PrintWriter D = new PrintWriter(path+"/D"+str+".txt", "UTF-8");
                PrintWriter P = new PrintWriter(path+"/P"+str+".txt", "UTF-8");
                PrintWriter M = new PrintWriter(path+"/M"+str+".txt", "UTF-8");
                PrintWriter H = new PrintWriter(path+"/H"+str+".txt", "UTF-8");
                PrintWriter G = new PrintWriter(path+"/G"+str+".txt", "UTF-8");
                PrintWriter B = new PrintWriter(path+"/B"+str+".txt", "UTF-8");
                PrintWriter F = new PrintWriter(path+"/F"+str+".txt", "UTF-8");
                PrintWriter Y = new PrintWriter(path+"/Y"+str+".txt", "UTF-8");
                PrintWriter W = new PrintWriter(path+"/W"+str+".txt", "UTF-8");
                PrintWriter K = new PrintWriter(path+"/K"+str+".txt", "UTF-8");
                PrintWriter V = new PrintWriter(path+"/V"+str+".txt", "UTF-8");
                PrintWriter X = new PrintWriter(path+"/X"+str+".txt", "UTF-8");
                PrintWriter Z = new PrintWriter(path+"/Z"+str+".txt", "UTF-8");
                PrintWriter J = new PrintWriter(path+"/J"+str+".txt", "UTF-8");
                PrintWriter Q = new PrintWriter(path+"/Q"+str+".txt", "UTF-8");
                PrintWriter NUMBER = new PrintWriter(path+"NUMBER"+str+".txt", "UTF-8");

                String st;
                while ((st = br.readLine()) != null) {
                    String[] split = st.split("@");
                    String[] check = split[0].split(" ");
                    String toCheck = split[0].replaceAll("-||'", "");
                    toCheck = toCheck.replaceAll("'", "");
                    toCheck = toCheck.replaceAll("/", "");
                    if ((split[0].charAt(0) >= 'a' && split[0].charAt(0) <= 'z' || split[0].charAt(0) >= 'A' && split[0].charAt(0) <= 'Z')) {
                        if (st.charAt(0) == 'E' || st.charAt(0) == 'e') {
                            E.write(st + '\n');
                            continue;
                        }
                        if (st.charAt(0) == 'A' || st.charAt(0) == 'a') {
                            A.write(st + '\n');
                            continue;
                        }
                        if (st.charAt(0) == 'R' || st.charAt(0) == 'r') {
                            R.write(st + '\n');
                            continue;
                        }
                        if (st.charAt(0) == 'I' || st.charAt(0) == 'i') {
                            I.write(st + '\n');
                            continue;
                        }
                        if (st.charAt(0) == 'o' || st.charAt(0) == 'O') {
                            O.write(st + '\n');
                            continue;
                        }
                        if (st.charAt(0) == 'T' || st.charAt(0) == 't') {
                            T.write(st + '\n');
                            continue;
                        }
                        if (st.charAt(0) == 'N' || st.charAt(0) == 'n') {
                            N.write(st + '\n');
                            continue;
                        }
                        if (st.charAt(0) == 'S' || st.charAt(0) == 's') {
                            S.write(st + '\n');
                            continue;
                        }
                        if (st.charAt(0) == 'L' || st.charAt(0) == 'l') {
                            L.write(st + '\n');
                            continue;
                        }
                        if (st.charAt(0) == 'C' || st.charAt(0) == 'c') {
                            C.write(st + '\n');
                            continue;
                        }
                        if (st.charAt(0) == 'U' || st.charAt(0) == 'u') {
                            U.write(st + '\n');
                            continue;
                        }
                        if (st.charAt(0) == 'D' || st.charAt(0) == 'd') {
                            D.write(st + '\n');
                            continue;
                        }
                        if (st.charAt(0) == 'P' || st.charAt(0) == 'p') {
                            P.write(st + '\n');
                            continue;
                        }
                        if (st.charAt(0) == 'M' || st.charAt(0) == 'm') {
                            M.write(st + '\n');
                            continue;
                        }
                        if (st.charAt(0) == 'H' || st.charAt(0) == 'h') {
                            H.write(st + '\n');
                            continue;
                        }
                        if (st.charAt(0) == 'G' || st.charAt(0) == 'g') {
                            G.write(st + '\n');
                            continue;
                        }
                        if (st.charAt(0) == 'B' || st.charAt(0) == 'b') {
                            B.write(st + '\n');
                            continue;
                        }
                        if (st.charAt(0) == 'F' || st.charAt(0) == 'f') {
                            F.write(st + '\n');
                            continue;
                        }
                        if (st.charAt(0) == 'Y' || st.charAt(0) == 'y') {
                            Y.write(st + '\n');
                            continue;
                        }
                        if (st.charAt(0) == 'W' || st.charAt(0) == 'w') {
                            W.write(st + '\n');
                            continue;
                        }
                        if (st.charAt(0) == 'K' || st.charAt(0) == 'k'

                        ) {
                            K.write(st + '\n');
                            continue;
                        }
                        if (st.charAt(0) == 'V' || st.charAt(0) == 'v'
                        ) {
                            V.write(st + '\n');
                            continue;
                        }
                        if (st.charAt(0) == 'X' ||
                                st.charAt(0) == 'x'
                        ) {
                            X.write(st + '\n');
                            continue;
                        }
                        if (st.charAt(0) == 'Z' ||
                                st.charAt(0) == 'z'
                        ) {
                            Z.write(st + '\n');
                            continue;
                        }
                        if (st.charAt(0) == 'J' ||
                                st.charAt(0) == 'j'
                        ) {
                            J.write(st + '\n');
                            continue;
                        }
                        if (st.charAt(0) == 'Q' ||
                                st.charAt(0) == 'q'
                        ) {
                            Q.write(st + '\n');
                            continue;
                        }
                    } else {
                        NUMBER.write(st + "\n");
                        continue;
                    }
                    E.close();
                    A.close();
                    R.close();
                    I.close();
                    T.close();
                    N.close();
                    S.close();
                    N.close();
                    S.close();
                    L.close();
                    N.close();
                    S.close();
                    L.close();
                    C.close();
                    U.close();
                    D.close();
                    P.close();
                    M.close();
                    H.close();
                    G.close();
                    B.close();
                    F.close();
                    Y.close();
                    W.close();
                    K.close();
                    V.close();
                    X.close();
                    Z.close();
                    J.close();
                    Q.close();
                    NUMBER.close();
                    System.out.println("finish");
                }
            } catch (Exception E) {
                System.out.println("finish");
            }
        }
    }

