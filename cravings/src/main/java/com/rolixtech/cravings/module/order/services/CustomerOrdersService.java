package com.rolixtech.cravings.module.order.services;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rolixtech.cravings.CravingsApplication;
import com.rolixtech.cravings.module.cravings.model.CravingsPromotionalVouchers;
import com.rolixtech.cravings.module.cravings.model.CravingsPromotionalVouchersChangeLog;
import com.rolixtech.cravings.module.cravings.services.CravingsPromotionalVouchersService;
import com.rolixtech.cravings.module.generic.services.FCMClient;
import com.rolixtech.cravings.module.generic.services.GenericUtility;
import com.rolixtech.cravings.module.order.POJO.OrderAddressPOJO;
import com.rolixtech.cravings.module.order.POJO.OrderPOJO;
import com.rolixtech.cravings.module.order.POJO.OrderProductsOptionalAddOnPOJO;
import com.rolixtech.cravings.module.order.POJO.OrderProductsPOJO;
import com.rolixtech.cravings.module.order.POJO.OrderProductsRequiredAddOnPOJO;
import com.rolixtech.cravings.module.order.dao.CustomerOrderAddressDao;
import com.rolixtech.cravings.module.order.dao.CustomerOrderChangeLogDao;
import com.rolixtech.cravings.module.order.dao.CustomerOrderDao;
import com.rolixtech.cravings.module.order.dao.CustomerOrderProductsDao;
import com.rolixtech.cravings.module.order.dao.CustomerOrderProductsOptionalAddOnDao;
import com.rolixtech.cravings.module.order.dao.CustomerOrderProductsRequiredAddOnDao;
import com.rolixtech.cravings.module.order.dao.CustomerOrderRemarksDao;
import com.rolixtech.cravings.module.order.model.CustomerOrder;
import com.rolixtech.cravings.module.order.model.CustomerOrderAddress;
import com.rolixtech.cravings.module.order.model.CustomerOrderChangeLog;
import com.rolixtech.cravings.module.order.model.CustomerOrderProducts;
import com.rolixtech.cravings.module.order.model.CustomerOrderProductsOptionalAddOn;
import com.rolixtech.cravings.module.order.model.CustomerOrderProductsRequiredAddOn;
import com.rolixtech.cravings.module.order.model.CustomerOrderRemarks;
import com.rolixtech.cravings.module.resturant.dao.CommonCategoriesDao;
import com.rolixtech.cravings.module.resturant.dao.CommonResturantsProductsDao;
import com.rolixtech.cravings.module.resturant.model.CommonCategories;
import com.rolixtech.cravings.module.resturant.model.CommonResturants;
import com.rolixtech.cravings.module.resturant.model.CommonResturantsCategories;
import com.rolixtech.cravings.module.resturant.model.CommonResturantsProducts;
import com.rolixtech.cravings.module.resturant.model.CommonUsersResturants;
import com.rolixtech.cravings.module.resturant.services.CommonResturantsCategoriesService;
import com.rolixtech.cravings.module.resturant.services.CommonResturantsProductsAddOnService;
import com.rolixtech.cravings.module.resturant.services.CommonResturantsProductsService;
import com.rolixtech.cravings.module.resturant.services.CommonResturantsService;
import com.rolixtech.cravings.module.users.dao.CommonUsersDevicesDao;
import com.rolixtech.cravings.module.users.models.CommonUsersDevices;
import com.rolixtech.cravings.module.users.services.CommonUsersService;

@Service
public class CustomerOrdersService {
	
	@Autowired
	private CustomerOrderDao OrderDao;
	
	@Autowired
	private CustomerOrderAddressDao OrderAddressDao;
	
	@Autowired
	private CustomerOrderProductsDao OrderProductsDao;
	
	@Autowired
	private CustomerOrderProductsOptionalAddOnDao OrderProductsOptionalAddOnDao;
	@Autowired
	private CustomerOrderProductsRequiredAddOnDao OrderProductsRequiredAddOnDao;
	
	@Autowired
	private CommonResturantsService ResturantsService;
	
	@Autowired
	private CommonResturantsProductsService ResturantsProductsService;
	
	@Autowired
	private CustomerOrderStatusService OrderStatusService;
	
	@Autowired
	private CommonResturantsProductsDao ResturantsProductsDao;	
	
	@Autowired
	private CommonResturantsCategoriesService ResturantsCategoriesService;
	
	@Autowired
	private CommonResturantsProductsAddOnService ProductsAddOnService;
	
	@Autowired
	private GenericUtility utility;
	
	
	@Autowired
	private CustomerOrderChangeLogDao OrderChangeLogDao;
	
	@Autowired
	private CommonUsersService UsersService;
	
	@Autowired
	private FCMClient fireBaseService;
	
	@Autowired
	private CravingsPromotionalVouchersService VouchersService;
	
	@Autowired
	private CommonUsersDevicesDao DevicesDao;
	
	@Autowired
	private CustomerOrderRemarksDao OrderRemarksDao;
	
	public String findLabelById(long categoryId) {
		String label="";
//		CommonCategories Categories=CategoriesDao.findById(categoryId);
//		if(Categories!=null) {
//			label=Categories.getLabel();
//		}
		return label;
	}
	
