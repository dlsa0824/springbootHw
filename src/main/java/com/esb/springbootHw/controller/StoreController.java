package com.esb.springbootHw.controller;

import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.esb.springbootHw.constant.ResultCode;
import com.esb.springbootHw.entity.ResultMessage;
import com.esb.springbootHw.entity.ItemInfo;
import com.esb.springbootHw.exception.ItemNotAvailableException;
import com.esb.springbootHw.exception.ItemNotEnoughException;
import com.esb.springbootHw.model.request.ItemInfoRequestDto;
import com.esb.springbootHw.model.response.ItemInfoResponseDto;
import com.esb.springbootHw.model.response.TotalPriceResponseDto;
import com.esb.springbootHw.service.StoreService;
import com.esb.springbootHw.validation.ValidationGroup;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class StoreController {
	
	@Autowired
	private StoreService storeServiceImp;
	
	private ObjectMapper objectMapper = new ObjectMapper();

	@PostMapping("/getPrice")
	public ItemInfoResponseDto getPrice(@Validated(ValidationGroup.query.class) @RequestBody ItemInfoRequestDto itemInfoRequestDto) throws ItemNotAvailableException {
		throw new ItemNotAvailableException("1", "1");
//		List<ItemInfo> items = itemInfoRequestDto.getItems();
//		ItemInfoResponseDto itemInfoResponseDto = new ItemInfoResponseDto();
//		itemInfoResponseDto.setCode(ResultCode.UPDATE_SUCCESS.getCode());
//		itemInfoResponseDto.setItems(storeServiceImp.getItemsInfo(items));
//		return itemInfoResponseDto;
	}
	
	@PostMapping("/doPay")
	public TotalPriceResponseDto doPay(@Validated(ValidationGroup.pay.class) @RequestBody ItemInfoRequestDto itemInfoRequestDto) throws ItemNotAvailableException, ItemNotEnoughException {
		List<ItemInfo> items = itemInfoRequestDto.getItems();
		TotalPriceResponseDto totalPriceResponseDto = new TotalPriceResponseDto();
		totalPriceResponseDto.setCode(ResultCode.UPDATE_PART_SUCCESS.getCode());
		totalPriceResponseDto.setTotalPrice(storeServiceImp.payItems(items));
		return totalPriceResponseDto;
	}
	
	@PostMapping("/insertItem")
	public ResultMessage insertItem(@Validated(ValidationGroup.update.class) @RequestBody ItemInfo itemInfo) throws DuplicateKeyException{
		storeServiceImp.insertStock(itemInfo);
		return new ResultMessage(ResultCode.UPDATE_SUCCESS.getCode(), Arrays.asList(ResultCode.UPDATE_SUCCESS.getMessage()));
	}
	
	@PostMapping("/deleteItem")
	public ResultMessage deleteItem(@Validated(ValidationGroup.query.class) @RequestBody ItemInfo itemInfo) throws ItemNotAvailableException{
		ResultMessage result = storeServiceImp.deleteStock(itemInfo);
		return result;
	}
	
	@PostMapping("/batchInsertItem")
	public ResultMessage batchInsertItem(@Validated(ValidationGroup.update.class) @RequestBody List<ItemInfo> items) {
		ResultMessage result = storeServiceImp.batchInsertStock(items);
		return result;
	}
	
	@PostMapping("/batchDeleteItem")
	public ResultMessage batchDeleteItem(@Validated(ValidationGroup.query.class) @RequestBody List<ItemInfo> items) throws ItemNotAvailableException{
		ResultMessage result = storeServiceImp.batchDeleteStock(items);
		return result;
	}
	
	@PostMapping("/batchUpdateItem")
	public ResultMessage batchUpdateItem(@Validated(ValidationGroup.update.class) @RequestBody List<ItemInfo> items) throws ItemNotAvailableException{
		ResultMessage result = storeServiceImp.batchUpdateStock(items);
		return result;
	}
	
	@PostMapping("/callApiTest")
	public String callApiTest(Object request) throws JsonProcessingException {
	    RestTemplate restTemplate = new RestTemplate();
	    
	    Object response = restTemplate.postForObject("https://api-osipt.testesunbank.com.tw/esunbankt/bankapi/test/v1/tw/up9999/pressureTest?client_id=73ce286e-c27d-4949-9b65-69581589620b", 
	            "{}", StoreController.class);
	    System.out.println(objectMapper.writeValueAsString(response));
	    return "call api success";
	}
}