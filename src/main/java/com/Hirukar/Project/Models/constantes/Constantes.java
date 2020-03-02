package com.Hirukar.Project.Models.constantes;

import com.Hirukar.Project.Models.Beans.Periodo;
import com.google.gson.Gson;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.LinkedList;

public class Constantes{
    public static Gson gson = new Gson();
    public static BCryptPasswordEncoder bCrypt = new BCryptPasswordEncoder();
    public static final String[] diasDaSemana = new String[]{"SEGUNDA","TERCA","QUARTA","QUINTA","SEXTA"};
    public static final int[] horarios = new int[]{0,1,2,3,4,5,6,7,7,9,10,11,12,13,14};
    public static String formatarJson(String raw) {
        return raw
                .replace("\\","")
                .replace("\"[","[")
                .replace("]\"","]")
                .replace("\"{","{")
                .replace("}\"","}");
    }

}