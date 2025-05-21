package ksy.medichat.drug.controller;

import ksy.medichat.drug.crawling.DrugCrawling;
import ksy.medichat.drug.dao.DrugInfoMapper;
import ksy.medichat.drug.vo.DrugInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DrugInfoController {

    @Autowired
    private DrugInfoMapper drugMapper;
    
    @Autowired
    private DrugCrawling drugCrawling;
    
    //데이터 DB 저장
    @GetMapping("/insert/drug/3002")
    public String insert() {
    	drugCrawling.main();
    	for (DrugInfoVO drug : drugCrawling.list) {
    		drugMapper.insertDrugInfo(drug);
		}
    	
    	return "/main";
    }
    
}