	public void Save(OrderPOJO order,long userId){
		
		System.out.println("OrderPOJO ======>>>>   "+order);
		
		OrderPOJO orderr=order;
		if(orderr!=null)
		{ 
			long resturantId=orderr.getResturantId();
			double totalAmount=orderr.getTotalAmount(); 
			double totalGst=orderr.getTotalGst();
			String orderType=orderr.getOrderType();
			
			double subtotal=orderr.getSubtotal();
			double discount=orderr.getDiscount();
			double deliveryFee=orderr.getDeliveryFee();
			int deliveryTime=orderr.getDeliveryTime();
			double totalGstPercentage=orderr.getTotalGstPercentage();
			
			double serviceFee=orderr.getServiceFee();
			long voucherId=order.getVoucherId();
			String instructions=orderr.getInstructions();
			
			CustomerOrder Order=new CustomerOrder(); 
			Order.setResturantId(resturantId);
			Order.setOrderType(orderType);
			
			String orderNumber=utility.getDateWithOutSlashesId()+"-"+utility.createRandomCode(4);
			Order.setOrderNumber("CRA-"+orderNumber);
			Order.setTotalAmount(totalAmount);
			Order.setTotalGst(totalGst);
			Order.setDeliveryFee(deliveryFee);
			Order.setDiscount(discount);
			Order.setSubtotal(subtotal);
			Order.setUserId(userId);
			Order.setCreatedOn(new Date());
			Order.setOrderStatusId(1);
			Order.setTotalGstPercentage(totalGstPercentage);
			Order.setDeliveryTime(deliveryTime);
			Order.setServiceFee(serviceFee);
			Order.setInstructions(instructions);
			
			
			if(voucherId!=0) {
				
				Order.setVoucherId(orderr.getVoucherId());
				VouchersService.voucherRedeemed(orderr.getVoucherId());
				
			}
			
			
			OrderDao.save(Order);
			
			OrderAddressPOJO address=orderr.getAddress();
			if(address!=null) {
				CustomerOrderAddress Address=new CustomerOrderAddress();
				Address.setCityName(address.getCityName());
				Address.setCompleteAddress(address.getCompleteAddress());
				Address.setLat(address.getLat());
				Address.setLng(address.getLng());
				Address.setOrderId(Order.getId());
				Address.setArea(address.getArea());
				Address.setHouseNo(address.getHouseNo());
				Address.setStreetNo(address.getStreetNo());
				OrderAddressDao.save(Address);
				
			}
			
			List<OrderProductsPOJO> Products=orderr.getProducts();
			if(!Products.isEmpty()) {
				Products.stream().forEach(
					product->{
						
						OrderProductsPOJO singleProduct=product;
						if(singleProduct!=null) {
							double price=singleProduct.getPrice();
							int quantity=singleProduct.getQuantity();
							long productId=singleProduct.getProductId();
							
							
							CustomerOrderProducts OrderProducts=new CustomerOrderProducts();
							OrderProducts.setPrice(price);
							OrderProducts.setProductId(productId);
							OrderProducts.setQuantity(quantity);
							OrderProducts.setOrderId(Order.getId());
							OrderProductsDao.save(OrderProducts);
							
							
							List<OrderProductsRequiredAddOnPOJO> requiredAddOns= singleProduct.getRequiredAddOn();
							if(!requiredAddOns.isEmpty()) {
								requiredAddOns.stream().forEach(
										requiredAddOn->{
											
											OrderProductsRequiredAddOnPOJO addOn=requiredAddOn;
											if(addOn!=null) {
												long addOnproductId=addOn.getId();
												double addOnPrice=addOn.getPrice();
												
												OrderProductsRequiredAddOnDao.save(new CustomerOrderProductsRequiredAddOn(OrderProducts.getId(),addOnproductId,addOnPrice));
											}
									}
								);
							}	
							
							List<OrderProductsOptionalAddOnPOJO> optionalAddOns= singleProduct.getOptionalAddOn();
							if(!optionalAddOns.isEmpty()) {
								optionalAddOns.stream().forEach(
										optionalAddOn->{
											OrderProductsOptionalAddOnPOJO addOn=optionalAddOn;
											if(addOn!=null) {
												long addOnproductId=addOn.getId();
												double addOnPrice=addOn.getPrice();
												OrderProductsOptionalAddOnDao.save(new CustomerOrderProductsOptionalAddOn(OrderProducts.getId(),addOnproductId,addOnPrice));
												
											}
									}
								);
							}
						}
						
						
						
					}
				);
			}
			
		}
		
	}
	
	
	public List<Map> getAllUserPastOrdersSummary(long orderIdd){
		
		List<Map> list=new ArrayList<>();
		List<CustomerOrder> orders=OrderDao.findAllById(orderIdd);
		if(!orders.isEmpty()) {
			orders.stream().forEach(
				order->{
					
						long orderId=order.getId();
						Map Row=new HashMap<>();
						Row.put("id", order.getId());
						Row.put("resturantId", order.getResturantId());
						Row.put("resturantLabel",ResturantsService.findLabelById(order.getResturantId()));
						Row.put("resturantBanner",ResturantsService.findBannerImgById(order.getResturantId()));
						Row.put("orderNumber", order.getOrderNumber());
						Row.put("totalAmount", order.getTotalAmount());
						Row.put("totalGst", order.getTotalGst());
						Row.put("totalGstPercentage", order.getTotalGstPercentage());
						Row.put("deliveryTime", order.getDeliveryTime());
						Row.put("orderType", order.getOrderType());
						Row.put("createdOn", order.getCreatedOn());
						Row.put("orderStatusId", order.getOrderStatusId());
						Row.put("orderStatusLabel", OrderStatusService.findLabelByIdAndRequirementType(order.getOrderStatusId(),3));
						Row.put("subtotal", order.getSubtotal());
						Row.put("discount", order.getDiscount());
						Row.put("deliveryFee", order.getDeliveryFee());
						Row.put("serviceFee", order.getServiceFee());
						Row.put("instructions", order.getInstructions());
						if(utility.parseInt(order.getIsHelpRequired())==1) {
							Row.put("isRequiredHelpLabel","Yes");
						}else {
							Row.put("isRequiredHelpLabel","No");
						}
						
						CustomerOrderAddress Address=OrderAddressDao.findByOrderId(orderId);
						if(Address!=null) {	
							Row.put("cityName", Address.getCityName());
							Row.put("completeAddress", Address.getCompleteAddress());
							Row.put("area", Address.getArea());
							Row.put("houseNo", Address.getHouseNo());
							Row.put("streetNo", Address.getStreetNo());
							
							
						}else {
							Row.put("cityName", "");
							Row.put("completeAddress", "");
							Row.put("area", "");
							Row.put("houseNo", "");
							Row.put("streetNo","");
							
						}
						
						List<Map> orderProductsList=new ArrayList<>();
						List<CustomerOrderProducts> OrderProducts=OrderProductsDao.findAllByOrderId(orderId);
						if(!OrderProducts.isEmpty()) {
							OrderProducts.stream().forEach(
								OrderProduct->{
									Map Rowproduct=new HashMap<>();
									Rowproduct.put("price", OrderProduct.getPrice());
									Rowproduct.put("productId", OrderProduct.getProductId());
									Rowproduct.put("productLabel",ResturantsProductsService.findLabelById(OrderProduct.getProductId()));
									Rowproduct.put("quantity", OrderProduct.getQuantity());
									
									
									List<Map> requiredAddOnsList=new ArrayList<>();
									List<CustomerOrderProductsRequiredAddOn> requiredAddOns=OrderProductsRequiredAddOnDao.findAllByOrderProductId(OrderProduct.getId());
									if(!requiredAddOns.isEmpty()) {
										requiredAddOns.stream().forEach(
												requiredAddOn->{
													Map RowproductRequiredAddon=new HashMap<>();
													RowproductRequiredAddon.put("price", requiredAddOn.getPrice());
													RowproductRequiredAddon.put("id", requiredAddOn.getProductId());
													RowproductRequiredAddon.put("label",ResturantsProductsService.findLabelById(OrderProduct.getProductId()));
													requiredAddOnsList.add(RowproductRequiredAddon);
													
													
												}
										);
									}
									Rowproduct.put("requiredAddOn", requiredAddOnsList);
									
									List<Map> optionalAddOnsList=new ArrayList<>();
									List<CustomerOrderProductsOptionalAddOn> optionalAddOns=OrderProductsOptionalAddOnDao.findAllByOrderProductId(OrderProduct.getId());
									if(!optionalAddOns.isEmpty()) {
										optionalAddOns.stream().forEach(
												optionalAddOn->{
													Map RowproductOptionalAddon=new HashMap<>();
													RowproductOptionalAddon.put("price", optionalAddOn.getPrice());
													RowproductOptionalAddon.put("id", optionalAddOn.getProductId());
													RowproductOptionalAddon.put("label",ResturantsProductsService.findLabelById(optionalAddOn.getProductId()));
													optionalAddOnsList.add(RowproductOptionalAddon);
													
													
												}
										);
									}
									Rowproduct.put("optionalAddOn", optionalAddOnsList);
									
									orderProductsList.add(Rowproduct);
										
								}
							);
						}
						
						Row.put("products",orderProductsList);
						
						list.add(Row);
				}
			
			);
		}
		
		
		
		return list;
		
	}
	
