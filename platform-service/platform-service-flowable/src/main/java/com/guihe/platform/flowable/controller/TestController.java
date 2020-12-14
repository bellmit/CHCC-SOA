package com.guihe.platform.flowable.controller;

import org.flowable.engine.ProcessEngine;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * @author CHCC
 * @FileName TestController
 * @Date 2020/11/25 9:58 上午
 * @Version 1.0
 * @Description TODO flowable流程测试
 */
@RestController
public class TestController {

    @Resource
    private RuntimeService runtimeService;

    @Resource
    private TaskService taskService;

    @Resource
    private ProcessEngine processEngine;

    @Resource
    private RepositoryService repositoryService;

    //创建流程
    @RequestMapping(value = "/add")
    public String addExpense(String userId, Integer money) {
        //启动流程
        HashMap<String, Object> map = new HashMap();
        map.put("taskUser", userId);
        map.put("money", money);
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("Expense", map);
        return "提交成功.流程Id为：" + processInstance.getId();
    }

    //根据用户id查询流程列表
    @RequestMapping(value = "/list")
    public Object list(String userId) {
        List<Task> tasks = taskService.createTaskQuery().taskAssignee(userId).orderByTaskCreateTime().desc().list();
        for (Task task : tasks) {
            System.out.println(task.toString());
        }
        return tasks.toArray().toString();
    }

    @RequestMapping(value = "apply")
    public String apply(String taskId) {
        Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
        if (task == null) {
            throw new RuntimeException("流程不存在");
        }
        //通过审核
        HashMap<String, Object> map = new HashMap();
        map.put("outcome", "通过");
        taskService.complete(taskId, map);
        return "processed ok!";
    }

    @RequestMapping(value = "reject")
    public String reject(String taskId) {
        HashMap<String, Object> map = new HashMap();
        map.put("outcome", "驳回");
        taskService.complete(taskId, map);
        return "reject";
    }

}
