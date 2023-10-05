package com.example;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtField;
import javassist.CtMethod;
import javassist.CtNewMethod;

public class Main {
    public static void main(String[] args) {
        try {
            ClassPool pool = ClassPool.getDefault();
            CtClass ClassePessoa = pool.makeClass("ClassePessoa");

            CtField nomePessoa = new CtField(CtClass.charType, "nome", ClassePessoa);
            CtField idadePessoa = new CtField(CtClass.intType, "idade", ClassePessoa);

            ClassePessoa.addField(nomePessoa);
            ClassePessoa.addField(idadePessoa);

            CtConstructor construtor = new CtConstructor(new CtClass[] { CtClass.charType, CtClass.intType }, ClassePessoa);
            construtor.setBody("{ this.nome = $1; this.idade = $2; }");
            ClassePessoa.addConstructor(construtor);

            CtMethod ImprimePessoa = new CtMethod(CtClass.voidType, "imprimePessoa", new CtClass[] {}, ClassePessoa);
            ImprimePessoa.setBody("{ System.out.println(\"Nome: \" + this.nome); System.out.println(\"Idade: \" + this.idade); }");
            ClassePessoa.addMethod(ImprimePessoa);

            ClassePessoa.writeFile("../demo/target/classes/com/example/");

            Class<?> ClasseDinamica = ClassePessoa.toClass();
            Object InstanciaPessoa = ClasseDinamica.getConstructor(String.class, int.class).newInstance("Jonas", 25);
            ClasseDinamica.getMethod("imprimePessoa").invoke(InstanciaPessoa);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}