	public List<Map> getAllUserPastOrders(long userId){
		
		List<Map> list=new ArrayList<>();
		List<Long> statusIds=new ArrayList<>();
		statusIds.add(7L);
		statusIds.add(8L);
		statusIds.add(9L);
		statusIds.add(10L);
		List<CustomerOrder> orders=OrderDao.findAllByUserIdAndOrderStatusIdIn(userId,statusIds);
		if(!orders.isEmpty()) {
			orders.stream().forEach(
				order->{
					
						long orderId=order.getId();
						Map Row=new HashMap<>();
						Row.put("id", order.getId());
						Row.put("resturantId", order.getResturantId());
						Row.put("resturantLabel",ResturantsService.findLabelById(order.getResturantId()));
						Row.put("resturantLogo",ResturantsService.findLogoImgById(order.getResturantId()));
						Row.put("totalAmount", order.getTotalAmount());
						Row.put("createdOn", order.getCreatedOn());
						Row.put("orderstatus", order.getOrderStatusId());
						Row.put("orderStatusId", order.getOrderStatusId());
						Row.put("orderStatusLabel", OrderStatusService.findLabelByIdAndRequirementType(order.getOrderStatusId(),3));
						Row.put("subtotal", order.getSubtotal());
						Row.put("discount", order.getDiscount());
						Row.put("deliveryFee", order.getDeliveryFee());
						Row.put("totalGstPercentage", order.getTotalGstPercentage());
						Row.put("deliveryTime", order.getDeliveryTime());
						Row.put("serviceFee", order.getServiceFee());
						Row.put("instructions", order.getInstructions());
						Row.put("totalGst", order.getTotalGst());
						Row.put("orderType", order.getOrderType());
						if(utility.parseInt(order.getIsHelpRequired())==1) {
							Row.put("isRequiredHelpLabel","Yes");
						}else {
							Row.put("isRequiredHelpLabel","No");
						}
						String products = "";
						List<CustomerOrderProducts> OrderProducts=OrderProductsDao.findAllByOrderId(orderId);
						if(!OrderProducts.isEmpty()) {
							 
						    Map Rowproduct=new HashMap<>();
							for(int p=0;p<OrderProducts.size();p++) {	
									
								if(products.equals("")) {
									products=ResturantsProductsService.findLabelById(OrderProducts.get(p).getProductId());
								}else {
									products+=","+ResturantsProductsService.findLabelById(OrderProducts.get(p).getProductId());
								}	
							}
							
						}
						
						Row.put("products",products);
						
						list.add(Row);
				}
			
			);
		}
		
		
		
		return list;
		
	}
	
	
	public List<Map> getUserActiveOrder(long userId){
		
		List<Map> list=new ArrayList<>();
		List<Long> statusIds=new ArrayList<>();
//		statusIds.add(5L);
//		statusIds.add(6L);
//		statusIds.add(7L);
		statusIds.add(7L);
		statusIds.add(8L);
		statusIds.add(9L);
		statusIds.add(10L);
		
		//
		//List<CustomerOrder> orders=OrderDao.findAllByUserIdAndOrderStatusId(userId,2l);
		List<CustomerOrder> orders=OrderDao.findAllByOrderStatusIdGreaterThanOrderAndOrderStatusIdNotInAndUserIdOrderByIdDesc(0l,statusIds,userId);
		if(!orders.isEmpty()) {
			orders.stream().forEach(
				order->{
					
						long orderId=order.getId();
						Map Row=new HashMap<>();
						Row.put("id", order.getId());
						Row.put("resturantId", order.getResturantId());
						Row.put("resturantLabel",ResturantsService.findLabelById(order.getResturantId()));
						Row.put("resturantLogo",ResturantsService.findLogoImgById(order.getResturantId()));
						Row.put("totalAmount", order.getTotalAmount());
						Row.put("createdOn", order.getCreatedOn());
						Row.put("orderStatusId", order.getOrderStatusId());
						Row.put("orderStatusLabel", OrderStatusService.findLabelByIdAndRequirementType(order.getOrderStatusId(),3));
						
						Row.put("subtotal", order.getSubtotal());
						Row.put("discount", order.getDiscount());
						Row.put("deliveryFee", order.getDeliveryFee());
						Row.put("totalGst", order.getTotalGst());
						Row.put("totalGstPercentage", order.getTotalGstPercentage());
						Row.put("serviceFee", order.getServiceFee());
						Row.put("instructions", order.getInstructions());
						Row.put("orderType", order.getOrderType());
						if(utility.parseInt(order.getIsHelpRequired())==1) {
							Row.put("isRequiredHelpLabel","Yes");
						}else {
							Row.put("isRequiredHelpLabel","No");
						}
						if(order.getOrderStatusId()>=3) {
							
							Date orderRemainingDateTime=DateUtils.addMinutes(order.getOrderStatusChangedOn(), order.getDeliveryTime());
							if(orderRemainingDateTime!=null) {
								
								String remainingTime=this.getOrderRemainingDeliverTime(orderRemainingDateTime);
								
								
								Row.put("deliveryTime", remainingTime); 
							}
							 
							
							
							
						}else {
							Row.put("deliveryTime", "- -");
						}
						
						String products = "";
						List<CustomerOrderProducts> OrderProducts=OrderProductsDao.findAllByOrderId(orderId);
						if(!OrderProducts.isEmpty()) {
							 
						    Map Rowproduct=new HashMap<>();
							for(int p=0;p<OrderProducts.size();p++) {	
									
								if(products.equals("")) {
									products=ResturantsProductsService.findLabelById(OrderProducts.get(p).getProductId());
								}else {
									products+=","+ResturantsProductsService.findLabelById(OrderProducts.get(p).getProductId());
								}	
							}
							
						}
						
						Row.put("products",products);
						
						
						list.add(Row);
				}
			
			);
		}
		
		
		
		return list;
		
	}

	
	public List<Map> getUserActiveOrdersSummary(long orderIdd){
		
		List<Map> list=new ArrayList<>();
		CustomerOrder order=OrderDao.findByUserIdAndOrderStatusId(orderIdd,2l);
		if(order!=null) {
					
			long orderId=order.getId();
			Map Row=new HashMap<>();
			Row.put("id", order.getId());
			Row.put("resturantId", order.getResturantId());
			Row.put("resturantLabel",ResturantsService.findLabelById(order.getResturantId()));
			Row.put("resturantBanner",ResturantsService.findBannerImgById(order.getResturantId()));
			Row.put("orderNumber", order.getOrderNumber());
			Row.put("totalAmount", order.getTotalAmount());
			Row.put("totalGst", order.getTotalGst());
			Row.put("orderType", order.getOrderType());
			Row.put("createdOn", order.getCreatedOn());
			Row.put("orderStatusId", order.getOrderStatusId());
			Row.put("orderStatusLabel", OrderStatusService.findLabelByIdAndRequirementType(order.getOrderStatusId(),3));
			
			Row.put("subtotal", order.getSubtotal());
			Row.put("discount", order.getDiscount());
			Row.put("deliveryFee", order.getDeliveryFee());
			Row.put("totalGstPercentage", order.getTotalGstPercentage());
			Row.put("serviceFee", order.getServiceFee());
			Row.put("instructions", order.getInstructions());
			if(utility.parseInt(order.getIsHelpRequired())==1) {
				Row.put("isRequiredHelpLabel","Yes");
			}else {
				Row.put("isRequiredHelpLabel","No");
			}
			if(order.getOrderStatusId()>=3) {
				
				Date orderRemainingDateTime=DateUtils.addMinutes(order.getOrderStatusChangedOn(), order.getDeliveryTime());
				if(orderRemainingDateTime!=null) {
					
					String remainingTime=this.getOrderRemainingDeliverTime(orderRemainingDateTime);
					
					
					Row.put("deliveryTime", remainingTime); 
				}
				 
				
				
				
			}else {
				Row.put("deliveryTime", "- -");
			}
			CustomerOrderAddress Address=OrderAddressDao.findByOrderId(orderId);
			if(Address!=null) {	
				Row.put("cityName", Address.getCityName());
				Row.put("completeAddress", Address.getCompleteAddress());
				Row.put("area", Address.getArea());
				Row.put("houseNo", Address.getHouseNo());
				Row.put("streetNo", Address.getStreetNo());
				
				
			}else {
				Row.put("cityName", "");
				Row.put("completeAddress", "");
				Row.put("area", "");
				Row.put("houseNo", "");
				Row.put("streetNo","");
				
			}
			
			List<Map> orderProductsList=new ArrayList<>();
			List<CustomerOrderProducts> OrderProducts=OrderProductsDao.findAllByOrderId(orderId);
			if(!OrderProducts.isEmpty()) {
				OrderProducts.stream().forEach(
					OrderProduct->{
						Map Rowproduct=new HashMap<>();
						Rowproduct.put("price", OrderProduct.getPrice());
						Rowproduct.put("productId", OrderProduct.getProductId());
						Rowproduct.put("productLabel",ResturantsProductsService.findLabelById(OrderProduct.getProductId()));
						Rowproduct.put("quantity", OrderProduct.getQuantity());
						
						
						List<Map> requiredAddOnsList=new ArrayList<>();
						List<CustomerOrderProductsRequiredAddOn> requiredAddOns=OrderProductsRequiredAddOnDao.findAllByOrderProductId(OrderProduct.getId());
						if(!requiredAddOns.isEmpty()) {
							requiredAddOns.stream().forEach(
									requiredAddOn->{
										Map RowproductRequiredAddon=new HashMap<>();
										RowproductRequiredAddon.put("price", requiredAddOn.getPrice());
										RowproductRequiredAddon.put("id", requiredAddOn.getProductId());
										RowproductRequiredAddon.put("label",ResturantsProductsService.findLabelById(requiredAddOn.getProductId()));
										requiredAddOnsList.add(RowproductRequiredAddon);
										
										
									}
							);
						}
						Rowproduct.put("requiredAddOn", requiredAddOnsList);
						
						List<Map> optionalAddOnsList=new ArrayList<>();
						List<CustomerOrderProductsOptionalAddOn> optionalAddOns=OrderProductsOptionalAddOnDao.findAllByOrderProductId(OrderProduct.getId());
						if(!optionalAddOns.isEmpty()) {
							optionalAddOns.stream().forEach(
									optionalAddOn->{
										Map RowproductOptionalAddon=new HashMap<>();
										RowproductOptionalAddon.put("price", optionalAddOn.getPrice());
										RowproductOptionalAddon.put("id", optionalAddOn.getProductId());
										RowproductOptionalAddon.put("label",ResturantsProductsService.findLabelById(optionalAddOn.getProductId()));
										optionalAddOnsList.add(RowproductOptionalAddon);
										
										
									}
							);
						}
						Rowproduct.put("optionalAddOn", optionalAddOnsList);
						
						orderProductsList.add(Rowproduct);
							
					}
				);
			}
			
			Row.put("products",orderProductsList);
			
			list.add(Row);
				
		}
		
		
		
		return list;
		
	}
	
	

