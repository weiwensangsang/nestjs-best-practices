package com.weiwensangsang.web.rest.vm;

import net.sf.json.JSONObject;

public class GodAnswer {
    private String grade;
    private String analysis;

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getAnalysis() {
        return analysis;
    }

    public void setAnalysis(String analysis) {
        this.analysis = analysis;
    }

    @Override
    public String toString() {
        return "GodAnswer{" +
            "grade='" + grade + '\'' +
            ", analysis='" + analysis + '\'' +
            '}';
    }

    public GodAnswer() {
        grade = "吉";
        analysis = "大吉大利, 今天吃鸡";
    }

    public static GodAnswer create(String jsonStr) {
        JSONObject obj = JSONObject.fromObject(jsonStr);
        GodAnswer answer = (GodAnswer) JSONObject.toBean(obj, GodAnswer.class);
        return answer;
    }
}
