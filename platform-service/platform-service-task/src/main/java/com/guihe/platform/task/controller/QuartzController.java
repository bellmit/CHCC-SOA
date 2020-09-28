package com.guihe.platform.task.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.guihe.platform.core.domain.QrtzJobDetails;
import com.guihe.platform.core.form.BaseForm;
import com.guihe.platform.task.config.SpringContextBean;
import com.guihe.platform.task.service.QuartzService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author CHCC
 * @FileName QuartzController
 * @Date 2020/9/26 2:29 下午
 * @Version 1.0
 * @Description TODO Quartz远程调用控制器
 */
@Slf4j
@RestController
@RequestMapping("/service-quartz/handle")
public class QuartzController {

    @Resource
    private QuartzService quartzService;

    /**
     * @Description TODO 获取所有任务
     * @Author CHCC
     * @Date 2020/9/28 10:11 上午
     * @params  * @param form
     * @returen com.baomidou.mybatisplus.core.metadata.IPage
     **/
    @PostMapping("/list")
    public IPage findQuartzList(@RequestBody BaseForm form){
        IPage page = quartzService.findList(form);
        return page;
    }

    /**
     * @Description TODO 获取所有正在运行的任务
     * @Author CHCC
     * @Date 2020/9/28 10:12 上午
     * @params  * @param
     * @returen java.util.List
     **/
    @GetMapping("/run/list")
    public List findQuartzRunList(){
        return quartzService.queryRunJob();
    }

    /**
     * @Description TODO 通过任务名获取任务详情
     * @Author CHCC
     * @Date 2020/9/28 4:32 下午
     * @params  * @param name
     * @returen com.guihe.platform.core.domain.QrtzJobDetails
     **/
    @PostMapping("/findByName")
    public QrtzJobDetails findByName(@RequestParam("name") String name){
        return quartzService.findByName(name);
    }

    /**
     * @Description TODO 删除一个任务
     * @Author CHCC
     * @Date 2020/9/28 10:12 上午
     * @params  * @param qrtzJobDetails
     * @returen void
     **/
    @PostMapping("/deleteJob")
    public void deleteJob(@RequestBody QrtzJobDetails qrtzJobDetails){
        quartzService.deleteJob(qrtzJobDetails.getJobName(),qrtzJobDetails.getJobGroup());
    }

    /**
     * @Description TODO 暂停任务
     * @Author CHCC
     * @Date 2020/9/28 10:12 上午
     * @params  * @param qrtzJobDetails
     * @returen void
     **/
    @PostMapping("/pauseJob")
    public void pauseJob(@RequestBody QrtzJobDetails qrtzJobDetails){
        quartzService.pauseJob(qrtzJobDetails.getJobName(),qrtzJobDetails.getJobGroup());
    }

    /**
     * @Description TODO 恢复任务
     * @Author CHCC
     * @Date 2020/9/28 10:12 上午
     * @params  * @param qrtzJobDetails
     * @returen void
     **/
    @PostMapping("/resumeJob")
    public void resumeJob(@RequestBody QrtzJobDetails qrtzJobDetails){
        quartzService.resumeJob(qrtzJobDetails.getJobName(),qrtzJobDetails.getJobGroup());
    }

    /**
     * @Description TODO 立即执行任务
     * @Author CHCC
     * @Date 2020/9/28 10:13 上午
     * @params  * @param qrtzJobDetails
     * @returen void
     **/
    @PostMapping("/runJob")
    public void runJob(@RequestBody QrtzJobDetails qrtzJobDetails){
        quartzService.runAJobNow(qrtzJobDetails.getJobName(),qrtzJobDetails.getJobGroup());
    }

    /**
     * @Description TODO 添加一个任务
     * @Author CHCC
     * @Date 2020/9/28 10:13 上午
     * @params  * @param qrtzJobDetails
     * @returen void
     **/
    @PostMapping("/addJob")
    public void addJob(@RequestBody QrtzJobDetails qrtzJobDetails){
        try {
            String jobName = qrtzJobDetails.getJobName();
            QrtzJobDetails details = quartzService.findByName(jobName);
            if(details != null) return;
            Class<? extends QuartzJobBean> clazz = (Class<? extends QuartzJobBean>) Class.forName(qrtzJobDetails.getJobClassName());
            Object bean = SpringContextBean.getBean(clazz);
            if(bean == null) return;
            quartzService.addJob(clazz,jobName,qrtzJobDetails.getJobGroup(),qrtzJobDetails.getDescription(),qrtzJobDetails.getJobTime(),qrtzJobDetails.getJobDataMap());
        }catch (Exception e){
            log.error("Quartz新增任务操作失败",e);
        }
    }

    /**
     * @Description TODO 修改任务时间表达式
     * @Author CHCC
     * @Date 2020/9/28 10:13 上午
     * @params  * @param qrtzJobDetails
     * @returen void
     **/
    @PostMapping("/editJobTime")
    public void editJobTime(@RequestBody QrtzJobDetails qrtzJobDetails){
        quartzService.updateJob(qrtzJobDetails.getJobName(),qrtzJobDetails.getJobGroup(),qrtzJobDetails.getJobTime());
    }


}