	public List<Map> getAllUserActiveOrdersSummary(long orderIdd){
		
		List<Map> list=new ArrayList<>();
		List<CustomerOrder> orders=OrderDao.findAllById(orderIdd);
		if(!orders.isEmpty()) {
			orders.stream().forEach(
				order->{
					
						long orderId=order.getId();
						Map Row=new HashMap<>();
						Row.put("id", order.getId());
						Row.put("resturantId", order.getResturantId());
						Row.put("resturantLabel",ResturantsService.findLabelById(order.getResturantId()));
						Row.put("resturantBanner",ResturantsService.findBannerImgById(order.getResturantId()));
						Row.put("orderNumber", order.getOrderNumber());
						Row.put("totalAmount", order.getTotalAmount());
						Row.put("totalGst", order.getTotalGst());
						Row.put("orderType", order.getOrderType());
						Row.put("createdOn", order.getCreatedOn());
						Row.put("orderStatusId", order.getOrderStatusId());
						Row.put("orderStatusLabel", OrderStatusService.findLabelByIdAndRequirementType(order.getOrderStatusId(),3));
						Row.put("subtotal", order.getSubtotal());
						Row.put("discount", order.getDiscount());
						Row.put("deliveryFee", order.getDeliveryFee());
						Row.put("totalGstPercentage", order.getTotalGstPercentage());
						Row.put("serviceFee", order.getServiceFee());
						Row.put("instructions", order.getInstructions());
						if(utility.parseInt(order.getIsHelpRequired())==1) {
							Row.put("isRequiredHelpLabel","Yes");
						}else {
							Row.put("isRequiredHelpLabel","No");
						}
						if(order.getOrderStatusId()>=3) {
							
							Date orderRemainingDateTime=DateUtils.addMinutes(order.getOrderStatusChangedOn(), order.getDeliveryTime());
							if(orderRemainingDateTime!=null) {
								
								String remainingTime=this.getOrderRemainingDeliverTime(orderRemainingDateTime);
								
								
								Row.put("deliveryTime", remainingTime); 
							}
							 
							
							
							
						}else {
							Row.put("deliveryTime", "- -");
						}
						CustomerOrderAddress Address=OrderAddressDao.findByOrderId(orderId);
						if(Address!=null) {	
							Row.put("cityName", Address.getCityName());
							Row.put("completeAddress", Address.getCompleteAddress());
							Row.put("area", Address.getArea());
							Row.put("houseNo", Address.getHouseNo());
							Row.put("streetNo", Address.getStreetNo());
							
							
						}else {
							Row.put("cityName", "");
							Row.put("completeAddress", "");
							Row.put("area", "");
							Row.put("houseNo", "");
							Row.put("streetNo","");
							
						}
						
						List<Map> orderProductsList=new ArrayList<>();
						List<CustomerOrderProducts> OrderProducts=OrderProductsDao.findAllByOrderId(orderId);
						if(!OrderProducts.isEmpty()) {
							OrderProducts.stream().forEach(
								OrderProduct->{
									Map Rowproduct=new HashMap<>();
									Rowproduct.put("price", OrderProduct.getPrice());
									Rowproduct.put("productId", OrderProduct.getProductId());
									Rowproduct.put("quantity", OrderProduct.getQuantity());
									Rowproduct.put("productLabel",ResturantsProductsService.findLabelById(OrderProduct.getProductId()));
									
									List<Map> requiredAddOnsList=new ArrayList<>();
									List<CustomerOrderProductsRequiredAddOn> requiredAddOns=OrderProductsRequiredAddOnDao.findAllByOrderProductId(OrderProduct.getId());
									if(!requiredAddOns.isEmpty()) {
										requiredAddOns.stream().forEach(
												requiredAddOn->{
													Map RowproductRequiredAddon=new HashMap<>();
													RowproductRequiredAddon.put("price", requiredAddOn.getPrice());
													RowproductRequiredAddon.put("id", requiredAddOn.getProductId());
													RowproductRequiredAddon.put("label",ResturantsProductsService.findLabelById(requiredAddOn.getProductId()));
													requiredAddOnsList.add(RowproductRequiredAddon);
													
													
												}
										);
									}
									Rowproduct.put("requiredAddOn", requiredAddOnsList);
									
									List<Map> optionalAddOnsList=new ArrayList<>();
									List<CustomerOrderProductsOptionalAddOn> optionalAddOns=OrderProductsOptionalAddOnDao.findAllByOrderProductId(OrderProduct.getId());
									if(!optionalAddOns.isEmpty()) {
										optionalAddOns.stream().forEach(
												optionalAddOn->{
													Map RowproductOptionalAddon=new HashMap<>();
													RowproductOptionalAddon.put("price", optionalAddOn.getPrice());
													RowproductOptionalAddon.put("id", optionalAddOn.getProductId());
													RowproductOptionalAddon.put("label",ResturantsProductsService.findLabelById(optionalAddOn.getProductId()));
													optionalAddOnsList.add(RowproductOptionalAddon);
													
													
												}
										);
									}
									Rowproduct.put("optionalAddOn", optionalAddOnsList);
									
									orderProductsList.add(Rowproduct);
										
								}
							);
						}
						
						Row.put("products",orderProductsList);
						
						list.add(Row);
				}
			
			);
		}
		
		
		
		return list;
		
	}

