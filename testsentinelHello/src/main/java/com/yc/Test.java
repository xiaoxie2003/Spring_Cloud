package com.yc;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Test {
    public static void main(String[] args) {
        initFlowRules(); //初始化sentinel流量规则的代码

        while (true){
            Entry entry = null;
            try {
                //监控的入口点
                entry = SphU.entry("HelloWorld!");

                //业务逻辑-开始
                System.out.println("HelloWorld!");
                //业务逻辑-结束

            }catch (BlockException e){
                //流控逻辑处理-开始
                System.out.println("block");
                //流控逻辑处理-结束
            }finally {
                if(entry != null){
                    entry.exit();
                }
            }
        }
    }

    private static void initFlowRules(){
        //规则的容器
        //FlowRule流量规则
        List<FlowRule> rules = new ArrayList<>();
        FlowRule rule = new FlowRule();
        rule.setResource("HelloWorld!");  //resuourcename 资源名  sentinel API
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS); //流控类型(基于每秒访问次数的流控规则)
        rule.setCount(20000); // 指定的每秒超过20000请求   阈值
        rules.add(rule);
        FlowRuleManager.loadRules(rules); //加入到sentinel的流控规则管理器
    }
}
