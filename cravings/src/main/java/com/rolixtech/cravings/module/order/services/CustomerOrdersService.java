package com.rolixtech.cravings.module.order.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Service;

import com.rolixtech.cravings.CravingsApplication;
import com.rolixtech.cravings.module.order.POJO.OrderAddressPOJO;
import com.rolixtech.cravings.module.order.POJO.OrderPOJO;
import com.rolixtech.cravings.module.order.POJO.OrderProductsOptionalAddOnPOJO;
import com.rolixtech.cravings.module.order.POJO.OrderProductsPOJO;
import com.rolixtech.cravings.module.order.POJO.OrderProductsRequiredAddOnPOJO;
import com.rolixtech.cravings.module.order.dao.CustomerOrderAddressDao;
import com.rolixtech.cravings.module.order.dao.CustomerOrderDao;
import com.rolixtech.cravings.module.order.dao.CustomerOrderProductsDao;
import com.rolixtech.cravings.module.order.dao.CustomerOrderProductsOptionalAddOnDao;
import com.rolixtech.cravings.module.order.dao.CustomerOrderProductsRequiredAddOnDao;
import com.rolixtech.cravings.module.order.model.CustomerOrder;
import com.rolixtech.cravings.module.order.model.CustomerOrderAddress;
import com.rolixtech.cravings.module.order.model.CustomerOrderProducts;
import com.rolixtech.cravings.module.order.model.CustomerOrderProductsOptionalAddOn;
import com.rolixtech.cravings.module.order.model.CustomerOrderProductsRequiredAddOn;
import com.rolixtech.cravings.module.resturant.dao.CommonCategoriesDao;
import com.rolixtech.cravings.module.resturant.model.CommonCategories;
import com.rolixtech.cravings.module.resturant.model.CommonResturants;
import com.rolixtech.cravings.module.resturant.model.CommonResturantsCategories;
import com.rolixtech.cravings.module.resturant.services.CommonResturantsProductsService;
import com.rolixtech.cravings.module.resturant.services.CommonResturantsService;

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
	
	
	public String findLabelById(long categoryId) {
		String label="";
//		CommonCategories Categories=CategoriesDao.findById(categoryId);
//		if(Categories!=null) {
//			label=Categories.getLabel();
//		}
		return label;
	}
	
	public void Save(OrderPOJO order){
		
		OrderPOJO orderr=order;
		if(orderr!=null)
		{ 
			long resturantId=orderr.getResturantId();
			double totalAmount=orderr.getTotalAmount();
			double totalGst=orderr.getTotalGst();
			String orderType=orderr.getOrderType();
			long userId=orderr.getUserId();
			
			CustomerOrder Order=new CustomerOrder();
			Order.setResturantId(resturantId);
			Order.setOrderType(orderType);
			Order.setOrderNumber(orderType);
			Order.setTotalAmount(totalAmount);
			Order.setTotalGst(totalGst);
			Order.setUserId(userId);
			Order.setCreatedOn(new Date());
			OrderDao.save(Order);
			
			OrderAddressPOJO address=orderr.getAddress();
			if(address!=null) {
				CustomerOrderAddress Address=new CustomerOrderAddress();
				Address.setCityName(address.getCityName());
				Address.setCompleteAddress(address.getCompleteAddress());
				Address.setLat(address.getLat());
				Address.setLng(address.getLng());
				Address.setOrderId(Order.getId());
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
						Row.put("orderType", order.getOrderType());
						Row.put("createdOn", order.getCreatedOn());
						Row.put("orderStatusId", order.getOrderStatusId());
						Row.put("orderStatusLabel", OrderStatusService.findLabelById(order.getOrderStatusId()));
						
						
						CustomerOrderAddress Address=OrderAddressDao.findByOrderId(orderId);
						if(Address!=null) {	
							Row.put("cityName", Address.getCityName());
							Row.put("completeAddress", Address.getCompleteAddress());
							
						}else {
							Row.put("cityName", "");
							Row.put("completeAddress", "");
							
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
									
									
									List<Map> requiredAddOnsList=new ArrayList<>();
									List<CustomerOrderProductsRequiredAddOn> requiredAddOns=OrderProductsRequiredAddOnDao.findAllByOrderProductId(OrderProduct.getId());
									if(!requiredAddOns.isEmpty()) {
										requiredAddOns.stream().forEach(
												requiredAddOn->{
													Map RowproductRequiredAddon=new HashMap<>();
													RowproductRequiredAddon.put("price", requiredAddOn.getPrice());
													RowproductRequiredAddon.put("id", requiredAddOn.getProductId());
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
		List<CustomerOrder> orders=OrderDao.findAllByUserId(userId);
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
						Row.put("orderStatusLabel", OrderStatusService.findLabelById(order.getOrderStatusId()));
						
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
		List<CustomerOrder> orders=OrderDao.findAllByUserIdAndOrderStatusId(userId,1l);
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
						Row.put("orderStatusLabel", OrderStatusService.findLabelById(order.getOrderStatusId()));
						
						
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
		CustomerOrder order=OrderDao.findByUserIdAndOrderStatusId(orderIdd,0);
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
			Row.put("orderStatusLabel", OrderStatusService.findLabelById(order.getOrderStatusId()));
			
			
			CustomerOrderAddress Address=OrderAddressDao.findByOrderId(orderId);
			if(Address!=null) {	
				Row.put("cityName", Address.getCityName());
				Row.put("completeAddress", Address.getCompleteAddress());
				
			}else {
				Row.put("cityName", "");
				Row.put("completeAddress", "");
				
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
						Row.put("orderStatusLabel", OrderStatusService.findLabelById(order.getOrderStatusId()));
						CustomerOrderAddress Address=OrderAddressDao.findByOrderId(orderId);
						if(Address!=null) {	
							Row.put("cityName", Address.getCityName());
							Row.put("completeAddress", Address.getCompleteAddress());
							
						}else {
							Row.put("cityName", "");
							Row.put("completeAddress", "");
							
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
									
									
									List<Map> requiredAddOnsList=new ArrayList<>();
									List<CustomerOrderProductsRequiredAddOn> requiredAddOns=OrderProductsRequiredAddOnDao.findAllByOrderProductId(OrderProduct.getId());
									if(!requiredAddOns.isEmpty()) {
										requiredAddOns.stream().forEach(
												requiredAddOn->{
													Map RowproductRequiredAddon=new HashMap<>();
													RowproductRequiredAddon.put("price", requiredAddOn.getPrice());
													RowproductRequiredAddon.put("id", requiredAddOn.getProductId());
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

	public List<Map> getAllActiveOrders() {
		List<Map> list=new ArrayList<>();
		
		List<CustomerOrder> orders=OrderDao.findAllByOrderStatusIdGreaterThan(0l);
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
						Row.put("orderStatusLabel", OrderStatusService.findLabelById(order.getOrderStatusId()));
						CustomerOrderAddress Address=OrderAddressDao.findByOrderId(orderId);
						if(Address!=null) {	
							Row.put("cityName", Address.getCityName());
							Row.put("completeAddress", Address.getCompleteAddress());
							
						}else {
							Row.put("cityName", "");
							Row.put("completeAddress", "");
							
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
									
									
									List<Map> requiredAddOnsList=new ArrayList<>();
									List<CustomerOrderProductsRequiredAddOn> requiredAddOns=OrderProductsRequiredAddOnDao.findAllByOrderProductId(OrderProduct.getId());
									if(!requiredAddOns.isEmpty()) {
										requiredAddOns.stream().forEach(
												requiredAddOn->{
													Map RowproductRequiredAddon=new HashMap<>();
													RowproductRequiredAddon.put("price", requiredAddOn.getPrice());
													RowproductRequiredAddon.put("id", requiredAddOn.getProductId());
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
		
		CustomerOrder order=OrderDao.findAllByIdAndOrderStatusIdGreaterThan(orderId,0);
		if(order!=null) {
			
					
					
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
				Row.put("orderStatusLabel", OrderStatusService.findLabelById(order.getOrderStatusId()));
				CustomerOrderAddress Address=OrderAddressDao.findByOrderId(orderId);
				if(Address!=null) {	
					Row.put("cityName", Address.getCityName());
					Row.put("completeAddress", Address.getCompleteAddress());
					
				}else {
					Row.put("cityName", "");
					Row.put("completeAddress", "");
					
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
							
							
							List<Map> requiredAddOnsList=new ArrayList<>();
							List<CustomerOrderProductsRequiredAddOn> requiredAddOns=OrderProductsRequiredAddOnDao.findAllByOrderProductId(OrderProduct.getId());
							if(!requiredAddOns.isEmpty()) {
								requiredAddOns.stream().forEach(
										requiredAddOn->{
											Map RowproductRequiredAddon=new HashMap<>();
											RowproductRequiredAddon.put("price", requiredAddOn.getPrice());
											RowproductRequiredAddon.put("id", requiredAddOn.getProductId());
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
	

}
