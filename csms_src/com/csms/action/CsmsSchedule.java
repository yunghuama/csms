package com.csms.action;

import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.Mem;
import org.hyperic.sigar.Sigar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.csms.service.SysService;


@Component
public class CsmsSchedule {
	
	@Autowired
    private SysService sysService;
	
	
	@Scheduled(cron="0 */5 * * * *")
	public void sysWarn(){
		try{
			Sigar sigar = new Sigar();
			CpuPerc cp = sigar.getCpuPerc();
			Mem mem = sigar.getMem();

			sysService.saveWarn(cp.getCombined()*100, mem.getUsed()/1024/1024, 0, mem.getTotal()/1024/1024);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
}