	public List<Map> getAllActiveOrders(Long[]  orderStatusIds) {
		List<Map> list=new ArrayList<>();
		List<Long> statusIdNotIn=new ArrayList<>();
		statusIdNotIn.add(7l);
		statusIdNotIn.add(8l); 
		statusIdNotIn.add(9l);
		statusIdNotIn.add(10l);
		List<CustomerOrder> orders=new ArrayList<>();
		if(orderStatusIds!=null) {
			List<Long> orderstatIdsList=Arrays.asList(orderStatusIds);
			if(!orderstatIdsList.isEmpty()) {
				orders=OrderDao.findAllByOrderStatusIdInOrderByIdDesc(orderstatIdsList);
			}
			
		}else {
			orders=OrderDao.findAllByOrderStatusIdGreaterThanOrderAndOrderStatusIdNotByIdDesc(0l,statusIdNotIn);
		}		
		
		if(!orders.isEmpty()) {
			orders.stream().forEach(
				order->{
					
						long orderId=order.getId();
						Map Row=new HashMap<>();
						Row.put("id", order.getId());
						Row.put("resturantId", order.getResturantId());
						Row.put("resturantLabel",ResturantsService.findLabelById(order.getResturantId()));
						Row.put("resturantBanner",ResturantsService.findBannerImgById(order.getResturantId()));
						Row.put("orderNumber", order.getOrderNumber());
						Row.put("totalAmount", order.getTotalAmount());
						Row.put("totalGst", order.getTotalGst());
						Row.put("orderType", order.getOrderType());
						Row.put("createdOn", order.getCreatedOn());
						Row.put("orderStatusId", order.getOrderStatusId());
						Row.put("orderStatusLabel", OrderStatusService.findLabelByIdAndRequirementType(order.getOrderStatusId(),1));
						
						Row.put("subtotal", order.getSubtotal());
						Row.put("discount", order.getDiscount());
						Row.put("deliveryFee", order.getDeliveryFee());
						Row.put("totalGstPercentage", order.getTotalGstPercentage());
						
						Row.put("deliveryTime", order.getDeliveryTime());
						Row.put("serviceFee", order.getServiceFee());
						Row.put("instructions", order.getInstructions());
						if(utility.parseInt(order.getIsHelpRequired())==1) {
							Row.put("isRequiredHelpLabel","Yes");
						}else {
							Row.put("isRequiredHelpLabel","No");
						}
						
						CustomerOrderAddress Address=OrderAddressDao.findByOrderId(orderId);
						if(Address!=null) {	
							Row.put("cityName", Address.getCityName());
							Row.put("completeAddress", Address.getCompleteAddress());
							Row.put("area", Address.getArea());
							Row.put("houseNo", Address.getHouseNo());
							Row.put("streetNo", Address.getStreetNo());
							Row.put("lat", Address.getLat());
							Row.put("lng",Address.getLng());
							
						}else {
							Row.put("cityName", "");
							Row.put("completeAddress", "");
							Row.put("area", "");
							Row.put("houseNo", "");
							Row.put("streetNo","");
							Row.put("lat", "");
							Row.put("lng","");
							
						}
						
						List<Map> orderProductsList=new ArrayList<>();
						List<CustomerOrderProducts> OrderProducts=OrderProductsDao.findAllByOrderId(orderId);
						if(!OrderProducts.isEmpty()) {
							OrderProducts.stream().forEach(
								OrderProduct->{
									Map Rowproduct=new HashMap<>();
									Rowproduct.put("price", OrderProduct.getPrice());
									Rowproduct.put("productId", OrderProduct.getProductId());
									Rowproduct.put("quantity", OrderProduct.getQuantity());
									Rowproduct.put("productLabel",ResturantsProductsService.findLabelById(OrderProduct.getProductId()));
									
									List<Map> requiredAddOnsList=new ArrayList<>();
									List<CustomerOrderProductsRequiredAddOn> requiredAddOns=OrderProductsRequiredAddOnDao.findAllByOrderProductId(OrderProduct.getId());
									if(!requiredAddOns.isEmpty()) {
										requiredAddOns.stream().forEach(
												requiredAddOn->{
													Map RowproductRequiredAddon=new HashMap<>();
													RowproductRequiredAddon.put("price", requiredAddOn.getPrice());
													RowproductRequiredAddon.put("id", requiredAddOn.getProductId());
													RowproductRequiredAddon.put("label",ResturantsProductsService.findLabelById(requiredAddOn.getProductId()));
													requiredAddOnsList.add(RowproductRequiredAddon);
													
													
												}
										);
									}
									Rowproduct.put("requiredAddOn", requiredAddOnsList);
									
									List<Map> optionalAddOnsList=new ArrayList<>();
									List<CustomerOrderProductsOptionalAddOn> optionalAddOns=OrderProductsOptionalAddOnDao.findAllByOrderProductId(OrderProduct.getId());
									if(!optionalAddOns.isEmpty()) {
										optionalAddOns.stream().forEach(
												optionalAddOn->{
													Map RowproductOptionalAddon=new HashMap<>();
													RowproductOptionalAddon.put("price", optionalAddOn.getPrice());
													RowproductOptionalAddon.put("id", optionalAddOn.getProductId());
													RowproductOptionalAddon.put("label",ResturantsProductsService.findLabelById(optionalAddOn.getProductId()));
													optionalAddOnsList.add(RowproductOptionalAddon);
													
													
												}
										);
									}
									Rowproduct.put("optionalAddOn", optionalAddOnsList);
									
									orderProductsList.add(Rowproduct);
										
								}
							);
						}
						
						Row.put("products",orderProductsList);
						
						list.add(Row);
				}
			
			);
		}
		
		
		
		return list;
	}
	
	
	public List<Map> getAllOrders() {
		List<Map> list=new ArrayList<>();
		
		List<CustomerOrder> orders=OrderDao.findAllByOrderByIdDesc(); 
		if(!orders.isEmpty()) {
			orders.stream().forEach(
				order->{
					
						long orderId=order.getId();
						Map Row=new HashMap<>();
						Row.put("id", order.getId());
						Row.put("resturantId", order.getResturantId());
						Row.put("resturantLabel",ResturantsService.findLabelById(order.getResturantId()));
						Row.put("resturantBanner",ResturantsService.findBannerImgById(order.getResturantId()));
						Row.put("orderNumber", order.getOrderNumber());
						Row.put("totalAmount", order.getTotalAmount());
						Row.put("totalGst", order.getTotalGst());
						Row.put("orderType", order.getOrderType());
						Row.put("createdOn", order.getCreatedOn());
						Row.put("orderStatusId", order.getOrderStatusId());
						Row.put("orderStatusLabel", OrderStatusService.findLabelByIdAndRequirementType(order.getOrderStatusId(),1));
						Row.put("subtotal", order.getSubtotal());
						Row.put("discount", order.getDiscount());
						Row.put("deliveryFee", order.getDeliveryFee());
						Row.put("totalGstPercentage", order.getTotalGstPercentage());
						if(utility.parseInt(order.getIsHelpRequired())==1) {
							Row.put("isRequiredHelpLabel","Yes");
						}else {
							Row.put("isRequiredHelpLabel","No");
						}
						
						Row.put("serviceFee", order.getServiceFee());
						Row.put("instructions", order.getInstructions());
						Row.put("deliveryTime", order.getDeliveryTime());
						Row.put("serviceFee", order.getServiceFee());
						Row.put("instructions", order.getInstructions());
						List<Map> remarks=new ArrayList<>();
						List<CustomerOrderRemarks> OrderRemarks=OrderRemarksDao.findByOrderIdOrderByIdDesc(orderId);
						if(!OrderRemarks.isEmpty()) {
							for(int i=0;i<OrderRemarks.size();i++) {
								Map remarksRows=new HashMap<>();
								remarksRows.put("cravingsRemarks", OrderRemarks.get(i).getRemarks());
								remarksRows.put("cravingsRemarksBy", UsersService.getUserName(OrderRemarks.get(i).getRemarksAddedBy()));
								remarksRows.put("cravingsRemarksOn", OrderRemarks.get(i).getRemarksAddedOn());
								remarks.add(remarksRows);
							}
						}
							
					    Row.put("remarks", OrderRemarks);
							
						
						CustomerOrderAddress Address=OrderAddressDao.findByOrderId(orderId);
						if(Address!=null) {	
							Row.put("cityName", Address.getCityName());
							Row.put("completeAddress", Address.getCompleteAddress());
							Row.put("area", Address.getArea());
							Row.put("houseNo", Address.getHouseNo());
							Row.put("streetNo", Address.getHouseNo());
							
							
						}else {
							Row.put("cityName", "");
							Row.put("completeAddress", "");
							Row.put("area", "");
							Row.put("houseNo", "");
							Row.put("streetNo","");
							
						}
						
						List<Map> orderProductsList=new ArrayList<>();
						List<CustomerOrderProducts> OrderProducts=OrderProductsDao.findAllByOrderId(orderId);
						if(!OrderProducts.isEmpty()) {
							OrderProducts.stream().forEach(
								OrderProduct->{
									Map Rowproduct=new HashMap<>();
									Rowproduct.put("price", OrderProduct.getPrice());
									Rowproduct.put("productId", OrderProduct.getProductId());
									Rowproduct.put("quantity", OrderProduct.getQuantity());
									Rowproduct.put("productLabel",ResturantsProductsService.findLabelById(OrderProduct.getProductId()));
									
									List<Map> requiredAddOnsList=new ArrayList<>();
									List<CustomerOrderProductsRequiredAddOn> requiredAddOns=OrderProductsRequiredAddOnDao.findAllByOrderProductId(OrderProduct.getId());
									if(!requiredAddOns.isEmpty()) {
										requiredAddOns.stream().forEach(
												requiredAddOn->{
													Map RowproductRequiredAddon=new HashMap<>();
													RowproductRequiredAddon.put("price", requiredAddOn.getPrice());
													RowproductRequiredAddon.put("id", requiredAddOn.getProductId());
													RowproductRequiredAddon.put("label",ResturantsProductsService.findLabelById(requiredAddOn.getProductId()));
													requiredAddOnsList.add(RowproductRequiredAddon);
													
													
												}
										);
									}
									Rowproduct.put("requiredAddOn", requiredAddOnsList);
									
									List<Map> optionalAddOnsList=new ArrayList<>();
									List<CustomerOrderProductsOptionalAddOn> optionalAddOns=OrderProductsOptionalAddOnDao.findAllByOrderProductId(OrderProduct.getId());
									if(!optionalAddOns.isEmpty()) {
										optionalAddOns.stream().forEach(
												optionalAddOn->{
													Map RowproductOptionalAddon=new HashMap<>();
													RowproductOptionalAddon.put("price", optionalAddOn.getPrice());
													RowproductOptionalAddon.put("id", optionalAddOn.getProductId());
													RowproductOptionalAddon.put("label",ResturantsProductsService.findLabelById(optionalAddOn.getProductId()));
													optionalAddOnsList.add(RowproductOptionalAddon);
													
													
												}
										);
									}
									Rowproduct.put("optionalAddOn", optionalAddOnsList);
									
									orderProductsList.add(Rowproduct);
										
								}
							);
						}
						
						Row.put("products",orderProductsList);
						
						list.add(Row);
				}
			
			);
		}
		
		
		
		return list;
	}
	
