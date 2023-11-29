package com.ujn.spu.config.cron;

import com.ujn.spu.core.WebSocketClient;
import com.ujn.spu.domain.dto.HistoryLocDTO;
import com.ujn.spu.domain.entity.HistoryLocEntity;
import com.ujn.spu.domain.entity.RtLocEntity;
import com.ujn.spu.service.HistoryLocService;
import com.ujn.spu.service.RtLocService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * 垃圾清理器
 *
 * @Author: Richard.Lee
 * @Date: created by 2021/4/5
 * corn的定义
 * 秒（0~59）
 * 分钟（0~59）
 * 小时（0~23）
 * 天（月）（0~31，但是你需要考虑你月的天数）
 * 月（0~11）
 * 天（星期）（1~7 1=SUN 或 SUN，MON，TUE，WED，THU，FRI，SAT）
 */
@Configuration
@EnableScheduling
public class TaskCleaner {

	private static int cnts=0;

	@Autowired
	private HistoryLocService hlService;

	@Autowired
	private RtLocService rlService;
	public static TaskCleaner taskCleaner;

	@PostConstruct//用于在依赖关系注入完成之后需要执行的方法上，以执行任何初始化
	public void init() {
		taskCleaner = this;
		taskCleaner.rlService=this.rlService;
		taskCleaner.hlService=this.hlService;
	}


	/**
	 * 每天1点清理
	 * 清理已合并的切片文件
	 */
	@Scheduled(cron = "0 * * * * ?")
	public void cleanTaska() {
//		System.out.println("每分钟的第0秒执行");
		//更新数据库
		List<RtLocEntity> rlList=taskCleaner.rlService.listALLData();
		if(!rlList.isEmpty()) {
			RtLocEntity rlEntity=rlList.get(0);
			System.out.println("获取到实时loc数据:"+rlEntity.getLongitude()+rlEntity.getLatitude());

			//从历史数据库中获取最新的一条
			Optional<HistoryLocEntity> hlEn=taskCleaner.hlService.findNewrestItem();
			boolean isInsert=false;

			if (hlEn.isPresent()){
				HistoryLocEntity hlEntity=hlEn.get();
				System.out.println("获取到最新历史数据:"+hlEntity.getDatetime()+hlEntity.getLongitude()+hlEntity.getLatitude());
				//比较经纬度不同则加入一条最新记录
				if ((!rlEntity.getLongitude().equals(hlEntity.getLongitude()))
						||(!rlEntity.getLatitude().equals(hlEntity.getLatitude()))){
					isInsert=true;
				}else{
					System.out.println("数据相同则不更新");
				}

				if ((++cnts)==60){
					System.out.println("定期清理开始。。。。。。");
					cnts=0;
					//从历史数据库中获取最旧的一条
					Optional<HistoryLocEntity> ohlEn=taskCleaner.hlService.findOldestItem();
					if (ohlEn.isPresent()){
						HistoryLocEntity ohlEntity=ohlEn.get();
						System.out.println("新旧id:"+hlEntity.getId()+","+ohlEntity.getId());

						if ((hlEntity.getId()-ohlEntity.getId())>2000)
						{
							taskCleaner.hlService.delteBatchItems(taskCleaner.hlService.findOlder100Items());
						}
					}
				}
			}else{
					System.out.println("历史数据为空需要插入");
					isInsert=true;
			}
			if (isInsert){
				HistoryLocDTO hlDTO=new HistoryLocDTO();
				//Date date = new Date();
				//SimpleDateFormat formatter = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
				BeanUtils.copyProperties(rlEntity,hlDTO);
				//hlDTO.setDatetime(formatter.format(date));
				taskCleaner.hlService.createArticle(hlDTO);
			}

		}
		return;
	}

	/**
	 * 每天0点清理回收站
	 * 清理回收站文件
	 */
	/*
	@Scheduled(cron = "0 0 0 * * ?")
	public void cleanTaskb() {
		System.out.println("每天0时执行");
		return;
	}*/
}
