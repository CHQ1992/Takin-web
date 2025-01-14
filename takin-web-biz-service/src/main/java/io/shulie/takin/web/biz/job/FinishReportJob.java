package io.shulie.takin.web.biz.job;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.google.common.collect.Lists;
import io.shulie.takin.job.annotation.ElasticSchedulerJob;
import io.shulie.takin.utils.json.JsonHelper;
import io.shulie.takin.web.biz.service.report.ReportService;
import io.shulie.takin.web.biz.service.report.ReportTaskService;
import io.shulie.takin.web.common.domain.WebResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author 无涯
 * @date 2021/6/15 6:08 下午
 */
@Component
@ElasticSchedulerJob(jobName = "finishReportJob",
    // 分片序列号和参数用等号分隔 不需要参数可以不加
    //shardingItemParameters = "0=0,1=1,2=2",
    isSharding = true,
    cron = "*/10 * * * * ?",
    description = "压测报告状态，汇总报告")
@Slf4j
public class FinishReportJob implements SimpleJob {
    @Autowired
    private ReportTaskService reportTaskService;
    @Autowired
    private ReportService reportService;

    @Override
    public void execute(ShardingContext shardingContext) {
        long start = System.currentTimeMillis();
        List<Object> reportIds = Lists.newArrayList();
        WebResponse runningResponse = reportService.queryListRunningReport();
        if (runningResponse.getSuccess() == true && runningResponse.getData() != null) {
            reportIds.addAll((List)runningResponse.getData());
        }
        log.info("获取正在压测中的报告:{}", JsonHelper.bean2Json(reportIds));
        for (Object obj : reportIds) {
            // 开始数据分片
            long reportId = Long.parseLong(String.valueOf(obj));
            if (reportId % shardingContext.getShardingTotalCount() == shardingContext.getShardingItem()) {
                log.info("------Thread ID: {}, {},任务总片数: {}, 当前分片项: {},当前参数:{}, 当前任务名称: {},当前任务参数 {},reportId :{}",
                    Thread.currentThread().getId(),
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()),
                    shardingContext.getShardingTotalCount(),
                    shardingContext.getShardingItem(),
                    shardingContext.getShardingParameter(),
                    shardingContext.getJobName(),
                    shardingContext.getJobParameter(),
                    reportId
                );
                reportTaskService.finishReport(reportId);
            }
        }
        log.info("finishReport 执行时间:{}", System.currentTimeMillis() - start);
    }
}