	public List<Map> getActiveOrdersByOrderIdAdmin(long orderId) {
		List<Map> list=new ArrayList<>();
		
		CustomerOrder order=OrderDao.findById(orderId);
		if(order!=null) {
			
					
					
				Map Row=new HashMap<>();
				Row.put("id", order.getId());
				Row.put("resturantId", order.getResturantId());
				Row.put("resturantLabel",ResturantsService.findLabelById(order.getResturantId()));
				Row.put("resturantBanner",ResturantsService.findBannerImgById(order.getResturantId()));
				Row.put("resturantLat",ResturantsService.findResturantLatById(order.getResturantId()));
				Row.put("resturantLng",ResturantsService.findResturantLngById(order.getResturantId()));
				Row.put("resturantAddress",ResturantsService.findResturantAddressById(order.getResturantId()));
				Row.put("orderNumber", order.getOrderNumber());
				Row.put("totalAmount", order.getTotalAmount());
				Row.put("totalGst", order.getTotalGst());
				Row.put("orderType", order.getOrderType());
				Row.put("createdOn", order.getCreatedOn());
				Row.put("orderStatusId", order.getOrderStatusId());
				Row.put("orderStatusLabel", OrderStatusService.findLabelByIdAndRequirementType(order.getOrderStatusId(),1));
				Row.put("subtotal", order.getSubtotal());
				Row.put("discount", order.getDiscount());
				Row.put("deliveryFee", order.getDeliveryFee());
				Row.put("totalGstPercentage", order.getTotalGstPercentage());
				Row.put("deliveryTime", order.getDeliveryTime());
				Row.put("customerName", UsersService.getUserName(order.getUserId()));
				Row.put("customerId", order.getUserId());
				Row.put("customerPreviousOrders",getAllPreviousOrders(order.getUserId()));
				Row.put("customerContact",UsersService.getUserContactNumber(order.getUserId()));
				Row.put("resturantContact",ResturantsService.findResturantContactById(order.getResturantId()) );
				Row.put("resturantContact2",ResturantsService.findResturantContact2ById(order.getResturantId()) );
				Row.put("resturantContact3",ResturantsService.findResturantContact3ById(order.getResturantId()) );
				Row.put("resturantContact4",ResturantsService.findResturantContact4ById(order.getResturantId()) );
				Row.put("isRequiredHelp",utility.parseInt(order.getIsHelpRequired()) );
				if(utility.parseInt(order.getIsHelpRequired())==1) {
					Row.put("isRequiredHelpLabel","Yes");
				}else {
					Row.put("isRequiredHelpLabel","No");
				}
				Row.put("serviceFee", order.getServiceFee());
				Row.put("instructions", order.getInstructions());
				
				List<Map> remarks=new ArrayList<>();
				List<CustomerOrderRemarks> OrderRemarks=OrderRemarksDao.findByOrderIdOrderByIdDesc(orderId);
				if(!OrderRemarks.isEmpty()) {
					for(int i=0;i<OrderRemarks.size();i++) {
						Map remarksRows=new HashMap<>();
						remarksRows.put("cravingsRemarks", OrderRemarks.get(i).getRemarks());
						remarksRows.put("cravingsRemarksBy", UsersService.getUserName(OrderRemarks.get(i).getRemarksAddedBy()));
						remarksRows.put("cravingsRemarksOn", OrderRemarks.get(i).getRemarksAddedOn());
						remarks.add(remarksRows);
					}
				}
				
				Row.put("remarks", remarks);
				
//				if(order.getRemarks()!=null) {
//					Row.put("cravingsRemarks", order.getRemarks());
//				}else {
//					Row.put("cravingsRemarks", "");
//				}
//				
				
				
				CustomerOrderAddress Address=OrderAddressDao.findByOrderId(orderId);
				if(Address!=null) {	
					Row.put("cityName", Address.getCityName());
					Row.put("completeAddress", Address.getCompleteAddress());
					Row.put("lat", Address.getLat());
					Row.put("lng", Address.getLng());
					Row.put("area", Address.getArea());
					Row.put("houseNo", Address.getHouseNo());
					Row.put("streetNo", Address.getStreetNo());
					
					
				}else {
					Row.put("cityName", "");
					Row.put("completeAddress", "");
					Row.put("lat","" );
					Row.put("lng", "");
					Row.put("area", "");
					Row.put("houseNo", "");
					Row.put("streetNo","");
					
					
				}
				

				
				List<Map> orderProductsList=new ArrayList<>();
				List<CustomerOrderProducts> OrderProducts=OrderProductsDao.findAllByOrderId(orderId);
				if(!OrderProducts.isEmpty()) {
					OrderProducts.stream().forEach(
						OrderProduct->{
							Map Rowproduct=new HashMap<>();
							Rowproduct.put("price", OrderProduct.getPrice());
							Rowproduct.put("productId", OrderProduct.getProductId());
							Rowproduct.put("quantity", OrderProduct.getQuantity());
							Rowproduct.put("productLabel",ResturantsProductsService.findLabelById(OrderProduct.getProductId()));
							
							List<Map> requiredAddOnsList=new ArrayList<>();
							List<CustomerOrderProductsRequiredAddOn> requiredAddOns=OrderProductsRequiredAddOnDao.findAllByOrderProductId(OrderProduct.getId());
							if(!requiredAddOns.isEmpty()) {
								requiredAddOns.stream().forEach(
										requiredAddOn->{
											Map RowproductRequiredAddon=new HashMap<>();
											RowproductRequiredAddon.put("price", requiredAddOn.getPrice());
											RowproductRequiredAddon.put("id", requiredAddOn.getProductId());
											RowproductRequiredAddon.put("productLabel",ResturantsProductsService.findLabelById(requiredAddOn.getProductId()));
											requiredAddOnsList.add(RowproductRequiredAddon);
											
											
										}
								);
							}
							Rowproduct.put("requiredAddOn", requiredAddOnsList);
							
							List<Map> optionalAddOnsList=new ArrayList<>();
							List<CustomerOrderProductsOptionalAddOn> optionalAddOns=OrderProductsOptionalAddOnDao.findAllByOrderProductId(OrderProduct.getId());
							if(!optionalAddOns.isEmpty()) {
								optionalAddOns.stream().forEach(
										optionalAddOn->{
											Map RowproductOptionalAddon=new HashMap<>();
											RowproductOptionalAddon.put("price", optionalAddOn.getPrice());
											RowproductOptionalAddon.put("id", optionalAddOn.getProductId());
											RowproductOptionalAddon.put("productLabel",ResturantsProductsService.findLabelById(optionalAddOn.getProductId()));
											optionalAddOnsList.add(RowproductOptionalAddon);
											
											
										}
								);
							}
							Rowproduct.put("optionalAddOn", optionalAddOnsList);
							
							orderProductsList.add(Rowproduct);
								
						}
					);
				}
				
				Row.put("products",orderProductsList);
				
				list.add(Row);
		}
			
			
		
		
		
		return list;
	}
	
	
	private String  getAllPreviousOrders(long userId) {
		String  purposeList="";
		
		List<Long> orderStatusIds=new ArrayList<>();
		orderStatusIds.add(7l);
		orderStatusIds.add(8l); 
		orderStatusIds.add(9l);
		orderStatusIds.add(10l);
		if(!orderStatusIds.isEmpty()) {
			
			for(int i=0;i<orderStatusIds.size();i++){
				if(purposeList.equals("")) {
					
					purposeList= OrderStatusService.findLabelByIdAndRequirementType(orderStatusIds.get(i),1)+" : "+ getNumberOfSpecificOrderType(userId,orderStatusIds.get(i)) ;
					
				}else {
					
					purposeList+= ", " +OrderStatusService.findLabelByIdAndRequirementType(orderStatusIds.get(i),1)+" : "+ getNumberOfSpecificOrderType(userId,orderStatusIds.get(i));
					
				}
						
			}
		 
		}
		
		return purposeList;
	}

	
	public int getNumberOfSpecificOrderType(long userId,long orderStatusId) {
		 
		int totalCount=utility.parseInt(OrderDao.countAllByUserIdAndOrderStatusId(userId,orderStatusId));
		
		return totalCount;
		
	}
	
	public void changeOrderStatus(long orderId,long statusId,int remmaining,long userId) throws IOException, org.json.simple.parser.ParseException {
		
		
		CustomerOrder order=OrderDao.findById(orderId);
		if(order!=null) {
			CustomerOrderChangeLog OrderChangeLog=new CustomerOrderChangeLog();
			CustomerOrder iRealChange=OrderDao.findById(orderId);
					BeanUtils.copyProperties(iRealChange, OrderChangeLog);
					
					OrderChangeLog.setId(0);
					OrderChangeLog.setRecordId(orderId);
					OrderChangeLog.setLogCreatedBy(userId);
					OrderChangeLog.setLogCreatedOn(new Date());
					OrderChangeLog.setLogReason("Change Order Status");
					OrderChangeLog.setLogTypeId(utility.parseLong("1"));
					OrderChangeLogDao.save(OrderChangeLog); 
			if(statusId==3) {
				long resturantUserId=ResturantsService.getResturantUserIdByResturantId(order.getResturantId());
				
				CommonUsersDevices device=DevicesDao.findByUserId(resturantUserId);
				if(device!=null) {
					fireBaseService.sendNotification(device.getDeviceId(), "New Order", "Click to view / confirm order");
				}
				
				
			}		
					
			if(statusId==4) {
				order.setDeliveryTime(remmaining);
			}
			
			order.setOrderStatusId(statusId);
			order.setOrderStatusChangedBy(userId);
			order.setOrderStatusChangedOn(new Date());
			OrderDao.save(order);	
		}
		
		
	}

	public List<Map> getMostPopularProducts(int limit) {
		List<Map> list=new ArrayList<>();
		
		List<Long> mostOrderedProducts=new ArrayList<>();
		if(limit==0) {
			mostOrderedProducts=OrderDao.findAllPopularProductsByIsActiveAndIsDeleted(1,0);
		}else{
			mostOrderedProducts=OrderDao.findAllPopularProductsByIsActiveAndIsDeletedAndLimit(1,0,limit);
		}
				
		if(!mostOrderedProducts.isEmpty()) {
			
			mostOrderedProducts.stream().forEach(
					mostOrderedProduct->{
					CommonResturantsProducts Product=ResturantsProductsDao.findById(utility.parseLong(mostOrderedProduct));
					if(Product!=null) {
						Map Row=new HashMap<>();
						Row.put("id", Product.getId());
						
						Row.put("label",Product.getLabel());
						Row.put("description", Product.getDescription());
						Row.put("resturantId", Product.getResturantId());
						Row.put("salesTax", Product.getSalesTax());
						Row.put("salesTaxPercentage", Product.getSalesTaxPercentage());
						Row.put("grossAmount", Product.getGrossAmount());
						Row.put("netAmount", Product.getNetAmount());
						Row.put("discount", Product.getDiscount());
						Row.put("rate", Product.getRate());
						
						
						if(Product.getIsTimingEnable()==0) {
							Row.put("isTimingEnable", Product.getIsActive());
							Row.put("isTimingEnableLable", "No");
							Row.put("availabilityFrom", "");
							Row.put("availabilityTo", "");
							
						}else {
							Row.put("isTimingEnable", Product.getIsActive());
							Row.put("isTimingEnableLable", "Yes");
							
							
							
							Row.put("availabilityFrom", Product.getAvailabilityFrom().getTime());
							Row.put("availabilityFromDisplay", utility.getDisplayTimeFromDate((Product.getAvailabilityFrom())));
							
								
							Row.put("availabilityToDisplay", utility.getDisplayTimeFromDate((Product.getAvailabilityTo())));
							
							Row.put("availabilityTo", (Product.getAvailabilityTo().getTime()));
							Row.put("isClosed","false");
						//Row.put("isClosed", ResturantsProductsService.getIsClosedTimingsByProductId(Product.getId()));
						}
						
						
						
						Row.put("isAvailable", Product.getIsAvailable());
						if(Product.getIsActive()==0) {
							Row.put("isActive", Product.getIsActive());
							Row.put("isActiveLabel", "In-Active");
						}else {
							Row.put("isActive", Product.getIsActive());
							Row.put("isActiveLabel", "Active");
						}
						Row.put("productImgUrl", Product.getProductImgUrl());
						Row.put("rating", Product.getRating());
						Row.put("resturantCategoryId", Product.getResturantCategoryId());
						Row.put("resturantCategoryLabel", ResturantsCategoriesService.findLabelById(Product.getResturantCategoryId()));
						if(Product.getIsExtra()==0) {
							Row.put("isExtraLabel","No");
							Row.put("isExtra",Product.getIsExtra());
						}else {
							Row.put("isExtraLabel","Yes");
							Row.put("isExtra",Product.getIsExtra());
							 
							
						}
						
						list.add(ResturantsService.getBasicChargesDetailByResturant(Row,Product.getResturantId()));
					}
					
					
			
				}
			);	
				
				
				
		}
			
			
		
		
		
		return list;
	}
	
	
	public   String  getOrderRemainingDeliverTime(Date orderRemainingDate) { 
		
  	  	String remainingTime="";
		
  	  	Date currentdate=DateUtils.addMinutes(new Date(),0); 
		
		long diff =  orderRemainingDate.getTime() - currentdate.getTime();
		long remingTimeMins=0;
		if(diff>0) {
			remingTimeMins=(diff/1000)/60;
			
		       
	        remainingTime=remingTimeMins +" minutes.";
		}
		
		
		
        
        
        return remainingTime;
    }

	public void addCravingsComments(long recordId, String cravingsRemarks, long userId) {
		CustomerOrderRemarks OrderRemarks=new CustomerOrderRemarks();
		
			
//			CustomerOrder iRealChange=OrderDao.findById(recordId);
//					BeanUtils.copyProperties(iRealChange, OrderChangeLog);
//					
//					OrderChangeLog.setId(0);
//					OrderChangeLog.setRecordId(recordId);
//					OrderChangeLog.setLogCreatedBy(userId);
//					OrderChangeLog.setLogCreatedOn(new Date());
//					OrderChangeLog.setLogReason("Added Remarks");
//					OrderChangeLog.setLogTypeId(utility.parseLong("1"));
//					OrderChangeLogDao.save(OrderChangeLog);
					
			
		OrderRemarks.setOrderId(recordId);	
		OrderRemarks.setRemarksAddedOn(new Date());
		OrderRemarks.setRemarksAddedBy(userId);
		OrderRemarks.setRemarks(cravingsRemarks);
		OrderRemarksDao.save(OrderRemarks);	
		
		
	}
	
	
	
	/**
	 * Resturant Calls Starts
	 *************************************************************************************************************/
    	
	public List<Map> getAllActiveOrdersByResturantId(long resturantId,long orderStatusId) {
		List<Map> list=new ArrayList<>();
		List<CustomerOrder> orders=new ArrayList<>();
		
		if(orderStatusId==7) {
			List<Long> orderStatuslist=new ArrayList<>();
			orderStatuslist.add(7L);
			orderStatuslist.add(8L);
			orderStatuslist.add(9L);
			orderStatuslist.add(10L);
			 orders=OrderDao.findAllByOrderStatusIdInAndResturantIdAndCreatedOnBetween24hrsOrderByIdDesc(orderStatuslist,resturantId);
		}else {
			 orders=OrderDao.findAllByOrderStatusIdAndResturantIdOrderByIdDesc(orderStatusId,resturantId);
		}
		if(!orders.isEmpty()) {
			orders.stream().forEach(
				order->{
					
						long orderId=order.getId();
						Map Row=new HashMap<>();
						Row.put("id", order.getId());
						Row.put("resturantId", order.getResturantId());
						Row.put("resturantLabel",ResturantsService.findLabelById(order.getResturantId()));
						Row.put("resturantBanner",ResturantsService.findBannerImgById(order.getResturantId()));
						Row.put("orderNumber", order.getOrderNumber());
						Row.put("totalAmount", order.getTotalAmount());
						Row.put("totalGst", order.getTotalGst());
						Row.put("orderType", order.getOrderType());
						Row.put("createdOn", order.getCreatedOn());
						Row.put("orderStatusId", order.getOrderStatusId());
						Row.put("orderStatusLabel", OrderStatusService.findLabelByIdAndRequirementType(order.getOrderStatusId(),1));
						Row.put("subtotal", order.getSubtotal());
						Row.put("discount", order.getDiscount());
						Row.put("deliveryFee", order.getDeliveryFee());
						Row.put("totalGstPercentage", order.getTotalGstPercentage());
						Row.put("deliveryTime", order.getDeliveryTime());
						Row.put("serviceFee", order.getServiceFee());
						Row.put("instructions", order.getInstructions());
						Row.put("customerName", UsersService.getUserName(order.getUserId()));
						Row.put("customerId", order.getUserId());
						Row.put("customerPreviousOrders",getAllPreviousOrders(order.getUserId()));
						if(utility.parseInt(order.getIsHelpRequired())==1) {
							Row.put("isRequiredHelpLabel","Yes");
						}else {
							Row.put("isRequiredHelpLabel","No");
						}
						CustomerOrderAddress Address=OrderAddressDao.findByOrderId(orderId);
						if(Address!=null) {	
							Row.put("cityName", Address.getCityName());
							Row.put("completeAddress", Address.getCompleteAddress());
							Row.put("area", Address.getArea());
							Row.put("houseNo", Address.getHouseNo());
							Row.put("streetNo", Address.getStreetNo());
							Row.put("lat", Address.getLat());
							Row.put("lng",Address.getLng());
							
						}else {
							Row.put("cityName", "");
							Row.put("completeAddress", "");
							Row.put("area", "");
							Row.put("houseNo", "");
							Row.put("streetNo","");
							Row.put("lat", "");
							Row.put("lng","");
							
						}
						
						List<Map> orderProductsList=new ArrayList<>();
						List<CustomerOrderProducts> OrderProducts=OrderProductsDao.findAllByOrderId(orderId);
						if(!OrderProducts.isEmpty()) {
							OrderProducts.stream().forEach(
								OrderProduct->{
									Map Rowproduct=new HashMap<>();
									Rowproduct.put("price", OrderProduct.getPrice());
									Rowproduct.put("productId", OrderProduct.getProductId());
									Rowproduct.put("quantity", OrderProduct.getQuantity());
									Rowproduct.put("productLabel",ResturantsProductsService.findLabelById(OrderProduct.getProductId()));
									
									List<Map> requiredAddOnsList=new ArrayList<>();
									List<CustomerOrderProductsRequiredAddOn> requiredAddOns=OrderProductsRequiredAddOnDao.findAllByOrderProductId(OrderProduct.getId());
									if(!requiredAddOns.isEmpty()) {
										requiredAddOns.stream().forEach(
												requiredAddOn->{
													Map RowproductRequiredAddon=new HashMap<>();
													RowproductRequiredAddon.put("price", requiredAddOn.getPrice());
													RowproductRequiredAddon.put("id", requiredAddOn.getProductId());
													RowproductRequiredAddon.put("label",ResturantsProductsService.findLabelById(requiredAddOn.getProductId()));
													requiredAddOnsList.add(RowproductRequiredAddon);
													
													
												}
										);
									}
									Rowproduct.put("requiredAddOn", requiredAddOnsList);
									
									List<Map> optionalAddOnsList=new ArrayList<>();
									List<CustomerOrderProductsOptionalAddOn> optionalAddOns=OrderProductsOptionalAddOnDao.findAllByOrderProductId(OrderProduct.getId());
									if(!optionalAddOns.isEmpty()) {
										optionalAddOns.stream().forEach(
												optionalAddOn->{
													Map RowproductOptionalAddon=new HashMap<>();
													RowproductOptionalAddon.put("price", optionalAddOn.getPrice());
													RowproductOptionalAddon.put("id", optionalAddOn.getProductId());
													RowproductOptionalAddon.put("label",ResturantsProductsService.findLabelById(optionalAddOn.getProductId()));
													optionalAddOnsList.add(RowproductOptionalAddon);
													
													
												}
										);
									}
									Rowproduct.put("optionalAddOn", optionalAddOnsList);
									
									orderProductsList.add(Rowproduct);
										
								}
							);
						}
						
						Row.put("products",orderProductsList);
						
						list.add(Row);
				}
			
			);
		}
		
		
		
		return list;
	}
	
	
	
	public void changeOrderStatusByResturant(long orderId,long statusId,long userId) {
		
		
		CustomerOrder order=OrderDao.findById(orderId);
		if(order!=null) {
			CustomerOrderChangeLog OrderChangeLog=new CustomerOrderChangeLog();
			CustomerOrder iRealChange=OrderDao.findById(orderId);
					BeanUtils.copyProperties(iRealChange, OrderChangeLog);
					
					OrderChangeLog.setId(0);
					OrderChangeLog.setRecordId(orderId);
					OrderChangeLog.setLogCreatedBy(userId);
					OrderChangeLog.setLogCreatedOn(new Date());
					OrderChangeLog.setLogReason("Change Order Status By Resturant");
					OrderChangeLog.setLogTypeId(utility.parseLong("1"));
					OrderChangeLogDao.save(OrderChangeLog);
					
			
			
			order.setOrderStatusId(statusId);
			order.setOrderStatusChangedBy(userId);
			order.setOrderStatusChangedOn(new Date());
			OrderDao.save(order);	
		}
		
		
	}
	
	@Transactional
	public void getHelp(long orderId) {
		OrderDao.UpdateIsHelpRequiredById(orderId);	
	}
	
	/** Resturant Calls Ends
	 ***********************************************************************************************************/
	
	
	public void SaveAdmin(OrderPOJO order,long userId){
		
		
		OrderPOJO orderr=order;
		if(orderr!=null)
		{ 
			long resturantId=orderr.getResturantId();
			double totalAmount=orderr.getTotalAmount(); 
			double totalGst=orderr.getTotalGst();
			String orderType=orderr.getOrderType();
			
			double subtotal=orderr.getSubtotal();
			double discount=orderr.getDiscount();
			double deliveryFee=orderr.getDeliveryFee();
			int deliveryTime=orderr.getDeliveryTime();
			double totalGstPercentage=orderr.getTotalGstPercentage();
			
			double serviceFee=orderr.getServiceFee();
			long voucherId=order.getVoucherId();
			String instructions=orderr.getInstructions();
			long customerId=orderr.getUserId();
			int isAdmin=utility.parseInt(orderr.getIsAdmin());
			CustomerOrder Order=new CustomerOrder(); 
			Order.setResturantId(resturantId);
			Order.setOrderType(orderType);
			
			String orderNumber=utility.getDateWithOutSlashesId()+"-"+utility.createRandomCode(4);
			Order.setOrderNumber("CRA-"+orderNumber);
			Order.setTotalAmount(totalAmount);
			Order.setTotalGst(totalGst);
			Order.setDeliveryFee(deliveryFee);
			Order.setDiscount(discount);
			Order.setSubtotal(subtotal);
			Order.setUserId(customerId);
			Order.setCreatedOn(new Date());
			Order.setOrderStatusId(1);
			Order.setTotalGstPercentage(totalGstPercentage);
			Order.setDeliveryTime(deliveryTime);
			Order.setServiceFee(serviceFee);
			Order.setInstructions(instructions);
			if(isAdmin==1) {
				Order.setIsAdmin(isAdmin);
				Order.setOrderCreatedByAdminId(userId);
				
			}
			
			if(voucherId!=0) {
				
				Order.setVoucherId(orderr.getVoucherId());
				VouchersService.voucherRedeemed(orderr.getVoucherId());
				
			}
			
			
			OrderDao.save(Order);
			
			OrderAddressPOJO address=orderr.getAddress();
			if(address!=null) {
				CustomerOrderAddress Address=new CustomerOrderAddress();
				Address.setCityName(address.getCityName());
				Address.setCompleteAddress(address.getCompleteAddress());
				Address.setLat(address.getLat());
				Address.setLng(address.getLng());
				Address.setOrderId(Order.getId());
				Address.setArea(address.getArea());
				Address.setHouseNo(address.getHouseNo());
				Address.setStreetNo(address.getStreetNo());
				OrderAddressDao.save(Address);
				
			}
			
			List<OrderProductsPOJO> Products=orderr.getProducts();
			if(!Products.isEmpty()) {
				Products.stream().forEach(
					product->{
						
						OrderProductsPOJO singleProduct=product;
						if(singleProduct!=null) {
							double price=singleProduct.getPrice();
							int quantity=singleProduct.getQuantity();
							long productId=singleProduct.getProductId();
							
							
							CustomerOrderProducts OrderProducts=new CustomerOrderProducts();
							OrderProducts.setPrice(price);
							OrderProducts.setProductId(productId);
							OrderProducts.setQuantity(quantity);
							OrderProducts.setOrderId(Order.getId());
							OrderProductsDao.save(OrderProducts);
							
							
							List<OrderProductsRequiredAddOnPOJO> requiredAddOns= singleProduct.getRequiredAddOn();
							if(!requiredAddOns.isEmpty()) {
								requiredAddOns.stream().forEach(
										requiredAddOn->{
											
											OrderProductsRequiredAddOnPOJO addOn=requiredAddOn;
											if(addOn!=null) {
												long addOnproductId=addOn.getId();
												double addOnPrice=addOn.getPrice();
												
												OrderProductsRequiredAddOnDao.save(new CustomerOrderProductsRequiredAddOn(OrderProducts.getId(),addOnproductId,addOnPrice));
											}
									}
								);
							}	
							
							List<OrderProductsOptionalAddOnPOJO> optionalAddOns= singleProduct.getOptionalAddOn();
							if(!optionalAddOns.isEmpty()) {
								optionalAddOns.stream().forEach(
										optionalAddOn->{
											OrderProductsOptionalAddOnPOJO addOn=optionalAddOn;
											if(addOn!=null) {
												long addOnproductId=addOn.getId();
												double addOnPrice=addOn.getPrice();
												OrderProductsOptionalAddOnDao.save(new CustomerOrderProductsOptionalAddOn(OrderProducts.getId(),addOnproductId,addOnPrice));
												
											}
									}
								);
							}
						}
						
						
						
					}
				);
			}
			
		}
		
	}
	
	
}
